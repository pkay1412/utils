package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlUser extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUser.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,User.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	User test = create();
    	User ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), User.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static User create()
    {
    	User xml = new User();
    	xml.setValue("myUser");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlUser.initJaxb();
		TestXmlUser.initFiles();	
		TestXmlUser test = new TestXmlUser();
		test.save();
    }
}