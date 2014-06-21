package log.impl;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import log.Log;

public class LogImpl implements Log {
	private java.util.logging.Logger logWriter = java.util.logging.Logger.getLogger("FlightSystemLogger");
	
	public LogImpl() {
		try{
			logWriter.setLevel(Level.FINE);
			FileHandler handler = new FileHandler("FlightSystem.log");
			logWriter.addHandler(handler);
		}catch(Exception ex){
			System.out.println("can not get LogImpl " + ex);
		}
	}

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
