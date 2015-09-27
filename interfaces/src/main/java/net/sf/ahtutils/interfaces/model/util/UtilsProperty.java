package net.sf.ahtutils.interfaces.model.util;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;

public interface UtilsProperty extends EjbSaveable
{
	public String getKey();
	public void setKey(String key);
	
	public String getValue();
	public void setValue(String value);
	
	public boolean isFrozen();
	public void setFrozen(boolean frozen);
}
