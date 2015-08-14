package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlXlsDefinition extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlXlsDefinition.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,XlsDefinition.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	XlsDefinition test = create(true);
    	XlsDefinition ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), XlsDefinition.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static XlsDefinition create(boolean withChildren)
    {
    	XlsDefinition xml = new XlsDefinition();
    	
    	if(withChildren)
    	{
    		xml.getXlsWorkbook().add(TestXmlXlsWorkbook.create(false));
    		xml.getXlsWorkbook().add(TestXmlXlsWorkbook.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlXlsDefinition.initJaxb();
		TestXmlXlsDefinition.initFiles();
		TestXmlXlsDefinition test = new TestXmlXlsDefinition();
		test.save();
    }
}