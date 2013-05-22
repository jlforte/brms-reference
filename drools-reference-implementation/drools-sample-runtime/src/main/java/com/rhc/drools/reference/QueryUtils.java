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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;

/**
 * 
 */
public class QueryUtils {

	/**
	 * Creates query commands based on the QueryInfo annotation on methods in the Class being passed in.
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected static Set<Command> buildQueryCommands( Class clazz ) {
		Set<Command> queryCommands = new HashSet<Command>();
		if ( clazz != null ) {
			Collection<Field> fields = getAllFields( clazz );
			for ( Field field : fields ) {
				DroolsQueryInfo queryInfo = field.getAnnotation( DroolsQueryInfo.class );
				if ( queryInfo != null ) {
					String queryName = queryInfo.queryName();
					queryCommands.add( CommandFactory.newQuery( queryName, queryName ) );
				}
			}
		}
		return queryCommands;
	}

	public static Collection<?> extractCollectionFromExecutionResults( ExecutionResults exectionResults,
			String queryName, String binding ) {
		Collection<Object> list = new ArrayList<Object>();
		if ( exectionResults != null ) {
			QueryResults queryResult = (QueryResults) exectionResults.getValue( queryName );
			if ( queryResult != null ) {
				for ( QueryResultsRow row : queryResult ) {
					list.add( row.get( binding ) );
				}
			}
		}
		return list;
	}

	public static Collection<Field> getAllFields( Class<?> clazz ) {
		Collection<Field> fields = new ArrayList<Field>();
		addFields( clazz, fields );
		Class<?> superClazz = clazz;
		while ( superClazz.getSuperclass() != null ) {
			superClazz = superClazz.getSuperclass();
			addFields( superClazz, fields );
		}
		return fields;
	}

	private static void addFields( Class<?> clazz, Collection<Field> fields ) {
		for ( Field field : clazz.getDeclaredFields() ) {
			fields.add( field );
		}
	}
}
