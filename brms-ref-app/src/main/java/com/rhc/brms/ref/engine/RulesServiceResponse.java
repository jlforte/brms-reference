package com.rhc.brms.ref.engine;

import java.util.List;
import java.util.Map;

import org.drools.event.rule.AfterActivationFiredEvent;

public class RulesServiceResponse {
	
	private Map<String, List<AfterActivationFiredEvent>> firedRules;

	public Map<String, List<AfterActivationFiredEvent>> getFiredRules() {
		return firedRules;
	}

	public void setFiredRules(
			Map<String, List<AfterActivationFiredEvent>> firedRules) {
		this.firedRules = firedRules;
	}
	
}
