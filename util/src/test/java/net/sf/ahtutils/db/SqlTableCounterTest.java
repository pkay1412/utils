package net.sf.ahtutils.db;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.db.sql.SqlTableCounter;
import net.sf.ahtutils.test.AhtUtilsTestBootstrap;

public class SqlTableCounterTest
{
	final static Logger logger = LoggerFactory.getLogger(SqlTableCounterTest.class);
	
	private static final String cDb1 = "test.db.counter.db1";
	private static final String cDb2 = "test.db.counter.db2";
	
    public static void main(String[] args) throws Exception
    {
    	Configuration config = AhtUtilsTestBootstrap.init();
    	
    	File db1 = new File(config.getString(cDb1));
    	File db2 = new File(config.getString(cDb2));
    	
    	logger.info(cDb1+": "+db1.getAbsolutePath());
    	logger.info(cDb2+": "+db2.getAbsolutePath());
    	
        SqlTableCounter sqltc = new SqlTableCounter(db1, db2);
        sqltc.output();
    }
}
