package com.rhc.ref.jbehave;

import java.util.HashSet;
import java.util.Set;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.rhc.brms.ref.domain.Application;
import com.rhc.brms.ref.domain.Customer;
import com.rhc.brms.ref.mortgageApplication.MortgageApplicationRequest;

public class MortgageApplicationStory {

	
	@Given("when i derp de derp $storyTable")
	public void whenI() {
		MortgageApplicationRequest request = new MortgageApplicationRequest( withTheseApplications() ,  andTheseCustomers() );
		
	}
	
	@When( "I evaluate these objects in the mortgage application" )
	public void whenIEvaluateTheseObjectsInTheMortgageApplication(){
		
	}
	
	@Then( "I except the mortgages added to be ")
	public void iExpectTheNumberOfMortageApplicationsToBe(){
		
	}
	
	public Set<Application> withTheseApplications( ){
		Set<Application> applications = new HashSet<Application>();
		
		return applications;
	}
	
	public Set<Customer> andTheseCustomers(  ){
		Set<Customer> customers = new HashSet<Customer>();
		
		return customers;
	}
}

