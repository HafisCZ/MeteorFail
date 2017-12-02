package com.hiraishin.undefined.util.logger;

import java.time.LocalDateTime;

public class Log {

	private Severity severity;
	private String message;
	private LocalDateTime dateTime;

	public Log(Severity severity, String message, LocalDateTime dateTime) {
		this.severity = severity;
		this.message = message;
		this.dateTime = dateTime;
	}

	public Severity getSeverity() {
		return severity;
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

}
