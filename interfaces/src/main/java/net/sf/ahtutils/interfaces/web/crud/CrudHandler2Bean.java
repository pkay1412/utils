package net.sf.ahtutils.interfaces.web.crud;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface CrudHandler2Bean <T extends EjbWithId>
{
	T crud2Build(Class<T> cT);
	T crud2Update(T t);
	void crud2Select();
}