package com.rhc.brms.ref.engine;

import java.util.Set;

import com.rhc.brms.ref.domain.Application;
import com.rhc.brms.ref.domain.Customer;

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
public class MortageApplicationRequest {

	private Set<Application> applications;
	private Set<Customer> customers;

	public MortageApplicationRequest( Set<Application> applications, Set<Customer> customers ) {
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

}
