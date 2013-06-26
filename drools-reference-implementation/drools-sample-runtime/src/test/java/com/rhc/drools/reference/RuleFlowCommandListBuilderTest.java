package com.rhc.drools.reference;

import java.util.HashSet;
import java.util.Set;

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
	private StatelessDroolsComponent drools;

	@Test
	public void test() {
		Assert.assertNotNull( drools );

		drools.execute( new DroolsRequest() {

			@Override
			public Set<Object> getAllFacts() {
				return new HashSet<Object>();
			}

			@Override
			public String getProcessId() {
				return "com.rhc.drools.test";
			}
		}, null );

		Assert.assertEquals( 2, drools.getPreviouslyFiredActivations().size() );
	}
}
