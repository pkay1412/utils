package net.sf.ahtutils.interfaces.model.with;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithParent extends EjbWithId
{
	public String resolveParentAttribute();
}
