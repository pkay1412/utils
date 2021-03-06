package net.sf.ahtutils.xml.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlTime extends AbstractXmlFinanceTest<Time>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTime.class);
	
	public TestXmlTime(){super(Time.class);}
	public static Time create(boolean withChildren){return (new TestXmlTime()).build(withChildren);}
    
    public Time build(boolean withChilds)
    {
    	Time xml = new Time();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	xml.setRecord(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlTime test = new TestXmlTime();
		test.saveReferenceXml();
    }
}