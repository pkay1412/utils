package net.sf.ahtutils.factory.xml.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.utils.Criteria;

public class XmlCriteriaFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlCriteriaFactory.class);
		
	public static Criteria build(String code, Boolean value){return build(code,Boolean.class.getSimpleName(),value.toString());}
	public static Criteria build(String code, Integer value){return build(code,Integer.class.getSimpleName(),value.toString());}
	public static Criteria build(String code, String value){return build(code,String.class.getSimpleName(),value.toString());}
	
	private static Criteria build(String code, String type, String value)
	{
		Criteria xml = new Criteria();
		xml.setCode(code);
		xml.setType(type);
		xml.setValue(value);
		return xml;
	}
}