package net.sf.ahtutils.factory.xml.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.system.ConstraintScope;

public class XmlConstraintScopeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlConstraintScopeFactory.class);
		
	public static ConstraintScope build(String code)
	{
		ConstraintScope xml = XmlConstraintScopeFactory.build();
		xml.setCode(code);
		return xml;
	}
	
	public static ConstraintScope build()
	{
		ConstraintScope xml = new ConstraintScope();
		return xml;
	}
}