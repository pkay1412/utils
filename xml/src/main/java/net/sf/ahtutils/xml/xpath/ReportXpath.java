package net.sf.ahtutils.xml.xpath;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.xml.AhtUtilsNsPrefixMapper;
import net.sf.ahtutils.xml.report.Info;
import net.sf.ahtutils.xml.report.Jr;
import net.sf.ahtutils.xml.report.Media;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.ahtutils.xml.report.Template;
import net.sf.ahtutils.xml.report.Templates;
import net.sf.ahtutils.xml.report.XlsDefinition;
import net.sf.ahtutils.xml.report.XlsWorkbook;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.jxpath.JXPathContext;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportXpath
{
	final static Logger logger = LoggerFactory.getLogger(ReportXpath.class);
	
	public static synchronized Media getMedia(List<Media> list, String type) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Report report = new Report();
		report.getMedia().addAll(list);
		JXPathContext context = JXPathContext.newContext(report);
		
		@SuppressWarnings("unchecked")
		List<Media> listResult = (List<Media>)context.selectNodes("media[@type='"+type+"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Media.class.getSimpleName()+" for type="+type);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Media.class.getSimpleName()+" for type="+type);}
		return listResult.get(0);
	}
	
	public static synchronized Report getReport(Reports reports, String id) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(reports);
		
		@SuppressWarnings("unchecked")
		List<Report> listResult = (List<Report>)context.selectNodes("report[@id='"+id+"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Report.class.getSimpleName()+" for id="+id);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Report.class.getSimpleName()+" for id="+id);}
		return listResult.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized ArrayList<Jr> getSubreports(Reports reports, String id, String format)
	{
		JXPathContext context = JXPathContext.newContext(reports);
		ArrayList<Jr> subReports = new ArrayList<Jr>();
		subReports.addAll((ArrayList<Jr>) context.selectNodes("//jr[parent::media/parent::report/@id='" +id +"' and parent::media/@type='" +format +"' and @type='sr']"));
		return subReports;
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized Jr getMr(Reports reports, String id, String format) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("//jr[@type='mr' ");
		sb.append("and parent::media/parent::report/@id='").append(id).append("' ");
		sb.append("and parent::media/@type='").append(format).append("']");
		
		JXPathContext context = JXPathContext.newContext(reports);
		List<Jr> listResult = (List<Jr>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Report.class.getSimpleName()+" for id="+id);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Report.class.getSimpleName()+" for id="+id);}
		return listResult.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized Jr getSr(Reports reports, String id, String subreport, String format) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("//jr[@type='sr' ");
		sb.append("and parent::media/parent::report/@id='").append(id).append("' ");
		sb.append("and @name='").append(subreport).append("' ");
		sb.append("and parent::media/@type='").append(format).append("']");
		
		JXPathContext context = JXPathContext.newContext(reports);
		List<Jr> listResult = (List<Jr>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Report.class.getSimpleName()+" for id="+id);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Report.class.getSimpleName()+" for id="+id);}
		return listResult.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized Template getTemplate(Templates templates, String id) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(templates);
		List<Template> listResult = (List<Template>)context.selectNodes("//template[@id='" +id +"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Template.class.getSimpleName()+" for id="+id);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Template.class.getSimpleName()+" for id="+id);}
		return listResult.get(0);
	}
	
	public static Info getReportInfo(Document jdomDocument)
	{
		XPathExpression<Element> xpath = XPathFactory.instance().compile("/report/info", Filters.element());
		Element eInfo = xpath.evaluateFirst(jdomDocument);
		
		eInfo = JDomUtil.setNameSpaceRecursive(eInfo, AhtUtilsNsPrefixMapper.nsReport);
		Info info = JDomUtil.toJaxb(eInfo, Info.class);
		return info;
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized XlsWorkbook getWorkbook(XlsDefinition definition, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(definition);
		List<XlsWorkbook> listResult = (List<XlsWorkbook>)context.selectNodes("//xlsWorkbook[@code='" +code +"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+XlsWorkbook.class.getSimpleName()+" for code="+code);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+XlsWorkbook.class.getSimpleName()+" for code="+code);}
		return listResult.get(0);
	}
}