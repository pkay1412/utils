package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlXlsColumns extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlXlsColumns.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,XlsColumns.class);}
    
    @Test
    public void testReport() throws FileNotFoundException
    {
    	XlsColumns test = create(true);
    	XlsColumns ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), XlsColumns.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static XlsColumns create(boolean withChildren)
    {
    	XlsColumns xml = new XlsColumns();
    	
    	xml.setQuery("myQuery");
    	
    	if(withChildren)
    	{
    		xml.getXlsColumn().add(TestXmlXlsColumn.create(false));xml.getXlsColumn().add(TestXmlXlsColumn.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlXlsColumns.initJaxb();
		TestXmlXlsColumns.initFiles();
		TestXmlXlsColumns test = new TestXmlXlsColumns();
		test.save();
    }
}