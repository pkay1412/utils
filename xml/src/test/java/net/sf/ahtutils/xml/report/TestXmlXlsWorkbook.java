package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlXlsWorkbook extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlXlsWorkbook.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,XlsWorkbook.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	XlsWorkbook test = create(true);
    	XlsWorkbook ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), XlsWorkbook.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static XlsWorkbook create(boolean withChildren)
    {
    	XlsWorkbook xml = new XlsWorkbook();
    	xml.setCode("myCode");
    	
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
			
		TestXmlXlsWorkbook.initJaxb();
		TestXmlXlsWorkbook.initFiles();
		TestXmlXlsWorkbook test = new TestXmlXlsWorkbook();
		test.save();
    }
}