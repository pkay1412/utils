package net.sf.ahtutils.factory.xml.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.audit.Change;
import net.sf.ahtutils.xml.audit.Scope;

public class XmlChangeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlChangeFactory.class);
	
	public static Change build(int aid, Scope scope)
	{
		Change xml = new Change();
		xml.setAid(aid);
		xml.setScope(scope);
		return xml;
	}
}