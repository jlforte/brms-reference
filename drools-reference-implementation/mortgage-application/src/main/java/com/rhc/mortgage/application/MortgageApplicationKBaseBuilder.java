package com.rhc.mortgage.application;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhc.drools.reference.KnowledgeBaseBuilder;
import com.rhc.drools.reference.StatelessDroolsComponent;

public class MortgageApplicationKBaseBuilder implements KnowledgeBaseBuilder {

	private static Logger logger = LoggerFactory.getLogger( StatelessDroolsComponent.class );
	private static KnowledgeBase kBase;
	
	@Override
	public KnowledgeBase buildKnowledgeBase() {
		
		if (kBase != null){
			return kBase;
		}	
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		long startTime = System.currentTimeMillis();
		logger.debug( "Building Knowledge Base..." );

		// add any new rules here
		kbuilder.add( ResourceFactory.newClassPathResource( "ApplicationValidation.drl", getClass() ), ResourceType.DRL );
		kbuilder.add( ResourceFactory.newClassPathResource( "CustomerValidation.drl", getClass() ), ResourceType.DRL );
		kbuilder.add( ResourceFactory.newClassPathResource( "MortgageRules.drl", getClass() ), ResourceType.DRL );
		kbuilder.add( ResourceFactory.newClassPathResource( "Queries.drl", getClass() ), ResourceType.DRL );

		if ( kbuilder.hasErrors() ) {
			logger.error( kbuilder.getErrors().toString() );
		}

		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );

		logger.debug( "Building Knowledge Base took " + ( System.currentTimeMillis() - startTime ) + " ms" );

		// cache the kBase - its expensive to build
		kBase = kbase;
		
		return kbase;
	}

}
