package net.sf.ahtutils.prototype.web.mbean.admin.utils;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.exlp.util.io.StringUtil;

public class AbstractOptionTableBean //<S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription>
										implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOptionTableBean.class);
	private static final long serialVersionUID = 1L;

	protected boolean allowStatusAdd;public boolean isAllowStatusAdd() {return allowStatusAdd;}
	protected boolean allowStatusReorder;public boolean isAllowStatusReorder() {return allowStatusReorder;}
	protected boolean allowCodeChange;public boolean isAllowCodeChange() {return allowCodeChange;}
	protected boolean allowSave; public boolean isAllowSave() {return allowSave;}
	protected boolean allowRemove; public boolean isAllowRemove() {return allowRemove;}
	
	protected boolean hasDeveloperAction;public boolean isHasDeveloperAction() {return hasDeveloperAction;}
	protected boolean hasAdministratorAction,hasTranslatorAction;
	
	public AbstractOptionTableBean()
	{
		hasDeveloperAction = false;
		hasAdministratorAction = true;
		hasTranslatorAction = true;
		
		allowStatusReorder = true;
		allowSave = true;
		allowRemove = true;
	}
	
	protected void updateSecurity(UtilsJsfSecurityHandler jsfSecurityHandler, String viewCode)
	{
		String actionDeveloper = viewCode+".Developer";
		String actionAdministrator = viewCode+".Administrator";
		String actionTranslator = viewCode+".Translator";
		
		hasDeveloperAction = jsfSecurityHandler.allow(actionDeveloper);
		hasAdministratorAction = jsfSecurityHandler.allow(actionAdministrator);
		hasTranslatorAction = jsfSecurityHandler.allow(actionTranslator);
		
		allowStatusReorder = hasDeveloperAction || hasAdministratorAction;
		allowSave = hasDeveloperAction || hasAdministratorAction || hasTranslatorAction;
		allowRemove = hasDeveloperAction || hasAdministratorAction;
		
		if(logger.isInfoEnabled())
		{
			logger.info(StringUtil.stars());
			logger.info("Actions");
			logger.info("\t"+hasDeveloperAction+" hasDeveloperAction "+actionDeveloper);
			logger.info("\t"+hasAdministratorAction+" hasAdministratorAction "+actionAdministrator);
			logger.info("\t"+hasTranslatorAction+" hasTranslatorAction "+actionTranslator);
		}
	}
	
	public void reorder() throws UtilsConstraintViolationException, UtilsLockingException {}
	
	public void debug(boolean debug)
	{
		if(debug)
		{
			logger.info(StringUtil.stars());
			logger.info("Page Actions");
			logger.info("\t"+allowStatusAdd+" allowStatusAdd");
			logger.info("\t"+allowStatusReorder+" allowStatusReorder");
			logger.info("\t"+allowCodeChange+" allowCodeChange");
			logger.info("\t"+allowSave+" allowSave");
			logger.info("\t"+allowRemove+" allowRemove");
		}
	}
	
	// Parents
	protected long parentId;
	public long getParentId(){return parentId;}
	public void setParentId(long parentId){this.parentId = parentId;}
}