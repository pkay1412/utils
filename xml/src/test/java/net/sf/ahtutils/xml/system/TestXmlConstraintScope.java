package net.sf.ahtutils.xml.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlConstraintScope extends AbstractXmlSystemTest<ConstraintScope>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlConstraintScope.class);
	
	public TestXmlConstraintScope(){super(ConstraintScope.class);}
	public static ConstraintScope create(boolean withChildren){return (new TestXmlConstraintScope()).build(withChildren);}
        
    public ConstraintScope build(boolean withChilds)
    {
    	ConstraintScope xml = new ConstraintScope();

    	if(withChilds)
    	{

    	}
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlConstraintScope test = new TestXmlConstraintScope();
		test.saveReferenceXml();
    }
}