package com.rhc.drools.reference;

import org.drools.KnowledgeBase;

/**
 * Drools Concern #1: Accumulation of Knowledge Resources (i.e. rules, queries, workflows, templates, decision
 * tables), compilation of those resources into Knowledge Packages, and creation of a Knowledge Base from the
 * Knowledge Packages. This tends to be the element that varies most application to application, so we've left
 * problem to be solved by a concrete class. All this solution needs is a KnowledgeBase to returned - it doesn't
 * care how you create it. Here are just a few ways that you could tackle Knowledge Asset Management:
 * 
 * 1) One time compilation from the class path using the KnowledgeBuilder. This is the solution we present here. <br>
 * 2) Compilation of remote resources using a KnowledgeAgent. This is useful when rules live in Guvnor or a remote
 * file store. <br>
 * 3) Application wide caching strategy that builds and stores KnowledgeBases for numerous business processes. <br>
 * 4) Pre-compiled KnowledgePackages or KnowledgeBase. This can be done with the ant task or Guvnor
 */

public interface KnowledgeBaseBuilder {

	public KnowledgeBase buildKnowledgeBase();
}
