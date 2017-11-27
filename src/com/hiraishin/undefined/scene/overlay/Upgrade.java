package com.hiraishin.undefined.scene.overlay;

import java.util.prefs.Preferences;

import com.hiraishin.undefined.Game;

public class Upgrade {
	
	public static enum UpgradeType {
		LIFE, SPEED, JUMP_HEIGHT, FALL_SPEED
	}

	private static Preferences JREG_PREFERECES = Preferences.userNodeForPackage(Game.class);
	private static String PREFIX_KEY_0 = "coins";
	private static String PREFIX_KEY_1 = "upgrades";
	
	private static int[][] UPGRADE_COSTS = {
		{100, 200, 300, 400, 500 },
		{100, 200, 300, 400, 500 },
		{100, 200, 300, 400, 500 }
	};
	
	private int coins = 0;
	private int[] boughtUpgrades = new int[5];
	
	private static Upgrade upgrade;
	
	public static Upgrade getInstance() {
		if (upgrade == null) {
			upgrade = new Upgrade();
		}
		
		return upgrade;
	}
	
	public static void save() {
		Upgrade upgrade = Upgrade.getInstance();
		JREG_PREFERECES.putInt(PREFIX_KEY_0, upgrade.coins);
		for (int i = 0; i < upgrade.boughtUpgrades.length; i++) {
			JREG_PREFERECES.putInt(PREFIX_KEY_1 + i, upgrade.boughtUpgrades[i]);
		}
	}
	
	public static void load() {
		Upgrade upgrade = Upgrade.getInstance();
		upgrade.coins = JREG_PREFERECES.getInt(PREFIX_KEY_0, 0);
		for (int i = 0; i < upgrade.boughtUpgrades.length; i++) {
			upgrade.boughtUpgrades[i] = JREG_PREFERECES.getInt(PREFIX_KEY_1 + i, 0);
		}
	}
	
	public static int toInt(UpgradeType upgrade) {
		switch (upgrade) {
			case LIFE:
				return 0;
			case SPEED:
				return 1;
			case JUMP_HEIGHT:
				return 2;
			case FALL_SPEED:
				return 3;
			default:
				return -1;
		}
	}
	
	public boolean buy(UpgradeType upgrade) {
		if (coins >= UPGRADE_COSTS[toInt(upgrade)][boughtUpgrades[toInt(upgrade)]]) {
			coins -= UPGRADE_COSTS[toInt(upgrade)][boughtUpgrades[toInt(upgrade)]++];
			return true;
		} else {
			return false;
		}
	}
	
	public void addCoins() {
		this.coins++;
	}
	
	public void addCoins(int coins) {
		this.coins += coins;
	}
	
	public int getCoins() {
		return this.coins;
	}
	
	public int get(UpgradeType upgrade) {
		return this.boughtUpgrades[Upgrade.toInt(upgrade)];
	}
}
