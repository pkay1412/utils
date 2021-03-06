package net.sf.ahtutils.xml.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlExceptions extends AbstractXmlSyncTest<Exceptions>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlExceptions.class);
	
	public TestXmlExceptions(){super(Exceptions.class);}
	public static Exceptions create(boolean withChildren){return (new TestXmlExceptions()).build(withChildren);}
    
    public Exceptions build(boolean withChilds)
    {
    	Exceptions xml = new Exceptions();

    	if(withChilds)
    	{
    		xml.getException().add(TestXmlException.create(false));
    		xml.getException().add(TestXmlException.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();	
		TestXmlExceptions test = new TestXmlExceptions();
		test.saveReferenceXml();
    }
}