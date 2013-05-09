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

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;


public class ReflectiveExecutionResultsTransformerTest {

	@Test
	public void test() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException, InstantiationException {

		CommandListBuilder commandListBuilder = new RuleFlowCommandListBuilder();

		ClasspathKnowledgeBaseBuilder kBuilder = new ClasspathKnowledgeBaseBuilder();
		kBuilder.addKnowledgeResource( "ExecutionResultsTest.drl" );

		ExecutionResultsTransformer<MockResponse> transformer = new ReflectiveExecutionAnnotationResultsTransformer<MockResponse>();

		StatelessDroolsComponent<MockResponse> engine = new StatelessDroolsComponent<MockResponse>();
		engine.setResponse( MockResponse.class );
		engine.setCommandListBuilder( commandListBuilder );
		engine.setKnowledgeBaseBuilder( kBuilder );
		engine.setResultsTransformer( transformer );

		MockRequest request = new MockRequest();
		request.addObject( "Calvin" );
		request.addObject( "Hobbes" );
		request.addObject( new Integer( 1 ) );
		request.addObject( new Integer( 2 ) );

		MockResponse response = engine.execute( request );
		Set<String> expectedStrings = new HashSet<String>();
		expectedStrings.add( "Calvin" );
		expectedStrings.add( "Hobbes" );
		Set<Integer> expectedIntegers = new HashSet<Integer>();
		expectedIntegers.add( 1 );
		expectedIntegers.add( 2 );

		assertTrue( "Strings are not correct", response.getStrings().equals( expectedStrings ) );
		assertTrue( "Integers are not correct", response.getIntegers().equals( expectedIntegers ) );
	}

}
