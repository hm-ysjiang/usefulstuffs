package hmysjiang.usefulstuffs.utils.helper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hmysjiang.usefulstuffs.Reference;

public class LogHelper {
	
	private static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
	
	public static void info(String msg) {
		LOGGER.log(Level.INFO, msg);
	}
	
	public static void info(Object obj) {
		LOGGER.log(Level.INFO, obj.toString());
	}
	
	public static void error(String msg) {
		LOGGER.log(Level.ERROR, msg);
	}
	
	public static void error(Object obj) {
		LOGGER.log(Level.INFO, obj.toString());
	}
	
	public static void warn(String msg) {
		LOGGER.log(Level.WARN, msg);
	}
	
	public static void warn(Object obj) {
		LOGGER.log(Level.INFO, obj.toString());
	}
	
	public static void debug(String msg) {
		LOGGER.log(Level.INFO, msg);
	}
	
	public static void debug(Object obj) {
		LOGGER.log(Level.INFO, obj.toString());
	}
	
}
