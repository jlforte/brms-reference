package com.rhc.drools.reference;

import java.util.Collection;
import java.util.HashSet;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RuleFlowCommandListBuilderTest {

	@Resource(name = "droolsComponent")
	private StatelessDroolsComponent<Collection<Object>, Collection<Object>> drools;

	@Test
	public void test() {
		Assert.assertNotNull( drools );

		drools.execute( new HashSet<Object>() );

		Assert.assertEquals( 2, drools.getPreviouslyFiredActivations().size() );
	}
}
