package net.sf.ahtutils.controller.audit;

import java.util.*;

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
            scopes.add(change.getScope());
        }
        List<Scope> singleScopes = isDouble(scopes);
        return singleScopes;
    }

    private List<Scope> isDouble(List<Scope> scopes)
    {
        Map<String, Scope> scopeIdentity = new HashMap<String, Scope>();
        for(Scope sc : scopes)
            if(!scopeIdentity.containsKey(sc.getId() + "-" + sc.getClazz()) && !scopeIdentity.containsValue(sc))
                scopeIdentity.put(sc.getId() + "-" + sc.getClazz(), sc);

        List<Scope> scopeList = new ArrayList<Scope>();
        for (Object o : scopeIdentity.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            scopeList.add((Scope) pair.getValue());
        }
        return scopeList;
    }
}