package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlInfo extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlInfo.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix,"info2");
	}
    
    @Test
    public void testInfo() throws FileNotFoundException
    {
    	Info test = create(true);
    	Info ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Info.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Info create(boolean withChildren)
    {
    	Info xml = new Info();
    	Info.Title title = new Info.Title();
    	title.setValue("testTitle");
    	xml.setTitle(title);
    	Info.Subtitle subtitle = new Info.Subtitle();
    	subtitle.setValue("testSubTitle");
    	xml.setSubtitle(subtitle);
    	Info.Footer footer = new Info.Footer();
    	footer.setValue("testFooter");
    	xml.setFooter(footer);
    	Info.Record record = new Info.Record();
    	record.setValue(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	xml.setRecord(record);
    	
    	xml.setHash(TestXmlHash.create());
    	xml.setUser(TestXmlUser.create());
    	
    	if(withChildren)
    	{
    		xml.getJr().add(TestXmlJr.create());xml.getJr().add(TestXmlJr.create());
    		
    		xml.setLabels(TestXmlLabels.create(false));
    	}
    	
    	xml.setFile(TestXmlFile.create());
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlInfo.initJaxb();
		TestXmlInfo.initFiles();	
		TestXmlInfo test = new TestXmlInfo();
		test.save();
    }
}