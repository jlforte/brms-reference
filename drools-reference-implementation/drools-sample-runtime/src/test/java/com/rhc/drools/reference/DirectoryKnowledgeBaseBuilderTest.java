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
import java.io.IOException;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DirectoryKnowledgeBaseBuilderTest {

	private String directoryThatShouldExist;
	private String emptyDirectory;
	private String directoryWithBadFiles;
	private String directoryWithSimpleDrl;
	private String directoryWithSimpleDrl2;

	@Before
	public void setUp() throws IOException {
		String baseDirectory = new File( "" ).getCanonicalPath();

		this.directoryThatShouldExist = "/directoryThatShouldNotExist";
		this.directoryWithBadFiles = baseDirectory
				.concat( "/src/test/resources/com/rhc/drools/reference/directoryWithBadFiles" );
		this.emptyDirectory = baseDirectory.concat( "/src/test/resources/com/rhc/drools/reference/emptyDirectory" );
		this.directoryWithSimpleDrl = baseDirectory
				.concat( "/src/test/resources/com/rhc/drools/reference/directoryWithEmptyRule" );
		this.directoryWithSimpleDrl2 = baseDirectory
				.concat( "/src/test/resources/com/rhc/drools/reference/directoryWithEmptyRule2" );
	}

	@Test
	public void shouldFailToBuildWithInvalidDirectory() {

		// Given
		DirectoryKnowledgeBaseBuilder builder = new DirectoryKnowledgeBaseBuilder( directoryThatShouldExist );

		// When
		KnowledgeBase kBase = builder.getKnowledgeBase();

		// Then
		Assert.assertNull( kBase );
	}

	@Test
	public void shouldFailToBuildWithAValidDirectoryWithNoFiles() {
		// Given
		DirectoryKnowledgeBaseBuilder builder = new DirectoryKnowledgeBaseBuilder( emptyDirectory );

		// When
		KnowledgeBase kBase = builder.getKnowledgeBase();

		// Then
		Assert.assertTrue(
				String.format( "Directory (%s) must exist - chances are you've removed it. ", emptyDirectory ),
				new File( emptyDirectory ).isDirectory() );

		Assert.assertNull( String.format(
				"Knowledge Base should be null because the %s should not have files in it. Check that it's empty.",
				emptyDirectory ), kBase );

	}

	@Test
	public void shouldFailToBuildWithAValidDirectoryAndInvalidResources() {
		// Given

		DirectoryKnowledgeBaseBuilder builder = new DirectoryKnowledgeBaseBuilder( directoryWithBadFiles );

		// When
		KnowledgeBase kBase = builder.getKnowledgeBase();

		// Then
		Assert.assertTrue(
				String.format( "Directory (%s) must exist - chances are you've removed it. ", directoryWithBadFiles ),
				new File( directoryWithBadFiles ).isDirectory() );

		Assert.assertNull( String.format(
				"Knowledge Base should be null because the %s should not have files in it. Check that it's empty.",
				directoryWithBadFiles ), kBase );
	}

	@Test
	public void shouldBuildAKnowledgeBaseWithAValidDirectoryWithAValidDrlAndRetainKBaseAfterRebuildWithBadDirectory() {

		// Given
		DirectoryKnowledgeBaseBuilder builder = new DirectoryKnowledgeBaseBuilder( directoryWithSimpleDrl );

		// When
		KnowledgeBase kBase = builder.getKnowledgeBase();

		// Then
		Assert.assertNotNull( kBase );
		StatefulKnowledgeSession ksession = kBase.newStatefulKnowledgeSession();
		Assert.assertNotNull( ksession );
		ksession.fireAllRules();
		ksession.dispose();

		// When
		builder.setDirectoryPath( directoryThatShouldExist );
		builder.buildKnowledgeBase();
		kBase = builder.getKnowledgeBase();

		// Then
		Assert.assertNotNull( kBase );
		ksession = kBase.newStatefulKnowledgeSession();
		Assert.assertNotNull( ksession );
		ksession.fireAllRules();
		ksession.dispose();

	}

	@Test
	public void shouldBuildAKnowledgeBaseWithAValidDirectoryWithAValidDrlAndThenReBuildWithASecondValidDirectoryWithNewRules() {

		// Given
		DirectoryKnowledgeBaseBuilder builder = new DirectoryKnowledgeBaseBuilder( directoryWithSimpleDrl );

		// When
		KnowledgeBase kBase = builder.getKnowledgeBase();

		// Then
		Assert.assertNotNull( kBase );
		StatefulKnowledgeSession ksession = kBase.newStatefulKnowledgeSession();
		Assert.assertNotNull( ksession );
		ksession.fireAllRules();
		ksession.dispose();

		// When
		builder.setDirectoryPath( directoryWithSimpleDrl2 );
		builder.buildKnowledgeBase();
		kBase = builder.getKnowledgeBase();

		// Then
		Assert.assertNotNull( kBase );
		ksession = kBase.newStatefulKnowledgeSession();
		Assert.assertNotNull( ksession );
		ksession.fireAllRules();
		ksession.dispose();
	}

}
