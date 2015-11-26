package net.sf.ahtutils.interfaces.model.ranking;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsRankedResult<T extends EjbWithId>
{	
	double getRanking();
	void setRaning(double ranking);
	
	T getEntity();
	void setEntitiy(T entity);
}