package com.hiraishin.undefined.scene;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

/**
 * Scene representing individual game scenes
 */
public abstract class GameScene {
	
	private final Scene scene;
	protected final Group group;

	public GameScene(double width, double height) {
		group = new Group();
		scene = new Scene(group, width, height);
	}

	public Scene getScene() {
		return this.scene;
	}
	
	protected void add(Node... nodes) {
		for (Node n : nodes) {
			group.getChildren().add(n);
		}
	}
	
	public abstract void update();	
	public abstract void reset();
}
