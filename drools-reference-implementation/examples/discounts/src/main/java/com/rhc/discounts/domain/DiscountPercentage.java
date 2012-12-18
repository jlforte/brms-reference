package com.rhc.discounts.domain;

public class DiscountPercentage implements IDiscount {

	String type;
	float percentage;
	int max;
	
	public DiscountPercentage(String type, float percentage, int max){
		this.type = type;
		this.percentage = percentage;
		this.max = max;
	}
	
	@Override
	public String getType() {
		return type;
	}

	@Override
	public float getValue(float cost) {
		return percentage*cost;
	}

	@Override
	public int getMax() {
		return max;
	}

	@Override
	public void setMax(int max) {
		this.max=max;

	}

}
