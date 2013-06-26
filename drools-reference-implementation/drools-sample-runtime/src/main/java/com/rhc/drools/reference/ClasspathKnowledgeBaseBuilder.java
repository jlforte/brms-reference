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

import java.util.HashSet;
import java.util.Set;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Uses a list of classpath resources to construct a KnowledgeBase. This is the simplest way to build a KBase.
 * 
 * Note the creation of the KnowledgeBase is lazy
 */
public class ClasspathKnowledgeBaseBuilder implements KnowledgeBaseBuilder {

	private static Logger logger = LoggerFactory.getLogger( ClasspathKnowledgeBaseBuilder.class );
	private KnowledgeBase kBase;
	private Set<String> knowledgeResources;

	public ClasspathKnowledgeBaseBuilder( Set<String> knowledgeResources ) {
		this.knowledgeResources = knowledgeResources;
	}

	public ClasspathKnowledgeBaseBuilder() {
	}

	@Override
	public KnowledgeBase getKnowledgeBase() {

		if ( this.kBase == null ) {
			this.kBase = buildKnowledgeBase();
		}
		return this.kBase;
	}

	private KnowledgeBase buildKnowledgeBase() {

		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		long startTime = System.currentTimeMillis();
		logger.debug( "Building Knowledge Base..." );

		if ( knowledgeResources != null ) {
			for ( String resourceFile : knowledgeResources ) {

				if ( resourceFile.endsWith( ".drl" ) ) {
					kbuilder.add( ResourceFactory.newClassPathResource( resourceFile, getClass() ), ResourceType.DRL );
				} else if ( resourceFile.endsWith( ".bpmn" ) ) {
					kbuilder.add( ResourceFactory.newClassPathResource( resourceFile, getClass() ), ResourceType.BPMN2 );
				}

			}
		}

		if ( kbuilder.hasErrors() ) {
			logger.error( kbuilder.getErrors().toString() );
		}

		this.kBase = kbuilder.newKnowledgeBase();

		logger.debug( "Building Knowledge Base took " + ( System.currentTimeMillis() - startTime ) + " ms" );

		return kBase;
	}

	public void setKnowledgeResources( Set<String> knowledgeResources ) {
		this.knowledgeResources = knowledgeResources;
	}

	public void addKnowledgeResource( String resourceFile ) {
		if ( this.knowledgeResources == null ) {
			this.knowledgeResources = new HashSet<String>();
		}

		this.knowledgeResources.add( resourceFile );
	}

}
