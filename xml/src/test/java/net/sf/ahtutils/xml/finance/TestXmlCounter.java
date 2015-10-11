package net.sf.ahtutils.xml.finance;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlCounter extends AbstractXmlFinanceTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCounter.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,Counter.class);
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Counter actual = create(true);
    	Counter expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Counter.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Counter create(boolean withChilds)
    {
    	Counter xml = new Counter();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	xml.setCounter(2345);
    	    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		AbstractXmlTest.initJaxb();
		
		TestXmlCounter.initFiles();	
		TestXmlCounter test = new TestXmlCounter();
		test.save();
    }
}