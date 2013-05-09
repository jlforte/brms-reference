package com.rhc.drools.reference;

import java.util.Collection;

/**
 * Simple class to be used in this test alone
 * 
 */
public class MockResponse {

	@DroolsQueryInfo(queryName = "Get Strings", binding = "$string")
	private Collection<String> strings;

	@DroolsQueryInfo(queryName = "Get Integers", binding = "$integer")
	private Collection<Integer> integers;

	public MockResponse() {
	}

	public Collection<String> getStrings() {
		return strings;
	}

	public void setStrings( Collection<String> strings ) {
		this.strings = strings;
	}

	public Collection<Integer> getIntegers() {
		return integers;
	}

	public void setIntegers( Collection<Integer> integers ) {
		this.integers = integers;
	}

	@Override
	public String toString() {
		return "MockRespose [strings=" + strings + ", integers=" + integers + "]";
	}

}