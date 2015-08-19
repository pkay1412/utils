package net.sf.ahtutils.xml.audit;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlRevision extends AbstractXmlAuditTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRevision.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Revision.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Revision actual = create(true);
    	Revision expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Revision.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Revision create(boolean withChilds)
    {
    	Revision xml = new Revision();
    	xml.setRev(123);
    	xml.setDate(AbstractXmlAuditTest.getDefaultXmlDate());
    	
    	if(withChilds)
    	{
    		xml.setUser(TestXmlUser.create(false));
    		xml.getScope().add(TestXmlScope.create(false));xml.getScope().add(TestXmlScope.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlRevision.initJaxb();
		TestXmlRevision.initFiles();	
		TestXmlRevision test = new TestXmlRevision();
		test.save();
    }
}