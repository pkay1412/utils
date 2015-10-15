package net.sf.ahtutils.interfaces.web.crud;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface CrudHandlerBean <T extends EjbWithId>
{
	T crudBuild(Class<T> cT);
	T crudUpdate(T t);
	void crudNotifySelect(T t);
	void crudNotifyRemove(T t);
	void crudNotifySave(T t);
	void crudRmConstraintViolation(Class<T> cT);
	
	
}