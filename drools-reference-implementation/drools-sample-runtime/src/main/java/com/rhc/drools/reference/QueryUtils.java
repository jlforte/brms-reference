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

import org.drools.command.Command;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;

/**
 * 
 */
public class QueryUtils {

	@SuppressWarnings("rawtypes")
	public static Set<Command> buildQueryCommands( Set<QueryDeclaration> queryDeclarations ) {
		Set<Command> queryCommands = new HashSet<Command>();
		if ( queryDeclarations != null ) {
			for ( QueryDeclaration<?> qi : queryDeclarations ) {
				queryCommands.add( qi.buildQueryCommand() );
			}
		}
		return queryCommands;
	}

	@SuppressWarnings("unchecked")
	public static <T> Set<T> extractSetFromExecutionResults( ExecutionResults exectionResults,
			QueryDeclaration<T> queryDeclaration ) {
		Set<T> set = new HashSet<T>();
		if ( exectionResults != null ) {
			QueryResults queryResult = (QueryResults) exectionResults.getValue( queryDeclaration.getQueryName() );
			if ( queryResult != null ) {
				for ( QueryResultsRow row : queryResult ) {
					set.add( (T) row.get( queryDeclaration.getVariableDeclaration() ) );
				}
			}
		}
		return set;
	}
}
