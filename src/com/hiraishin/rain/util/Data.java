package com.hiraishin.rain.util;

import java.io.Serializable;

public class Data<T> implements Serializable {

	/*
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Instance final variables
	 */
	private final String name;

	/*
	 * Instance variables
	 */
	private T value;

	/*
	 * Constructors
	 */
	public Data(String name, T def) {
		this.name = name;
		this.value = def;
	}

	/*
	 * Instance functions
	 */
	public T getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}

	public void setValue(T t) {
		this.value = t;
	}

}