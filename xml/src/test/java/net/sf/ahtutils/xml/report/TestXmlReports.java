package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlReports extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlReports.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"reports2.xml");
	}
    
    @Test
    public void testReports() throws FileNotFoundException
    {
    	Reports test = create();
    	Reports ref = (Reports)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Reports.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Reports create()
    {
    	Reports reports = new Reports();
    	reports.setDir("testDir");
    	reports.setResources("reports.ahtutils-util/resources.xml");
    	reports.setTemplates("reports.ahtutils-util/templates.xml");
    	reports.getReport().add(TestXmlReport.create());
    	return reports;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlReports.initJaxb();
		TestXmlReports.initFiles();	
		TestXmlReports test = new TestXmlReports();
		test.save();
    }
}