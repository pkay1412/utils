package net.sf.ahtutils.xml.security;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStaff extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStaff.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Staff.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Staff actual = create(true);
    	Staff expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Staff.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Staff create(boolean withChilds)
    {
    	Staff xml = new Staff();
    	xml.setId(123);
    	
    	if(withChilds)
    	{
    		xml.setRole(TestXmlRole.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlStaff.initFiles();	
		TestXmlStaff test = new TestXmlStaff();
		test.save();
    }
}