package net.sf.ahtutils.jsf.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.web.OverlaySelectionBean;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class UtilsOverlaySelectionHandler <T extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(UtilsOverlaySelectionHandler.class);
	
	public static final long serialVersionUID=1;

    private OverlaySelectionBean<T> bean;

    private T selection;
	public T getSelection() {return selection;}
	public void setSelection(T selection) {this.selection = selection;}

	//All available entities for Selection
	private List<T> available;
	public List<T> getAvailable() {return available;}
	public void setAvailable(List<T> available) {this.available = available;}

	private List<T> subset;
	public List<T> getSubset() {return subset;}
	public void setSubset(List<T> subset) {this.subset = subset;}
	
	public UtilsOverlaySelectionHandler(OverlaySelectionBean<T> bean)
    {
		this.bean=bean;
    }

    public void selectOverlay() throws UtilsLockingException, UtilsConstraintViolationException
    {
    	jsfErrors();
    	bean.opSelect(selection);
    }
    
    public void selectUi() throws UtilsLockingException, UtilsConstraintViolationException
    {
    	logger.warn("selectUi");
    }
    
    public void removeListener() throws UtilsLockingException, UtilsConstraintViolationException
    {
    	jsfErrors();
    	logger.warn("removeListener");
    	bean.opRemove(selection);
    }
    
    private void jsfErrors()
    {
    	if(selection==null){logger.warn("The selection parameter is null, is the JSF component inside a form?");}
    }
}