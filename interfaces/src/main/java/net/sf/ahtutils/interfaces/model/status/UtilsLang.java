package net.sf.ahtutils.interfaces.model.status;

import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;

public interface UtilsLang extends EjbRemoveable
{
	long getId();
	void setId(long id);
	
	String getLkey();
	void setLkey(String lkey);
	
	String getLang();
	void setLang(String name);
}