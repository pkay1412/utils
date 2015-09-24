package net.sf.ahtutils.interfaces.web;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface CrudHandlerBean <T extends EjbWithId>
{
	T buildEntity(Class<T> cT);
	T updateEntity(T t);
}