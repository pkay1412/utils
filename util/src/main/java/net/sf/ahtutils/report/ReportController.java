package net.sf.ahtutils.report;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

public class ReportController extends AbstractReportControl
{
	static Log logger = LogFactory.getLog(ReportController.class);
	
	public ReportController(Configuration config)
	{
		super(config);
	}
	
	public void addParameter(String key, Object value)
	{
		mapReportParameter.put(key, value);
	}
	
	public void generateReport(String reportId, Output output, Direction dir)
	{
		rId = reportId;
		Document doc=null;
		logger.debug("Starting Report Generation");
		compileSubReports(output,dir);
		fillParameterMap(doc);
	}
		
	public void fillParameterMap(Document doc)
	{	
		mapReportParameter.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, doc);
		mapReportParameter.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
		mapReportParameter.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.00");
		mapReportParameter.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.ENGLISH);
		mapReportParameter.put(JRParameter.REPORT_LOCALE, Locale.US);
	}
	
	@SuppressWarnings("deprecation")
	public static void export(JasperPrint jsPrint, String name)
	{
		try
		{	
			logger.info("Exporting report to PDF");
			JasperExportManager.exportReportToPdfFile(jsPrint, "dist/"+name+".pdf");
			
			logger.info("Exporting report to Excel Sheet");
			OutputStream os = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jsPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, os);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.exportReport();
			
			try
			{
				OutputStream osProcessed = ReportUtil.RemoveEmptyCells(os);
				
				OutputStream outputStreamProcessed = new FileOutputStream ("dist/"+name+".xls"); 
				((ByteArrayOutputStream)osProcessed).writeTo(outputStreamProcessed);
			}
			catch (FileNotFoundException e) {logger.error(e);}
			catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		catch (JRException e) {logger.error("Error in JasperReport creation: " +e.getMessage());}
	}
}