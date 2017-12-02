package com.hiraishin.undefined._essentials;

import java.util.HashMap;
import java.util.Map;

import com.hiraishin.undefined._essentials.upgrade.Upgrade;
import com.hiraishin.undefined._util.resource.RegistryStorage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public enum Upgrades {

	INSTANCE;

	private final Map<Upgrade, Integer> upgrades;
	private final IntegerProperty moneyProperty;

	private Upgrades() {
		moneyProperty = new SimpleIntegerProperty();

		upgrades = new HashMap<>();
		for (Upgrade upgrade : Upgrade.values()) {
			upgrades.put(upgrade, 0);
		}
	}

	public void upgrade(Upgrade upgrade) {
		int nextLevel = upgrades.get(upgrade) + 1;
		if (nextLevel < upgrade.getRawData().length) {
			int levelCost = upgrade.getCost(nextLevel);
			if (levelCost <= moneyProperty.get()) {
				moneyProperty.set(moneyProperty.get() - levelCost);
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
		moneyProperty.set(moneyProperty.get() + 1);
	}

	public void load() {
		moneyProperty.set(RegistryStorage.INSTANCE.readInteger("money", 0));
		for (Map.Entry<Upgrade, Integer> entry : upgrades.entrySet()) {
			int level = RegistryStorage.INSTANCE.readInteger(entry.getKey().toString().toLowerCase(), 0);
			if (level < 0) {
				entry.setValue(0);
			} else if (level >= entry.getKey().getRawData().length) {
				entry.setValue(entry.getKey().getRawData().length - 1);
			} else {
				entry.setValue(level);
			}
		}
	}

	public void save() {
		RegistryStorage.INSTANCE.write("money", moneyProperty.get());
		for (Map.Entry<Upgrade, Integer> entry : upgrades.entrySet()) {
			RegistryStorage.INSTANCE.write(entry.getKey().toString().toLowerCase(), entry.getValue());
		}
	}

	public IntegerProperty moneyProperty() {
		return moneyProperty;
	}

}
