package net.sf.ahtutils.interfaces.controller.report;

import java.io.InputStream;

public interface UtilsPdfReport extends UtilsReport
{	
	public static String mimeType = "application/pdf";
	
	public InputStream pdfStream() throws Exception;
	public String pdfFileName();
}