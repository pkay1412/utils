package net.sf.ahtutils.doc.latex.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.graph.Node;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.ofx.menu.OfxMenuTreeFactory;
import net.sf.ahtutils.doc.ofx.security.list.OfxSecurityCategoryListFactory;
import net.sf.ahtutils.doc.ofx.security.table.OfxRoleTableFactory;
import net.sf.ahtutils.doc.ofx.security.table.OfxViewTableFactory;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.security.Security;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

public class LatexSecurityWriter extends AbstractDocumentationLatexWriter
{	
	final static Logger logger = LoggerFactory.getLogger(LatexSecurityWriter.class);
	
	private final static String dirTabs = "tab/security";
	private final static String dirDescriptions = "description/security";
		
	private OfxSecurityCategoryListFactory ofSecurityCategoryList;
	
	private List<String> headerKeysViews;
	
	public LatexSecurityWriter(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(config,translations,langs,cmm,dsm);
		
		ofSecurityCategoryList = new OfxSecurityCategoryListFactory(config,langs,translations,cmm,dsm);
		
		headerKeysViews = new ArrayList<String>();
		headerKeysViews.add("auSecurityTableViewName");
		headerKeysViews.add("auSecurityTableViewDescription");
	}
	
	public void createViewTabs(String xmlFile) throws UtilsConfigurationException
	{
		logger.info("Creating view tables from "+xmlFile+" to LaTex");
		try
		{
			Access access = JaxbUtil.loadJAXB(xmlFile, Access.class);
			for(Category category : access.getCategory())
			{
				for(String lang : langs)
				{
					File f = new File(baseLatexDir,lang+"/"+dirTabs+"/views-"+category.getCode()+".tex");
					OfxViewTableFactory fOfx = new OfxViewTableFactory(config,lang,translations);
					String content = fOfx.buildLatexViewTable(category,headerKeysViews);
					StringIO.writeTxt(f, content);
				}
			}
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	public void writeMenuTrees(String xmlMenu, String xmlViews) throws UtilsConfigurationException
	{
		try
		{
			logger.info("Creating menu trees from "+xmlMenu+" to LaTex");
			Menu menu = JaxbUtil.loadJAXB(xmlMenu,Menu.class);
			Access access = JaxbUtil.loadJAXB(xmlViews, Access.class);
			for(String lang : langs)
			{
				OfxMenuTreeFactory f = new OfxMenuTreeFactory(config,lang,translations);
				org.openfuxml.content.graph.Tree tree = f.build(menu,access);
				JaxbUtil.trace(tree);
				for(Node node : tree.getNode().getNode())
				{
					logger.info(node.getCode());
				}
			}
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
	}

	@Deprecated
	public void createRoleTabs(String xmlFile) throws UtilsConfigurationException
	{
		logger.info("Creating view tables from "+xmlFile+" to LaTex");
		String[] headerKeysRoles = {"auSecurityTableRoleName","auSecurityTableRoleDescription"};
		try
		{
			Access access = JaxbUtil.loadJAXB(xmlFile, Access.class);
			for(Category category : access.getCategory())
			{
				for(String lang : langs)
				{
					File f = new File(baseLatexDir,lang+"/"+dirTabs+"/role-"+category.getCode()+".tex");
					OfxRoleTableFactory fOfx = new OfxRoleTableFactory(config,lang,translations);
					String content = fOfx.saveDescription(category,headerKeysRoles);
					StringIO.writeTxt(f, content);
				}
			}
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	public void createRoleTabsSecurity(String xmlFile) throws UtilsConfigurationException
	{
		logger.info("Creating view tables from "+xmlFile+" to LaTex");
		String[] headerKeysRoles = {"auSecurityTableRoleName","auSecurityTableRoleDescription"};
		try
		{
			Security access = JaxbUtil.loadJAXB(xmlFile, Security.class);
			for(net.sf.ahtutils.xml.security.Category category : access.getCategory())
			{
				for(String lang : langs)
				{
					File f = new File(baseLatexDir,lang+"/"+dirTabs+"/role-"+category.getCode()+".tex");
					OfxRoleTableFactory fOfx = new OfxRoleTableFactory(config,lang,translations);
					String content = fOfx.saveDescription(category,headerKeysRoles);
					StringIO.writeTxt(f, content);
				}
			}
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	@Deprecated
	public void writeCategoryDescriptionsOld(String xmlFile, String extractId) throws UtilsConfigurationException
	{
		logger.info("Creating descriptions from "+xmlFile+" to LaTex");
		for(String lang : langs)
		{
			File f = new File(baseLatexDir,lang+"/"+dirDescriptions+"/"+extractId+".tex");
			
			try
			{
				logger.debug("Converting "+xmlFile+" to LaTex ("+f.getAbsolutePath());
				Access access = JaxbUtil.loadJAXB(xmlFile, Access.class);
				OfxSecurityCategoryListFactory latexFactory = new OfxSecurityCategoryListFactory(config,lang,translations,cmm,dsm);
				String content = latexFactory.saveDescription(access.getCategory());
				StringIO.writeTxt(f, content);
			}
			catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
			catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
		}
	}
	
	public void categoryList(String xmlFile, String extractId) throws UtilsConfigurationException, FileNotFoundException
	{
		logger.info("Creating descriptions from "+xmlFile+" to LaTex");
		
		Security security = JaxbUtil.loadJAXB(xmlFile, Security.class);
		org.openfuxml.content.list.List list = ofSecurityCategoryList.list(security.getCategory());
		JaxbUtil.info(list);
		
		for(String lang : langs)
		{
			File f = new File(baseLatexDir,lang+"/"+dirDescriptions+"/"+extractId+".tex");
			
			try
			{
				logger.info("Converting "+xmlFile+" to LaTex ("+f.getAbsolutePath());
				Security access = JaxbUtil.loadJAXB(xmlFile, Security.class);
				OfxSecurityCategoryListFactory latexFactory = new OfxSecurityCategoryListFactory(config,lang,translations,cmm,dsm);
				String content = latexFactory.saveDescriptionSec(access.getCategory());
				StringIO.writeTxt(f, content);
			}
			catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
			catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
		}
	}
}