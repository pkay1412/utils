package net.sf.ahtutils.xml.issue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractXmlTest;

public abstract class AbstractXmlIssueTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlIssueTest.class);
	
	public AbstractXmlIssueTest(Class<T> cXml)
	{
		super(cXml,"issue");
	}
}