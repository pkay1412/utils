package net.sf.ahtutils.model.interfaces.finance;

import net.sf.ahtutils.model.interfaces.EjbWithId;

public interface UtilsFigure extends EjbWithId
{
	double getValue();
	void setValue(double value);
}