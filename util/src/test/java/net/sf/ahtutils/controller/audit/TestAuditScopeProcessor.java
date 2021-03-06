package net.sf.ahtutils.controller.audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.audit.XmlChangeFactory;
import net.sf.ahtutils.factory.xml.audit.XmlRevisionFactory;
import net.sf.ahtutils.factory.xml.audit.XmlRevisionsFactory;
import net.sf.ahtutils.factory.xml.audit.XmlScopeFactory;
import net.sf.ahtutils.test.AbstractAhtUtilsTest;
import net.sf.ahtutils.test.AhtUtilsTestBootstrap;
import net.sf.ahtutils.xml.audit.Change;
import net.sf.ahtutils.xml.audit.Revision;
import net.sf.ahtutils.xml.audit.Revisions;
import net.sf.ahtutils.xml.audit.Scope;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestAuditScopeProcessor extends AbstractAhtUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestAuditScopeProcessor.class);
	
	private List<Change> list;
	private Revisions revisions;
	
	private AuditScopeProcessor asp;
	
	@Before
    public void init()
    {
		initScopeList();
		initRevisions();
        asp = new AuditScopeProcessor();
    }
	
	private void initScopeList()
	{
		Scope a = XmlScopeFactory.build(1, "a");
        Scope b = XmlScopeFactory.build(1, "b");
        Scope c = XmlScopeFactory.build(2, "c");c.setCategory("catC");
        Scope d = XmlScopeFactory.build(2, "c");d.setCategory("catC");

        list = new ArrayList<Change>();
        list.add(XmlChangeFactory.build(1, a));
        list.add(XmlChangeFactory.build(2, a));
        list.add(XmlChangeFactory.build(3, b));
        list.add(XmlChangeFactory.build(4, c));
        list.add(XmlChangeFactory.build(5, d));
	}
	
	private void initRevisions()
	{
		revisions = XmlRevisionsFactory.build();
		
		Revision r1 = XmlRevisionFactory.build(1, new Date());
		Scope a = XmlScopeFactory.build(1, "a");
		a.getChange().add(XmlChangeFactory.build(1, "a1"));
		a.getChange().add(XmlChangeFactory.build(2, "a2"));
		r1.getScope().add(a);
		revisions.getRevision().add(r1);
       
		Revision r2 = XmlRevisionFactory.build(2, new Date());
		Scope b = XmlScopeFactory.build(2, "b");
		Scope c = XmlScopeFactory.build(3, "c");
		b.getChange().add(XmlChangeFactory.build(1, "b1"));
		b.getChange().add(XmlChangeFactory.build(2, "b2"));
		c.getChange().add(XmlChangeFactory.build(3, "c3"));
		r2.getScope().add(b);
		r2.getScope().add(c);
		revisions.getRevision().add(r2);
	}

    @Test
    public void nrOfScopes()
    {
        List<Scope> actual = asp.group(list);
        Assert.assertEquals(3, actual.size());
    }

    @Test
    public void childs()
    {
        List<Scope> actual = asp.group(list);
        for(Scope s : actual)
        {
            Assert.assertTrue(s.isSetChange());
        }
        Revision r = new Revision();
        r.getScope().addAll(actual);
        JaxbUtil.info(r);
        Assert.assertEquals(3, actual.size());
    }
    
    @Test
    public void ordering()
    {
    	int i=0;
    	List<Scope> actual = asp.group(list);
    	for(Scope s : actual)
    	{
    		for(Change c : s.getChange())
    		{
    			Assert.assertEquals(list.get(i), c);
    			i++;
    		}
    	}
    }
    
    public void flat()
    {
    	JaxbUtil.info(revisions);
    	
    	List<Change> changes = asp.flat(revisions.getRevision());
    	Scope scope = new Scope();
    	scope.getChange().addAll(changes);
    	JaxbUtil.info(scope);
    }
    
    public static void main (String[] args) throws Exception
	{
		AhtUtilsTestBootstrap.init();
		
		TestAuditScopeProcessor test = new TestAuditScopeProcessor();
		
//		test.init();test.nrOfScopes();
//		test.init();test.childs();
//		test.init();test.ordering();
		test.init();test.flat();
	} 
}