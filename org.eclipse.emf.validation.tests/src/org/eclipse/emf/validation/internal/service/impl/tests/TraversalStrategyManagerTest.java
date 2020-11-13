/**
 * Copyright (c) 2014 CEA and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA - Initial API and implementation
 */
package org.eclipse.emf.validation.internal.service.impl.tests;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.ModelValidationService;

import junit.framework.TestCase;

/**
 * Indirect tests for the {@code TraversalStrategyManager} class.
 */
public class TraversalStrategyManagerTest extends TestCase {
	
	public TraversalStrategyManagerTest(String name) {
		super(name);
	}

	/**
	 * Test that the TraversalStrategyManager does not leak EPackages loaded locally in a ResourceSet.
	 * 
	 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=433050
	 */
	public void testTraversalStrategyManagerDoesNotLeakDynamicEPackages() {
		ResourceSet rset = new ResourceSetImpl();
		
		// Track a phantom reference to the package that should not leak
		ReferenceQueue<EPackage> queue = new ReferenceQueue<EPackage>();
		EObject thing = loadThing(rset);
		final Reference<?> ref = new PhantomReference<EPackage>(thing.eClass().getEPackage(), queue);
		
		// Validate (which triggers the TraversalStrategyManager)
		ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH).validate(thing);
		
		// Unload everything
		for (Resource next : rset.getResources()) {
			next.unload();
		}
		rset.getResources().clear();
		thing = null;
		rset = null;
		
		// Verify that the package has not leaked
		Reference<?> cleared = null;
		
		for (int i = 0; (cleared == null) && (i < 5); i++) {
			// Try to suggest garbage collection
			System.gc();
			
			try {
				cleared = queue.remove(1000);
			} catch (Exception e) {
				// try again
			}
		}
		
		assertSame(ref, cleared);
	}
	
	//
	// Test framework
	//
	
	EObject loadThing(ResourceSet rset) {
		Resource res = rset.getResource(URI.createPlatformPluginURI("org.eclipse.emf.validation.tests/model/thing.xmi", true), true);
		return res.getContents().get(0);
	}
}
