package com.rhc.brms.ref.engine;

import java.util.Set;

import com.rhc.brms.ref.domain.Application;
import com.rhc.brms.ref.domain.Customer;

public class RulesServiceRequest {

	private Set<Application> applications;
	private Set<Customer> customers;

	public RulesServiceRequest( Set<Application> applications, Set<Customer> customers ) {
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
	
	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}
	
}
