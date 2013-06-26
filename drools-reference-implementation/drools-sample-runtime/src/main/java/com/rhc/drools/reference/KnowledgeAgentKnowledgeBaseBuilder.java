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
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;

/**
 * An implementation of the KnowledgeBaseBuilder using the KnowledgeAgent which can poll remote resources to dynamically
 * deploy a KnowledgeBase at runtime. There are several useful defaults provided by this implementation.
 * 
 * Note the creation of the KnowledgeBase is lazy.
 * 
 */
public class KnowledgeAgentKnowledgeBaseBuilder implements KnowledgeBaseBuilder {

	private KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent( "MyAgent" );

	/**
	 * Use the same syntax use in a change set: prefix with the protocol <code>http:</code> or <code>file:</code> or
	 * <code>classpath:</code>
	 */
	private String path;

	public KnowledgeAgentKnowledgeBaseBuilder( String path ) {
		setPath( path );
	}

	public KnowledgeAgentKnowledgeBaseBuilder() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rhc.drools.reference.KnowledgeBaseBuilder#getKnowledgeBase()
	 */
	@Override
	public KnowledgeBase getKnowledgeBase() {
		// kagent handles caching for us
		return this.kagent.getKnowledgeBase();
	}

	public Resource buildChangeSetResource( String path ) throws IllegalArgumentException {
		if ( path.startsWith( "http:" ) ) {
			// replace the prefix with nothing, thus leaving the file name
			return ResourceFactory.newUrlResource( path.replaceAll( "http:", "" ) );
		} else if ( path.startsWith( "file:" ) ) {
			// replace the prefix with nothing, thus leaving the file name
			return ResourceFactory.newFileResource( path.replaceAll( "file:", "" ) );
		} else if ( path.startsWith( "classpath:" ) ) {
			// replace the prefix with nothing, thus leaving the file name
			return ResourceFactory.newClassPathResource( path.replaceAll( "classpath:", "" ), getClass() );
		} else
			throw new IllegalArgumentException(
					"path is malformed: must be prefixed with http: classpath: or file: in order to indicate which resource to use" );
	}

	public String getPath() {
		return path;
	}

	public void setPath( String path ) {
		this.path = path;
		Resource changeSet = buildChangeSetResource( path );
		this.kagent.applyChangeSet( changeSet );
	}

}
