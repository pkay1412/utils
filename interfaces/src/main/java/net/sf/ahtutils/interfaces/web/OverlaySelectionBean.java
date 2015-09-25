package net.sf.ahtutils.interfaces.web;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface OverlaySelectionBean <T extends EjbWithId>
{
	void opSelect(T t) throws UtilsConstraintViolationException, UtilsLockingException;
}