package net.sf.ahtutils.controller.jboss;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.jboss.JbossModuleConfigurator.Product;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JDomUtil;


public class JbossConfigConfigurator
{
	final static Logger logger = LoggerFactory.getLogger(JbossConfigConfigurator.class);
	
	private static final String srcBaseDir = "listing.aht-utils/admin/installation/jboss/config";
	
	private MultiResourceLoader mrl;
	
	private File jbossBaseDir;
	
	private Document doc;
	
	public JbossConfigConfigurator(Product product, String version,String jbossDir)
	{
		jbossBaseDir = new File(jbossDir);
		mrl = new MultiResourceLoader();
		
		String reference = srcBaseDir+"/"+product+"/"+version+"/standalone.xml";
		
		if(mrl.isAvailable(reference))
		{
			try {doc = JDomUtil.load(mrl.searchIs(reference));}
			catch (FileNotFoundException e) {e.printStackTrace();}
		}
		else
		{
			try
			{
				String local = "../doc/src/main/resources/"+reference;
				doc = JDomUtil.load(mrl.searchIs(local));
			}
			catch (FileNotFoundException e){logger.warn("You need to set the base-dir to doc in IntelliJ");e.printStackTrace();}
		}	
	}
	
	public void addDs()
	{
        List<Namespace> ns = new ArrayList<Namespace>();
        ns.add(Namespace.getNamespace("ns1", "urn:jboss:domain:1.6"));
        ns.add(Namespace.getNamespace("ns2", "urn:jboss:domain:datasources:1.2" ));

        XPathExpression<Element> xpee = XPathFactory.instance().compile("/ns1:server/ns1:profile/ns2:subsystem/ns2:datasources", Filters.element(), null, ns);
        Element ele = xpee.evaluateFirst(doc);
        ele.setContent(0, getDummyElement("datasourceTest"));
    }


	
	public void addDbDriver()
	{
        List<Namespace> ns = new ArrayList<Namespace>();
        ns.add(Namespace.getNamespace("ns1", "urn:jboss:domain:1.6"));
        ns.add(Namespace.getNamespace("ns2", "urn:jboss:domain:datasources:1.2" ));

        XPathExpression<Element> xpee = XPathFactory.instance().compile("/ns1:server/ns1:profile/ns2:subsystem/ns2:datasources", Filters.element(), null, ns);
        List<Element> ele = xpee.evaluate(doc);
        for(Element e : ele)
            System.out.println(e.getName());
//        ele.get(2).setContent(2,getDummyElement("driverTest"));
	}
	
	public void changePublicInterface()
	{
		//Change <interfaces><interface name="management">
	}
	
	private Element getDummyElement(String test)
	{
		Element element = new Element(test);
		return element;
	}
	
	public void write(File fTarget)
	{
		JDomUtil.debug(doc);
		logger.info("Writing to "+jbossBaseDir.getAbsolutePath());
	}
}