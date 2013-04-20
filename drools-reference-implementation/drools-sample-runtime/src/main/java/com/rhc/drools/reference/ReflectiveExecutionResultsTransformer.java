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

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.drools.runtime.ExecutionResults;

/**
 * @author Justin Holmes
 * 
 */
public class ReflectiveExecutionResultsTransformer<Response> implements ExecutionResultsTransformer<Object> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rhc.drools.reference.ExecutionResultsTransformer#transform(org.drools.runtime.ExecutionResults,
	 * java.util.Set)
	 */
	@Override
	public Object transform( ExecutionResults results, Set<QueryDeclaration> queryDeclarations ) {

		return null;
	}

	public static Object transform( ExecutionResults results, Set<QueryDeclaration> queryDeclarations, Class clazz ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		Class<?> beanClass = Class.forName( clazz.getName() );
		
		Object bean = beanClass.newInstance();
		for ( QueryDeclaration query : queryDeclarations ){
			PropertyUtils.setProperty( bean, query.getResponsePropertyName(), QueryUtils.extractSetFromExecutionResults( results, query ) );
		}
		
		return bean;
	}

}
