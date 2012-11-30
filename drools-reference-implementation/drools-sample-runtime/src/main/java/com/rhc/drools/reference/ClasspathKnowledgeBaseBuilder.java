package com.rhc.drools.reference;

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
	
	public KnowledgeBase buildKnowledgeBase(){
	
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		long startTime = System.currentTimeMillis();
		logger.debug( "Building Knowledge Base..." );

		if ( knowledgeResources != null ) {
			for ( String resource : knowledgeResources ) {
				kbuilder.add( ResourceFactory.newClassPathResource( resource, getClass() ), ResourceType.DRL );
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

}
