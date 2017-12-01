package com.hiraishin.undefined._essentials;

import java.util.HashMap;
import java.util.Map;

import com.hiraishin.undefined._essentials.upgrade.Upgrade;
import com.hiraishin.undefined._util.resource.RegistryStorage;

public enum Upgrades {

	INSTANCE;

	private static final String MONEY_KEY = "MONEY";

	private final Map<Upgrade, Integer> upgrades;
	private int money;

	private Upgrades() {
		upgrades = new HashMap<>();
		for (Upgrade upgrade : Upgrade.values()) {
			upgrades.put(upgrade, 0);
		}
	}

	public void upgrade(Upgrade upgrade) {
		int nextLevel = upgrades.get(upgrade) + 1;
		if (nextLevel < upgrade.getRawData().length) {
			int levelCost = upgrade.getCost(nextLevel);
			if (levelCost <= money) {
				money -= levelCost;
				upgrades.compute(upgrade, (U, L) -> L + 1);
			}
		}
	}

	public int valueOf(Upgrade upgrade) {
		return upgrade.getValue(upgrades.get(upgrade));
	}

	public void addMoney() {
		addMoney(1);
	}

	public void addMoney(int amount) {
		money += amount;
	}

	public void load() {
		money = RegistryStorage.INSTANCE.readInteger(MONEY_KEY, 0);
		upgrades.forEach((K, V) -> V = RegistryStorage.INSTANCE.readInteger(K.toString(), 0));
	}

	public void save() {
		RegistryStorage.INSTANCE.write(MONEY_KEY, money);
		upgrades.forEach((K, V) -> RegistryStorage.INSTANCE.write(K.toString(), V));
	}

}
