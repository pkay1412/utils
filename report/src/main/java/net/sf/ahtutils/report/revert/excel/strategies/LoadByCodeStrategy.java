package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.Hashtable;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.report.revert.excel.ImportStrategy;
import net.sf.ahtutils.util.reflection.ReflectionsUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadByCodeStrategy implements ImportStrategy {
	
	final static Logger logger = LoggerFactory.getLogger(LoadByCodeStrategy.class);
	
	private UtilsFacade facade;
	
	private Hashtable<String, Object> tempPropertyStore;
	public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

	@Override
	public Object handleObject(Object object, String parameterClass, String property) {
		
		String code = (String) object;
		Class  lutClass      = null;
    	Object lookupEntity  = null;
    	
		// If there is no value found, return null or set the code to unknown
		if (code == null && !tempPropertyStore.containsKey("createEntityForUnknown"))
		{
			return null;
		}
		else if (code == null && tempPropertyStore.containsKey("createEntityForUnknown"))
		{
			code = "unknown";
		}
		
		logger.debug("Searching for Entity with Code " +code);
		// Try to find the entity with given code in database
    	try {
    		lutClass = (Class) Class.forName(parameterClass);
	    	lookupEntity = facade.fByCode(lutClass, code);
		} catch (Exception e) {
			logger.error("An error occured while trying to load entity with code " +code +" from database! " +e.getMessage());
			logger.info("Creating a new one!");
			
			// If the entity is not found, facade is null or some other error occurs, create a new object
			try {
				lookupEntity = lutClass.newInstance();
			} catch (InstantiationException ex) {
				logger.error(ex.getMessage());
			} catch (IllegalAccessException ex) {
				logger.error(ex.getMessage());
			}
			
			// Since this is a code based strategy, invoke the setCode method with the given code
			Object[] parameters = new Object[1];
			parameters[0] = code;
			try {
				ReflectionsUtil.simpleInvokeMethod("setCode", parameters, lutClass, lookupEntity);
			} catch (Exception ex) {
				logger.error("Could not invoke setCode method!");
				logger.error(ex.getMessage());
			}
		}
		
		// Return the result
    	return lookupEntity;
	}

	@Override
	public void setFacade(UtilsFacade facade) {
		this.facade = facade;
	}

}
