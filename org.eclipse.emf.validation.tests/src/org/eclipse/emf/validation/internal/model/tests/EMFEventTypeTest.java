/**
 * <copyright>
 *
 * Copyright (c) 2003-2005 IBM Corporation and others.
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
 * $Id$
 */

package org.eclipse.emf.validation.internal.model.tests;

import org.eclipse.emf.validation.EMFEventType;

import org.eclipse.emf.common.notify.Notification;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

/**
 * JUnit tests for the {@link EMFEventType} class.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class EMFEventTypeTest extends TestCase {

	public void test_getInstance() {
		assertSame(
				EMFEventType.NULL,
				EMFEventType.getInstance("")); //$NON-NLS-1$
		assertSame(
				EMFEventType.NULL,
				EMFEventType.getInstance(-1));

		assertSame(
				EMFEventType.ADD,
				EMFEventType.getInstance("Add")); //$NON-NLS-1$
		assertSame(
				EMFEventType.ADD,
				EMFEventType.getInstance(Notification.ADD));

		assertSame(
				EMFEventType.ADD_MANY,
				EMFEventType.getInstance("Add Many")); //$NON-NLS-1$
		assertSame(
				EMFEventType.ADD_MANY,
				EMFEventType.getInstance(Notification.ADD_MANY));

		assertSame(
				EMFEventType.MOVE,
				EMFEventType.getInstance("Move")); //$NON-NLS-1$
		assertSame(
				EMFEventType.MOVE,
				EMFEventType.getInstance(Notification.MOVE));

		assertSame(
				EMFEventType.REMOVE,
				EMFEventType.getInstance("Remove")); //$NON-NLS-1$
		assertSame(
				EMFEventType.REMOVE,
				EMFEventType.getInstance(Notification.REMOVE));

		assertSame(
				EMFEventType.REMOVE_MANY,
				EMFEventType.getInstance("Remove Many")); //$NON-NLS-1$
		assertSame(
				EMFEventType.REMOVE_MANY,
				EMFEventType.getInstance(Notification.REMOVE_MANY));

		assertSame(
				EMFEventType.REMOVING_ADAPTER,
				EMFEventType.getInstance("Removing Adapter")); //$NON-NLS-1$
		assertSame(
				EMFEventType.REMOVING_ADAPTER,
				EMFEventType.getInstance(Notification.REMOVING_ADAPTER));

		assertSame(
				EMFEventType.RESOLVE,
				EMFEventType.getInstance("Resolve")); //$NON-NLS-1$
		assertSame(
				EMFEventType.RESOLVE,
				EMFEventType.getInstance(Notification.RESOLVE));

		assertSame(
				EMFEventType.SET,
				EMFEventType.getInstance("Set")); //$NON-NLS-1$
		assertSame(
				EMFEventType.SET,
				EMFEventType.getInstance(Notification.SET));

		assertSame(
				EMFEventType.UNSET,
				EMFEventType.getInstance("Unset")); //$NON-NLS-1$
		assertSame(
				EMFEventType.UNSET,
				EMFEventType.getInstance(Notification.UNSET));
	}

	public void test_getAllInstances() {
		List instances = EMFEventType.getAllInstances();
		
		assertTrue(instances.contains(EMFEventType.NULL));
		assertTrue(instances.contains(EMFEventType.ADD));
		assertTrue(instances.contains(EMFEventType.ADD_MANY));
		assertTrue(instances.contains(EMFEventType.MOVE));
		assertTrue(instances.contains(EMFEventType.REMOVE));
		assertTrue(instances.contains(EMFEventType.REMOVE_MANY));
		assertTrue(instances.contains(EMFEventType.REMOVING_ADAPTER));
		assertTrue(instances.contains(EMFEventType.RESOLVE));
		assertTrue(instances.contains(EMFEventType.SET));
		assertTrue(instances.contains(EMFEventType.UNSET));
	}

	public void test_getName() {
		assertEquals("none", EMFEventType.NULL.getName()); //$NON-NLS-1$
		assertEquals("Add", EMFEventType.ADD.getName()); //$NON-NLS-1$
		assertEquals("Add Many", EMFEventType.ADD_MANY.getName()); //$NON-NLS-1$
		assertEquals("Move", EMFEventType.MOVE.getName()); //$NON-NLS-1$
		assertEquals("Remove", EMFEventType.REMOVE.getName()); //$NON-NLS-1$
		assertEquals("Remove Many", EMFEventType.REMOVE_MANY.getName()); //$NON-NLS-1$
		assertEquals("Removing Adapter", EMFEventType.REMOVING_ADAPTER.getName()); //$NON-NLS-1$
		assertEquals("Resolve", EMFEventType.RESOLVE.getName()); //$NON-NLS-1$
		assertEquals("Set", EMFEventType.SET.getName()); //$NON-NLS-1$
		assertEquals("Unset", EMFEventType.UNSET.getName()); //$NON-NLS-1$
	}

	public void test_isNull() {
		assertTrue(EMFEventType.NULL.isNull());
		assertFalse(EMFEventType.ADD.isNull());
		assertFalse(EMFEventType.ADD_MANY.isNull());
		assertFalse(EMFEventType.MOVE.isNull());
		assertFalse(EMFEventType.REMOVE.isNull());
		assertFalse(EMFEventType.REMOVE_MANY.isNull());
		assertFalse(EMFEventType.REMOVING_ADAPTER.isNull());
		assertFalse(EMFEventType.RESOLVE.isNull());
		assertFalse(EMFEventType.SET.isNull());
		assertFalse(EMFEventType.UNSET.isNull());
	}

	public void test_readResolve() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		ObjectOutput output = null;
		ObjectInput input = null;
		
		try {
			output = new ObjectOutputStream(stream);
			
			for (Iterator i = EMFEventType.getAllInstances().iterator(); i.hasNext();) {
				output.writeObject(i.next());
			}
			
			output.flush();

			input =	new ObjectInputStream(
					new ByteArrayInputStream(stream.toByteArray()));
			
			for (Iterator i = EMFEventType.getAllInstances().iterator(); i.hasNext();) {
				assertSame(i.next(), input.readObject());
			}
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		} finally {
			try {
				output.close();
				input.close();
			} catch (Exception e) {
				fail(e.getLocalizedMessage());
			}
		}
	}
}
