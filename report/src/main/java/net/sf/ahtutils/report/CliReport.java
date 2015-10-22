package net.sf.ahtutils.report;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliReport
{
	final static Logger logger = LoggerFactory.getLogger(CliReport.class);

    public static String cliDebug = "report.cli.debug";
    public static String cliObfuscate = "report.cli.obfuscate";
    public static String cliSave = "report.cli.save";
    public static String cliLang = "report.cli.lang";
    public static String cliOutputDir = "report.cli.dir";

    public static void debugCliConfig(Configuration config)
    {
        logger.info(cliDebug+" "+config.getBoolean(cliDebug));
        logger.info(cliObfuscate+" "+config.getBoolean(cliObfuscate));
        logger.info(cliSave+" "+config.getBoolean(cliSave));
        logger.info(cliLang+" "+config.getString(cliLang));
        logger.info(cliOutputDir+" "+config.getString(cliOutputDir));
    }
}
