package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTemplates extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTemplates.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"templates2.xml");
	}
    
    @Test
    public void testTemplates() throws FileNotFoundException
    {
    	Templates test = create();
    	Templates ref = (Templates)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Templates.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Templates create()
    {
    	Templates templates = new Templates();
    	templates.getTemplate().add(TestXmlTemplate.create());
    	return templates;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlTemplates.initJaxb();
		TestXmlTemplates.initFiles();	
		TestXmlTemplates test = new TestXmlTemplates();
		test.save();
    }
}