package com.hiraishin.undefined._essentials.upgrade;

public enum Upgrade {

	PLAYER_MAX_HEALTH(
			new int[][] { { 0, 0 }, { 100, 1 }, { 200, 2 }, { 300, 3 }, { 400, 4 } }), PLAYER_HORIZONTAL_SPEED(
					new int[][] { { 0, 0 }, { 100, 1 }, { 200, 2 }, { 300, 3 }, { 400, 4 } }), PLAYER_VERTICAL_SPEED(
							new int[][] { { 0, 0 }, { 100, 1 }, { 200, 2 }, { 300, 3 },
									{ 400, 4 } }), PLAYER_VERTICAL_SPEED_INCREMENTAL(
											new int[][] { { 0, 0 }, { 100, 1 }, { 200, 2 }, { 300, 3 }, { 400, 4 } });

	private final int[][] data;

	private Upgrade(int[][] data) {
		this.data = data;
	}

	public int getCost(int level) {
		return data[level][0];
	}

	public int getValue(int level) {
		return data[level][1];
	}

	public int[][] getRawData() {
		return data;
	}

}
