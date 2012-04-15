package net.sf.ahtutils.model.interfaces.security;

import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.EjbWithLang;
import net.sf.ahtutils.model.interfaces.EjbWithType;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsSecurityCategory<L extends UtilsLang,
									   D extends UtilsDescription,
									   C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
									   R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
									   V extends UtilsSecurityView<L,D,C,R,V,U,A>,
									   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
									   A extends UtilsSecurityAction<L,D,C,R,V,U,A>>
			extends EjbWithId,EjbWithCode,EjbWithType,EjbWithLang<L>,EjbWithDescription<D>
{
	public static enum Type {role,view,usecase}
	
//	public List<R> getRoles();
//	public void setRoles(List<R> roles);
}