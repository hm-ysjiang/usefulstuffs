package hmysjiang.usefulstuffs.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hmysjiang.usefulstuffs.Reference;

public class LogHelper {
	
	private static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
	
	public static void info(String msg) {
		LOGGER.log(Level.INFO, msg);
	}
	
	public static void error(String msg) {
		LOGGER.log(Level.ERROR, msg);
	}
	
	public static void warn(String msg) {
		LOGGER.log(Level.WARN, msg);
	}
	
}
