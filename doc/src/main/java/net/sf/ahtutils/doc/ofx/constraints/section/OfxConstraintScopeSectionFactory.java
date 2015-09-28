package net.sf.ahtutils.doc.ofx.constraints.section;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.OfxReferenceFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.doc.ofx.constraints.table.OfxConstraintTableFactory;
import net.sf.ahtutils.doc.ofx.util.OfxMultiLangFactory;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.system.ConstraintScope;

public class OfxConstraintScopeSectionFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxConstraintScopeSectionFactory.class);

	private OfxConstraintTableFactory ofTable;
		
	public void setConstraintTypes(Aht constraintTypes) {ofTable.setConstraintTypes(constraintTypes);}
	
	public OfxConstraintScopeSectionFactory(Configuration config, String[] langs, Translations translations)
	{
		super(config,langs,translations);
		ofTable = new OfxConstraintTableFactory(config,langs,translations);
	}
	
	public Section build(ConstraintScope scope) throws OfxAuthoringException
	{
		Section section = XmlSectionFactory.build();
		section.setContainer(true);
		
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.doNotModify(comment);
		section.getContent().add(comment);
		
		section.getContent().addAll(OfxMultiLangFactory.paragraph(langs, scope.getDescriptions()));
		
		Table table = ofTable.build(scope);
		
		Paragraph p = XmlParagraphFactory.build();
		p.getContent().add("All constraints are summarized in Table ");
		p.getContent().add(OfxReferenceFactory.build(table.getId()));
		p.getContent().add(".");
		
		section.getContent().add(p);
		section.getContent().add(table);
		
		return section;
	}
}