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
package com.rhc.drools.reference;

import org.drools.KnowledgeBase;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 *
 */

public class KnowledgeAgentKnowledgeBaseBuilderTest {

	@Test
	public void shouldRetrieveChangeSetAndBuildAKnowledgeBase() {
		KnowledgeAgentKnowledgeBaseBuilder kbuilder = new KnowledgeAgentKnowledgeBaseBuilder(
				"classpath:change-set.xml" );

		KnowledgeBase kBase = kbuilder.getKnowledgeBase();

		Assert.assertNotNull( kBase );
		Assert.assertEquals( 1, kBase.getKnowledgePackages().size() );
	}
}
