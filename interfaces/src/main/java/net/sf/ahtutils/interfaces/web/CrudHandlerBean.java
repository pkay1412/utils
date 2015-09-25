package net.sf.ahtutils.interfaces.web;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface CrudHandlerBean <T extends EjbWithId>
{
	T crudBuild(Class<T> cT);
	T crudUpdate(T t);
	void crudSelect();
}