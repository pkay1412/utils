package net.sf.ahtutils.xml.audit;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlRevisions extends AbstractXmlAuditTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRevisions.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Revisions.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Revisions actual = create(true);
    	Revisions expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Revisions.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Revisions create(boolean withChilds)
    {
    	Revisions xml = new Revisions();
    	
    	if(withChilds)
    	{
    		xml.getRevision().add(TestXmlRevision.create(false));
    		xml.getRevision().add(TestXmlRevision.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlRevisions.initJaxb();
		TestXmlRevisions.initFiles();	
		TestXmlRevisions test = new TestXmlRevisions();
		test.save();
    }
}