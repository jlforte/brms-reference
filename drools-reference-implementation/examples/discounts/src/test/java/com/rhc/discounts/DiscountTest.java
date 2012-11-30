package com.rhc.discounts;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rhc.discounts.domain.Customer;
import com.rhc.discounts.domain.Product;
import com.rhc.drools.reference.StatelessDroolsComponent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DiscountTest {
	
	@Resource(name="discountsDroolsComponent")
	private StatelessDroolsComponent<Customer,Customer> drools;
	private Customer customer;
	
	
	@Before
	public void init(){
		this.customer = new Customer( "customer" );
		
		Product hat = new Product( "scarf", 10.0f );
		
		this.customer.purchase( hat );
		
	}
	
	@Test
	public void test(){
		
		drools.execute( customer );
		
		int discountsExpected = 1;
		int discountsOffered = customer.discountsOffered();
		
		Assert.assertEquals( discountsExpected, discountsOffered );
	}
	

}
