package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlHash extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlHash.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Hash.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Hash test = create();
    	Hash ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Hash.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Hash create()
    {
    	Hash xml = new Hash();
    	xml.setValue("myHash");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlHash.initJaxb();
		TestXmlHash.initFiles();	
		TestXmlHash test = new TestXmlHash();
		test.save();
    }
}