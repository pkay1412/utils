package net.sf.ahtutils.xml.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlBcc extends AbstractXmlMailTest<Bcc>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlBcc.class);
	
	public TestXmlBcc(){super(Bcc.class);}
	public static Bcc create(boolean withChildren){return (new TestXmlBcc()).build(withChildren);}
    
    public Bcc build(boolean withChilds)
    {
    	Bcc xml = new Bcc();

    	if(withChilds)
    	{
    		xml.getEmailAddress().add(TestXmlEmailAddress.create(false));
    		xml.getEmailAddress().add(TestXmlEmailAddress.create(false));
    	}
    	    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlBcc test = new TestXmlBcc();
		test.saveReferenceXml();
    }
}