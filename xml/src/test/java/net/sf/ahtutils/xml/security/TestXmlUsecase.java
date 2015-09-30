package net.sf.ahtutils.xml.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlUsecase extends AbstractXmlSecurityTest<Usecase>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUsecase.class);
	
	public TestXmlUsecase(){super(Usecase.class);}
	public static Usecase create(boolean withChildren){return (new TestXmlUsecase()).build(withChildren);}
    
    public Usecase build(boolean withChilds)
    {
    	Usecase xml = new Usecase();
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
		TestXmlUsecase test = new TestXmlUsecase();
		test.saveReferenceXml();
    }
}