package net.sf.ahtutils.factory.xml.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.mail.Mail;

public class XmlMailFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlMailFactory.class);
		
	public static Mail build(String code)
	{
		Mail xml = new Mail();
		xml.setCode(code);
		return xml;
	}
}