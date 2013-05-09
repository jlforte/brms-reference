package com.rhc.drools.reference;

import java.util.ArrayList;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BPMTest {

	private static KnowledgeBase kBase;
	private static StatelessKnowledgeSession kSession;

	@BeforeClass
	public static void setUp() {
		ClasspathKnowledgeBaseBuilder kBuilder = new ClasspathKnowledgeBaseBuilder();
		kBuilder.addKnowledgeResource( "BPMTest.drl" );
		kBuilder.addKnowledgeResource( "test.bpmn" );
		kBase = kBuilder.getKnowledgeBase();
	}

	@Before
	public void setUpNewTest() {
		kSession = kBase.newStatelessKnowledgeSession();
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testStatelessSession() {
		List<Command> commands = new ArrayList<Command>();
		commands.add( CommandFactory.newStartProcess( "com.rhc.drools.test" ) );
		commands.add( CommandFactory.newFireAllRules() );
		kSession.execute( CommandFactory.newBatchExecution( commands ) );
	}

	@Test
	public void testStatefulSession() {
		StatefulKnowledgeSession kSess = kBase.newStatefulKnowledgeSession();
		kSess.startProcess( "com.rhc.drools.test" );
		System.out.println( kSess.getProcessInstances().size() );
		kSess.fireAllRules();
		System.out.println( kSess.getProcessInstances().size() );
	}
}
