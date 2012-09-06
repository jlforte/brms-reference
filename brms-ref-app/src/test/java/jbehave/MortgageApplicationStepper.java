package jbehave;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.drools.command.assertion.AssertEquals;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import com.rhc.brms.ref.domain.Application;
import com.rhc.brms.ref.domain.Customer;
import com.rhc.brms.ref.domain.Mortgage;
import com.rhc.brms.ref.mortgageApplication.MortgageApplicationRequest;
import com.rhc.brms.ref.mortgageApplication.MortgageApplicationResponse;
import com.rhc.brms.ref.mortgageApplication.MortgageApplicationService;

import static org.junit.Assert.*;

public class MortgageApplicationStepper {

	MortgageApplicationService droolsService;
	private MortgageApplicationRequest request;
	private MortgageApplicationResponse response;

	@BeforeStories
	public void setUp() {
		droolsService = new MortgageApplicationService();
		request = new MortgageApplicationRequest( null, null );
	}

	@Given( "there are these applications $applicationsTable" )
	public void givenTheseApplications( ExamplesTable applicationsTable ) {
		Set<Application> appSet = new HashSet<Application>();
		for ( Map<String, String> row : applicationsTable.getRows() ) {
			// System.out.println( Long.parseLong( row.get( "app_ID" ) ) );
			BigDecimal amount = new BigDecimal( row.get( "amount" ) );
			Long customerId = new Long( row.get( "custId" ) );
			Long applicationId = new Long( row.get( "appId" ) );
			Application app = new Application( amount, customerId,
					applicationId );
			appSet.add( app );
		}
		request.setApplications( appSet );
	}

	@Given( "these customers $customerTable" )
	public void andTheseCustomers( ExamplesTable customersTable ) {
		Set<Customer> custSet = new HashSet<Customer>();
		for ( Map<String, String> row : customersTable.getRows() ) {
			String name = row.get( "name" );
			Integer age = new Integer( row.get( "age" ) );
			Integer creditScore = new Integer( row.get( "creditScore" ) );
			Long id = new Long( row.get( "id" ) );
			Customer cust = new Customer( name, age, creditScore, id );

			custSet.add( cust );
		}
		request.setCustomers( custSet );
	}

	@When( "I evaluate these objects in the mortgage application" )
	public void whenIEvaluateTheseObjectsInTheMortgageApplication() {
		response = droolsService.executeAllRules( request );
	}

	@Then( "I expect the mortgages added to be $mortgagesTable" )
	public void iExpectTheNumberOfMortgageApplicationsToBe( ExamplesTable mortgagesTable ) {
		Set<Mortgage> expectedSet = makeMortgages( mortgagesTable );
		Set<Mortgage> actualSet = response.getNewMortgagesCreated();

		assertEquals( expectedSet, actualSet );
	}

	private Set<Mortgage> makeMortgages( ExamplesTable mortgagesTable ) {
		Set<Mortgage> tempSet = new HashSet<Mortgage>();
		for ( Map<String, String> row : mortgagesTable.getRows() ) {
			Long customerId = new Long( row.get( "custId" ) );
			Long applicationId = new Long( row.get( "appId" ) );
			BigDecimal amount = new BigDecimal( row.get( "amount" ) );
			Mortgage temp = new Mortgage( customerId, applicationId, amount );
			tempSet.add( temp );
		}
		return tempSet;
	}
}
