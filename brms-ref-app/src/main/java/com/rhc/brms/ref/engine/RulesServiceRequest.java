package com.rhc.brms.ref.engine;

import java.util.Collection;

import com.rhc.brms.ref.domain.Application;
import com.rhc.brms.ref.domain.Customer;

public class RulesServiceRequest {

	private Collection<Application> applications;
	private Collection<Customer> customers;
	
	
	public Collection<Application> getApplications() {
		return applications;
	}
	public void setApplications(Collection<Application> applications) {
		this.applications = applications;
	}
	public Collection<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(Collection<Customer> customers) {
		this.customers = customers;
	}
	
	
	
}
