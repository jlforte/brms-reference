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

import java.util.ArrayList;
import java.util.List;

import org.drools.command.Command;
import org.drools.command.CommandFactory;

/**
 * 
 * Simple implementation of the CommandListBuilder Interface to use jBPM to control rule flow
 * 
 */
public class RuleFlowCommandListBuilder implements CommandListBuilder {

	public RuleFlowCommandListBuilder() {
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<Command> buildBusinessLogicCommandList( DroolsRequest request ) {
		List<Command> commands = new ArrayList<Command>();
		commands.add( CommandFactory.newInsertElements( request.getAllFacts() ) );
		if ( request.getProcessId() != null ) {
			commands.add( CommandFactory.newStartProcess( request.getProcessId() ) );
		}
		commands.add( CommandFactory.newFireAllRules() );
		return commands;
	}

}
