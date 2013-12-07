package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlStatusFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlStatusFactory.class);
		
	private Status q;
	private String lang;
	
	@Deprecated
	public XmlStatusFactory(Status q){this("en",q);}
	
	public XmlStatusFactory(String lang,Status q)
	{
		this.lang=lang;
		this.q=q;
	}
	
	public <S extends UtilsStatus<L,D>, L extends UtilsLang, D extends UtilsDescription> Status build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<L,D>, L extends UtilsLang, D extends UtilsDescription> Status build(S ejb, String group)
	{
		Status xml = new Status();
		xml.setGroup(group);
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		if(q.isSetImage()){xml.setImage(ejb.getImage());}
		if(q.isSetVisible()){xml.setVisible(ejb.isVisible());}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory f = new XmlLangsFactory(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory f = new XmlDescriptionsFactory(q.getDescriptions());
			xml.setDescriptions(f.create(ejb.getDescription()));
		}
		
		if(q.isSetLabel())
		{
			if(ejb.getName()!=null)
			{
				if(ejb.getName().containsKey(lang)){xml.setLabel(ejb.getName().get(lang).getLang());}
				else
				{
					String msg = "No translation "+lang+" available in "+ejb;
					logger.warn(msg);
					xml.setLabel(msg);
				}
			}
			else
			{
				String msg = "No @name available in "+ejb;
				logger.warn(msg);
				xml.setLabel(msg);
			}
		}
		
		return xml;
	}
	
	public static Status create(String code)
	{
		Status xml = new Status();
		xml.setCode(code);
		return xml;
	}
}