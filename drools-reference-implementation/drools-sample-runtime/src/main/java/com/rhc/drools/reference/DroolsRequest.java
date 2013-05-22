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
	public Collection<Object> getAllFacts();

	/**
	 * This returns a process id that can be used by a command list builder. In the case of the Rule Flow Command List
	 * Builder, this is the BPM process to be run.
	 * 
	 * @return
	 */
	public String getProcessId();

}
