package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlElement extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlElement.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"element2.xml");
	}
    
    @Test
    public void testElement() throws FileNotFoundException
    {
    	Element test = create();
    	Element ref = (Element)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Element.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Element create()
    {
    	Element element = new Element();
    	element.setFile("ahtutils-basic");
    	element.setType("header");
    	return element;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlElement.initJaxb();
		TestXmlElement.initFiles();	
		TestXmlElement test = new TestXmlElement();
		test.save();
    }
}