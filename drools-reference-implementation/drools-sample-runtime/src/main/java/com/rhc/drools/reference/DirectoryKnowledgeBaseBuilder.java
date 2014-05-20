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

import java.io.File;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This
 * 
 */
public class DirectoryKnowledgeBaseBuilder implements KnowledgeBaseBuilder {

	private static Logger logger = LoggerFactory.getLogger( DirectoryKnowledgeBaseBuilder.class );

	private String directoryPath;
	private KnowledgeBase kBase;

	/**
	 * @param directoryPath
	 */
	public DirectoryKnowledgeBaseBuilder( String directoryPath ) {
		this.directoryPath = directoryPath;
	}

	public DirectoryKnowledgeBaseBuilder() {

	}

	@Override
	public KnowledgeBase getKnowledgeBase() {
		if ( this.kBase == null ) {
			buildKnowledgeBase();
		}
		return this.kBase;
	}

	@Override
	public void buildKnowledgeBase() {

		long startTime = System.currentTimeMillis();
		logger.debug( "Building Knowledge Base..." );

		File directory = new File( directoryPath );
		KnowledgeBuilder kbuilder = null;

		if ( isDirectoryValid( directory ) ) {
			kbuilder = getKnowledgeBuilderWithDirectoryResources( directory );
		} else {
			logger.error( String.format(
					"The directory %s is invalid. Please check the directory exists and is readable by Java",
					directoryPath ) );
		}

		if ( kbuilder == null || kbuilder.hasErrors() ) {
			logger.error( "Errors occurred building the Knowledge Base. The component will continue to use it's existing Knowledge Base" );
			if ( kbuilder != null ) {
				logger.error( kbuilder.getErrors().toString() );
			}
		} else {
			logger.info( "Knowledge Base build successful! The component is now using the new Knowledge Base." );
			this.kBase = kbuilder.newKnowledgeBase();
		}

		logger.debug( "Building Knowledge Base took " + ( System.currentTimeMillis() - startTime ) + " ms" );

	}

	private KnowledgeBuilder getKnowledgeBuilderWithDirectoryResources( File directory ) {

		logger.info( String.format( "Using directory: %s", directoryPath ) );

		KnowledgeBuilder builder = null;
		File[] fileList = directory.listFiles();

		int filesUsed = 0;
		if ( fileList.length != 0 ) {

			builder = KnowledgeBuilderFactory.newKnowledgeBuilder();

			for ( int i = 0; i < directory.listFiles().length; i++ ) {
				File childFile = directory.listFiles()[i];

				if ( childFile.isDirectory() ) {

					logger.info( String.format( "Child file %s is a directory, so it is being skipped.",
							childFile.getName() ) );
					continue;

				}

				ResourceType childFileResourceType = ResourceType.determineResourceType( childFile.getName() );

				if ( childFileResourceType == null ) {

					logger.warn( String
							.format(
									"Child file %s does not have an file extension supported by Drools ResourceType defaults. See Drools source for details: https://github.com/droolsjbpm/droolsjbpm-knowledge/blob/5.4.x/knowledge-api/src/main/java/org/drools/builder/ResourceType.java",
									childFile.getName() ) );
					continue;

				} else {

					filesUsed++;
					builder.add( ResourceFactory.newFileResource( childFile ), childFileResourceType );

					logger.debug( String.format( "Adding %s to the kBuilder", childFile.getName() ) );
				}
			}

		}

		if ( filesUsed == 0 ) {
			logger.error( "Directory does not contain any usable files" );
			builder = null;
		}

		return builder;
	}

	private boolean isDirectoryValid( File directory ) {
		return ( directory != null && directory.canRead() && directory.isDirectory() );
	}

	/**
	 * @return the directoryPath
	 */
	public String getDirectoryPath() {
		return directoryPath;
	}

	/**
	 * @param directoryPath
	 *            the directoryPath to set
	 */
	public void setDirectoryPath( String directoryPath ) {
		this.directoryPath = directoryPath;
	}
}
