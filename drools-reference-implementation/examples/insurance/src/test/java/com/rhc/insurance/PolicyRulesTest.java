package com.rhc.insurance;


import static org.junit.Assert.assertEquals;

import insurance.Member;
import insurance.Policy;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This is a sample class to launch a rule.
 */


public class PolicyRulesTest {

	static KnowledgeBase kbase;
	static StatefulKnowledgeSession ksession;
	static KnowledgeRuntimeLogger logger;
	
	

	
	@BeforeClass
	public static void setupKsession() {
		try {
			// load up the knowledge base
			kbase = readKnowledgeBase();
			ksession = kbase.newStatefulKnowledgeSession();
			//logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger(ksession, "log/policyQuote", 500);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	@AfterClass
	public static void closeKsession() {
		try {
			// load up the knowledge base
			//logger.close();
			ksession.dispose();

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Test
	public void lowPHlowBHTest() {
		
		try {
			//now create some test data
			Member member= new Member();
			member.setSmokesRegularly(true);
			member.setFilesMedicationRegularly(false);
			
			Policy policy = new Policy();
			
			// insert objects into working memory
			FactHandle driverFH = ksession.insert(member);
			FactHandle policyFH = ksession.insert(policy);
			ksession.fireAllRules();
			//logger.close();
			ksession.retract(driverFH);
			ksession.retract(policyFH);
			
			assertEquals("Price is 1000", 1000, policy.getPrice());
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	@Test
	public void lowPHhighBHTest() {
		
			try {
				//now create some test data
				Member member= new Member();
				member.setSmokesRegularly(true);
				member.setFilesMedicationRegularly(true);
				
				Policy policy = new Policy();
				
				// insert objects into working memory
				FactHandle driverFH = ksession.insert(member);
				FactHandle policyFH = ksession.insert(policy);
				ksession.fireAllRules();
				//logger.close();
				ksession.retract(driverFH);
				ksession.retract(policyFH);
				
				assertEquals("Price is 800", 800, policy.getPrice());
				
			} catch (Throwable t) {
				t.printStackTrace();
			}
	}

	@Test
	public void highPHlowBHTest() {
		
		try {
			//now create some test data
			Member member= new Member();
			member.setSmokesRegularly(false);
			member.setFilesMedicationRegularly(true);
			
			Policy policy = new Policy();
			
			// insert objects into working memory
			FactHandle memberFH = ksession.insert(member);
			FactHandle policyFH = ksession.insert(policy);
			ksession.fireAllRules();
			//logger.close();
			ksession.retract(memberFH);
			ksession.retract(policyFH);
			
			assertEquals("Price is 500", 500, policy.getPrice());
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	@Test
	public void highPHhighBHTest() {
		try {
			//now create some test data
			Member member= new Member();
			member.setSmokesRegularly(false);
			member.setFilesMedicationRegularly(true);
			
			Policy policy = new Policy();
			
			// insert objects into working memory
			FactHandle driverFH = ksession.insert(member);
			FactHandle policyFH = ksession.insert(policy);
			ksession.fireAllRules();
			//logger.close();
			ksession.retract(driverFH);
			ksession.retract(policyFH);
			
			assertEquals("Price is 300", 300, policy.getPrice());
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	

	
	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("policyquote.package"), ResourceType.DRL);
		hasErrors(kbuilder);
		kbuilder.add(ResourceFactory.newClassPathResource("lowPHlowBH.drl"), ResourceType.DRL);
		hasErrors(kbuilder);
		kbuilder.add(ResourceFactory.newClassPathResource("lowPHhighBH.drl", getClass), ResourceType.DRL);
		hasErrors(kbuilder);
		kbuilder.add(ResourceFactory.newClassPathResource("highPHlowBH.drl"), ResourceType.DRL);
		hasErrors(kbuilder);
		kbuilder.add(ResourceFactory.newClassPathResource("highPHhighBH.drl"), ResourceType.DRL);
		hasErrors(kbuilder);
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		hasErrors(kbuilder);
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

		return kbase;
	}
		
	private static void hasErrors(KnowledgeBuilder kbuilder) throws Exception {
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error: errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		
	}


}