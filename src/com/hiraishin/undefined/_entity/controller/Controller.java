package com.hiraishin.undefined._entity.controller;

import java.util.Objects;

import com.hiraishin.undefined._input.InputEventAdapter;

public final class Controller {

	private Controlled target;

	public Controller() {

	}

	public void setTarget(Controlled target) {
		this.target = Objects.requireNonNull(target);
	}

	public void detach() {
		target = null;
	}

	public void notify(InputEventAdapter adapter) {
		if (Objects.nonNull(target) && Objects.nonNull(adapter)) {
			target.control(adapter);
		}

	}

}
