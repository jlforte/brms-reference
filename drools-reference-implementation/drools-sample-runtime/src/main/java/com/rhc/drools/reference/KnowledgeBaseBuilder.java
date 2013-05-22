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

/**
 * <p>
 * Drools Concern #1: Accumulation of Knowledge Resources (i.e. rules, queries, workflows, templates, decision tables),
 * compilation of those resources into Knowledge Packages, and the creation of a Knowledge Base from the Knowledge
 * Packages. This tends to be the element that varies most application to application, so we've left problem to be
 * solved by a concrete class. All this solution needs is a KnowledgeBase to returned - it doesn't care how you create
 * it. Here are a few ideas:
 * </p>
 * 
 * 1) Runtime compilation from the class path using the KnowledgeBuilder (e.g MortgageApplicationKBaseBuilder TODO link
 * here) <br>
 * 2) Compilation of remote resources using a KnowledgeAgent. This is useful when Knowledge Packages live in Guvnor or a
 * remote file store. <br>
 * 3) Application wide caching strategy that builds and stores KnowledgeBases for numerous business processes. <br>
 * 4) Pre-compiled or serialized KnowledgePackages or KnowledgeBase living on the classpath. You can use the Drools ant
 * task, Guvnor or a custom solution here.
 */

public interface KnowledgeBaseBuilder {

	public KnowledgeBase getKnowledgeBase();
}
