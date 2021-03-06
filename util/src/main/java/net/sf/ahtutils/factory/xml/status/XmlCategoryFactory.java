package net.sf.ahtutils.factory.xml.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Category;

public class XmlCategoryFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlCategoryFactory.class);
			
	private String lang;
	private Category q;
	
	public XmlCategoryFactory(Category q){this(null,q);}
	public XmlCategoryFactory(String lang,Category q)
	{
		this.lang=lang;
		this.q=q;
	}
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Category build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Category build(S ejb, String group)
	{
		Category xml = new Category();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		xml.setGroup(group);
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		if(q.isSetDescriptions())
		{

		}
		
		if(q.isSetLabel() && lang!=null)
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
		else if(q.isSetLabel() && lang==null){logger.warn("Should render label, but lang is null");}
		
		return xml;
	}
	
	public static Category create(String code)
	{
		Category xml = new Category();
		xml.setCode(code);
		return xml;
	}
	
	public static Category label(String code, String label)
	{
		Category xml = new Category();
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
}