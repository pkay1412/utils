package net.sf.ahtutils.doc.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AhtUtilsDocBootstrap;

public class CliUtilsIconTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(CliUtilsIconTranscoder.class);


	public static void main(String args[]) throws Exception
	{
		AhtUtilsDocBootstrap.init();
		logger.info(System.getProperty("java.version"));
	}
}
