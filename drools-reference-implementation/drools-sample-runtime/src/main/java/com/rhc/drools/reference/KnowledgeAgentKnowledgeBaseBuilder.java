package com.rhc.drools.reference;

/* 
 * Written by Red Hat Consulting. 
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;

import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.io.Resource;
import org.drools.io.ResourceChangeNotifier;
import org.drools.io.ResourceChangeScanner;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;

/**
 * An implementation of the KnowledgeBaseBuilder using the KnowledgeAgent which can poll remote resources to dynamically
 * deploy a KnowledgeBase at runtime. There are several useful defaults provided by this implementation.
 * 
 * Note the creation of the KnowledgeBase is lazy.
 * 
 */
public class KnowledgeAgentKnowledgeBaseBuilder implements KnowledgeBaseBuilder {

	// Should these be here or in the constructor?
	private KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent( "MyAgent" );
	private ResourceChangeNotifier notifier = ResourceFactory.getResourceChangeNotifierService();
	private ResourceChangeScanner scanner = ResourceFactory.getResourceChangeScannerService();

	/**
	 * Use the same syntax use in a change set: prefix with the protocol <code>http:</code> or <code>file:</code> or
	 * <code>classpath:</code>
	 */
	private String path;

	/**
	 * In seconds
	 */
	private String scannerInterval;

	private boolean isInit = false;

	public KnowledgeAgentKnowledgeBaseBuilder( String path ) {
		setPath( path );
	}

	public KnowledgeAgentKnowledgeBaseBuilder() {
	}

	@Override
	public KnowledgeBase getKnowledgeBase() {
		if ( !isInit ) {
			init();
		}

		// kagent handles caching for us
		return this.kagent.getKnowledgeBase();
	}

	private void init() {
		if ( isInit ) {
			this.scanner.stop();
			this.notifier.stop();
		}

		Resource changeSet = buildChangeSetResource( path );
		this.kagent.applyChangeSet( changeSet );

		ResourceChangeScannerConfiguration sconf = scanner.newResourceChangeScannerConfiguration();
		sconf.setProperty( "drools.resource.scanner.interval", scannerInterval );
		scanner.configure( sconf );

		scanner.start();
		notifier.start();

		isInit = true;
	}

	public Resource buildChangeSetResource( String path ) {
		Resource resource;

		if ( path.startsWith( "http:" ) ) {
			// replace the prefix with nothing, thus leaving the file name
			resource = ResourceFactory.newUrlResource( path.replaceAll( "http:", "" ) );
		} else if ( path.startsWith( "file:" ) ) {
			// replace the prefix with nothing, thus leaving the file name
			resource = ResourceFactory.newFileResource( path.replaceAll( "file:", "" ) );
		} else if ( path.startsWith( "classpath:" ) ) {
			// replace the prefix with nothing, thus leaving the file name
			resource = ResourceFactory.newClassPathResource( path.replaceAll( "classpath:", "" ), getClass() );
		} else
			throw new IllegalArgumentException(
					"path is malformed: must be prefixed with http: classpath: or file: in order to indicate which resource to use" );

		// This is a check to see if the resource exist - expect barf if it
		// doesnt
		try {
			resource.getInputStream();
		} catch ( IOException e ) {
			System.err.println( "Java can't find your changeset" );
			e.printStackTrace();
		}

		return resource;
	}

	public String getPath() {
		return path;
	}

	public void setPath( String path ) {
		this.path = path;
	}

	public void setScannerInternal( String interval ) {
		this.scannerInterval = interval;
	}

	public String getScannerInterval() {
		return this.scannerInterval;
	}

}
