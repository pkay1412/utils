package net.sf.ahtutils.xml.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractXmlTest;

public abstract class AbstractXmlSystemTest <T extends Object> extends AbstractXmlTest<T> 
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSystemTest.class);
	
	public AbstractXmlSystemTest(Class<T> cXml)
	{
		super(cXml,"system");
	}
}