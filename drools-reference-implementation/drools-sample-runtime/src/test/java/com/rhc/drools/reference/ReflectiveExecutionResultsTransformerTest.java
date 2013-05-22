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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class ReflectiveExecutionResultsTransformerTest {

	@Test
	public void test() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException, InstantiationException {

		CommandListBuilder commandListBuilder = new RuleFlowCommandListBuilder();

		ClasspathKnowledgeBaseBuilder kBuilder = new ClasspathKnowledgeBaseBuilder();
		kBuilder.addKnowledgeResource( "ExecutionResultsTest.drl" );

		ExecutionResultsTransformer transformer = new ReflectiveExecutionResultsTransformer();

		StatelessDroolsComponent engine = new StatelessDroolsComponent();
		engine.setCommandListBuilder( commandListBuilder );
		engine.setKnowledgeBaseBuilder( kBuilder );
		engine.setResultsTransformer( transformer );

		MockRequest request = new MockRequest();
		request.addObject( "Calvin" );
		request.addObject( "Hobbes" );
		request.addObject( new Integer( 1 ) );
		request.addObject( new Integer( 2 ) );

		MockResponse response = engine.execute( request, MockResponse.class );
		List<String> expectedStrings = new ArrayList<String>();
		expectedStrings.add( "Calvin" );
		expectedStrings.add( "Hobbes" );
		List<Integer> expectedIntegers = new ArrayList<Integer>();
		expectedIntegers.add( 1 );
		expectedIntegers.add( 2 );
		Collections.sort( (List<String>) response.getStrings() );
		Collections.sort( (List<Integer>) response.getIntegers() );
		Collections.sort( expectedStrings );
		Collections.sort( expectedIntegers );

		assertTrue( "Strings are not correct", response.getStrings().equals( expectedStrings ) );
		assertTrue( "Integers are not correct", response.getIntegers().equals( expectedIntegers ) );
	}

}
