package com.rhc.discounts;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rhc.discounts.domain.Customer;
import com.rhc.discounts.domain.DiscountFixed;
import com.rhc.discounts.domain.DiscountPercentage;
import com.rhc.discounts.domain.IDiscount;
import com.rhc.discounts.domain.Product;
import com.rhc.drools.reference.StatelessDroolsComponent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DiscountTest {

	@Resource(name = "discountsDroolsComponent")
	private StatelessDroolsComponent drools;
	private Customer customer;

	@Before
	public void init() {
		this.customer = new Customer( "customer" );

		Product hat = new Product( "scarf", 10.0f );
		Product hat1 = new Product( "scarf", 10.0f );
		Product shirt = new Product( "shirt", 15.0f );

		IDiscount d = new DiscountFixed( "scarf", 2.0f, 3 );
		IDiscount per = new DiscountPercentage( "shirt", 0.10f, 1 );

		customer.addProduct( hat );
		customer.addProduct( hat1 );
		customer.addProduct( shirt );
		customer.addEligibleDiscount( d );
		customer.addEligibleDiscount( per );

	}

	@Test
	public void test() {

		drools.execute( customer, Customer.class, null );

		int discountsExpected = 3;
		int discountsOffered = customer.getDiscountsOffered();
		System.out.println( discountsOffered );

		Assert.assertEquals( discountsExpected, discountsOffered );

		float totalCostExpected = 29.5f;
		float totalCost = customer.getTotalCost();
		System.out.println( totalCost );

		Assert.assertEquals( totalCostExpected, totalCost );

		float amountSavedExpected = 5.5f;
		float amountSaved = customer.getAmountSaved();
		System.out.println( amountSaved );

		Assert.assertEquals( amountSavedExpected, amountSaved );

	}

}
