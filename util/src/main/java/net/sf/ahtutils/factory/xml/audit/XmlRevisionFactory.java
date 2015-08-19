package net.sf.ahtutils.factory.xml.audit;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.audit.Revision;
import net.sf.exlp.util.DateUtil;

public class XmlRevisionFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlRevisionFactory.class);
	
	public static Revision build(long rev, Date record)
	{
		Revision xml = new Revision();
		xml.setRev(rev);
		xml.setDate(DateUtil.toXmlGc(record));
		return xml;
	}
}