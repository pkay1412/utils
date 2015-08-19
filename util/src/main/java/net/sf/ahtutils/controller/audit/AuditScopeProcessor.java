package net.sf.ahtutils.controller.audit;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.xml.audit.Change;
import net.sf.ahtutils.xml.audit.Scope;

public class AuditScopeProcessor
{
	public AuditScopeProcessor()
	{
		
	}
	
	public List<Scope> group(List<Change> changes)
	{
		List<Scope> scopes = new ArrayList<Scope>();
		return scopes;
	}
}
