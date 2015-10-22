package net.sf.ahtutils.interfaces.controller.report;

import java.io.InputStream;

public interface UtilsPdfReport
{		
	public InputStream pdfStream() throws Exception;
	public String pdfFileName();
}