package com.hiraishin.undefined.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hiraishin.undefined.utils.Report.Level;

public class ReportManager {

	private static List<Report> reports = new ArrayList<>();
	
	/**
	 * Report information / warning / error
	 */
	public static void report(Level level, String message) {
		reports.add(new Report(level, message));
	}
	
	/**
	 * Print out reports
	 */
	public static void ss13ok() {
		Collections.sort(reports, (R1, R2) -> {
			return Report.LevelToInt(R1.getLevel()) - Report.LevelToInt(R2.getLevel());
		});
		
		for (Report r : reports) {
			System.out.println(r.getId() + "\t" + r.getLevel().toString() + "\t" + r.getDescription());
		}
	}
	
	/**
	 * Clear report list
	 */
	public static void clear() {
		reports.clear();
	}
}
