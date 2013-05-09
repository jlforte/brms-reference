package com.rhc.drools.reference;

import java.util.Set;

/**
 * Interface for inserting objects into the knowledge session.
 * 
 */
// TODO rename this to DroolsRequest
public interface Request {

	/**
	 * This method must be implemented. All objects returned by this method are inserted into the knowledge session.
	 * 
	 * @return
	 */
	public Set<Object> getAllObjects();

}
