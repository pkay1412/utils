package net.sf.ahtutils.xml.audit;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlAuditTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlAuditTest.class);
	
    protected static final String dirSuffix = "audit";
}