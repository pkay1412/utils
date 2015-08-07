package net.sf.ahtutils.controller.jboss;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.ahtutils.test.AhtUtilsTstBootstrap;
import net.sf.exlp.util.xml.JDomUtil;

import org.jdom2.Element;

public class CliJbossConfigConfigurator
{
	static Log logger = LogFactory.getLog(CliJbossConfigConfigurator.class);
			
	public static void main (String[] args) throws Exception
	{
		AhtUtilsTstBootstrap.init();
	
		String jbo = "/Volumes/ramdisk/jboss-eap-6.3";
		JbossConfigConfigurator jboss = new JbossConfigConfigurator(JbossModuleConfigurator.Product.eap,"6.3",jbo);
        jboss.changeTimeout(10);

        JDomUtil.debug(jboss.getDocument());
	}
	
    static Element getDummyElement(String test)
    {
        Element element = new Element(test);
        return element;
    }
}