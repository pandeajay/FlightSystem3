package log;

public interface Log {	
	void fine(String msg);
	void info(String msg);
	void config(String msg);
	void finer(String msg);
	void finest(String msg);
	void warning(String msg);

}
