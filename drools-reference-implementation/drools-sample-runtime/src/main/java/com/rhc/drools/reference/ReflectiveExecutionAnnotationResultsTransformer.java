package com.rhc.drools.reference;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.drools.runtime.ExecutionResults;
import org.h2.constant.SysProperties;

public class ReflectiveExecutionAnnotationResultsTransformer<Response> implements ExecutionResultsTransformer<Response> {

	
	private Class<Response> response;
	
	
	public ReflectiveExecutionAnnotationResultsTransformer(Class<Response> clazz){
		this.response = clazz;
	}
	
	public ReflectiveExecutionAnnotationResultsTransformer(){
		
	}
	
	
	@Override
	public Response transform(ExecutionResults results) {
		Response response = null;
		try {
			response = this.response.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Method method : this.response.getMethods()){
			QueryInfo queryInfo = method.getAnnotation(QueryInfo.class);
			if(queryInfo!=null){
				String queryName = queryInfo.queryName();
				String binding = queryInfo.binding();
				try {
					Set<?> set = QueryUtils.extractSetFromExecutionResults(results, queryName, binding);
					method.invoke(response, set);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	public Class<Response> getResponse() {
		return response;
	}

	public void setResponse(Class<Response> response) {
		this.response = response;
	}
	
}
