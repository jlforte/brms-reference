package com.rhc.brms.ref.engine;

import java.util.List;

import org.drools.command.Command;
import org.drools.runtime.ExecutionResults;

import com.rhc.brms.ref.core.ExecutionResultsTransformer;

public class MortageApplicationResultsTransformer implements ExecutionResultsTransformer<MortageApplicationResponse> {

	/**
	 * Queries to grab references from the engine. These are objects we will return to the caller.
	 */
	@SuppressWarnings("rawtypes")
	private final List<Command> queryCommandList = buildQueryCommandList();

	public MortageApplicationResponse transform( ExecutionResults results ) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List<Command> getQueryCommands() {
		return queryCommandList;
	}

	@SuppressWarnings("rawtypes")
	private static List<Command> buildQueryCommandList() {
		return null;
	}
}
