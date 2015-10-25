package net.sf.ahtutils.xml.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlRevisions extends AbstractXmlAuditTest<Revisions>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRevisions.class);
	
	public TestXmlRevisions(){super(Revisions.class);}
	public static Revisions create(boolean withChildren){return (new TestXmlRevisions()).build(withChildren);}
    
    public Revisions build(boolean withChilds)
    {
    	Revisions xml = new Revisions();
    	
    	if(withChilds)
    	{
    		xml.getRevision().add(TestXmlRevision.create(false));
    		xml.getRevision().add(TestXmlRevision.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlRevisions test = new TestXmlRevisions();
		test.saveReferenceXml();
    }
}