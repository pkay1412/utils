package net.sf.ahtutils.interfaces.controller.report;

import net.sf.ahtutils.xml.report.XlsWorkbook;

public interface UtilsXlsDefinitionResolver
{		
	public XlsWorkbook definition(String code);
}