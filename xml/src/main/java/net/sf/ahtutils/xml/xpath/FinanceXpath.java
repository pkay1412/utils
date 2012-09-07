package net.sf.ahtutils.xml.xpath;

import java.util.List;

import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.status.Translation;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.status.Type;
import net.sf.ahtutils.xml.status.Types;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinanceXpath
{
	final static Logger logger = LoggerFactory.getLogger(FinanceXpath.class);
	
	public static synchronized Lang getLang(Langs langs,String key) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(langs);
		
		StringBuffer sb = new StringBuffer();
		sb.append("lang[@key='").append(key).append("']");
		
		@SuppressWarnings("unchecked")
		List<Lang> listResult = (List<Lang>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Lang.class.getSimpleName()+" for key="+key);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Lang.class.getSimpleName()+" for key="+key);}
		return listResult.get(0);
	}
	
	public static synchronized Description getDescription(Descriptions descriptions,String key) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(descriptions);
		
		StringBuffer sb = new StringBuffer();
		sb.append("description[@key='").append(key).append("']");
		
		@SuppressWarnings("unchecked")
		List<Description> listResult = (List<Description>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Description.class.getSimpleName()+" for key="+key);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Description.class.getSimpleName()+" for key="+key);}
		return listResult.get(0);
	}
	
	public static synchronized Translation getTranslation(Translations translations,String key) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(translations);
		
		StringBuffer sb = new StringBuffer();
		sb.append("translation[@key='").append(key).append("']");
		
		@SuppressWarnings("unchecked")
		List<Translation> listResult = (List<Translation>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Translation.class.getSimpleName()+" for key="+key);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Translation.class.getSimpleName()+" for key="+key);}
		return listResult.get(0);
	}
	
	public static synchronized Lang getLang(Translations translations,String keyTranslation, String keyLang) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Translation translation = getTranslation(translations,keyTranslation);
		return getLang(translation.getLangs(),keyLang);
	}
	
	public static synchronized Type getType(Types types,String key) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(types);
		
		StringBuffer sb = new StringBuffer();
		sb.append("type[@key='").append(key).append("']");
		
		@SuppressWarnings("unchecked")
		List<Type> listResult = (List<Type>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Type.class.getSimpleName()+" for key="+key);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Type.class.getSimpleName()+" for key="+key);}
		return listResult.get(0);
	}
}