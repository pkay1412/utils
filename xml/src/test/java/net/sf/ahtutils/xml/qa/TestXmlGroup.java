package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlGroup extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlGroup.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Group.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Group actual = create(true);
    	Group expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Group.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Group create(boolean withChilds)
    {
    	Group xml = new Group();
    	xml.setId(123);
    	xml.setPosition(1);
    	xml.setName("myName");
    	
    	if(withChilds)
    	{
    		xml.setDescription(net.sf.ahtutils.xml.status.TestXmlDescription.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlGroup.initJaxb();
		TestXmlGroup.initFiles();	
		TestXmlGroup test = new TestXmlGroup();
		test.save();
    }
}