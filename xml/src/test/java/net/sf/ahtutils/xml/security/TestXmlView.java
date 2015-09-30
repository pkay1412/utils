package net.sf.ahtutils.xml.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlView extends AbstractXmlSecurityTest<View>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlView.class);
	
	public TestXmlView(){super(View.class);}
	public static View create(boolean withChildren){return (new TestXmlView()).build(withChildren);}
    
    public View build(boolean withChilds)
    {
    	View xml = new View();
    	xml.setId(123);
    	xml.setIndex(456);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlView test = new TestXmlView();
		test.saveReferenceXml();
    }
}