package com.rhc.discounts.domain;

public class DiscountFixed implements IDiscount {

	private String type;
	private float value;
	private int max;
	
	public DiscountFixed( String type, float value, int max ) {
		this.type = type;
		this.value = value;
		this.max = max;
	}
	
	public String getType() {
		return type;
	}
	public float getValue(float cost) {
		return value;
	}

	public int getMax() {
		return max;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
}
