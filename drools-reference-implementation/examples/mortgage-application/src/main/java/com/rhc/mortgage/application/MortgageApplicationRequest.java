package com.rhc.mortgage.application;

import java.util.HashSet;
import java.util.Set;

import com.rhc.drools.reference.DroolsRequest;
import com.rhc.mortgage.domain.Application;
import com.rhc.mortgage.domain.Customer;

/**
 * A class that holds all domain objects needed to evaluate a mortgage application.
 * 
 * We don't want this class to reference Drools or rules. This class is simply a container for the domain objects needed
 * to solve the business problem at hand, which this case is applying for a mortgage. We don't need to introduce
 * Drools/Rules naming as this an implementation detail, and this class should be reusable for alternate implentations
 * that do not use Drools.
 * 
 * 
 * 
 */
public class MortgageApplicationRequest implements DroolsRequest {

	private Set<Application> applications;
	private Set<Customer> customers;

	public MortgageApplicationRequest( Set<Application> applications, Set<Customer> customers ) {
		this.applications = applications;
		this.customers = customers;
	}

	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications( Set<Application> applications ) {
		this.applications = applications;
	}

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers( Set<Customer> customers ) {
		this.customers = customers;
	}

	@Override
	public Set<Object> getAllFacts() {
		Set<Object> set = new HashSet<Object>();
		set.addAll( applications );
		set.addAll( customers );
		return set;
	}

	@Override
	public String getProcessId() {
		// TODO Auto-generated method stub
		return null;
	}

}
