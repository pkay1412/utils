package net.sf.ahtutils.factory.txt.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.mail.EmailAddress;
import net.sf.ahtutils.xml.mail.Header;

public class TxtMailHeaderFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtMailHeaderFactory.class);
		
	public static String to(Header header)
	{
		StringBuffer sb = new StringBuffer();
		if(header.isSetTo())
		{
			for(EmailAddress email : header.getTo().getEmailAddress())
			{
				sb.append(email.getEmail());
				sb.append(", ");
			}
		}
		
		else
		{
			sb.append("No TO field");
		}
		return sb.toString();
	}
	
}