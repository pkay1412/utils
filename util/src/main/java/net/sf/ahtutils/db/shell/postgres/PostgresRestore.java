package net.sf.ahtutils.db.shell.postgres;

import java.io.File;
import java.util.Arrays;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.Configuration;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.db.UtilsDbShell;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.factory.xml.config.XmlParameterFactory;

public class PostgresRestore extends AbstractPostgresShell implements UtilsDbShell
{
	final static Logger logger = LoggerFactory.getLogger(PostgresRestore.class);
	
	public PostgresRestore(Configuration config)
    {
		this(config,null);
    }
	public PostgresRestore(Configuration config,Document xmlConfig)
    {
		super(config, UtilsDbShell.Operation.restore, xmlConfig);
		pDbRestore = XmlParameterFactory.build(UtilsDbShell.cfgBinRestore, "Shell command for restore", false);
		try{pDbRestore.setValue(config.getString(pDbRestore.getKey()));}
		catch (NoSuchElementException e){pDbRestore.setValue("pg_restore");}
		configurationParamter.getParameter().add(pDbRestore);
		
		pDbSuperUser = XmlParameterFactory.build("db."+operation.toString()+".super.name", "DB Superuser for athentication of "+operation.toString(), true);
		pDbSuperUser.setValue(config.getString(pDbSuperUser.getKey()));
		configurationParamter.getParameter().add(pDbSuperUser);
		
		pDbSuperPwd = XmlParameterFactory.build("db."+operation.toString()+".super.password", "DB Suepruser Password for athentication of "+operation.toString(), true);
		pDbSuperPwd.setValue(config.getString(pDbSuperPwd.getKey()));
		configurationParamter.getParameter().add(pDbSuperPwd);
    }
	
	@Deprecated
	public void buildCommands(boolean withStructure) throws ExlpUnsupportedOsException
	{
		buildRestoreDataScript();
	}
	
	public void writeShell() throws ExlpUnsupportedOsException
	{
		scope = UtilsDbShell.Scope.data;
		buildRestoreDataScript();
		this.save();
		
		txtWriter.clear();
		scope = UtilsDbShell.Scope.structure;
		buildRestoreStructureScript();
		this.save();
	}
	
	private void buildRestoreStructureScript() throws ExlpUnsupportedOsException
	{		
		super.cmdPre();
		restoreStructure();
		super.cmdPost();
	}
	
	private void buildRestoreDataScript() throws ExlpUnsupportedOsException
	{		
		super.cmdPre();

		for(String table : Arrays.asList(config.getStringArray(UtilsDbShell.cfgDbTablesRestore))){restoreTable(table);}
		for(String table : Arrays.asList(config.getStringArray(UtilsDbShell.cfgDbTablesRestoreTrigger))){restoreTableDisabledTrigger(table);}
		for(String table : Arrays.asList(config.getStringArray(UtilsDbShell.cfgDbSequenceRestore))){restoreSequence(table);}
//		for(String table : Arrays.asList(config.getStringArray(UtilsDbShell.cfgDbTablesKey))){fixPrimaryKey(table);}
		
		super.cmdPost();
	}
	
	public String restoreTable(String table) throws ExlpUnsupportedOsException
	{
		if(!pwdSet){setPwd(pDbPwd.getValue());}
		StringBuffer sb = new StringBuffer();
		sb.append(pDbRestore.getValue());
		sb.append(" --verbose");
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" -d ").append(pDbName.getValue());
		sb.append(" --no-privileges");
		sb.append(" --no-owner");
		sb.append(" --data-only");
		sb.append(" -t " + table);
		sb.append(" ").append(pSqlDir.getValue() + File.separator + pDbName.getValue() + ".sql");
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	public String restoreTableDisabledTrigger(String table) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		sb.append(pDbRestore.getValue());
		sb.append(" --verbose");
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbSuperUser.getValue());
		sb.append(" -d ").append(pDbName.getValue());
		sb.append(" --superuser=").append(pDbSuperUser.getValue());
		sb.append(" --disable-triggers");
		sb.append(" --no-privileges");
		sb.append(" --no-owner");
		sb.append(" --data-only");
		sb.append(" -t " + table);
		sb.append(" ").append(pSqlDir.getValue() + File.separator + pDbName.getValue() + ".sql");
		
		// Trigger http://dba.stackexchange.com/questions/23000/disable-constraints-before-using-pg-restore-exe
		// http://www.postgresonline.com/special_feature.php?sf_name=postgresql83_pg_dumprestore_cheatsheet
		
		if(pwdSet){unsetPwd();}
		setPwd(pDbSuperPwd.getValue());

		super.addLine(sb.toString());
		unsetPwd();
		super.addLine("");
		return sb.toString();
	}
	
	public String restoreSequence(String seq) throws ExlpUnsupportedOsException
	{
		//http://stackoverflow.com/questions/244243/how-to-reset-postgres-primary-key-sequence-when-it-falls-out-of-sync
		String table = seq.substring(0,seq.indexOf("_"));
		
		StringBuffer sb = new StringBuffer();
		sb.append(pDbShell.getValue());
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" -d ").append(pDbName.getValue());
		sb.append(" -c \"").append("SELECT setval('"+seq+"', (SELECT MAX(id) FROM "+table+"));").append("\"");
		
		if(!pwdSet){setPwd(pDbPwd.getValue());}
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	public String fixPrimaryKey(String table)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(pDbShell.getValue());
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" -d ").append(pDbName.getValue());
		sb.append(" -c \"").append("ALTER TABLE ").append(table).append(" ADD PRIMARY KEY (id);").append("\"");
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	public void restoreStructure() throws ExlpUnsupportedOsException
	{		
		StringBuffer sb = new StringBuffer();
		sb.append(pDbRestore.getValue());
		sb.append(" --verbose");
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" -d ").append(pDbName.getValue());
		sb.append(" --no-privileges");
		sb.append(" --no-owner");
		sb.append(" --schema-only");
		sb.append(" ").append(pSqlDir.getValue() + File.separator + pDbName.getValue() + ".sql");		
		super.addLine(sb.toString());
	}
}