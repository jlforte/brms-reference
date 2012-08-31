package com.rhc.brms.ref.mortageApplication;

import java.util.List;
import java.util.Map;

import org.drools.event.rule.AfterActivationFiredEvent;

/**
 * Holds all objects that tell us the result of our Mortgage Applications.
 * 
 * 
 * We don't want this class to reference Drools or rules. This class is simply a container for the domain objects needed
 * to solve the business problem at hand, which this case is applying for a mortgage. We don't need to introduce
 * Drools/Rules naming as this an implementation detail, and this class should be reusable for alternate implentations
 * that do not use Drools.
 * 
 */
public class MortageApplicationResponse {

	// TODO Still need to relocate this guy, as he is a Drools artifact and
	// ought to else where
	private Map<String, List<AfterActivationFiredEvent>> firedRules;

	public Map<String, List<AfterActivationFiredEvent>> getFiredRules() {
		return firedRules;
	}

	public void setFiredRules( Map<String, List<AfterActivationFiredEvent>> firedRules ) {
		this.firedRules = firedRules;
	}

}
