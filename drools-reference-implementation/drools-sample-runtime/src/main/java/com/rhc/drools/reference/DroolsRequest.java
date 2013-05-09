package com.rhc.drools.reference;

import java.util.Collection;

/**
 * Interface for inserting objects into the knowledge session.
 * 
 */
public interface DroolsRequest {

	/**
	 * This method must be implemented. All objects returned by this method are inserted into the knowledge session.
	 * 
	 * @return
	 */
	public Collection<Object> getAllObjects();

}
