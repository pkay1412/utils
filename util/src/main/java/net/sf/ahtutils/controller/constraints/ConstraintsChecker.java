package net.sf.ahtutils.controller.constraints;

import net.sf.ahtutils.util.reflection.ReflectionsUtil;

public class ConstraintsChecker {
    public static boolean notNull(Object object, String attribute) throws Exception
    {
        return ReflectionsUtil.simpleInvokeMethod(("get" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1)),
                                                    new Object[]{},
                                                    object.getClass(),
                                                    object)
                                                    != null;
	}
}