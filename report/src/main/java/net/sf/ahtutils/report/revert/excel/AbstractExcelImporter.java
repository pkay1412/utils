package net.sf.ahtutils.report.revert.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.util.reflection.ReflectionsUtil;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractExcelImporter <C extends Serializable, I extends ImportStrategy> {

	final static Logger logger = LoggerFactory.getLogger(AbstractExcelImporter.class);
	
	protected File                       excelFile;
	protected XSSFWorkbook               workbook;
	protected Sheet                      activeSheet;
	public  UtilsFacade                facade;
	protected Map<String, Class>   handler;
	protected short                      primaryKey;
	protected Hashtable<String, C>       entities          = new Hashtable<String, C>();
	protected Hashtable<String, Object>  tempPropertyStore = new Hashtable<String, Object>();
	protected Boolean                    hasPrimaryKey     = false;
	
	public  Integer                    enititesSaved = 0;	
	public Integer getEnititesSaved() {return enititesSaved;}
	public void setEnititesSaved(Integer enititesSaved) {this.enititesSaved = enititesSaved;}

	public AbstractExcelImporter(String filename) throws IOException
	{
		// Prepare file to be read
		this.excelFile      = new File(filename);
		FileInputStream fis = new FileInputStream(excelFile);
		
		// Read Excel workbook from given file(name)
		this.workbook       = new XSSFWorkbook(fis);
	}
	
	public void setFacade(UtilsFacade facade)
	{
		this.facade = facade;
	}
	
	public void setPrimaryKey(Integer columnNumber)
	{
		this.primaryKey    = columnNumber.shortValue();
		this.hasPrimaryKey = true;
	}
	
	public void setHandler(Map<String, Class> strategies)
	{
		this.handler = strategies;
	}
	
	public void selectSheetByName(String name)
	{
		// Select a sheet from the Excel workbook by name
		activeSheet         = workbook.getSheet(name);
	}
	
	public void selectFirstSheet()
	{
		// Select a sheet from the Excel workbook by name
		activeSheet         = workbook.getSheetAt(0);
	}
	
	public void debugHeader()
	{
		debugRow(0);
	}
	
	public void debugFirstRow()
	{
		debugRow(1);
	}
	
	public void debugRow(Integer rowIndex)
	{
		// Using a StringBuffer to create one line with all column titles
		StringBuffer sb = new StringBuffer();
		sb.append("Debugging Row " +rowIndex +" ... ");
		
		// Selecting first row since this should be the place where the column titles should be placed 
		Row firstRow    = activeSheet.getRow(rowIndex);
		
		// Iterating through all cells in first row
		for (short i = firstRow.getFirstCellNum() ; i < firstRow.getLastCellNum() ; i++)
		{
			Cell cell = firstRow.getCell(i);
			// Get the Cell Value as Object
			Object object = getCellValue(cell);
			
			// Get a String representation of the value
			String cellValue = getStringValue(object);
			
			// Add the content of the cell to StringBuffer
			sb.append("Column " +i +": '" +cellValue + "' ");
		}
		
		// Show the StringBuffer content in logging
		logger.info(sb.toString());
	}
	
		
	public Object getCellValue(Cell cell)
	{
		Object value = new Object();
		
		// Prevent a NullPointerException
		if (cell != null)
		{
			if (cell.getHyperlink()!=null)
						{
							FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
							Hyperlink link = cell.getHyperlink();
							String address = link.getAddress();
							if(logger.isTraceEnabled()){logger.trace("Found a Hyperlink to " +cell.getHyperlink().getAddress() +" in cell " +cell.getRowIndex() +"," +cell.getColumnIndex());}
							cell = evaluator.evaluateInCell(cell);
						}
			// Depending on the cell type, the value is read using Apache POI methods
			switch (cell.getCellType()) {
			
				// String are easy to handle
				case Cell.CELL_TYPE_STRING : 
					logger.trace("Found string " +cell.getStringCellValue());
					value = cell.getStringCellValue();
					break;
					
				// Since date formatted cells are also of the numeric type, this needs to be processed
				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell))
					{
						Date date = cell.getDateCellValue();
						DateFormat df = SimpleDateFormat.getDateInstance();
						logger.trace("Found date " +df.format(date));
						value = date;
					}
					else
					{
						logger.trace("Found general number " +cell.getNumericCellValue());
						value = cell.getNumericCellValue();
					}
					break;
			}
		}
		else
		{
			logger.trace("Found cell with NULL value");
		}
		return value;
	}
	
	public String getStringValue(Object object)
	{
		// Since a simple "toString()" is not sufficient because the value can also be null, 
		// a more complex method has been chosen here to get a String representation of a cell value 
		String value = "";
		if (object.getClass().equals(String.class))
		{
			value = (String) object;
		}
		else if (object.getClass().equals(Date.class))
		{
			Date date = (Date) object;
			DateFormat df = SimpleDateFormat.getDateInstance();
			value = df.format(date);
		}
		else if (object.getClass().equals(Double.class))
		{
			Double doubleValue = (Double) object;
			value = doubleValue.toString();
		}
		else
		{
			value = "CELL IS NULL";
		}
		return value;
	}
	
	public ArrayList<C> createEntitiesFromData(Map<Short, String> associationTable, Boolean skipTitle, Class<C> entityObject) throws Exception
	{
		// Create a list to hold the Entity classes to be created
		ArrayList<C> importedEntities = new ArrayList<C>();
		
		// Define the rows to begin with and to end with, whether with or without first row
		Integer end   = activeSheet.getLastRowNum();
		Integer start = activeSheet.getFirstRowNum();
		if (skipTitle) {start++;}
		
		// Iterate through all given rows
		for (int i = start; i < end+1; i++)
		{
			// Get the next row
			Row row = activeSheet.getRow(i);
			
			// See if there is already an instance created for this key, otherwise create a new one
			String entityKey = getStringValue(getCellValue(row.getCell(primaryKey)));
			C entity = entityObject.newInstance();
			if (hasPrimaryKey)
			{
				if ( this.entities.containsKey(entityKey))
				{
					entity = this.entities.get(entityKey);
				}
			}
			
			// Iterate through the columns and assign data as given in the association table
			for (short j = row.getFirstCellNum() ; j < row.getLastCellNum() ; j++)
			{
				Cell cell = row.getCell(j);
				
				// Get the Cell Value as Object
				Object object = getCellValue(cell);
				
				// Read the name of the property that should be filled with the data from this column
				String propertyName = associationTable.get(j);
			
			    // Assign the data to the entity using the setter
				logger.trace("Cell " +row.getRowNum() +"," +j);
				if (propertyName!=null && !object.getClass().getCanonicalName().endsWith("java.lang.Object"))
				{
					String property = propertyName;
					if(logger.isTraceEnabled()){logger.trace("Setting " +property + " to " +object.toString() +" type: " +object.getClass().getCanonicalName() +")");}
					tempPropertyStore.put(property, object.toString());
					invokeSetter(property,
							      new Object[] { object },
							      entity.getClass(),
							      entity);
				}
			}
			//facade.save(entity);
			importedEntities.add(entity);
			if (hasPrimaryKey)
			{
				entities.put(entityKey, entity);
			}
		}
		return importedEntities;
		
	}
	
	
		
	 protected void invokeSetter(String   property, 
			 							Object[] parameters,
			 							Class    targetClass,
			 							Object   target)        throws Exception
	 {
		String methodName = "set" +property;
	 	logger.trace("Invoking " +methodName);
	 	
	 	// Now find the correct method
	 	Method[] methods = targetClass.getMethods();
	 	Class parameter  = null;
	 	Method m         = null;
	 	for (Method method : methods)
	 	{
	 		if (method.getName().equals(methodName))
	 		{
	 			parameter = method.getParameterTypes()[0];
	 			m = method;
	 		}
	 	}
	
        if (Modifier.isPrivate(m.getModifiers()))
        {
            m.setAccessible(true);
        }
        
        // Determine parameter type of setter
        // Type t = m.getGenericParameterTypes()[0];
        // String parameterClass = t.getTypeName();
        
        String parameterClass = parameter.getCanonicalName();
     //   logger.info(parameterClass);
     //   logger.info(parameters[0].getClass().getCanonicalName());
        
        // Lets see if the setter is accepting a data type that is available in Excel (String, Double, Date)
        // Otherwise assume that it is used with a lookup table
        
        if (ReflectionsUtil.hasMethod(target, methodName))
        {
        	if (!(parameterClass.equals("java.lang.Double") || parameterClass.equals("double") || parameterClass.equals("long") || parameterClass.equals("java.util.Date") || parameterClass.equals("java.lang.String")))
            {
            	logger.trace("Loading import strategy for " +parameterClass +": " +handler.get(parameterClass) +".");
            	// Instantiate new strategy to handle import
            	ImportStrategy strategy = (ImportStrategy) handler.get(parameterClass).newInstance();
            	
            	// Pass database connection and current set of temporary properties
				
            	strategy.setFacade(facade);
            	strategy.setTempPropertyStore(tempPropertyStore);
            	
            	// Process import step
				Object value  = strategy.handleObject(parameters[0], parameterClass, property);
            	parameters[0] =  value;
            	
            	// Sync new temporary properties if any added
            	tempPropertyStore = strategy.getTempPropertyStore();
				
				// Add the current property/value pair, can be useful when inspecting IDs (overwritten for new lines for examples)
				if(logger.isTraceEnabled()){logger.trace("Set " +property + " to " + value.toString());}
				tempPropertyStore.put(property, value);
            }
			
			// Needed to correct the Class of the general number
            if (parameterClass.equals("long"))
            {
            	Number number = (Number) parameters[0];
            	parameters[0] = number.longValue();
            }
			
			// This is important if the String is a Number, Excel will format the cell to be a "general number"
			if (parameterClass.equals("java.lang.String"))
            {
            	parameters[0] = parameters[0] +"";
            }
			
			// Validate the result
			// Get the list of validators from context
			HashMap<String, String> validators = (HashMap<String, String>) tempPropertyStore.get("validators");
			HashMap<String, ArrayList<String>> validationFailed = (HashMap) tempPropertyStore.get("validationFailed");
			
			if (validators.containsKey(property))
			{
				String entityType = validators.get(property);
				logger.info("Validator found for " +property +" class to be searched: " +entityType);
				if(!validate(parameters[0].toString(), property, entityType))
				{
					ArrayList<String> failed = new ArrayList<String>();
					if (validationFailed.containsKey(property))
					{
						validationFailed.get(property).add(parameters[0].toString());
					}
					else
					{
						failed.add(parameters[0].toString());
						validationFailed.put(property, failed);
					}
				}
			}
			
			// Now invoke the method with the parameter from the Excel sheet
            m.invoke(target, parameters);
        }
        else
        {
        	logger.trace("Entity does not have the method " +methodName +". Initiating special treatment.");
        	
        }
        
	 }
	 
	 public Boolean validate(String code, String property, String entityType)
	{
		// Try to get an object from database
		Object o = null;
		try {
			Class entityClass = Class.forName(entityType);
			o = facade.fByCode(entityClass, code);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		if (!(o == null))
		{
			return false;
		}
		return true;
		
	}
	 
	public Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}
}
