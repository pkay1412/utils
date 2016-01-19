package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlField extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlField.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"field2.xml");
	}
    
    @Test
    public void testField() throws FileNotFoundException
    {
    	Field test = create();
    	Field ref = (Field)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Field.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Field create()
    {
    	Field field = new Field();
    	field.setExpression("/report/title");
    	field.setType("field");
    	field.setName("title");
    	return field;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlField.initJaxb();
		TestXmlField.initFiles();	
		TestXmlField test = new TestXmlField();
		test.save();
    }
}