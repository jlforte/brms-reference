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

import org.drools.runtime.ExecutionResults;

/**
 * <p>
 * Drools Concern #4: Transforming a the <code>ExecutionResults</code> into a response the calling code can use.
 * </p>
 * 
 */
public interface ExecutionResultsTransformer {

	/**
	 * Defines transformation to a response the application can understand
	 * 
	 * @param results
	 *            from a Drools Batch Execution
	 */
	public <Response> Response transform( ExecutionResults results, Class<Response> response );

}