package net.sf.ahtutils.interfaces.bean;

import net.sf.ahtutils.xml.system.ConstraintScope;

public interface ConstraintsBean
{
	String getMessage(String category, String scope, String code, String lang);
	ConstraintScope getScope(String category, String scope, String lang);
}