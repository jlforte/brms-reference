package com.rhc.drools.reference;

import org.drools.KnowledgeBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComponentManagerTest {

	@Before
	public void shouldClearComponentMangerState() {
		ComponentManager.reset();
	}

	@Test
	public void shouldCacheKBase() {
		StatelessDroolsComponent component = new StatelessDroolsComponent();
		KnowledgeBaseBuilder kbaseBuilder = new ClasspathKnowledgeBaseBuilder();
		component.setKnowledgeBaseBuilder( kbaseBuilder );
		ComponentManager.rebuildAllKnowledgeBases();
		KnowledgeBase originalKBase = kbaseBuilder.getKnowledgeBase();
		KnowledgeBase cacheKBase = kbaseBuilder.getKnowledgeBase();

		Assert.assertTrue( originalKBase == cacheKBase );
	}

	@Test
	public void shouldUsesNewKBaseAfterRebuild() {
		StatelessDroolsComponent component = new StatelessDroolsComponent();
		KnowledgeBaseBuilder kbaseBuilder = new ClasspathKnowledgeBaseBuilder();
		component.setKnowledgeBaseBuilder( kbaseBuilder );
		ComponentManager.rebuildAllKnowledgeBases();
		KnowledgeBase originalKBase = kbaseBuilder.getKnowledgeBase();
		ComponentManager.rebuildAllKnowledgeBases();
		KnowledgeBase newKBase = kbaseBuilder.getKnowledgeBase();

		Assert.assertFalse( originalKBase == newKBase );
	}

	@Test
	public void shouldChangeNameWhenSetNameIsCalledAndStillUsedCachedKBase() {
		StatelessDroolsComponent component = new StatelessDroolsComponent();
		KnowledgeBaseBuilder kbaseBuilder = new ClasspathKnowledgeBaseBuilder();
		component.setKnowledgeBaseBuilder( kbaseBuilder );
		ComponentManager.rebuildAllKnowledgeBases();
		KnowledgeBase originalKBase = kbaseBuilder.getKnowledgeBase();
		component.setName( "Change" );
		StatelessDroolsComponent sameComponent = ComponentManager.getComponent( "Change" );
		KnowledgeBase cacheKBase = sameComponent.getKnowledgeBaseBuilder().getKnowledgeBase();

		Assert.assertTrue( originalKBase == cacheKBase );
	}
}
