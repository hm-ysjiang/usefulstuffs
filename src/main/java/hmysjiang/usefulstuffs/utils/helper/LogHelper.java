package hmysjiang.usefulstuffs.utils.helper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hmysjiang.usefulstuffs.Reference;

public class LogHelper {
	
	private static final Logger logger = LogManager.getLogger(Reference.MOD_ID);
	
	public static void info(String msg) {
		logger.log(Level.INFO, msg);
	}
	
	public static void error(String msg) {
		logger.log(Level.ERROR, msg);
	}
	
	public static void warn(String msg) {
		logger.log(Level.WARN, msg);
	}
	
}
