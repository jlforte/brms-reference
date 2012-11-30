package com.rhc.discounts.domain;

import java.util.ArrayList;
import java.util.Collection;

public class Customer {

	private String name;
	private Collection<Purchase> purchases;
	
	public Customer( String name ) {
		this.name = name;
		this.purchases = new ArrayList<Purchase>();
	}

	public int discountsOffered(){
		int discounts = 0;
		for ( Purchase p : purchases ){
			discounts += p.getDiscounts().size();
		}
		return discounts;
	}
	
	public void purchase(Product product){
		purchases.add( new Purchase( this, product ) );
	}
	
	public String getName() {
		return name;
	}

	public Collection<Purchase> getPurchases() {
		return purchases;
	}

}
