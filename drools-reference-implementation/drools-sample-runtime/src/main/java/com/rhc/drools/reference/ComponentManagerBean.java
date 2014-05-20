package com.rhc.drools.reference;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ComponentManagerBean {

	private static final String DEFAULT_NAME = "component_";

	private Map<String, StatelessDroolsComponent> componentMap;
	private int namelessCount = 1;

	public ComponentManagerBean() {
		this.componentMap = new HashMap<String, StatelessDroolsComponent>();
	}

	/**
	 * If the component has a name that already exists, will return false.
	 * 
	 * @param component
	 */
	public boolean addComponent( StatelessDroolsComponent component ) {
		String name = component.getName();
		return addComponent( component, name );
	}

	private boolean addComponent( StatelessDroolsComponent component, String name ) {
		if ( name == null ) {
			name = DEFAULT_NAME + namelessCount;
			namelessCount++;
			component.updateName( name );
		}
		if ( componentMap.containsKey( name ) ) {
			return false;
		}
		componentMap.put( name, component );
		return true;
	}

	public boolean removeComponent( String name ) {
		if ( !componentMap.containsKey( name ) ) {
			return false;
		}

		componentMap.remove( name );
		return true;
	}

	public Collection<StatelessDroolsComponent> getAllManagedComponents() {
		return componentMap.values();
	}

	public void rebuildAllKnowledgeBases() {
		for ( String name : componentMap.keySet() ) {
			StatelessDroolsComponent component = componentMap.get( name );
			component.getKnowledgeBaseBuilder().buildKnowledgeBase();
		}
	}

	public StatelessDroolsComponent getComponent( String name ) {
		return componentMap.get( name );
	}

	public boolean rebuildKnowledgeBase( String name ) {
		StatelessDroolsComponent component = componentMap.get( name );
		if ( component == null ) {
			return false;
		}

		component.getKnowledgeBaseBuilder().buildKnowledgeBase();
		return true;
	}

	public boolean changeName( String oldName, String newName ) {
		StatelessDroolsComponent component = componentMap.get( oldName );

		if ( component == null ) {
			return false;
		}

		removeComponent( oldName );

		return addComponent( component, newName );

	}

}
