package net.sf.ahtutils.interfaces.controller.report;

import java.io.InputStream;

public interface UtilsXlsReport extends UtilsReport
{		
	public static String mimeType = "application/msexcel";
	
	public InputStream xlsStream() throws Exception;
	public String xlsFileName();
}