package net.sf.ahtutils.interfaces.model.monitoring;

import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface UtilsMonitorIndicator<C extends UtilsMonitorComponent<C,I,D,V>,I extends UtilsMonitorIndicator<C,I,D,V>, D extends UtilsMonitorData<C,I,D,V>,V extends UtilsMonitorValue<C,I,D,V>>
		extends EjbWithId,EjbWithName,EjbWithCode
{
//	C getComponent();
//	void setComponent(C component);
}