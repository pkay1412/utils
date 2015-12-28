package net.sf.ahtutils.model.ejb.security;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.model.ejb.status.AhtUtilsDescription;
import net.sf.ahtutils.model.ejb.status.AhtUtilsLang;
import net.sf.ahtutils.model.ejb.user.AhtUtilsUser;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
@EjbErNode(name="Usecase",category="security")

public class AhtUtilsSecurityUsecase implements EjbWithCode,Serializable,EjbRemoveable,EjbPersistable,
			UtilsSecurityUsecase<AhtUtilsLang,AhtUtilsDescription,AhtUtilsSecurityCategory,AhtUtilsSecurityRole,AhtUtilsSecurityView,AhtUtilsSecurityUsecase,AhtUtilsSecurityAction,AhtUtilsUser>
{
	public static final long serialVersionUID=1;
	
	public static enum Code {test}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	@NotNull @ManyToOne
	private AhtUtilsSecurityCategory category;
	public AhtUtilsSecurityCategory getCategory() {return category;}
	public void setCategory(AhtUtilsSecurityCategory category) {this.category = category;}
	
	@NotNull
	private String code;
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	@Override public String resolveParentAttribute() {return "category";}
	
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}
	
	private Boolean documentation;
	@Override public Boolean isDocumentation() {return documentation;}
	@Override public void setDocumentation(Boolean documentation) {this.documentation = documentation;}

	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, AhtUtilsLang> name;
	public Map<String, AhtUtilsLang> getName() {return name;}
	public void setName(Map<String, AhtUtilsLang> name) {this.name = name;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, AhtUtilsDescription> description;
	public Map<String, AhtUtilsDescription> getDescription() {return description;}
	public void setDescription(Map<String, AhtUtilsDescription> description) {this.description = description;}
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<AhtUtilsSecurityAction> actions;
	public List<AhtUtilsSecurityAction> getActions() {if(actions==null){actions = new ArrayList<AhtUtilsSecurityAction>();}return actions;}
	public void setActions(List<AhtUtilsSecurityAction> actions) {this.actions = actions;}
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<AhtUtilsSecurityView> views;
	public List<AhtUtilsSecurityView> getViews() {if(views==null){views = new ArrayList<AhtUtilsSecurityView>();}return views;}
	public void setViews(List<AhtUtilsSecurityView> views) {this.views = views;}
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<AhtUtilsSecurityRole> roles;
	@Override public List<AhtUtilsSecurityRole> getRoles() {if(roles==null){roles = new ArrayList<AhtUtilsSecurityRole>();}return roles;}
	@Override public void setRoles(List<AhtUtilsSecurityRole> roles) {this.roles = roles;}
	
	
	public boolean equals(Object object)
	{
        return (object instanceof AhtUtilsSecurityUsecase) ? id == ((AhtUtilsSecurityUsecase) object).getId() : (object == this);
    }
}