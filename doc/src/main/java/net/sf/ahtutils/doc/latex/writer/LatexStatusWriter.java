package net.sf.ahtutils.doc.latex.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.sf.ahtutils.db.xml.UtilsDbXmlSeedUtil;
import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.doc.ofx.status.OfxStatusTableFactory;
import net.sf.ahtutils.doc.ofx.status.OfxStatusTableFactory.Code;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.dbseed.Db;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.latex.OfxMultiLangLatexWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexStatusWriter extends AbstractDocumentationLatexWriter
{	
	final static Logger logger = LoggerFactory.getLogger(LatexStatusWriter.class);
	
	public static String systemConstraintsType = "db.aht-utils/system/constraints/type.xml";
	
	private OfxMultiLangLatexWriter ofxMlw;
	private UtilsDbXmlSeedUtil seedUtil;
	
	private String seedKey,seedKeyParent;
	private boolean withIcon;
	
	public LatexStatusWriter(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm,DefaultSettingsManager dsm, String dirTable) throws UtilsConfigurationException
	{
		super(config,translations,langs,cmm,dsm);
		File baseDir = new File(config.getString(UtilsDocumentation.keyBaseLatexDir));
		ofxMlw = new OfxMultiLangLatexWriter(baseDir,langs,cmm,dsm);
		ofxMlw.setDirTable(dirTable);
		
		withIcon = false;
		
		String dbSeedFile = config.getString(UtilsDbXmlSeedUtil.configKeySeed);
		logger.debug("Using seed: "+dbSeedFile);
		try
		{
			Db dbSeed = (Db)JaxbUtil.loadJAXB(dbSeedFile, Db.class);
			seedUtil = new UtilsDbXmlSeedUtil(dbSeed);
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	public void buildStatusTable(String seedKeyStatus) throws UtilsConfigurationException
	{
		buildStatusTable(seedKeyStatus, 10,30,40);
	}
	
	public void table(boolean withIcon,Aht ahtStatus,String texName) throws UtilsConfigurationException
	{
		this.withIcon=withIcon;
		if(withIcon){table(ahtStatus,null,texName,15,10,30,40);}
		else{table(ahtStatus,null,texName,10,30,40);}
	}
	public void table(boolean withIcon,String seedKey) throws UtilsConfigurationException
	{
		this.withIcon=withIcon;
		this.seedKey=seedKey;
		this.seedKeyParent=null;
		table(15,10,30,40);
	}
	
	public void buildStatusTable(String seedKey, String seedKeyParent) throws UtilsConfigurationException
	{
		this.withIcon=false;
		this.seedKey=seedKey;
		this.seedKeyParent=seedKeyParent;
		table(15,10,30,40);
	}
	
	public void buildStatusTable(String seedKey, int... colWidths) throws UtilsConfigurationException
	{
		this.withIcon=false;
		this.seedKey=seedKey;
		this.seedKeyParent=null;
		table(colWidths);
	}
	
	public void buildStatusTable(String seedKey, String seedKeyParent, int... colWidths) throws UtilsConfigurationException
	{
		this.withIcon=false;
		this.seedKey=seedKey;
		this.seedKeyParent=seedKeyParent;
		table(colWidths);
	}
	
	private void table(int... colWidths) throws UtilsConfigurationException
	{	
		Aht athStatus;
		Aht ahtParents = null;
		
		try
		{
			athStatus = JaxbUtil.loadJAXB(seedUtil.getExtractName(seedKey), Aht.class);
			if(seedKeyParent!=null){ahtParents = JaxbUtil.loadJAXB(seedUtil.getExtractName(seedKeyParent), Aht.class);}
			
			String texName = seedUtil.getContentName(seedKey);
			texName = texName.substring(0, texName.indexOf(".xml"));
			
			table(athStatus, ahtParents, texName, colWidths);
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	private void table(Aht athStatus, Aht ahtParents, String texName, int... colWidths) throws UtilsConfigurationException
	{	
		try
		{
			logger.info(texName);
			OfxStatusTableFactory fOfx = new OfxStatusTableFactory(config,langs,translations);
			fOfx.setColWidths(colWidths);
			if(ahtParents!=null){fOfx.activateParents(ahtParents);}
			fOfx.renderColumn(Code.icon, withIcon);
			
			Table table = fOfx.buildLatexTable(texName.replaceAll("/", "."),athStatus);
			ofxMlw.table("/status/"+texName, table);
		}
		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
}