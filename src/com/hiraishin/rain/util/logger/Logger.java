package com.hiraishin.rain.util.logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public enum Logger {

	/**
	 * Create only single Logger instance
	 */
	INSTANCE();

	/**
	 * List for storing logs
	 */
	private final List<Log> logs = new ArrayList<>();

	/**
	 * Create log
	 * 
	 * @param severity
	 * @param message
	 */
	public void log(Severity severity, String message) {
		logs.add(new Log(severity, message, LocalDateTime.now()));
	}

	/**
	 * Get list of logs
	 * 
	 * @return list
	 */
	public List<Log> getLogs() {
		return logs;
	}

	/**
	 * Print logs onto consumer
	 * 
	 * @param consumer
	 */
	public void print(Consumer<String> consumer) {
		logs.forEach(Log -> consumer.accept(new StringBuilder().append(Log.severity).append('\t').append(Log.timestamp)
				.append('\t').append(Log.message).toString()));
	}

	/**
	 * Log entity
	 */
	private static class Log {

		public final Severity severity;
		public final String message;
		public final LocalDateTime timestamp;

		public Log(Severity severity, String message, LocalDateTime timestamp) {
			this.severity = severity;
			this.message = message;
			this.timestamp = timestamp;
		}

	}

}
