package net.sf.ahtutils.xml.srs;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.xml.system.AbstractXmlSystemTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlSrsTest <T extends Object> extends AbstractXmlTest<T> 
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSystemTest.class);
	
	public AbstractXmlSrsTest(Class<T> cXml)
	{
		super(cXml,"srs");
	}
}