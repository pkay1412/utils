package net.sf.ahtutils.xml.dbseed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractXmlTest;

public abstract class AbstractXmlDbseedTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlDbseedTest.class);
	
	public AbstractXmlDbseedTest(Class<T> cXml)
	{
		super(cXml,"dbseed");
	}
}