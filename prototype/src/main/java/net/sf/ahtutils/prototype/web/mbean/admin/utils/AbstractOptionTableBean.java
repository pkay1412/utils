package net.sf.ahtutils.prototype.web.mbean.admin.utils;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;

public class AbstractOptionTableBean //<S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription>
										implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOptionTableBean.class);
	private static final long serialVersionUID = 1L;

	protected boolean hasDeveloperAction;
	
	public AbstractOptionTableBean()
	{
		hasDeveloperAction = false;
	}
	
	protected void updateSecurity(UtilsJsfSecurityHandler jsfSecurityHandler, String actionDeveloper)
	{
		hasDeveloperAction = jsfSecurityHandler.allow(actionDeveloper);
		
		if(logger.isTraceEnabled())
		{
			logger.info(hasDeveloperAction+" hasDeveloperAction "+actionDeveloper);
		}
	}
	
	public void reorder() throws UtilsConstraintViolationException, UtilsLockingException {}
}