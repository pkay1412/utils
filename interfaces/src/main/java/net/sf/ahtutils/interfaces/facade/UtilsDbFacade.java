package net.sf.ahtutils.interfaces.facade;

import java.util.List;
import java.util.Map;

import org.openfuxml.content.table.Table;

public interface UtilsDbFacade extends UtilsFacade
{
	//<T extends Object>
	long count(Class<?> cl);
	//<T extends Object>
	Map<Class<?>, Long> count(List<Class<?>> list);
	
	Table connections(String userName);
}