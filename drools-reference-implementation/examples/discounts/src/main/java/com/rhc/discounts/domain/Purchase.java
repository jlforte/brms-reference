package com.rhc.discounts.domain;

import java.util.Collection;
import java.util.HashSet;

public class Purchase {

	private Customer customer;
	private Product product;
	private Collection<Discount> discounts;

	public Purchase( Customer customer, Product product ) {
		this.customer = customer;
		this.product = product;
		this.discounts = new HashSet<Discount>();
	}

	public void addDiscount( Discount discount ){
		this.discounts.add( discount );
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public Product getProduct() {
		return product;
	}
	public Collection<Discount> getDiscounts() {
		return discounts;
	}
	

}
