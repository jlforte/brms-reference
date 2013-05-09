package com.rhc.drools.reference;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Should be put on the methods in a Response class that is used by the Component. Will only work if it is put on a
 * method that takes in a single parameter that is a Set.
 * 
 * 
 */

// TODO make this a field type
// TODO change name to DroolsQueryInfo
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryInfo {

	/**
	 * The name of the Query
	 * 
	 * @return
	 */
	String queryName();

	/**
	 * The specific value that the Object being queried is bound to.
	 * 
	 * @return
	 */
	String binding();

}
