package net.sf.ahtutils.factory.xml.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.xml.security.Staffs;

public class XmlStaffsFactory <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlStaffsFactory.class);
			
	public static Staffs build()
	{
		Staffs xml = new Staffs();
		return xml;
	}
}