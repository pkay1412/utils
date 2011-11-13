package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlUsecase extends AbstractXmlAccessTest
{
	static Log logger = LogFactory.getLog(TestXmlUsecase.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"usecase.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Usecase actual = create();
    	Usecase expected = (Usecase)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Usecase.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Usecase create(){return create(true);}
    public static Usecase create(boolean withChilds)
    {
    	Usecase xml = new Usecase();
    	xml.setCode("myCode");
    	xml.setIndex(1);
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlUsecase.initFiles();	
		TestXmlUsecase test = new TestXmlUsecase();
		test.save();
    }
}