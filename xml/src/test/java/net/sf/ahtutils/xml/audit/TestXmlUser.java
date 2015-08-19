package net.sf.ahtutils.xml.audit;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlUser extends AbstractXmlAuditTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUser.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,User.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	User actual = create(true);
    	User expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), User.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static User create(boolean withChilds)
    {
    	User xml = new User();
    	xml.setFirstName("myFirst");
    	xml.setLastName("myLast");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlUser.initJaxb();
		TestXmlUser.initFiles();	
		TestXmlUser test = new TestXmlUser();
		test.save();
    }
}