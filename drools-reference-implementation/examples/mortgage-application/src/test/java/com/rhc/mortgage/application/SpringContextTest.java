/* 
 * Written by Red Hat Consulting.	
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rhc.mortgage.application;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.rhc.drools.reference.ClasspathKnowledgeBaseBuilder;
import com.rhc.drools.reference.ComponentManager;

@ContextConfiguration
public class SpringContextTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "mortgageKBaseBuilder")
	private ClasspathKnowledgeBaseBuilder foo;

	@Resource(name = "mortgageKBaseBuilder")
	private ClasspathKnowledgeBaseBuilder bar;

	/**
	 * This is a sanity check on Spring's singleton behavior
	 */
	@Test
	public void shouldMakeNullBarsResources() {

		if ( foo == null && bar == null ) {
			Assert.fail();
		}

		// Show both have a valid kbase
		Assert.assertEquals( 1, foo.getKnowledgeBase().getKnowledgePackages().size() );
		Assert.assertEquals( 1, bar.getKnowledgeBase().getKnowledgePackages().size() );

		// make foo null directly, and bar null indirectly - its the same reference
		foo.setKnowledgeResources( null );
		ComponentManager.rebuildAllKnowledgeBases();

		// show both are empty kbases
		Assert.assertEquals( 0, foo.getKnowledgeBase().getKnowledgePackages().size() );
		Assert.assertEquals( 0, bar.getKnowledgeBase().getKnowledgePackages().size() );

	}

}
