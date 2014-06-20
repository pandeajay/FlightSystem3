package utilities;

public class Logger {
	private static java.util.logging.Logger logWriter = null;
	
	static void init(java.util.logging.Logger logr){
		if(logWriter != null){
			return ;
		}
		if(logr == null){
			logWriter = java.util.logging.Logger.getLogger(Logger.class.getName());
		}else{
			Logger.logWriter = logr;
		}
	}
	static void fine(String msg){		
		Logger.logWriter.fine(msg);
	}	
	static void info(String msg){
		Logger.logWriter.info(msg);
	}
	static void config(String msg){
		Logger.logWriter.config(msg);
	}
	static void finer(String msg){
		Logger.logWriter.finer(msg);
	}
	static void finest(String msg){
		Logger.logWriter.finest(msg);
	}
	static void warning(String msg){
		Logger.logWriter.warning(msg);
	}
}
