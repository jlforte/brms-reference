package jbehave;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import com.rhc.drools.reference.ClasspathKnowledgeBaseBuilder;
import com.rhc.drools.reference.ReflectiveExecutionResultsTransformer;
import com.rhc.drools.reference.StatelessDroolsComponent;
import com.rhc.mortgage.application.MortgageApplicationCommandListBuilder;
import com.rhc.mortgage.application.MortgageApplicationRequest;
import com.rhc.mortgage.application.MortgageApplicationResponse;
import com.rhc.mortgage.domain.Application;
import com.rhc.mortgage.domain.Customer;
import com.rhc.mortgage.domain.Mortgage;

public class MortgageApplicationStepper {

	StatelessDroolsComponent droolsComponent;
	private MortgageApplicationRequest request;
	private MortgageApplicationResponse response;

	@BeforeStories
	public void setUp() {
		droolsComponent = new StatelessDroolsComponent();
		droolsComponent.setCommandListBuilder( new MortgageApplicationCommandListBuilder() );
		droolsComponent.setKnowledgeBaseBuilder( new ClasspathKnowledgeBaseBuilder( buildDrls() ) );
		ReflectiveExecutionResultsTransformer resultsTransformer = new ReflectiveExecutionResultsTransformer();
		droolsComponent.setResultsTransformer( resultsTransformer );
		request = new MortgageApplicationRequest( null, null );
	}

	@Given("there are these applications $applicationsTable")
	public void givenTheseApplications( ExamplesTable applicationsTable ) {
		Set<Application> appSet = new HashSet<Application>();
		for ( Map<String, String> row : applicationsTable.getRows() ) {
			// System.out.println( Long.parseLong( row.get( "app_ID" ) ) );
			BigDecimal amount = new BigDecimal( row.get( "amount" ) );
			Long customerId = new Long( row.get( "custId" ) );
			Long applicationId = new Long( row.get( "appId" ) );
			Application app = new Application( amount, customerId, applicationId );
			appSet.add( app );
		}
		request.setApplications( appSet );
	}

	@Given("these customers $customerTable")
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

	@When("I evaluate these objects in the mortgage application")
	public void whenIEvaluateTheseObjectsInTheMortgageApplication() {
		response = droolsComponent.execute( request, MortgageApplicationResponse.class, null );
	}

	@Then("I expect the mortgages added to be $mortgagesTable")
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

	public static Set<String> buildDrls() {
		Set<String> drls = new HashSet<String>();
		drls.add( "Queries.drl" );
		drls.add( "ApplicationValidation.drl" );
		drls.add( "CustomerValidation.drl" );
		drls.add( "MortgageRules.drl" );
		return drls;
	}

}
