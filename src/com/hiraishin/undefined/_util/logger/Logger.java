package com.hiraishin.undefined._util.logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public enum Logger {

	INSTANCE;

	private final List<Log> logs;

	private Logger() {
		logs = new ArrayList<>();
	}

	public void log(Severity severity, String message) {
		logs.add(new Log(severity, message, LocalDateTime.now()));
	}

	public void flush() {
		logs.clear();
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void print() {
		for (Log log : logs) {
			switch (log.getSeverity()) {
			case INFORMATION:
			case WARNING: {
				System.out.println(log.getSeverity() + "\t" + log.getDateTime() + "\t" + log.getMessage());
				break;
			}
			case ERROR: {
				System.err.println(log.getSeverity() + "\t" + log.getDateTime() + "\t" + log.getMessage());
				break;
			}
			}
		}
	}

}
