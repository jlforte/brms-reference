package com.rhc.brms.ref.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.command.runtime.rule.AgendaGroupSetFocusCommand;
import org.drools.runtime.ExecutionResults;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import com.rhc.brms.ref.core.RulesApplicationInterface;
import com.rhc.brms.ref.core.ExecutionResultsTransformer;
import com.rhc.brms.ref.core.StatelessDroolsService;
import com.rhc.brms.ref.util.AgendaGroupCommandUtil;

/**
 * 
 * A simple service that will return a RulesServiceResponse object which reflects 
 * 
 */
public class DroolsExecutionService implements RulesApplicationInterface<RulesServiceRequest, RulesServiceResponse> {

   private static final Logger logger = Logger.getLogger(DroolsExecutionService.class);

   private StatelessDroolsService droolsService = new StatelessDroolsService();
   private ExecutionResultsTransformer<RulesServiceResponse> transformer = new RulesServiceResultsTransformer();

   /*
    * (non-Javadoc)
    * @see com.cigna.drools.core.ApplicationInterface#executeAllRules(java.lang.Object)
    */
   public RulesServiceResponse executeAllRules(RulesServiceRequest request) {
      
      List<Command> commands = new ArrayList<Command>();

     
      //commands.add( CommandFactory.newInsert( request.getRequestDate()));  // insert Request Date
      
      // insert Facts being checked
//      MemberOccurrenceProfileAndIdentiferType[] memlist = request.getIndividualList().getIndividualArray(0).getMemberOccurrences().getMemberOccurrenceArray();
//		for (MemberOccurrenceProfileAndIdentiferType mem : memlist){ 
//			commands.add( CommandFactory.newInsert((MemberOccurrenceProfileAndIdentiferType)mem));
//			System.out.println("Inserting Member");
//			for (CoverageSummaryType cov : mem.getCoverageSummaries().getCoverageSummaryArray()){
//				commands.add( CommandFactory.newInsert((CoverageSummaryType)cov));
//				System.out.println("Inserting Coverage");
//				for (BenefitOptionType benefit : cov.getBenefitOptions().getBenefitOptionArray()) {
//					commands.add( CommandFactory.newInsert((BenefitOptionType)benefit));
//					System.out.println("Inserting Benefit");
//					for (CoverageOwnerSummaryType covOwner : benefit.getCoverageOwners().getCoverageOwnerArray()) {
//						System.out.println("Inserting CoverageOwner");
//						commands.add( CommandFactory.newInsert((CoverageOwnerSummaryType)covOwner));
//					}
//				}
//			}
//		}

	  //Set the Response Object
      RulesServiceResponse response = new RulesServiceResponse();
      commands.add( CommandFactory.newInsert(response, "response")); 
      //Set the Agenda groups
      //commands.add( AgendaGroupCommandUtil.buildAgendaGroupSetFocusCommand( "FilterCoverages" ) );
      //commands.add( AgendaGroupCommandUtil.buildAgendaGroupSetFocusCommand( "IdentifyCoverages" ) );
      commands.add( CommandFactory.newFireAllRules() );
      
      //Execute commands
      ExecutionResults results = droolsService.executeCommandList( commands );
      response = transformer.transform(results);
      response.setFiredRules( droolsService.getFiredActivations());
   
      return response;
   }
   
   
   /*
    * This ought to go in a utility class
    */


}
