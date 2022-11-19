/**
 * Copyright (c) 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.internal.service.tests;

import java.util.Collection;

import org.eclipse.emf.validation.service.EventTypeService;
import org.eclipse.emf.validation.service.INotificationGenerator;

import junit.framework.TestCase;

/**
 * JUnit tests for the {@link EventTypeService} class.
 *
 * @author David Cummings (dcummin)
 */
public class EventTypeServiceTest extends TestCase {
	/**
	 * Constructor for ConstraintRegistryTest.
	 *
	 * @param name
	 */
	public EventTypeServiceTest(String name) {
		super(name);
	}

	public void test_getInstance_177647() {
		EventTypeService instance = EventTypeService.getInstance();
		assertSame(instance, EventTypeService.getInstance());
	}

	public void test_getNotificationGenerators_177647() {
		Collection<INotificationGenerator> generators = EventTypeService.getInstance().getNotificationGenerators();

		assertEquals(generators.size(), 1);
	}

	public void test_getNotificationGenerator_177647() {
		INotificationGenerator generator = EventTypeService.getInstance().getNotificationGenerator("Special Order"); //$NON-NLS-1$

		assertNotNull(generator);
	}
}
