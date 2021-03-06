package net.sf.ahtutils.xml.aht;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.finance.TestXmlCurrency;
import net.sf.ahtutils.xml.security.TestXmlUser;
import net.sf.ahtutils.xml.status.TestXmlStatus;

public class TestXmlAht extends AbstractXmlAhtTest<Aht>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAht.class);
	
	public TestXmlAht(){super(Aht.class);}
	public static Aht create(boolean withChildren){return (new TestXmlAht()).build(withChildren);}
    
    public Aht build(boolean withChilds)
    {
    	Aht xml = new Aht();
        	
    	if(withChilds)
    	{
    		xml.getStatus().add(TestXmlStatus.create(false));xml.getStatus().add(TestXmlStatus.create(false));
    		xml.getCurrency().add(TestXmlCurrency.create(false));xml.getCurrency().add(TestXmlCurrency.create(false));
    		xml.getUser().add(TestXmlUser.create(false));xml.getUser().add(TestXmlUser.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlAht test = new TestXmlAht();
		test.saveReferenceXml();
    }
}