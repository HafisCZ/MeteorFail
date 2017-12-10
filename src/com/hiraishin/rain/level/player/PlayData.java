package com.hiraishin.rain.level.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.hiraishin.rain.util.Data;

public enum PlayData {

	/*
	 * Definitions
	 */
	INSTANCE;

	public static final File DATA_FILE = new File("playdata.ser");

	/*
	 * Instance variables
	 */
	private Map<Integer, Data<Integer>> statistics;

	/*
	 * Instance functions
	 */
	public Map<Integer, Data<Integer>> getStatistics() {
		if (Objects.isNull(this.statistics)) {
			load();
		}

		return this.statistics;
	}

	public void load() {
		try {
			PlayData.<Integer>read(DATA_FILE, this.statistics);
		} catch (Exception e) {
			buildDefaultData();
		}
	}

	public void save() {
		try {
			PlayData.<Integer>write(DATA_FILE, this.statistics);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buildDefaultData() {
		this.statistics = new HashMap<Integer, Data<Integer>>();

		this.statistics.put(0, new Data<>("Experience gained", 0));
		this.statistics.put(1, new Data<>("Shields collected", 0));
		this.statistics.put(2, new Data<>("Damage taken", 0));
		this.statistics.put(3, new Data<>("Power nodes collected", 0));

		save();
	}

	/*
	 * Static functions
	 */
	@SuppressWarnings("unchecked")
	private static <T> void read(File file, Map<Integer, Data<T>> data)
			throws FileNotFoundException, ClassNotFoundException, IOException {
		FileInputStream istream = new FileInputStream(file);
		ObjectInputStream oistream = new ObjectInputStream(istream);

		oistream.reset();

		data = (Map<Integer, Data<T>>) oistream.readObject();

		oistream.close();
		istream.close();
	}

	private static <T> void write(File file, Map<Integer, Data<T>> data) throws FileNotFoundException, IOException {
		FileOutputStream fostream = new FileOutputStream(file);
		ObjectOutputStream oostream = new ObjectOutputStream(fostream);

		oostream.reset();

		oostream.writeObject(data);

		oostream.close();
		fostream.close();
	}

}