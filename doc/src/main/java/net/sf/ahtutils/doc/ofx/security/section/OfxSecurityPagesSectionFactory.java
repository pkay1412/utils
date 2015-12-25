package net.sf.ahtutils.doc.ofx.security.section;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.layout.Container;
import org.openfuxml.content.layout.Font;
import org.openfuxml.content.list.Item;
import org.openfuxml.content.list.List;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlBoxFactory;
import org.openfuxml.factory.xml.layout.XmlContainerFactory;
import org.openfuxml.factory.xml.layout.XmlFontFactory;
import org.openfuxml.factory.xml.layout.XmlWidthFactory;
import org.openfuxml.factory.xml.list.OfxListFactory;
import org.openfuxml.factory.xml.list.OfxListItemFactory;
import org.openfuxml.factory.xml.media.XmlMediaFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.editorial.XmlMarginaliaFactory;
import org.openfuxml.factory.xml.text.OfxEmphasisFactory;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.doc.ofx.util.OfxMultiLangFactory;
import net.sf.ahtutils.xml.security.Role;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;

public class OfxSecurityPagesSectionFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxSecurityPagesSectionFactory.class);

	private OfxEmphasisFactory ofxItalic;
	private Font fMarginText;
		
	public OfxSecurityPagesSectionFactory(Configuration config, String[] langs, Translations translations)
	{
		super(config,langs,translations);
		ofxItalic = new OfxEmphasisFactory(false,true);
		fMarginText = XmlFontFactory.relative(-3);
	}
	
	
	public Section build(net.sf.ahtutils.xml.access.View view) throws OfxAuthoringException
	{
		Section section = XmlSectionFactory.build();
		section.setContainer(true);
		
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.doNotModify(comment);
		section.getContent().add(comment);
		
		if(view.isSetDescriptions())
		{
			section.getContent().addAll(OfxMultiLangFactory.paragraph(langs, view.getDescriptions()));
		}

		if(view.isSetActions() && view.getActions().getAction().size()>0)
		{
			section.getContent().addAll(introductionAction(view));
			List list = OfxListFactory.unordered();
			for(net.sf.ahtutils.xml.access.Action action : view.getActions().getAction())
			{
				Item item = OfxListItemFactory.build();
				for(String lang : langs)
				{
					Paragraph p = XmlParagraphFactory.build(lang);
					
					try
					{
						Lang l = StatusXpath.getLang(action.getLangs(), lang);
						p.getContent().add(ofxItalic.build(l.getTranslation()));
						p.getContent().add(": ");
					}
					catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
					catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
					
					try
					{
						Description d = StatusXpath.getDescription(action.getDescriptions(), lang);
						p.getContent().add(d.getValue());
					}
					catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
					catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
					
					item.getContent().add(p);
				}
				list.getItem().add(item);
			}
			section.getContent().add(list);
		}
		
		JaxbUtil.trace(section);
		return section;
	}
	
	private java.util.List<Serializable> introductionAction(net.sf.ahtutils.xml.access.View view)
	{		
		java.util.List<Serializable> content = new ArrayList<Serializable>();
		
		Paragraph p = new Paragraph();
		p.getContent().add("There are different page specific action defined which require certain access right to be performed.");
		p.getContent().add(" The actual roles and actions are summarzied in the status bar with the grants for the current user.");
		content.add(p);
		
		if(view.getRoles().getRole().size()>0)
		{
			Marginalia m = XmlMarginaliaFactory.build();
			m.getContent().add(XmlBoxFactory.build());
			
			Image image = new Image();
			image.setWidth(XmlWidthFactory.percentage(25));
			image.setMedia(XmlMediaFactory.build("svg.aht-utils/icon/ui/security/shield-red.svg", "icon/ui/security/shield-red"));
			m.getContent().add(image);
			
			for(String lang : langs)
			{
				Paragraph pRoles = XmlParagraphFactory.build(lang);
				pRoles.getContent().add(fMarginText);
				pRoles.getContent().add("Roles for ");
				
				try
				{
					Lang l = StatusXpath.getLang(view.getLangs(), lang);
					pRoles.getContent().add(ofxItalic.build(l.getTranslation()));
					pRoles.getContent().add(":");
				}
				catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
				catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
				
				m.getContent().add(pRoles);
			}
			
			Container c = XmlContainerFactory.build(fMarginText);
			
			
			for(Role role : view.getRoles().getRole())
			{
				c.getContent().addAll(OfxMultiLangFactory.paragraph(langs, role.getLangs()));
			}
			m.getContent().add(c);
			
			content.add(m);
		}
		
		return content;
	}
}