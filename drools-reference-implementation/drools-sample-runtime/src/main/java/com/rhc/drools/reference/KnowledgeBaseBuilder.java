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
