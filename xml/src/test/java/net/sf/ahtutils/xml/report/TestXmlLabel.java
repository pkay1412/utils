package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLabel extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLabel.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Label.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Label test = create();
    	Label ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Label.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Label create()
    {
    	Label xml = new Label();
    	xml.setScope("myScope");
    	xml.setKey("myKey");
    	xml.setValue("myLabel");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlLabel.initJaxb();
		TestXmlLabel.initFiles();	
		TestXmlLabel test = new TestXmlLabel();
		test.save();
    }
}