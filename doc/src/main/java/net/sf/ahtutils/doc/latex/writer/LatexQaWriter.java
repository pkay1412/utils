package net.sf.ahtutils.doc.latex.writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.latex.OfxMultiLangLatexWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.doc.ofx.qa.OfxQaContainerInputSectionFactory;
import net.sf.ahtutils.doc.ofx.qa.OfxQaGroupSectionFactory;
import net.sf.ahtutils.doc.ofx.qa.OfxSectionQaCategoryFactory;
import net.sf.ahtutils.doc.ofx.qa.OfxSectionQaNfrFactory;
import net.sf.ahtutils.doc.ofx.qa.table.OfxQaAgreementTableFactory;
import net.sf.ahtutils.doc.ofx.qa.table.OfxQaRoleTableFactory;
import net.sf.ahtutils.doc.ofx.qa.table.OfxQaSummaryTableFactory;
import net.sf.ahtutils.doc.ofx.qa.table.OfxQaStaffTableFactory;
import net.sf.ahtutils.doc.ofx.status.OfxStatusTableFactory;
import net.sf.ahtutils.doc.ofx.status.OfxStatusTableFactory.Code;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.qa.Category;
import net.sf.ahtutils.xml.qa.Qa;
import net.sf.ahtutils.xml.security.Staff;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.survey.Survey;

public class LatexQaWriter extends AbstractDocumentationLatexWriter
{	
	final static Logger logger = LoggerFactory.getLogger(LatexQaWriter.class);
	
	private OfxMultiLangLatexWriter ofxMlw;
	
	private OfxQaContainerInputSectionFactory ofContainerInput;
	private OfxSectionQaNfrFactory ofNfr;
	private OfxSectionQaCategoryFactory ofQaFr;
	
	private boolean withResponsible,withOrganisation;
	
	private String imagePathPrefix;
	public String getImagePathPrefix() {return imagePathPrefix;}
	public void setImagePathPrefix(String imagePathPrefix) {this.imagePathPrefix = imagePathPrefix;}

	public LatexQaWriter(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(config,translations,langs,cmm,dsm);
		File baseDir = new File(config.getString(UtilsDocumentation.keyBaseLatexDir));
		ofxMlw = new OfxMultiLangLatexWriter(baseDir,langs,cmm,dsm);
		
		ofContainerInput = new OfxQaContainerInputSectionFactory(config,langs,translations);
		ofNfr = new OfxSectionQaNfrFactory(config,langs,translations);
		ofQaFr = new OfxSectionQaCategoryFactory(config,langs,translations);
		
		imagePathPrefix = null;
		withResponsible = false;
		withOrganisation = false;
	}
	

	
	private List<String> buildHeaderKeys()
	{
		List<String> keys = new ArrayList<String>();
		
		keys.add("auTableQaName");
		keys.add("auTableQaRole");
		if(withResponsible){keys.add("auTableQaResponsibilities");}
		if(withOrganisation){keys.add("auTableQaOrganisation");}
		
		return keys;
	}

	public void writeQaRoles(net.sf.ahtutils.xml.security.Category securityCategory) throws OfxAuthoringException, IOException
	{
		for(String lang : langs)
		{
			writeQaRoles(securityCategory, lang);
		}
	}
	public void writeQaRoles(net.sf.ahtutils.xml.security.Category securityCategory, String lang) throws OfxAuthoringException, IOException
	{
		File f = new File(baseLatexDir+"/"+lang+"/tab/qa/roles.tex");
		
		OfxQaRoleTableFactory fOfx = new OfxQaRoleTableFactory(config,lang,translations);
		Table table = fOfx.build(securityCategory, buildHeaderKeys());
		writeTable(table, f);
	}
	
	public void writeQaStatusResult(Aht aht, String id, String file) throws OfxAuthoringException, UtilsConfigurationException, IOException
	{
		OfxStatusTableFactory fOfx = new OfxStatusTableFactory(config, langs, translations);
		fOfx.renderColumn(Code.icon, true);
		fOfx.renderColumn(Code.name, true,OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.left));
		fOfx.setImagePathPrefix(imagePathPrefix);
		Table table = fOfx.buildLatexTable(id,aht);
		ofxMlw.table("qa/status/"+file, table, "table");
	}
	
	//Team
	public void team(Qa qa,boolean withResponsible, boolean withOrganisation) throws OfxAuthoringException, IOException
	{
		setWithResponsible(withResponsible);
		setWithOrganisation(withOrganisation);
		for(String lang : langs)
		{
			writeQaTeam(qa, lang);
		}
	}
		
	public void writeQaTeam(Qa qa,String lang) throws OfxAuthoringException, IOException
	{
		File f = new File(baseLatexDir+"/"+lang+"/tab/qa/team.tex");
		
		OfxQaStaffTableFactory fOfx = new OfxQaStaffTableFactory(config,lang,translations);
		fOfx.setColumns(OfxQaStaffTableFactory.ColumCode.name,OfxQaStaffTableFactory.ColumCode.role,OfxQaStaffTableFactory.ColumCode.organisationDepartment);
		Table table = fOfx.team(qa.getStaff());
		ofxMlw.table("qa/team", table, "table");
		
		writeTable(table, f);
	}
	
	public void groups(Qa qa) throws OfxAuthoringException, IOException, OfxConfigurationException
	{
		OfxQaGroupSectionFactory f = new OfxQaGroupSectionFactory(config,langs,translations);
		ofxMlw.section(1, "qa/groups",f.build(qa.getGroups()));
	}
	
	// Agreements
	public void writeQaAgreement(Category c,Aht testStatus) throws OfxAuthoringException, IOException
	{
		for(String lang : langs){writeQaAgreement(c, testStatus,lang);}
	}
	public void writeQaAgreement(Category c,Aht testStatus,String lang) throws OfxAuthoringException, IOException
	{
		File f = new File(baseLatexDir+"/"+lang+"/table/qa/agreement/fr/"+c.getCode()+".tex");
		
		OfxQaAgreementTableFactory fOfx = new OfxQaAgreementTableFactory(config,lang,translations);
		fOfx.setImagePathPrefix(imagePathPrefix);
		Table table = fOfx.build(c,testStatus);
		writeTable(table, f);
	}
			
	public void writeQaCategoriesInputs(Qa qa) throws OfxAuthoringException, IOException, OfxConfigurationException
	{
		ofxMlw.section(1,"/qa/fr",ofContainerInput.build(qa,"/section/qa/fr"));
		ofxMlw.section(1,"/qa/agreements",ofContainerInput.build(qa,"/table/qa/agreement/fr"));
		ofxMlw.section(1,"/qa/summary",ofContainerInput.build(qa,"/table/qa/summary/fr"));
	}
		
	public void writeQaFr(Category category) throws OfxAuthoringException, IOException, OfxConfigurationException
	{
		ofxMlw.section(2,"/qa/fr/"+category.getCode(),ofQaFr.build(category));
	}
	
	public void frSummary(Category c,Aht testConditions,Aht resultStatus,String lang) throws OfxAuthoringException, IOException
	{
		OfxQaSummaryTableFactory fOfx = new OfxQaSummaryTableFactory(config,lang,translations);
		fOfx.setImagePathPrefix(imagePathPrefix);
		Table table = fOfx.build(c,testConditions,resultStatus);		
		ofxMlw.table("qa/summary/fr/"+c.getCode(), table);
	}
	
	public void writeQaNfr(Survey surveyQuestions, Survey surveyAnswers, List<Staff> staff) throws OfxAuthoringException, IOException, OfxConfigurationException
	{
		ofxMlw.section(2,"/qa/nfr",ofContainerInput.build(surveyQuestions.getTemplate(),"/section/qa/nfr"));
		
		for(net.sf.ahtutils.xml.survey.Section surveySection : surveyQuestions.getTemplate().getSection())
		{
			ofxMlw.section(2, "/qa/nfr/"+surveySection.getPosition(), ofNfr.build(surveySection,surveyAnswers,staff));
		}
	}
		
	public void setWithResponsible(boolean withResponsible) {this.withResponsible = withResponsible;}
	public void setWithOrganisation(boolean withOrganisation) {this.withOrganisation = withOrganisation;}
	
	public void setUnits(Aht units){ofNfr.setUnits(units);}
	public void setTestConditions(Aht conditions){ofQaFr.setTestConditions(conditions);}
}