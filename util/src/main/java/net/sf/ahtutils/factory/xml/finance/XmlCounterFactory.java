package net.sf.ahtutils.factory.xml.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.finance.Counter;

public class XmlCounterFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlCounterFactory.class);
	
	public static Counter create(String code, int counter)
	{
		Counter xml = new Counter();
		xml.setCode(code);
		xml.setCounter(counter);
		return xml;
	}
}