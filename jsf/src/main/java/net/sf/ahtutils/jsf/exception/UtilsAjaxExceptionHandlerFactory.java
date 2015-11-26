package net.sf.ahtutils.jsf.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class UtilsAjaxExceptionHandlerFactory extends ExceptionHandlerFactory
{
	//http://stackoverflow.com/questions/18003309/silence-fullajaxexceptionhandler/
	
    private ExceptionHandlerFactory wrapped;

    public UtilsAjaxExceptionHandlerFactory(ExceptionHandlerFactory wrapped)
    {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getExceptionHandler()
    {
        return new UtilsAjaxExceptionHandler(getWrapped().getExceptionHandler());
    }

    @Override
    public ExceptionHandlerFactory getWrapped()
    {
        return wrapped;
    }
}