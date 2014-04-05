package net.sf.ahtutils.xml.sync;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDataUpdate extends AbstractXmlSyncTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDataUpdate.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,DataUpdate.class);
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	DataUpdate actual = create(true);
    	DataUpdate expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), DataUpdate.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static DataUpdate create(boolean withChilds)
    {
    	DataUpdate xml = new DataUpdate();
    	
    	if(withChilds)
    	{
    		xml.getMapper().add(TestXmlMapper.create(false));
    		xml.getMapper().add(TestXmlMapper.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlDataUpdate.initJaxb();
		TestXmlDataUpdate.initFiles();	
		TestXmlDataUpdate test = new TestXmlDataUpdate();
		test.save();
    }
}