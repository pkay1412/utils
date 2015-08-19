package net.sf.ahtutils.xml.audit;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlScope extends AbstractXmlAuditTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlScope.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Scope.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Scope actual = create(true);
    	Scope expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Scope.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Scope create(boolean withChilds)
    {
    	Scope xml = new Scope();
    	xml.setId(123);
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.getChange().add(TestXmlChange.create(false));xml.getChange().add(TestXmlChange.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlScope.initJaxb();
		TestXmlScope.initFiles();	
		TestXmlScope test = new TestXmlScope();
		test.save();
    }
}