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
        for(Change change : changes)
        {
                if(scopes.size() == 0 || !isDouble(scopes, change.getScope()))
                {
                    scopes.add(change.getScope());
                }
        }
		return scopes;
	}

    private boolean isDouble(List<Scope> scopes, Scope scope)
    {
        boolean tf = false;
        for (Scope scope1 : scopes) {
            if (scope.equals(scope1)) {
                tf = true;
            }
        }
        return tf;
    }
}