package com.rhc.brms.ref;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.drools.event.rule.AfterActivationFiredEvent;
import org.junit.Assert;
import org.junit.Test;

import com.rhc.brms.ref.domain.Application;
import com.rhc.brms.ref.domain.Customer;
import com.rhc.brms.ref.mortageApplication.MortageApplicationRequest;
import com.rhc.brms.ref.mortageApplication.MortageApplicationResponse;
import com.rhc.brms.ref.mortageApplication.MortageApplicationService;

public class MortgageTest {

	private static HashMap<String, List<AfterActivationFiredEvent>> firedActivations;
	private static MortageApplicationService droolsExecService = new MortageApplicationService();

	private final static Long CUSTOMER_ID_1 = Long.valueOf( 1 );
	private final static Long CUSTOMER_ID_2 = Long.valueOf( 2 );
	private final static Long CUSTOMER_ID_3 = Long.valueOf( 3 );

	private final static Long APPLICATION_ID_1 = Long.valueOf( 1 );
	private final static Long APPLICATION_ID_2 = Long.valueOf( 2 );
	private final static Long APPLICATION_ID_3 = Long.valueOf( 3 );

	@Test
	public void shouldDoSomeStuff() {
		MortageApplicationRequest request = new MortageApplicationRequest( createApplications(), createCustomers() );

		// Response the Rules engine
		MortageApplicationResponse response = droolsExecService.executeAllRules( request );

		Assert.assertNotNull( response );
	}

	private Set<Customer> createCustomers() {

		Set<Customer> customers = new HashSet<Customer>();
		customers.add( new Customer( "Dave", 20, 700, CUSTOMER_ID_1 ) );
		customers.add( new Customer( "Mike", 18, 750, CUSTOMER_ID_2 ) );
		customers.add( new Customer( "Bryan", 28, 450, CUSTOMER_ID_3 ) );

		return customers;
	}

	private Set<Application> createApplications() {

		Set<Application> applications = new HashSet<Application>();
		applications.add( new Application( new BigDecimal( 15000 ), CUSTOMER_ID_1, APPLICATION_ID_1 ) );
		applications.add( new Application( new BigDecimal( 75000 ), CUSTOMER_ID_3, APPLICATION_ID_2 ) );
		applications.add( new Application( new BigDecimal( 85000 ), CUSTOMER_ID_2, APPLICATION_ID_3 ) );

		return applications;
	}

}
