package net.sf.ahtutils.controller.constraints;

import net.sf.ahtutils.util.reflection.ReflectionsUtil;
import net.sf.ahtutils.xml.system.Constraint;
import net.sf.ahtutils.xml.system.ConstraintAttribute;
import net.sf.ahtutils.xml.system.ConstraintScope;

public class ConstraintsChecker {
	public static boolean notNull(Object object, String attribute) throws Exception
	{
		return ReflectionsUtil.resolveExpression(object, attribute) != null;
	}

	public static String notNull(Object object, String attribute, ConstraintScope scope) throws Exception
	{
		if(!notNull(object, attribute))
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