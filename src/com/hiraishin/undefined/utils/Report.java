package com.hiraishin.undefined.utils;

import java.util.Objects;

/**
 * Class representing various messages caused by game
 */
public class Report {
	
	public enum Level {
		INFORMATION,
		WARNING,
		ERROR
	}

	private static int errorCount = 0;
	
	private int id;
	private Level level;
	private String description;

	/**
	 * 
	 * @param description Description of report
	 */
	public Report(Level level, String description) {
		this.id = errorCount++;
		this.level = Objects.requireNonNull(level);
		this.description = description;
	}
	
	/**
	 * 
	 * @return Report ID
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * 
	 * @return Report level
	 */
	public Level getLevel() {
		return this.level;
	}
	
	/**
	 * 
	 * @return Report description
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Get integer representation on report level
	 */
	public static int LevelToInt(Level level) {
		switch (level) {
			case ERROR: return 2;
			case WARNING: return 1;
			default:
			case INFORMATION: return 0;
		}
	}
	
}
