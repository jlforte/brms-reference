package com.rhc.discounts.domain;

public interface ICustomer {
	
	public void addProduct(Product product);
	public void addEligibleDiscount(IDiscount discount);
	public void addAppliedDiscount(IDiscount discount);
	public float getTotalCost();
	public float getAmountSaved();
	public int getDiscountsOffered();
	

	
}
