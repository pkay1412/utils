package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlXlsSheets extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlXlsSheets.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,XlsSheets.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	XlsSheets test = create(true);
    	XlsSheets ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), XlsSheets.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static XlsSheets create(boolean withChildren)
    {
    	XlsSheets xml = new XlsSheets();
    	xml.setQuery("myQuery");
    	
    	if(withChildren)
    	{
    		xml.getXlsSheet().add(TestXmlXlsSheet.create(false));
    		xml.getXlsSheet().add(TestXmlXlsSheet.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlXlsSheets.initJaxb();
		TestXmlXlsSheets.initFiles();
		TestXmlXlsSheets test = new TestXmlXlsSheets();
		test.save();
    }
}