package net.sf.ahtutils.controller.constraints;

import net.sf.ahtutils.util.reflection.ReflectionsUtil;
import net.sf.ahtutils.xml.system.Constraint;
import net.sf.ahtutils.xml.system.ConstraintAttribute;
import net.sf.ahtutils.xml.system.ConstraintScope;

public class ConstraintsChecker {
    public static boolean notNull(Object object, String attribute) throws Exception
    {
        return ReflectionsUtil.simpleInvokeMethod(("get" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1)),
                                                    new Object[]{},
                                                    object.getClass(),
                                                    object)
                                                    != null;
	}
    
    public static String notNull(Object object, String attribute, ConstraintScope scope) throws Exception
    {
    	String methode = ("get" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1));
    	if(ReflectionsUtil.simpleInvokeMethod(methode,
                                                  new Object[]{},
                                                  object.getClass(),
                                                  object)
                                                  == null)
    	{
	    	for (Constraint con : scope.getConstraint())
	        {
	            if(con.getType().getCode().equals("notNull"))
	            {
	            	for(ConstraintAttribute a : con.getConstraintAttribute())
	            	{
	            		if(a.getAttribute().equals(attribute))
	            		{
	            			return con.getDescriptions().getDescription().get(0).getValue();
	            		}
	            	}
	            }
	        }
    	}
        return null;
	}
}