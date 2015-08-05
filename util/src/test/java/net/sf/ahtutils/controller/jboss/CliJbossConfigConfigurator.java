package net.sf.ahtutils.controller.jboss;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.ahtutils.test.AhtUtilsTstBootstrap;

public class CliJbossConfigConfigurator
{
	static Log logger = LogFactory.getLog(CliJbossConfigConfigurator.class);
			
	public static void main (String[] args) throws Exception
	{
		AhtUtilsTstBootstrap.init();
	
		String jbo = "/Volumes/ramdisk/jboss-eap-6.3";
		JbossConfigConfigurator jboss = new JbossConfigConfigurator(JbossModuleConfigurator.Product.eap,"6.3",jbo);
//        jboss.addDs();
        jboss.addDbDriver();
//        jboss.write(null);

	}
}