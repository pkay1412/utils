package net.sf.ahtutils.doc.ofx.security.table;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Cell;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.doc.ofx.util.OfxMultiLangFactory;
import net.sf.ahtutils.xml.access.Action;
import net.sf.ahtutils.xml.security.Usecase;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class OfxSecurityActionTableFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxSecurityActionTableFactory.class);
	
	private List<String> headerKeys;
		
	public OfxSecurityActionTableFactory(Configuration config, String[] langs, Translations translations)
	{
		super(config,langs,translations);
		
		headerKeys = new ArrayList<String>();
		headerKeys.add("auTableHeadSecurityUsecaseName");
		headerKeys.add("auTableHeadSecurityUsecaseDescription");
	}
	
	public Table build(net.sf.ahtutils.xml.access.View view) throws OfxAuthoringException
	{
		Table table = new Table();
//		table.setId("table.qa.nfr.questions."+section.getPosition());
		table.setSpecification(createSpecifications(view));
		
		table.setTitle(OfxMultiLangFactory.title(langs, view.getLangs()));
			
		table.setContent(createContent(view));
		
		return table;
	}
	
	private Specification createSpecifications(net.sf.ahtutils.xml.access.View view)
	{
		int roles=0;
		if(view.isSetRoles()){roles = view.getRoles().getRole().size();}
		
		Specification spec = new Specification();
		spec.setFloat(XmlFloatFactory.build(false));
		spec.setColumns(new Columns());
		spec.getColumns().getColumn().add(OfxColumnFactory.flex(30,true));
		spec.getColumns().getColumn().add(OfxColumnFactory.flex((100-30-(3*roles)),false));
		for(int i=1;i<=roles;i++)
		{
			spec.getColumns().getColumn().add(OfxColumnFactory.flex(3,false));
		}
		
		return spec;
	}
	
	private Content createContent(net.sf.ahtutils.xml.access.View view) throws OfxAuthoringException
	{
		Head head = new Head();
		head.getRow().add(createHeaderRow(headerKeys));
		if(view.isSetRoles())
		{
			for(int i=1;i<=view.getRoles().getRole().size();i++)
			{
				head.getRow().get(0).getCell().add(OfxCellFactory.createParagraphCell(i));
			}
		}
		
		Body body = new Body();
		for(Action uc : view.getActions().getAction())
		{
			body.getRow().add(createRow(view,uc));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createRow(net.sf.ahtutils.xml.access.View view, Action action) throws OfxAuthoringException
	{
		Row row = new Row();
		
		row.getCell().add(OfxMultiLangFactory.cell(langs, action.getLangs()));
		row.getCell().add(OfxMultiLangFactory.cell(langs, action.getDescriptions()));
		
		if(view.isSetRoles())
		{
			for(int i=1;i<=view.getRoles().getRole().size();i++)
			{
				row.getCell().add(OfxCellFactory.createParagraphCell("X"));
			}
		}
		
		return row;
	}
}