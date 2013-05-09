package com.rhc.drools.reference;

import java.util.Set;

/**
 * Simple class to be used in this test alone
 * 
 */
public class MockResponse {

	@DroolsQueryInfo(queryName = "Get Strings", binding = "$string")
	private Set<String> strings;

	@DroolsQueryInfo(queryName = "Get Integers", binding = "$integer")
	private Set<Integer> integers;

	public MockResponse() {
	}

	public Set<String> getStrings() {
		return strings;
	}

	public void setStrings( Set<String> strings ) {
		this.strings = strings;
	}

	public Set<Integer> getIntegers() {
		return integers;
	}

	public void setIntegers( Set<Integer> integers ) {
		this.integers = integers;
	}

	@Override
	public String toString() {
		return "MockRespose [strings=" + strings + ", integers=" + integers + "]";
	}

}