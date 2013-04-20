/* 
 * Written by Red Hat Consulting.	
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rhc.drools.reference;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.StatelessKnowledgeSession;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Justin Holmes
 * 
 */

public class ReflectiveExecutionResultsTransformerTest {

	private static Set<QueryDeclaration> queries = new HashSet<QueryDeclaration>();
	private static ExecutionResults executionResults;

	@BeforeClass
	public static void setUp() {
		QueryDeclaration<String> stringDeclaration = new QueryDeclaration<String>();
		stringDeclaration.setQueryName( "Get Strings" );
		stringDeclaration.setResponsePropertyName( "strings" );
		stringDeclaration.setResultClass( MockResponse.class );
		stringDeclaration.setVariableDeclaration( "$string" );
		queries.add( stringDeclaration );

		QueryDeclaration<Integer> integerDeclaration = new QueryDeclaration<Integer>();
		integerDeclaration.setQueryName( "Get Integers" );
		integerDeclaration.setResponsePropertyName( "integers" );
		integerDeclaration.setResultClass( MockResponse.class );
		integerDeclaration.setVariableDeclaration( "$integer" );
		queries.add( integerDeclaration );

		List<Command> commands = new ArrayList<Command>();
		commands.add( CommandFactory.newInsert( new String( "Calvin" ) ) );
		commands.add( CommandFactory.newInsert( new String( "Hobbes" ) ) );
		commands.add( CommandFactory.newInsert( new Integer( "1" ) ) );
		commands.add( CommandFactory.newInsert( new Integer( "2" ) ) );
		commands.add( CommandFactory.newFireAllRules() );
		commands.addAll( QueryUtils.buildQueryCommands( queries ) );

		ClasspathKnowledgeBaseBuilder kBuilder = new ClasspathKnowledgeBaseBuilder();
		kBuilder.addKnowledgeResource( "ExecutionResultsTest.drl" );
		StatelessKnowledgeSession kSession = kBuilder.getKnowledgeBase().newStatelessKnowledgeSession();
		KnowledgeRuntimeLogger log = KnowledgeRuntimeLoggerFactory.newFileLogger( kSession, "ReflectiveExecutionResults" );
		executionResults = kSession.execute( CommandFactory.newBatchExecution( commands ) );
		log.close();
	}

	@Test
	public void test() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException, InstantiationException {

		for ( QueryDeclaration<?> query : queries ) {
			Assert.assertTrue( QueryUtils.extractSetFromExecutionResults( executionResults, query ).size() > 0 );
		}
		
		MockResponse response = (MockResponse) ReflectiveExecutionResultsTransformer.transform( executionResults, queries,
				MockResponse.class );
		
		System.out.println( response );

	}

}
