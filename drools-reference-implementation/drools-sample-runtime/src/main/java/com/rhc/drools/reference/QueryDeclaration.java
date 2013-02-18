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

import org.drools.command.Command;
import org.drools.command.CommandFactory;

/**
 * Simple class that encapsulates pertinent information about a Drools Query.
 * 
 * 
 */
public class QueryDeclaration<T> {

	private String queryName;
	private String variableDeclaration;

	public QueryDeclaration( String queryName, String variableDeclaration ) {
		this.queryName = queryName;
		this.variableDeclaration = variableDeclaration;
	}

	public QueryDeclaration() {
	}

	@SuppressWarnings("rawtypes")
	public Command buildQueryCommand() {
		// We reuse the queryName for the query outIdentifier to simplify the API
		return CommandFactory.newQuery( queryName, queryName );
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName( String queryName ) {
		this.queryName = queryName;
	}

	public String getVariableDeclaration() {
		return variableDeclaration;
	}

	public void setVariableDeclaration( String variableDeclaration ) {
		this.variableDeclaration = variableDeclaration;
	}

}
