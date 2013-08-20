package com.rhc.drools.reference;

import java.util.Collection;

import org.drools.KnowledgeBase;

public class ComponentManager {

	private static ComponentManagerBean compnentManager = new ComponentManagerBean();

	public static boolean addComponent( StatelessDroolsComponent component ) {
		return compnentManager.addComponent( component );
	}

	public static boolean removeComponent( String name ) {
		return compnentManager.removeComponent( name );
	}

	public static Collection<StatelessDroolsComponent> getAllManagedComponents() {
		return compnentManager.getAllManagedComponents();
	}

	public static void rebuildAllKnowledgeBases() {
		compnentManager.rebuildAllKnowledgeBases();
	}

	public static boolean rebuildKnowledgeBase( String name ) {
		return compnentManager.rebuildKnowledgeBase( name );
	}

	public static KnowledgeBase getKnowledgeBase( String name ) {
		return compnentManager.getKnowledgeBase( name );
	}

	public static boolean changeName( String oldName, String newName ) {
		return compnentManager.changeName( oldName, newName );
	}

	public static void refresh() {
		compnentManager = new ComponentManagerBean();
	}

}
