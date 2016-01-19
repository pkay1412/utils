package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlJr extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlJr.class);
		
	private static File fXml;
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"jr2.xml");
	}
    
    @Test
    public void testInfo() throws FileNotFoundException
    {
    	Jr test = create();
    	Jr ref = (Jr)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Jr.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Jr create()
    {
    	Jr jr = new Jr();
    	jr.setName("testReportName");
    	jr.setType("mr");
    	jr.setVisible(true);
    	return jr;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlJr.initJaxb();
		TestXmlJr.initFiles();	
		TestXmlJr test = new TestXmlJr();
		test.save();
    }
}