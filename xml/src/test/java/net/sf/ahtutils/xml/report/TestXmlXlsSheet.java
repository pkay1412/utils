package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlXlsSheet extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlXlsSheet.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,XlsSheet.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	XlsSheet test = create(true);
    	XlsSheet ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), XlsSheet.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static XlsSheet create(boolean withChildren)
    {
    	XlsSheet xml = new XlsSheet();
    	xml.setQuery("myQuery");
    	
    	if(withChildren)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.getXlsColumn().add(TestXmlXlsColumn.create(false));xml.getXlsColumn().add(TestXmlXlsColumn.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlXlsSheet.initJaxb();
		TestXmlXlsSheet.initFiles();
		TestXmlXlsSheet test = new TestXmlXlsSheet();
		test.save();
    }
}