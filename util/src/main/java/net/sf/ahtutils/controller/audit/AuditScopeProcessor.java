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
        LinkedHashMap<String, Scope> tmp = new LinkedHashMap<String, Scope>();

        for (Change change : changes) {
            tmp.put(change.getScope().getId() + "/" + change.getScope().getClazz(), change.getScope());
        }

        for(Map.Entry<String, Scope> me : tmp.entrySet()) {
            addChanges(changes, me);
            scopes.add(me.getValue());
        }
        deleteScopes(changes);

        return scopes;
    }

    private void addChanges(List<Change> changes, Map.Entry<String, Scope> me )
    {
        for (Change c : changes)
        {
            String key = c.getScope().getId() + "/" + c.getScope().getClazz();
            if (me.getKey().equals(key))
            {
                me.getValue().getChange().add(c);
            }
        }
    }

    private void deleteScopes(List<Change> changes)
    {
        for (Change c : changes)
        {
            c.setScope(null);
        }
    }
}