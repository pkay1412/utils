package net.sf.ahtutils.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.factory.xml.acl.XmlViewFactory;
import net.sf.ahtutils.controller.factory.xml.acl.XmlViewsFactory;
import net.sf.ahtutils.controller.factory.xml.security.XmlRoleFactory;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.xml.security.XmlActionFactory;
import net.sf.ahtutils.factory.xml.security.XmlActionsFactory;
import net.sf.ahtutils.factory.xml.security.XmlCategoryFactory;
import net.sf.ahtutils.factory.xml.security.XmlRolesFactory;
import net.sf.ahtutils.factory.xml.security.XmlSecurityFactory;
import net.sf.ahtutils.factory.xml.security.XmlStaffFactory;
import net.sf.ahtutils.factory.xml.security.XmlUsecaseFactory;
import net.sf.ahtutils.factory.xml.security.XmlUsecasesFactory;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsStaff;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.rest.security.UtilsSecurityRestExport;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.util.query.SecurityQuery;
import net.sf.ahtutils.web.rest.security.AbstractSecurityInit;
import net.sf.ahtutils.web.rest.security.SecurityInitViews;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.security.Role;
import net.sf.ahtutils.xml.security.Security;
import net.sf.ahtutils.xml.security.Staffs;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class SecurityRestService <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
				implements UtilsSecurityRestExport
{
	final static Logger logger = LoggerFactory.getLogger(SecurityRestService.class);
	
	private UtilsSecurityFacade fSecurity;
	
	private final Class<C> cCategory;
	private final Class<R> cRole;
	private final Class<V> cView;
	private final Class<U> cUsecase;
	
	private SecurityInitViews<L,D,C,R,V,U,A,USER> initViews;
	
	private XmlCategoryFactory<L,D,C,R,V,U,A,USER> fCategory;
	private XmlViewFactory xfView;
	private XmlRoleFactory<L,D,C,R,V,U,A,USER> fRole;
	private XmlActionFactory<L,D,C,R,V,U,A,USER> fAction;
	private XmlUsecaseFactory<L,D,C,R,V,U,A,USER> fUsecase;
	
	private SecurityRestService(UtilsSecurityFacade fSecurity,final Class<L> cL,final Class<D> cD,final Class<C> cCategory,final Class<V> cView,final Class<R> cRole,final Class<U> cUsecase,final Class<A> cAction,final Class<USER> cUser)
	{
		this.fSecurity=fSecurity;
		this.cCategory=cCategory;
		this.cRole=cRole;
		this.cView=cView;
		this.cUsecase=cUsecase;
		
		fCategory = new XmlCategoryFactory<L,D,C,R,V,U,A,USER>(null,SecurityQuery.exCategory());
		xfView = new XmlViewFactory(SecurityQuery.exViewOld(),null);
		fRole = new XmlRoleFactory<L,D,C,R,V,U,A,USER>(SecurityQuery.exRole(),null);
		fAction = new XmlActionFactory<L,D,C,R,V,U,A,USER>(SecurityQuery.exActionAcl());
		fUsecase = new XmlUsecaseFactory<L,D,C,R,V,U,A,USER>(SecurityQuery.exUsecase());
		
		initViews = AbstractSecurityInit.factoryViews(cL,cD,cCategory,cRole,cView,cUsecase,cAction,cUser,fSecurity);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
	SecurityRestService<L,D,C,R,V,U,A,USER>
		factory(UtilsSecurityFacade fSecurity, final Class<L> cL,final Class<D> cD,final Class<C> cCategory, final Class<V> cView, final Class<R> cRole, final Class<U> cUsecase,final Class<A> cAction,final Class<USER> cUser)
	{
		return new SecurityRestService<L,D,C,R,V,U,A,USER>(fSecurity,cL,cD,cCategory,cView,cRole,cUsecase,cAction,cUser);
	}

	public <STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,DOMAIN extends EjbWithId> Staffs exportStaffs(Class<STAFF> cStaff)
	{
		XmlStaffFactory<L,D,C,R,V,U,A,USER,STAFF,DOMAIN> f = new XmlStaffFactory<L,D,C,R,V,U,A,USER,STAFF,DOMAIN>(SecurityQuery.exStaff());
		
		Staffs staffs = new Staffs();
		
		for(STAFF ejb : fSecurity.all(cStaff))
		{
			staffs.getStaff().add(f.build(ejb));
		}
		
		return staffs;
	}

	@Override public Security exportSecurityViews()
	{
		Security xml = XmlSecurityFactory.build();
				
		for(C category : fSecurity.all(cCategory))
		{
			if(category.getType().equals(UtilsSecurityCategory.Type.view.toString()))
			{
				try
				{
					net.sf.ahtutils.xml.security.Category xmlCat = fCategory.build(category);
					xmlCat.setViews(XmlViewsFactory.build());
					for(V view : fSecurity.allForCategory(cView, cCategory, category.getCode()))
					{
						view = fSecurity.load(cView,view);
						View xView = xfView.build(view);
						xView.setActions(XmlActionsFactory.create());
						for(A action : view.getActions())
						{
							xView.getActions().getAction().add(fAction.create(action));
						}
						xmlCat.getViews().getView().add(xView);
					}
					
					xml.getCategory().add(xmlCat);
				}
				catch (UtilsNotFoundException e) {e.printStackTrace();}
			}
		}		
		return xml;
	}

	@Override public Security exportSecurityRoles()
	{
		Security xml = XmlSecurityFactory.build();

		for(C category : fSecurity.all(cCategory))
		{
			if(category.getType().equals(UtilsSecurityCategory.Type.role.toString()))
			{
				try
				{
					net.sf.ahtutils.xml.security.Category xmlCat = fCategory.build(category);
					xmlCat.setRoles(XmlRolesFactory.build());
					for(R role : fSecurity.allForCategory(cRole, cCategory, category.getCode()))
					{
						role = fSecurity.load(cRole,role);
						Role xRole = fRole.build(role);
						
						xmlCat.getRoles().getRole().add(xRole);
					}
					xml.getCategory().add(xmlCat);
				}
				catch (UtilsNotFoundException e) {e.printStackTrace();}
			}
		}		
		return xml;
	}

	@Override
	public Security exportSecurityUsecases()
	{
		Security xml = XmlSecurityFactory.build();

		for(C category : fSecurity.all(cCategory))
		{
			if(category.getType().equals(UtilsSecurityCategory.Type.usecase.toString()))
			{
				try
				{
					net.sf.ahtutils.xml.security.Category xmlCat = fCategory.build(category);
					xmlCat.setUsecases(XmlUsecasesFactory.build());
					for(U usecase : fSecurity.allForCategory(cUsecase, cCategory, category.getCode()))
					{
						xmlCat.getUsecases().getUsecase().add(fUsecase.build(usecase));
					}
					xml.getCategory().add(xmlCat);
				}
				catch (UtilsNotFoundException e) {e.printStackTrace();}
			}
		}		
		return xml;
	}
	
	public DataUpdate iuSecurityViews(Access views){return initViews.iuViews(views);}
}