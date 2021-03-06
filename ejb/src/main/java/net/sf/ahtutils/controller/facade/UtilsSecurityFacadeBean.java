package net.sf.ahtutils.controller.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityWithCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsStaff;
import net.sf.ahtutils.interfaces.model.security.UtilsStaffPool;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class UtilsSecurityFacadeBean<L extends UtilsLang,
D extends UtilsDescription,
C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
USER extends UtilsUser<L,D,C,R,V,U,A,USER>> extends UtilsFacadeBean implements UtilsSecurityFacade<L,D,C,R,V,U,A,USER>
{	
	public UtilsSecurityFacadeBean(EntityManager em)
	{
		super(em);
	}

	@Override public R load(Class<R> cRole, R role)
	{
		role = em.find(cRole, role.getId());
		role.getUsers().size();
		return role;
	}
	
	@Override public V load(Class<V> cView, V view)
	{
		view = em.find(cView, view.getId());
		view.getActions().size();
		return view;
	}
	
	@Override public List<V> allViewsForUser(Class<USER> clUser, USER user)
	{
		user = em.find(clUser, user.getId());
		Map<Long,V> views = new HashMap<Long,V>();
		for(R role : user.getRoles())
		{
			for(V rView : role.getViews())
			{
				views.put(rView.getId(), rView);
			}
			for(U u : role.getUsecases())
			{
				for(V uView : u.getViews())
				{
					views.put(uView.getId(), uView);
				}
			}
		}
		return new ArrayList<V>(views.values());
	}
	
	@Override public List<R> rolesForView(Class<V> cView, V view)
	{	
		return rolesForView(cView,null,view,null);
	}
	
	@Override public List<R> rolesForView(Class<V> cView, Class<USER> cUser, V view, USER user)
	{		
		view = em.find(cView, view.getId());
		Map<Long,R> roles = new HashMap<Long,R>();
		for(R r : view.getRoles())
		{
			if(!roles.containsKey(r.getId())){roles.put(r.getId(), r);}
		}
		for(U u : view.getUsecases())
		{
			for(R r : u.getRoles())
			{
				if(!roles.containsKey(r.getId())){roles.put(r.getId(), r);}
			}
		}
		return new ArrayList<R>(roles.values());
	}
	
	@Override public List<R> rolesForAction(Class<A> cAction, A action)
	{	
		return rolesForAction(cAction,null,action,null);
	}
	
	@Override public List<R> rolesForAction(Class<A> cAction, Class<USER> cUser, A action, USER user)
	{		
		action = em.find(cAction, action.getId());
		Map<Long,R> roles = new HashMap<Long,R>();
		for(R r : action.getRoles())
		{
			if(!roles.containsKey(r.getId())){roles.put(r.getId(), r);}
		}
		for(U u : action.getUsecases())
		{
			for(R r : u.getRoles())
			{
				if(!roles.containsKey(r.getId())){roles.put(r.getId(), r);}
			}
		}
		return new ArrayList<R>(roles.values());
	}
	
	@Override public List<A> allActionsForUser(Class<USER> clUser, USER user)
	{
		user = em.find(clUser, user.getId());
		Map<Long,A> actions = new HashMap<Long,A>();
		for(R r : user.getRoles())
		{
			for(A rAction : r.getActions())
			{
				actions.put(rAction.getId(), rAction);
			}
			for(U u : r.getUsecases())
			{
				for(A uAction : u.getActions())
				{
					actions.put(uAction.getId(), uAction);
				}
			}
		}
		return new ArrayList<A>(actions.values());
	}
	
	@Override public List<R> allRolesForUser(Class<USER> clUser, USER user)
	{
		user = em.find(clUser, user.getId());
		return user.getRoles();
	}
	
	@Override public <WC extends UtilsSecurityWithCategory<L,D,C,R,V,U,A,USER>> List<WC> allForCategory(Class<WC> clWc, Class<C> clC, String code) throws UtilsNotFoundException
	{
		if(logger.isTraceEnabled())
		{
			logger.info(clWc.getName());
			logger.info(UtilsSecurityRole.class.getSimpleName()+" ");
			logger.info(UtilsSecurityRole.class.getSimpleName()+" "+clWc.isAssignableFrom(UtilsSecurityRole.class));
			logger.info(UtilsSecurityView.class.getSimpleName()+" "+clWc.isAssignableFrom(UtilsSecurityView.class));
			logger.info(UtilsSecurityUsecase.class.getSimpleName()+" "+clWc.isAssignableFrom(UtilsSecurityUsecase.class));
		}
	
		String type = null;
		if(clWc.getSimpleName().contains("Usecase")){type=UtilsSecurityCategory.Type.usecase.toString();}
		else if(clWc.getSimpleName().contains("Role")){type=UtilsSecurityCategory.Type.role.toString();}
		else if(clWc.getSimpleName().contains("View")){type=UtilsSecurityCategory.Type.view.toString();}
		
		C category = this.fByTypeCode(clC, type, code);
		
		return this.allOrderedPositionParent(clWc,category);
	}	
	
	@Override
	public <S extends UtilsStaffPool<L,D,C,R,V,U,A,P,E,USER>, P extends EjbWithId, E extends EjbWithId>
		List<S>	fStaffPool(Class<S> clStaff, P pool)
	{return allForParent(clStaff, "pool", pool);}
	
	
	// STAFF
	@Override
	public < S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, DOMAIN extends EjbWithId>
		List<S> fStaffU(Class<S> clStaff, USER user)
	{return allForParent(clStaff, "user", user);}
	
	@Override
	public <S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,  DOMAIN extends EjbWithId>
		List<S> fStaffR(Class<S> clStaff, R role)
	{return allForParent(clStaff, "role", role);}
	
	@Override
	public < S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,  DOMAIN extends EjbWithId>
		List<S> fStaffD(Class<S> clStaff, DOMAIN domain)
	{return allForParent(clStaff, "domain", domain);}
	
	@Override
	public < S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,  DOMAIN extends EjbWithId>
		List<S> fStaffUR(Class<S> clStaff, USER user, R role)
	{return allForParent(clStaff, "user", user, "role",role);}
	
	@Override
	public < S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,  DOMAIN extends EjbWithId>
		List<S> fStaffUD(Class<S> clStaff, USER user, DOMAIN domain)
	{return allForParent(clStaff, "user", user, "domain",domain);}
	
	@Override
	public < S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,  DOMAIN extends EjbWithId> List<S> fStaffRD(Class<S> clStaff, R role, DOMAIN domain)
	{return allForParent(clStaff, "role", role, "domain",domain);}
	
	@Override
	public < S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,  DOMAIN extends EjbWithId> S fStaff(Class<S> clStaff, USER user, R role, DOMAIN domain) throws UtilsNotFoundException
	{
		return oneForParents(clStaff,"user",user,"role",role,"domain",domain);
	}
	
	@Override public void grantRole(Class<USER> clUser, Class<R> clRole, USER user, R role, boolean grant)
	{
		logger.trace("grantRole u:"+user.toString()+" r:"+role.toString()+" grand:"+grant);
		user = em.find(clUser,user.getId());
		role = em.find(clRole,role.getId());
		if(grant){addRole(clUser,clRole,user, role);}
		else{rmRole(clUser,clRole,user, role);}
		em.merge(user);
	}
	
	private 
	void addRole(Class<USER> clUser, Class<R> clRole, USER user, R role)
	{
		logger.trace("addRole u:"+user.toString()+" r:"+role.toString());
		if(!user.getRoles().contains(role)){user.getRoles().add(role);}
//		if(!role.getUsers().contains(user)){role.getUsers().add(user);}
		user = em.merge(user);
	}
	
	private 
	void rmRole(Class<USER> clUser, Class<R> clRole, USER user, R role)
	{
		if(user.getRoles().contains(role)){user.getRoles().remove(role);}
		if(role.getUsers().contains(user)){role.getUsers().remove(user);}
		user = em.merge(user);
		role = em.merge(role);
	}

	@Override
	public 
	boolean hasRole(Class<USER> clUser, Class<R> clRole, USER user, R role)
	{
		if(user==null || role==null){return false;}
		for(R r: allRolesForUser(clUser, user))
		{
			if(r.getId() == role.getId()){return true;}
		}
		return false;
	}

	@Override
	public <S extends UtilsStaff<L, D, C, R, V, U, A, USER, DOMAIN>, DOMAIN extends EjbWithId> List<DOMAIN> fDomains(Class<V> cView, Class<S> cStaff, USER user, V view)
	{
		List<R> roles = new ArrayList<R>();
		List<DOMAIN> result = new ArrayList<DOMAIN>();
		
		view = this.find(cView, view);
		roles.addAll(view.getRoles());
		
//		logger.info(StringUtil.stars());

		for(U u : view.getUsecases())
		{
			for(R role : u.getRoles())
			{
//				logger.info("\t\tR"+role.toString());
				if(!roles.contains(role))
				{
					roles.add(role);
				}
			}
		}
		
//		logger.info("Roles to check: "+roles.size());
		
		for(R role : roles)
		{
//			logger.info("\tR"+role.toString());
			for(S staff : this.fStaffUR(cStaff, user, role))
			{
				if(!result.contains(staff.getDomain()))
				{
					result.add(staff.getDomain());
				}
			}
		}
		return result;
	}
}
