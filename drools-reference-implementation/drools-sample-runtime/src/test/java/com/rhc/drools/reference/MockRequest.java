package com.rhc.drools.reference;

import java.util.HashSet;
import java.util.Set;

public class MockRequest implements DroolsRequest {

	private Set<Object> objectSet;

	@Override
	public Set<Object> getAllFacts() {
		return objectSet;
	}

	public void addObject( Object obj ) {
		if ( objectSet == null ) {
			objectSet = new HashSet<Object>();
		}
		objectSet.add( obj );
	}

	@Override
	public String getProcessId() {
		// TODO Auto-generated method stub
		return null;
	}

}
