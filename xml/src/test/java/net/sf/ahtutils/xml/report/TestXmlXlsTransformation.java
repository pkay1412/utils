package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlXlsTransformation extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlXlsTransformation.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,XlsTransformation.class);}
    
    @Test
    public void testReport() throws FileNotFoundException
    {
    	XlsTransformation test = create(true);
    	XlsTransformation ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), XlsTransformation.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static XlsTransformation create(boolean withChildren)
    {
    	XlsTransformation xml = new XlsTransformation();
    	
    	xml.setDataClass("myDataClass");
    	xml.setFormatPattern("myPattern");
    	xml.setBeanProperty("myBeanProperty");
    	
    	if(withChildren)
    	{

    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlXlsTransformation.initJaxb();
		TestXmlXlsTransformation.initFiles();
		TestXmlXlsTransformation test = new TestXmlXlsTransformation();
		test.save();
    }
}