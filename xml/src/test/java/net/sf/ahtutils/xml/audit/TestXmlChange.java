package net.sf.ahtutils.xml.audit;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlChange extends AbstractXmlAuditTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlChange.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Change.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Change actual = create(true);
    	Change expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Change.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Change create(boolean withChilds)
    {
    	Change xml = new Change();
    	xml.setAid(1);
    	xml.setAction("action");
    	xml.setText("myValue");
    	
    	if(withChilds)
    	{
    		xml.setScope(TestXmlScope.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlChange.initJaxb();
		TestXmlChange.initFiles();	
		TestXmlChange test = new TestXmlChange();
		test.save();
    }
}