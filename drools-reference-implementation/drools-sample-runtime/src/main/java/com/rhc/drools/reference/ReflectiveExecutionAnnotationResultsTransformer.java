package com.rhc.drools.reference;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.drools.runtime.ExecutionResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default Transformer. This class will set the Response based on the QueryInfo annotation on a method in the response.
 * 
 * @author justin
 * 
 * @param <Response>
 */
public class ReflectiveExecutionAnnotationResultsTransformer<Response> implements ExecutionResultsTransformer<Response> {

	private static Logger logger = LoggerFactory.getLogger( ReflectiveExecutionAnnotationResultsTransformer.class );
	private Class<Response> response;

	public ReflectiveExecutionAnnotationResultsTransformer() {

	}

	@Override
	public Response transform( ExecutionResults results ) {
		Response response = null;
		try {
			response = this.response.newInstance();
		} catch ( InstantiationException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( IllegalAccessException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for ( Method method : this.response.getMethods() ) {
			QueryInfo queryInfo = method.getAnnotation( QueryInfo.class );
			if ( queryInfo != null ) {
				String queryName = queryInfo.queryName();
				String binding = queryInfo.binding();
				Class<?>[] params = method.getParameterTypes();
				if ( params.length == 1 && params[0].isAssignableFrom( Set.class ) ) {
					try {
						Set<?> set = QueryUtils.extractSetFromExecutionResults( results, queryName, binding );
						method.invoke( response, set );
					} catch ( IllegalArgumentException e ) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch ( IllegalAccessException e ) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch ( InvocationTargetException e ) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					logger.warn( "QueryInfo annotation can not be used on " + method.getName()
							+ ". It only be used on methods that take in a single paramter which is a Set" );
				}
			}
		}
		return response;
	}

	public Class<Response> getResponse() {
		return response;
	}

	public void setResponse( Class<Response> response ) {
		this.response = response;
	}

}
