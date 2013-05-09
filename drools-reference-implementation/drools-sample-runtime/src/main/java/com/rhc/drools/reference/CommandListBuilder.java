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

import java.util.List;

import org.drools.command.Command;

/**
 * <p>
 * Drools Concern #2: Business Logic needed to insert objects into the Drools Runtime and manipulate the execution of
 * the engine (e.g. setting agenda groups or firing rules). The Drools Batch Command API offers an excellent out of the
 * box solution for this problem. With the Batch Command API, our business logic is entirely separate from the Drools
 * runtime framework which makes for cleaner code.
 * </p>
 * 
 * NOTE: We've left the task of retrieving objects from the Knowledge Session to the ExecutionResultsTransformer
 * 
 * 
 * @param request
 * @return List of Drools Commands that encapsulate our business logic.
 */

public interface CommandListBuilder {

	@SuppressWarnings("rawtypes")
	public List<Command> buildBusinessLogicCommandList( DroolsRequest request );

}
