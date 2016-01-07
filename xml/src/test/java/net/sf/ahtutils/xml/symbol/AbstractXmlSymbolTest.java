package net.sf.ahtutils.xml.symbol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractXmlTest;

public abstract class AbstractXmlSymbolTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSymbolTest.class);

	public AbstractXmlSymbolTest(Class<T> cXml)
	{
		super(cXml,"symbol");
	}
}