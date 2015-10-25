package net.sf.ahtutils.xml.audit;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlAuditTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlAuditTest.class);
	
	public AbstractXmlAuditTest(Class<T> cXml)
	{
		super(cXml,"audit");
	}
}