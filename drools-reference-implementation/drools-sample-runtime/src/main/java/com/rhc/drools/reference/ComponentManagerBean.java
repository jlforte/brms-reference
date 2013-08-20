package com.rhc.drools.reference;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;

public class ComponentManagerBean {

	private static final String DEFAULT_NAME = "component_";

	private Map<String, StatelessDroolsComponent> componentMap;
	private Map<String, KnowledgeBase> knowledgeBaseMap;
	private int namelessCount = 1;

	public ComponentManagerBean() {
		this.componentMap = new HashMap<String, StatelessDroolsComponent>();
		this.knowledgeBaseMap = new HashMap<String, KnowledgeBase>();
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
		knowledgeBaseMap.remove( name );
		return true;
	}

	public Collection<StatelessDroolsComponent> getAllManagedComponents() {
		return componentMap.values();
	}

	public void rebuildAllKnowledgeBases() {
		this.knowledgeBaseMap = new HashMap<String, KnowledgeBase>();
		for ( String name : componentMap.keySet() ) {
			StatelessDroolsComponent component = componentMap.get( name );
			knowledgeBaseMap.put( name, component.getKnowledgeBaseBuilder().buildKnowledgeBase() );
		}
	}

	public boolean rebuildKnowledgeBase( String name ) {
		StatelessDroolsComponent component = componentMap.get( name );
		if ( component == null ) {
			return false;
		}

		knowledgeBaseMap.put( name, component.getKnowledgeBaseBuilder().buildKnowledgeBase() );
		return true;
	}

	public KnowledgeBase getKnowledgeBase( String name ) {
		StatelessDroolsComponent component = componentMap.get( name );
		if ( component == null ) {
			return null;
		}

		KnowledgeBaseBuilder kBaseBuilder = component.getKnowledgeBaseBuilder();
		if ( kBaseBuilder.cacheKnowledgeBase() ) {
			if ( knowledgeBaseMap.containsKey( name ) ) {
				return knowledgeBaseMap.get( name );
			}
		}

		if ( rebuildKnowledgeBase( name ) ) {
			return knowledgeBaseMap.get( name );
		}

		return null;
	}

	public boolean changeName( String oldName, String newName ) {
		KnowledgeBase kbase = knowledgeBaseMap.get( oldName );
		StatelessDroolsComponent component = componentMap.get( oldName );

		if ( component == null ) {
			return false;
		}

		removeComponent( oldName );

		boolean added = addComponent( component, newName );

		if ( added && kbase != null ) {
			knowledgeBaseMap.put( newName, kbase );
		}

		return added;

	}

}
