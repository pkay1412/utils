package net.sf.ahtutils.factory.xml.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Labels;

public class XmlLabelsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlLabelsFactory.class);
		
	public static Labels build()
	{
		Labels xml = new Labels();
		return xml;
		
	}
}