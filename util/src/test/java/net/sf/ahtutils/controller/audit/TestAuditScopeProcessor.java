package net.sf.ahtutils.controller.audit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.audit.XmlChangeFactory;
import net.sf.ahtutils.factory.xml.audit.XmlScopeFactory;
import net.sf.ahtutils.test.AbstractAhtUtilsTest;
import net.sf.ahtutils.test.AhtUtilsTstBootstrap;
import net.sf.ahtutils.xml.audit.Change;
import net.sf.ahtutils.xml.audit.Scope;

public class TestAuditScopeProcessor extends AbstractAhtUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestAuditScopeProcessor.class);
	
	private List<Change> list;
	private AuditScopeProcessor asp;
	
	@Before
	public void init()
	{
		Scope a = XmlScopeFactory.build(1, "a");
		Scope b = XmlScopeFactory.build(1, "b");
		Scope c = XmlScopeFactory.build(2, "b");
		
		list = new ArrayList<Change>();
		list.add(XmlChangeFactory.build(1, a));
		list.add(XmlChangeFactory.build(2, a));
		list.add(XmlChangeFactory.build(3, b));
		list.add(XmlChangeFactory.build(4, c));
		
		asp = new AuditScopeProcessor();
	}
 
    @Test @Ignore
    public void nrOfScopes()
    {	
    	List<Scope> actual = asp.group(list);
    	Assert.assertEquals(3, actual.size());
    }
    
    public static void main (String[] args) throws Exception
	{
		AhtUtilsTstBootstrap.init();
		
		TestAuditScopeProcessor test = new TestAuditScopeProcessor();
		test.init();
		test.nrOfScopes();
	} 
}