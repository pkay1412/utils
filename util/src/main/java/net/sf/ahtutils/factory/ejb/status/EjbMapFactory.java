package net.sf.ahtutils.factory.ejb.status;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;

public class EjbMapFactory
{
	final static Logger logger = LoggerFactory.getLogger(EjbMapFactory.class);
	    
    public static <T extends EjbWithCode> Map<String,T> byCode(List<T> list)
    {
    	Map<String,T> map = new Hashtable<String,T>();
    	for(T s : list)
    	{
    		map.put(s.getCode(), s);
    	}
        return map;
    }
}