package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlGroups extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlGroups.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Groups.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Groups actual = create(true);
    	Groups expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Groups.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Groups create(boolean withChilds)
    {
    	Groups xml = new Groups();
    	
    	if(withChilds)
    	{
    		xml.getGroup().add(TestXmlGroup.create(false));
    		xml.getGroup().add(TestXmlGroup.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlGroups.initJaxb();
		TestXmlGroups.initFiles();	
		TestXmlGroups test = new TestXmlGroups();
		test.save();
    }
}