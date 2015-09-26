package net.sf.ahtutils.doc.ofx.security.table;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.doc.ofx.util.OfxMultiLangFactory;
import net.sf.ahtutils.xml.security.Category;
import net.sf.ahtutils.xml.security.Usecase;
import net.sf.ahtutils.xml.security.Usecases;
import net.sf.ahtutils.xml.status.Translations;

public class OfxSecurityUsecaseTableFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxSecurityUsecaseTableFactory.class);
	
	private List<String> headerKeys;
		
	public OfxSecurityUsecaseTableFactory(Configuration config, String[] langs, Translations translations)
	{
		super(config,langs,translations);
		
		headerKeys = new ArrayList<String>();
		headerKeys.add("auTableHeadSecurityUsecaseName");
		headerKeys.add("auTableHeadSecurityUsecaseDescription");
	}
	
	public Table build(Category category) throws OfxAuthoringException
	{
		Table table = new Table();
//		table.setId("table.qa.nfr.questions."+section.getPosition());
		table.setSpecification(createSpecifications());
		
		table.setTitle(OfxMultiLangFactory.title(langs, category.getLangs()));
			
		table.setContent(createContent(category.getUsecases()));
		
		return table;
	}
	
	private Specification createSpecifications()
	{
		Specification spec = new Specification();
		spec.setFloat(XmlFloatFactory.build(false));
		spec.setColumns(new Columns());
		spec.getColumns().getColumn().add(OfxColumnFactory.flex(30,true));
		spec.getColumns().getColumn().add(OfxColumnFactory.flex(70,false));
		
		return spec;
	}
	
	private Content createContent(Usecases usecases)
	{
		Head head = new Head();
		head.getRow().add(createHeaderRow(headerKeys));
		
		Body body = new Body();
		for(Usecase uc : usecases.getUsecase())
		{
			body.getRow().add(createRow(uc));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createRow(Usecase uc)
	{
		Row row = new Row();
		
		row.getCell().add(OfxMultiLangFactory.cell(langs, uc.getLangs()));
		row.getCell().add(OfxMultiLangFactory.cell(langs, uc.getDescriptions()));
		
		return row;
	}
}