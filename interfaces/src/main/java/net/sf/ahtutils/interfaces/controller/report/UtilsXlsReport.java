package net.sf.ahtutils.interfaces.controller.report;

import java.io.InputStream;

public interface UtilsXlsReport
{		
	public InputStream xlsStream() throws Exception;
	public String xlsFileName();
}