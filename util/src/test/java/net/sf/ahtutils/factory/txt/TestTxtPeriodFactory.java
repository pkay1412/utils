package net.sf.ahtutils.factory.txt;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractAhtUtilsTest;
import net.sf.ahtutils.test.AhtUtilsTestBootstrap;

public class TestTxtPeriodFactory extends AbstractAhtUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtPeriodFactory.class);
	
	private TxtPeriodFactory tfPeriod;
	
	@Before
	public void init()
	{
		tfPeriod = new TxtPeriodFactory();
	}
 
    @Test
    public void hours()
    {	
//    	String expected = "Now Testing: TestTxtDataUpdateFactory";
    	String actual = tfPeriod.debug(3125);
    	logger.debug(actual);
//    	Assert.assertEquals(expected, actual);
    }
       
	public static void main (String[] args) throws Exception
	{
		AhtUtilsTestBootstrap.init();
		
		TestTxtPeriodFactory test = new TestTxtPeriodFactory();
		test.init();
		
		test.hours();
	}
}