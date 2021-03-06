package net.sf.ahtutils.doc.latex.writer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.io.StringIO;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractDocumentationLatexWriter
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractDocumentationLatexWriter.class);
	
	protected Configuration config;
	protected String baseLatexDir;
	
	protected Translations translations;
	protected String[] langs;
	
	protected CrossMediaManager cmm;
	protected DefaultSettingsManager dsm;
	
	public AbstractDocumentationLatexWriter(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		this.config=config;
		this.translations=translations;
		this.langs=langs;
		this.cmm=cmm;
		this.dsm=dsm;
		
		baseLatexDir=config.getString(UtilsDocumentation.keyBaseLatexDir);
	}
	
	protected void writeTable(Table table, File f) throws OfxAuthoringException, IOException
	{
		LatexTableRenderer tableRenderer = new LatexTableRenderer(cmm,dsm);
		tableRenderer.setPreBlankLine(false);
		tableRenderer.render(table);
		
		StringWriter sw = new StringWriter();
		tableRenderer.write(sw);
		StringIO.writeTxt(f, sw.toString());
	}
	
	protected void writeSection(Section section, File f) throws OfxAuthoringException, IOException, OfxConfigurationException {writeSection(1, section, f);}
	protected void writeSection(int sectionLevel, Section section, File f) throws OfxAuthoringException, IOException, OfxConfigurationException
	{
		LatexSectionRenderer sectionRenderer = new LatexSectionRenderer(cmm,dsm,sectionLevel,new LatexPreamble(cmm,dsm));
		sectionRenderer.render(section);
		
		StringWriter sw = new StringWriter();
		sectionRenderer.write(sw);
		logger.info("Writing to : "+f.getAbsolutePath());
		StringIO.writeTxt(f, sw.toString());
	}
}