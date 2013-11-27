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

import java.io.File;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Assert;
import org.junit.Test;

public class DirectoryKnowledgeBaseBuilderTest {

	@Test
	public void shouldFailToBuildWithInvalidDirectory() {

		// Given
		DirectoryKnowledgeBaseBuilder builder = new DirectoryKnowledgeBaseBuilder( "/directoryThatShouldNotExist" );

		// When
		KnowledgeBase kBase = builder.getKnowledgeBase();

		// Then
		Assert.assertNull( kBase );
	}

	@Test
	public void shouldFailToBuildWithAValidDirectoryWithNoFiles() {
		// Given
		String directoryPath = "/home/sherl0ck/Code/brms-reference/drools-reference-implementation/drools-sample-runtime/src/test/resources/com/rhc/drools/reference/emptyDirectory";
		DirectoryKnowledgeBaseBuilder builder = new DirectoryKnowledgeBaseBuilder( directoryPath );

		// When
		KnowledgeBase kBase = builder.getKnowledgeBase();

		// Then
		Assert.assertTrue(
				String.format( "Directory (%s) must exist - chances are you've removed it. ", directoryPath ),
				new File( directoryPath ).isDirectory() );

		Assert.assertNull( String.format(
				"Knowledge Base should be null because the %s should not have files in it. Check that it's empty.",
				directoryPath ), kBase );

	}

	@Test
	public void shouldFailToBuildWithAValidDirectoryAndInvalidResources() {
		// Given
		String directoryPath = "/home/sherl0ck/Code/brms-reference/drools-reference-implementation/drools-sample-runtime/src/test/resources/com/rhc/drools/reference/directoryWithBadFiles";
		DirectoryKnowledgeBaseBuilder builder = new DirectoryKnowledgeBaseBuilder( directoryPath );

		// When
		KnowledgeBase kBase = builder.getKnowledgeBase();

		// Then
		Assert.assertTrue(
				String.format( "Directory (%s) must exist - chances are you've removed it. ", directoryPath ),
				new File( directoryPath ).isDirectory() );

		Assert.assertNull( String.format(
				"Knowledge Base should be null because the %s should not have files in it. Check that it's empty.",
				directoryPath ), kBase );
	}

	@Test
	public void shouldBuildAKnowledgeBaseWithAValidDirectoryWithTwoDrls() {

		// Given
		String directoryPath = "/home/sherl0ck/Code/brms-reference/drools-reference-implementation/drools-sample-runtime/src/test/resources/com/rhc/drools/reference/directoryWithEmptyRule";
		DirectoryKnowledgeBaseBuilder builder = new DirectoryKnowledgeBaseBuilder( directoryPath );

		// When
		KnowledgeBase kBase = builder.getKnowledgeBase();

		// Then
		Assert.assertNotNull( kBase );
		StatefulKnowledgeSession ksession = kBase.newStatefulKnowledgeSession();
		Assert.assertNotNull( ksession );
		ksession.fireAllRules();
		ksession.dispose();
	}

}
