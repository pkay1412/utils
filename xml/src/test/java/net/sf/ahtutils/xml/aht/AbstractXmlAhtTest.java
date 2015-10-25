package net.sf.ahtutils.xml.aht;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.xml.system.AbstractXmlSystemTest;

public abstract class AbstractXmlAhtTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSystemTest.class);
	
	public AbstractXmlAhtTest(Class<T> cXml)
	{
		super(cXml,"aht");
	}
}