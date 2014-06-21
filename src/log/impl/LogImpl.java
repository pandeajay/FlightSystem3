package log.impl;

import log.Log;

public class LogImpl implements Log {
	private static java.util.logging.Logger logWriter = java.util.logging.Logger.getLogger(LogImpl.class.getName());
	

	@Override
	public void fine(String msg) {
		
		logWriter.fine(msg);		
	}

	@Override
	public void info(String msg) {
		logWriter.info(msg);
		
	}

	@Override
	public void config(String msg) {
		logWriter.config(msg);
		
	}

	@Override
	public void finer(String msg) {
		logWriter.finer(msg);
		
	}

	@Override
	public void finest(String msg) {
		logWriter.finest(msg);
		
	}

	@Override
	public void warning(String msg) {
		logWriter.warning(msg);
		
	}

}
