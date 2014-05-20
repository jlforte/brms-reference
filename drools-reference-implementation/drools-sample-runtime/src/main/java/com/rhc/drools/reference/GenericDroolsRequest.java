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
import java.util.Collection;

/**
 * Simple Implementation of Drools Request Interface which is completely generic
 * 
 */
public class GenericDroolsRequest implements DroolsRequest {

	/**
	 * Contains all facts to be inserted in the Knowledge Session. Note, we are using a list so duplicates are allowed
	 */
	private ArrayList<Object> allFacts;

	/**
	 * The process or job to be run. With the default <code>RuleFlowCommandListBuilder</code>, this is the jBPM process
	 * representing the master rule flow.
	 */
	private String processId;

	public GenericDroolsRequest() {
	}

	public GenericDroolsRequest( ArrayList<Object> facts, String processId ) {
		super();
		this.allFacts = facts;
		this.processId = processId;
	}

	@Override
	public Collection<Object> getAllFacts() {
		if ( this.allFacts == null ) {
			this.allFacts = new ArrayList<Object>();
		}
		return this.allFacts;
	}

	@Override
	public String getProcessId() {
		return processId;
	}

	public void addFacts( Collection<Object> newFacts ) {
		this.getAllFacts().addAll( newFacts );

	}

	public void addFact( Object newFact ) {
		this.getAllFacts().add( newFact );

	}

	public void setAllFacts( Collection<Object> facts ) {
		this.allFacts = new ArrayList<Object>( facts );
	}

	public void setProcessId( String processId ) {
		this.processId = processId;
	}

}
