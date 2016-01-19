package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlResources extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlResources.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"resources2.xml");
	}
    
    @Test
    public void testResources() throws FileNotFoundException
    {
    	Resources test = create();
    	Resources ref = (Resources)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Resources.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Resources create()
    {
    	Resources resources = new Resources();
    	resources.setDir("src/main/resources/report/resources");
    	resources.getResource().add(TestXmlResource.create());
    	return resources;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlResources.initJaxb();
		TestXmlResources.initFiles();	
		TestXmlResources test = new TestXmlResources();
		test.save();
    }
}