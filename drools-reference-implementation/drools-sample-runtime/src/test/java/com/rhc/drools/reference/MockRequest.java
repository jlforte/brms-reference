package com.rhc.drools.reference;

import java.util.HashSet;
import java.util.Set;

public class MockRequest implements Request {

	private Set<Object> objectSet;
	
	@Override
	public Set<Object> getAllObjects() {
		return objectSet;
	}
	
	public void addObject(Object obj){
		if(objectSet == null){
			objectSet = new HashSet<Object>();
		}
		objectSet.add(obj);
	}

}
