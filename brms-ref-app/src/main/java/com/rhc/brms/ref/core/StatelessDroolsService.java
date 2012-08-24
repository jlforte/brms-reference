package com.rhc.brms.ref.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.DebugAgendaEventListener;
import org.drools.event.rule.DebugWorkingMemoryEventListener;
import org.drools.event.rule.DefaultAgendaEventListener;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.StatefulKnowledgeSession;



/**
 * 
 * This class handles everything related to executing rules and building a knowledge base.
 *
 */
public class StatelessDroolsService {

   private static final Logger logger = Logger.getLogger(StatelessDroolsService.class);
   private KnowledgeBase kbase;
   private HashMap<String, List<AfterActivationFiredEvent>> firedActivations;
   private boolean isRecordingFiredRuleActivations = false;
   private boolean isRecordingAuditLog = false;
   private String logFileName = "DroolsAuditLog";

   public ExecutionResults executeCommandList(List<Command> commands) {
	   
	   // Build Knowledge Base
      if ( this.kbase == null ) {
         this.kbase = buildKnowledgeBase();
      }

      // Setup of the session and audit logger
      StatefulKnowledgeSession ksession = this.kbase.newStatefulKnowledgeSession();
      
      
      // Audit Log can be turned on and off
      KnowledgeRuntimeLogger droolsAuditLogger = null;
      if ( isRecordingAuditLog ){
         droolsAuditLogger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, logFileName);
      }
      
      // Capture all fired rules if desired
      if ( isRecordingFiredRuleActivations ) { 
         addFiredRulesEventListener(ksession);
      }

      //ksession.addEventListener( new DebugAgendaEventListener() );
      //ksession.addEventListener( new DebugWorkingMemoryEventListener() );

      long startTime = System.currentTimeMillis();
      logger.info("Executing Drools commands...");
      ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(commands));
      logger.info("Executing Drools commands took " + (System.currentTimeMillis() - startTime) + " ms");

      // tear it down
      if ( isRecordingAuditLog ){
         droolsAuditLogger.close();
      }
      ksession.dispose();

      return results;
   }
   
   public Map<String, List<AfterActivationFiredEvent>> getFiredActivations() {
      return firedActivations;
   }

   /**
    * This is a slick way to capture all activations fired in the session so they can be interrogated 
    * 
    * @param kSession
    */
   private void addFiredRulesEventListener(StatefulKnowledgeSession kSession) {

      firedActivations = new HashMap<String, List<AfterActivationFiredEvent>>();
      kSession.addEventListener(new DefaultAgendaEventListener() {
         @Override
         public void afterActivationFired(AfterActivationFiredEvent event) {
            String name = event.getActivation().getRule().getName();
            List<AfterActivationFiredEvent> tempList = null;
            if (firedActivations.containsKey(name)) {
               tempList = firedActivations.get(name);
            } else {
               tempList = new ArrayList<AfterActivationFiredEvent>();
            }
            tempList.add(event);
            firedActivations.put(name, tempList);
         }
      });

   }
   
   /**
    * This is a simple method which can read in various rules resources and create your knowledge base.
    * 
    * @return kbase
    */
   private KnowledgeBase buildKnowledgeBase(){
      KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
      
      long startTime = System.currentTimeMillis();
      logger.info("Building Knowledge Base...");
      
      // add any new rules here
      kbuilder.add( ResourceFactory.newClassPathResource( "ApplicationValidation.drl", getClass() ), ResourceType.DRL );
      kbuilder.add( ResourceFactory.newClassPathResource( "CustomerValidation.drl", getClass() ), ResourceType.DRL );
      kbuilder.add( ResourceFactory.newClassPathResource( "MortgageRules.drl", getClass() ), ResourceType.DRL );
      
      if ( kbuilder.hasErrors() ) {
          logger.error( kbuilder.getErrors().toString() );
          System.out.println( kbuilder.getErrors().toString() );
      }
      
      KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
      kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );
      
      logger.info("Building Knowledge Base took " + (System.currentTimeMillis() - startTime) + " ms");
      
      return kbase;
   }
}
