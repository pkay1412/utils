package net.sf.ahtutils.interfaces.web.crud;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface CrudHandler1Bean <T extends EjbWithId>
{
	T crud1Build(Class<T> cT);
	T crud1Update(T t);
	void crud1Select();
}