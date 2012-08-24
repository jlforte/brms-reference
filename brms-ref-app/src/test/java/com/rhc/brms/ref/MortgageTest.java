package com.rhc.brms.ref;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.io.ResourceFactory;

import com.rhc.brms.ref.domain.Application;
import com.rhc.brms.ref.domain.Customer;
import com.rhc.brms.ref.engine.DroolsExecutionService;
import com.rhc.brms.ref.engine.RulesServiceRequest;
import com.rhc.brms.ref.engine.RulesServiceResponse;

public class MortgageTest {

	private static HashMap<String, List<AfterActivationFiredEvent>> firedActivations;
	private static DroolsExecutionService droolsExecService = new DroolsExecutionService();
	
	private final static Long CUSTOMER_ID_1 = Long.valueOf(1);
	private final static Long CUSTOMER_ID_2 = Long.valueOf(2);
	private final static Long CUSTOMER_ID_3 = Long.valueOf(3);
	
	private final static Long APPLICATION_ID_1 = Long.valueOf(1);
	private final static Long APPLICATION_ID_2 = Long.valueOf(2);
	private final static Long APPLICATION_ID_3 = Long.valueOf(3);
	
	
	public static void main(String[] args){
		
		try{
			
		RulesServiceRequest request = generateRequest();
			
			
		//Response the Rules engine
		RulesServiceResponse response = new RulesServiceResponse();
		response = droolsExecService.executeAllRules(request);
		
		System.out.println("Executed Rules");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static RulesServiceRequest generateRequest(){
		RulesServiceRequest request = new RulesServiceRequest();
		
		request.setCustomers(createCustomers());
		request.setApplications(createApplications());
		
		return request;
	}
	
	private static Collection<Customer> createCustomers(){
		
		List<Customer> customers = new ArrayList<Customer>();
		customers.add( new Customer( "Dave", 20, 700, CUSTOMER_ID_1 ) );
		customers.add( new Customer( "Mike", 18, 750, CUSTOMER_ID_2 ) );
		customers.add( new Customer( "Bryan", 28, 450, CUSTOMER_ID_3 ) );
		
		return customers;
	}
	
	private static Collection<Application> createApplications(){
		
		List<Application> applications = new ArrayList<Application>();
		applications.add(new Application(new BigDecimal( 15000 ) , CUSTOMER_ID_1 , APPLICATION_ID_1 ));
		applications.add(new Application(new BigDecimal( 75000 ) , CUSTOMER_ID_3 , APPLICATION_ID_2 ));
		applications.add(new Application(new BigDecimal( 85000 ) , CUSTOMER_ID_2 , APPLICATION_ID_3 ));
		
		return applications;
	
	}
	
	
	
	
}
