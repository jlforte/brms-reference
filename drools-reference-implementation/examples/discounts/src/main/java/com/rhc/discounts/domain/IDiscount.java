package com.rhc.discounts.domain;

public interface IDiscount {
	public String getType();
	public float getValue(float cost);
	public int getMax();
	public void setMax(int max);
}
