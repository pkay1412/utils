package net.sf.ahtutils.test;

import java.io.File;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlTest.class);
	
	protected static NsPrefixMapperInterface nsPrefixMapper;
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		nsPrefixMapper = new AhtUtilsNsPrefixMapper();
	}
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("src/test/resources/config");
		loggerInit.init();
    }
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals(JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected static XMLGregorianCalendar getXmlDefaultDate()
	{
		return DateUtil.getXmlGc4D(getDefaultDate());
	}
	protected static Date getDefaultDate()
	{
		return DateUtil.getDateFromInt(2011, 11, 11, 11, 11, 11);
	}
	
	protected NsPrefixMapperInterface getPrefixMapper()
	{
		if(nsPrefixMapper==null){nsPrefixMapper = new AhtUtilsNsPrefixMapper();}
		return nsPrefixMapper;
	}
	
	protected void save(Object xml, File f)
	{
		logger.debug("Saving Reference XML");
		JaxbUtil.debug(xml, getPrefixMapper());
    	JaxbUtil.save(f, xml, getPrefixMapper(), true);
	}
}