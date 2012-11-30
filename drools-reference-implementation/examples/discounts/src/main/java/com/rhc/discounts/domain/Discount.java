package com.rhc.discounts.domain;

public class Discount {

	private String type;
	private float value;
	
	public Discount( String type, float value ) {
		this.type = type;
		this.value = value;
	}
	
	public String getType() {
		return type;
	}
	public float getValue() {
		return value;
	}
	
	
}
