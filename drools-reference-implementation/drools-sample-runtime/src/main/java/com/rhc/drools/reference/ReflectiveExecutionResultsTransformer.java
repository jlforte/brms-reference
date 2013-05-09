package com.rhc.drools.reference;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;
import org.drools.runtime.ExecutionResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default Transformer. This class will set the Response based on the QueryInfo annotation on a method in the response.
 * 
 * 
 * @param <Response>
 */

public class ReflectiveExecutionResultsTransformer implements ExecutionResultsTransformer {

	private static Logger logger = LoggerFactory.getLogger( ReflectiveExecutionResultsTransformer.class );

	public ReflectiveExecutionResultsTransformer() {

	}

	@Override
	public <Response> Response transform( ExecutionResults results, Class<Response> responseClazz ) {
		Response response = null;
		try {
			response = responseClazz.newInstance();
		} catch ( InstantiationException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( IllegalAccessException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for ( Field field : QueryUtils.getAllFields( responseClazz ) ) {
			DroolsQueryInfo queryInfo = field.getAnnotation( DroolsQueryInfo.class );
			if ( queryInfo != null ) {
				String queryName = queryInfo.queryName();
				String binding = queryInfo.binding();
				Class<?> type = field.getType();
				if ( Collection.class.equals( type ) ) {
					try {
						Collection<?> list = QueryUtils.extractCollectionFromExecutionResults( results, queryName,
								binding );
						PropertyUtils.setProperty( response, field.getName(), list );
					} catch ( IllegalArgumentException e ) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch ( IllegalAccessException e ) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch ( InvocationTargetException e ) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch ( NoSuchMethodException e ) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					logger.warn( "QueryInfo annotation can not be used on " + field.getName()
							+ ". It only be used on fields which are of Type Collection" );
				}
			}
		}
		return response;
	}
}
