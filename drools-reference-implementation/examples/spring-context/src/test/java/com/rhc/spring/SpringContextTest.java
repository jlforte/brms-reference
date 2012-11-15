package com.rhc.spring;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * This will load a spring context definition defined on the classpath at com.rhc.spring.SpringContextTest-context.xml
 */
@ContextConfiguration
public class SpringContextTest extends AbstractJUnit4SpringContextTests {

	@Test
	public void test() {
		Assert.fail( "Hello Sloan, fix me" );
	}
}
