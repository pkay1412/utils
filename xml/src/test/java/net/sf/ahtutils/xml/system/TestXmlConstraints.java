package net.sf.ahtutils.xml.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlConstraints extends AbstractXmlSystemTest<Constraints>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlConstraints.class);
	
	public TestXmlConstraints(){super(Constraints.class);}
	public static Constraints create(boolean withChildren){return (new TestXmlConstraints()).build(withChildren);}
        
    public Constraints build(boolean withChilds)
    {
    	Constraints xml = new Constraints();

    	if(withChilds)
    	{
    		xml.getConstraintScope().add(TestXmlConstraintScope.create(false));
    		xml.getConstraintScope().add(TestXmlConstraintScope.create(false));
    	}
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlConstraints test = new TestXmlConstraints();
		test.saveReferenceXml();
    }
}