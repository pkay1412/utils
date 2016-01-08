package net.sf.ahtutils.xml.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractXmlTest;

public abstract class AbstractXmlFinanceTest <T extends Object> extends AbstractXmlTest<T> 
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlFinanceTest.class);
	
	public AbstractXmlFinanceTest(Class<T> cXml)
	{
		super(cXml,"finance");
	}
}