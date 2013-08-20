package com.rhc.drools.reference;

import java.util.Collection;

import org.drools.KnowledgeBase;

public class ComponentManager {

	private static ComponentManagerBean componentManager = new ComponentManagerBean();

	public static boolean addComponent( StatelessDroolsComponent component ) {
		return componentManager.addComponent( component );
	}

	public static boolean removeComponent( String name ) {
		return componentManager.removeComponent( name );
	}

	public static Collection<StatelessDroolsComponent> getAllManagedComponents() {
		return componentManager.getAllManagedComponents();
	}

	public static void rebuildAllKnowledgeBases() {
		componentManager.rebuildAllKnowledgeBases();
	}

	public static boolean rebuildKnowledgeBase( String name ) {
		return componentManager.rebuildKnowledgeBase( name );
	}

	public static KnowledgeBase getKnowledgeBase( String name ) {
		return componentManager.getKnowledgeBase( name );
	}

	public static boolean changeName( String oldName, String newName ) {
		return componentManager.changeName( oldName, newName );
	}

	public static void reset() {
		componentManager = new ComponentManagerBean();
	}

}
