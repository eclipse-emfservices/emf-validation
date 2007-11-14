/**
 * <copyright>
 *
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: EventTypeServiceTest.java,v 1.2 2007/11/14 18:03:42 cdamus Exp $
 */
 

package org.eclipse.emf.validation.internal.service.tests;

import java.util.Collection;

import junit.framework.TestCase;

import org.eclipse.emf.validation.service.EventTypeService;
import org.eclipse.emf.validation.service.INotificationGenerator;


/**
 * JUnit tests for the {@link EventTypeService} class.
 *
 * @author David Cummings (dcummin)
 */
public class EventTypeServiceTest extends TestCase {
	/**
 	 * Constructor for ConstraintRegistryTest.
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
		Collection<INotificationGenerator> generators =
			EventTypeService.getInstance().getNotificationGenerators();
		
		assertEquals(generators.size(), 1);
	}
	
	public void test_getNotificationGenerator_177647() {
		INotificationGenerator generator = EventTypeService.getInstance().getNotificationGenerator("Special Order"); //$NON-NLS-1$
		
		assertNotNull(generator);
	}
}
