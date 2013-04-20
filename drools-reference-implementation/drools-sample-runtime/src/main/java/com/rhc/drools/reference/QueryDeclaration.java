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

	// The name of the Drools Query
	private String queryName;
	
	// The declared variable name that the query result set is bound to in the Drools Query
	private String variableDeclaration;
	
	// The class of the result set. Drools does not require that the result set has a single type but we do
	private Class<?> resultClass;
	
	// The name of the property on the Response object you wish to set with the result set
	private String responsePropertyName;

	/**
	 * For use with the ReflectiveExecutionResultsTransformer
	 */
	public QueryDeclaration( String queryName, String variableDeclaration, Class<?> resultClass,
			String responsePropertyName ) {
		this.queryName = queryName;
		this.variableDeclaration = variableDeclaration;
		this.resultClass = resultClass;
		this.responsePropertyName = responsePropertyName;
	}
	
	/**
	 * For custom implementations of the ExecutionResultsTransformer
	 */
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

	public void setResultClass( Class<?> resultClass ) {
		this.resultClass = resultClass;
	}

	public Class<?> getResultClass() {
		return resultClass;
	}

	public void setResponsePropertyName( String responsePropertyName ) {
		this.responsePropertyName = responsePropertyName;
	}

	public String getResponsePropertyName() {
		return responsePropertyName;
	}

}
