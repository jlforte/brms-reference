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
		KnowledgeBase originalKBase = ComponentManager.getKnowledgeBase( component.getName() );
		KnowledgeBase cacheKBase = ComponentManager.getKnowledgeBase( component.getName() );

		Assert.assertTrue( originalKBase == cacheKBase );
	}

	@Test
	public void shouldUsesNewKBaseAfterRebuild() {
		StatelessDroolsComponent component = new StatelessDroolsComponent();
		KnowledgeBaseBuilder kbaseBuilder = new ClasspathKnowledgeBaseBuilder();
		component.setKnowledgeBaseBuilder( kbaseBuilder );
		ComponentManager.rebuildAllKnowledgeBases();
		KnowledgeBase originalKBase = ComponentManager.getKnowledgeBase( component.getName() );
		ComponentManager.rebuildAllKnowledgeBases();
		KnowledgeBase newKBase = ComponentManager.getKnowledgeBase( component.getName() );

		Assert.assertFalse( originalKBase == newKBase );
	}

	@Test
	public void shouldChangeNameWhenSetNameIsCalledAndStillUsedCachedKBase() {
		StatelessDroolsComponent component = new StatelessDroolsComponent();
		KnowledgeBaseBuilder kbaseBuilder = new ClasspathKnowledgeBaseBuilder();
		component.setKnowledgeBaseBuilder( kbaseBuilder );
		ComponentManager.rebuildAllKnowledgeBases();
		KnowledgeBase originalKBase = ComponentManager.getKnowledgeBase( component.getName() );
		component.setName( "Change" );

		KnowledgeBase cacheKBase = ComponentManager.getKnowledgeBase( component.getName() );

		Assert.assertTrue( originalKBase == cacheKBase );
	}
}
