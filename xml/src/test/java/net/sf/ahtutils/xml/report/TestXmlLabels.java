package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlLabels extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLabels.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Labels.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Labels test = create(true);
    	Labels ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Labels.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Labels create(boolean withChildren)
    {
    	Labels xml = new Labels();
   
    	if(withChildren)
    	{
    		xml.getLabel().add(TestXmlLabel.create());
    	}

    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlLabels.initJaxb();
		TestXmlLabels.initFiles();	
		TestXmlLabels test = new TestXmlLabels();
		test.save();
    }
}