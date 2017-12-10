package com.hiraishin.rain.level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public enum PlayData {

	/*
	 * Definitions
	 */
	STAT_COUNT_EXPERIENCE(0), STAT_COUNT_DAMAGE(0), STAT_COUNT_SHIELD(0), STAT_COUNT_NODES(0), STAT_COUNT_JUMP(
			0), STAT_COUNT_SKILLACTIVATION(0),

	PLAYER_LEVEL(1), PLAYER_POINTS(0), PLAYER_SELECTEDSKILL(0), PLAYER_HEALTH(5), PLAYER_SHIELD(0),

	UPGRADE_MOVEMENT(0), UPGRADE_POWERRATE(0), UPGRADE_SHOCKWAVE(0), UPGRADE_DOUBLEXP(0), UPGRADE_SHIELDSPAWN(0);

	private static final String SER_FILE = "playdata.ser";

	/*
	 * Instace variables
	 */
	private int value;

	/*
	 * Constructors
	 */
	private PlayData(int defaultValue) {
		this.value = defaultValue;
	}

	public void increment() {
		this.value++;
	}

	public void incrementBy(int value) {
		this.value += value;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static void load() {
		try {
			read(SER_FILE);
		} catch (Exception e) {
			try {
				write(SER_FILE);
			} catch (Exception f) {
				f.printStackTrace();
			}
		}
	}

	public static void save() {
		try {
			write(SER_FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Static functions
	 */
	private static void read(String file) throws FileNotFoundException, ClassNotFoundException, IOException {
		FileInputStream fiStream = new FileInputStream(new File(file));
		ObjectInputStream oiStream = new ObjectInputStream(fiStream);
		oiStream.reset();

		for (int i = 0; i < PlayData.values().length; i++) {
			PlayData.values()[i].value = oiStream.readInt();
		}

		oiStream.close();
		fiStream.close();
	}

	private static void write(String file) throws FileNotFoundException, IOException {
		FileOutputStream foStream = new FileOutputStream(new File(file));
		ObjectOutputStream ooStream = new ObjectOutputStream(foStream);
		ooStream.reset();

		for (int i = 0; i < PlayData.values().length; i++) {
			ooStream.writeInt(PlayData.values()[i].value);
		}

		ooStream.close();
		foStream.close();
	}

}
