package net.sf.ahtutils.prototype.web.mbean;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.bean.ConstraintsBean;
import net.sf.ahtutils.monitor.ProcessingTimeTracker;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.ahtutils.web.mbean.util.AbstractMenuBean;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.system.Constraint;
import net.sf.ahtutils.xml.system.ConstraintScope;
import net.sf.ahtutils.xml.system.Constraints;
import net.sf.exlp.util.xml.JaxbUtil;

public class PrototypeConstraintsBean extends AbstractMenuBean implements Serializable,ConstraintsBean
{
	final static Logger logger = LoggerFactory.getLogger(PrototypeConstraintsBean.class);
	private static final long serialVersionUID = 1L;
		
	private Map<String,Constraint> constraints;

    public void init(String artifact) throws FileNotFoundException
    {
		ProcessingTimeTracker ptt = new ProcessingTimeTracker(true);

		constraints = new Hashtable<String,Constraint>();
		Constraints index = JaxbUtil.loadJAXB("constraints."+artifact+"/index.xml", Constraints.class);
		for(ConstraintScope scopeCategory : index.getConstraintScope())
		{
			Constraints c = JaxbUtil.loadJAXB("constraints."+artifact+"/"+scopeCategory.getCategory()+".xml", Constraints.class);
			for(ConstraintScope scope : c.getConstraintScope())
			{
				for(Constraint constraint : scope.getConstraint())
				{
					if(constraint.isSetCode())
					{
						String key = scopeCategory.getCategory()+"-"+scope.getCode()+"-"+constraint.getCode();
						logger.info("Adding "+key);
						constraints.put(key, constraint);
					}
				}
			}
		}

		logger.info(AbstractLogMessage.postConstruct(ptt)+" with Constraints:"+constraints.size());
    }
    
    public String getMessage(String category, String scope, String code, String lang)
    {    	
    	String key = category+"-"+scope+"-"+code;
    	
    	if(constraints.containsKey(key))
    	{
    		Constraint c = constraints.get(key);
        	if(c.isSetDescriptions())
        	{
        		for(Description d : c.getDescriptions().getDescription())
            	{
            		if(d.getKey().equals(lang)){return d.getValue();}
            	}
        	}
    	}
    	
    	return "Constraint not found in list: "+key;
    }
}