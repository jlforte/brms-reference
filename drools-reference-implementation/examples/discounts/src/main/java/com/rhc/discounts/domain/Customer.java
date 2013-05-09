package com.rhc.discounts.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.rhc.drools.reference.DroolsRequest;

public class Customer implements ICustomer, DroolsRequest {

	private String name;
	private Collection<Product> products;
	private Collection<IDiscount> eligibleDiscounts;
	private Collection<IDiscount> appliedDiscounts;
	private int discountsOffered;
	private float totalCost;
	private float amountSaved;

	public Customer() {

	}

	public Customer( String name ) {
		this.name = name;
		this.products = new ArrayList<Product>();
		this.eligibleDiscounts = new ArrayList<IDiscount>();
		this.appliedDiscounts = new ArrayList<IDiscount>();
	}

	public String getName() {
		return name;
	}

	@Override
	public void addProduct( Product product ) {
		products.add( product );

	}

	@Override
	public void addEligibleDiscount( IDiscount discount ) {
		eligibleDiscounts.add( discount );

	}

	@Override
	public void addAppliedDiscount( IDiscount discount ) {
		appliedDiscounts.add( discount );
	}

	@Override
	public float getTotalCost() {
		// TODO Auto-generated method stub
		return totalCost;
	}

	@Override
	public float getAmountSaved() {
		// TODO Auto-generated method stub
		return amountSaved;
	}

	@Override
	public int getDiscountsOffered() {
		// TODO Auto-generated method stub
		return discountsOffered;
	}

	public Collection<Product> getProducts() {
		return products;
	}

	public Collection<IDiscount> getEligbleDiscounts() {
		return eligibleDiscounts;
	}

	public Collection<IDiscount> getAppliedDiscounts() {
		return appliedDiscounts;
	}

	public void setDiscountsOffered( int discountsOffered ) {
		this.discountsOffered = discountsOffered;
	}

	public void setTotalCost( float totalCost ) {
		this.totalCost = totalCost;
	}

	public void setAmountSaved( float amountSaved ) {
		this.amountSaved = amountSaved;
	}

	@Override
	public Set<Object> getAllObjects() {
		Set<Object> set = new HashSet<Object>();
		set.addAll( getProducts() );
		set.addAll( getEligbleDiscounts() );
		set.add( this );
		return set;
	}

}
