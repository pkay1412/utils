package net.sf.ahtutils.factory.xml.text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.text.Remark;

public class XmlRemarkFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlRemarkFactory.class);
	
	public static Remark build(String value) {return build(null,null,value);}
	
	public static <E extends Enum<E>> Remark build(E key, String value){return build(key.toString(),value);}
	public static Remark build(String key,String value) {return build(key,null,value);}
	
	public static Remark build(String key,Integer version,String value)
	{
		Remark xml = new Remark();
		if(key!=null){xml.setKey(key);}
		if(version!=null){xml.setVersion(version);}
		xml.setValue(value);
		return xml;
	}
}