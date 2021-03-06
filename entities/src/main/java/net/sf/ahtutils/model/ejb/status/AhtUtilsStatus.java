package net.sf.ahtutils.model.ejb.status;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
@DiscriminatorValue("generic")
@Table(name = "UtilsStatus", uniqueConstraints = @UniqueConstraint(columnNames = {"type","code"}))
public class AhtUtilsStatus implements UtilsStatus<AhtUtilsStatus,AhtUtilsLang,AhtUtilsDescription>,EjbRemoveable,Serializable
{
	private static final long serialVersionUID = 1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long id;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	protected Map<String, AhtUtilsLang> name;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	protected Map<String, AhtUtilsDescription> description;
		
	protected String code;
	
	private String symbol;
	@Override public String getSymbol(){return symbol;}
	@Override public void setSymbol(String symbol){this.symbol = symbol;}

	protected boolean visible;
	
	protected String image,imageAlt;
	
	protected String style;

	protected int position;

	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<
	
	public void setId(long id) {this.id = id;}
	public long getId() {return id;}
	
	public Map<String, AhtUtilsLang> getName(){if(name==null){name = new Hashtable<String, AhtUtilsLang>();}return name;}
	public void setName(Map<String, AhtUtilsLang> name) {this.name = name;}
	
	public Map<String, AhtUtilsDescription> getDescription() {if(description==null){description = new Hashtable<String, AhtUtilsDescription>();}return description;}
	public void setDescription(Map<String, AhtUtilsDescription> description) {this.description = description;}
	
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	public boolean isVisible() {return visible;}
	public void setVisible(boolean visible) {this.visible = visible;}
	
	public String getImage() {return image;}
	public void setImage(String image) {this.image = image;}
	
	@Override public String getImageAlt() {return imageAlt;}
	@Override public void setImageAlt(String imageAlt) {this.imageAlt=imageAlt;}
	
	public int getPosition() {return position;}
	public void setPosition(int position) {this.position = position;}
	
	public String getStyle() {return style;}
	public void setStyle(String style) {this.style = style;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" code="+code);
		return sb.toString();
	}
	
	@ManyToOne
	protected AhtUtilsStatus parent;
	public <P extends EjbWithCode> P getParent() {return (P)parent;}
	public <P extends EjbWithCode> void setParent(P parent) {this.parent=(AhtUtilsStatus)parent;}
}