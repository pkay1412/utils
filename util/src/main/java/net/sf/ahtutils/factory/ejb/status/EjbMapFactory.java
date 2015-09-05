package net.sf.ahtutils.factory.ejb.status;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbMapFactory
{
	final static Logger logger = LoggerFactory.getLogger(EjbMapFactory.class);
	    
    public static <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription> Map<String,S> byCode(List<S> list)
    {
    	Map<String,S> map = new Hashtable<String,S>();
    	for(S s : list)
    	{
    		map.put(s.getCode(), s);
    	}
        return map;
    }
}