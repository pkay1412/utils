package net.sf.ahtutils.interfaces.model.with.position;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithPosition extends EjbWithId
{
	public int getPosition();
	public void setPosition(int position);
}