package net.sf.ahtutils.doc.ofx.qa;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.doc.ofx.qa.table.OfxQaNfrQuestionTableFactory;
import net.sf.ahtutils.xml.status.Translations;

public class OfxSectionQaNfrFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxSectionQaNfrFactory.class);
	
	private OfxQaNfrQuestionTableFactory ofxTableQuestions;
	
	public OfxSectionQaNfrFactory(Configuration config, String lang, Translations translations)
	{
		this(config,new String[] {lang},translations);
	}
	public OfxSectionQaNfrFactory(Configuration config, String[] langs, Translations translations)
	{
		super(config,langs,translations);
		ofxTableQuestions = new OfxQaNfrQuestionTableFactory(config,langs,translations);
	}
	
	public Section build(net.sf.ahtutils.xml.survey.Section surveySection) throws OfxAuthoringException
	{
		Section xml = XmlSectionFactory.build();

		xml.getContent().add(XmlTitleFactory.build(surveySection.getDescription().getValue()));
		
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.doNotModify(comment);
		xml.getContent().add(comment);
		
		for(net.sf.ahtutils.xml.survey.Section ss : surveySection.getSection())
		{
			xml.getContent().add(section(ss));
		}
		
		return xml;
	}
	
	public Section section(net.sf.ahtutils.xml.survey.Section section) throws OfxAuthoringException
	{
		Section xml = XmlSectionFactory.build();

		xml.getContent().add(XmlTitleFactory.build(section.getDescription().getValue()));
		
		if(section.isSetRemark()){xml.getContent().add(XmlParagraphFactory.text(section.getRemark().getValue()));}
		
		if(section.isSetQuestion()){xml.getContent().add(ofxTableQuestions.build(section));}
		
		return xml;
	}
}