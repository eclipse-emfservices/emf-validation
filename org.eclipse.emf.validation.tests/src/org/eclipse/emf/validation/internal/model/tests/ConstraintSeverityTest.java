/**
 * <copyright>
 *
 * Copyright (c) 2003, 2007 IBM Corporation and others.
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

import org.eclipse.emf.validation.model.ConstraintSeverity;

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
 * JUnit tests for the {@link ConstraintSeverity} class.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintSeverityTest extends TestCase {

	public void test_getInstance() {
		assertSame(
				ConstraintSeverity.NULL,
				ConstraintSeverity.getInstance("")); //$NON-NLS-1$
		assertSame(
				ConstraintSeverity.INFO,
				ConstraintSeverity.getInstance(ConstraintSeverity.INFO.getName()));
		assertSame(
				ConstraintSeverity.WARNING,
				ConstraintSeverity.getInstance(ConstraintSeverity.WARNING.getName()));
		assertSame(
				ConstraintSeverity.ERROR,
				ConstraintSeverity.getInstance(ConstraintSeverity.ERROR.getName()));
	}

	public void test_getAllInstances() {
		List instances = ConstraintSeverity.getAllInstances();
		
		assertTrue(instances.contains(ConstraintSeverity.NULL));
		assertTrue(instances.contains(ConstraintSeverity.INFO));
		assertTrue(instances.contains(ConstraintSeverity.WARNING));
		assertTrue(instances.contains(ConstraintSeverity.ERROR));
	}

	public void test_getName() {
		assertEquals("none", ConstraintSeverity.NULL.getName()); //$NON-NLS-1$
		assertEquals("INFO", ConstraintSeverity.INFO.getName()); //$NON-NLS-1$
		assertEquals("WARNING", ConstraintSeverity.WARNING.getName()); //$NON-NLS-1$
		assertEquals("ERROR", ConstraintSeverity.ERROR.getName()); //$NON-NLS-1$
	}

	public void test_isNull() {
		assertTrue(ConstraintSeverity.NULL.isNull());
		assertFalse(ConstraintSeverity.INFO.isNull());
		assertFalse(ConstraintSeverity.WARNING.isNull());
		assertFalse(ConstraintSeverity.ERROR.isNull());
	}

	public void test_readResolve() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		ObjectOutput output = null;
		ObjectInput input = null;
		
		try {
			output = new ObjectOutputStream(stream);
			
			for (Iterator i = ConstraintSeverity.getAllInstances().iterator(); i.hasNext();) {
				output.writeObject(i.next());
			}
			
			output.flush();

			input =	new ObjectInputStream(
					new ByteArrayInputStream(stream.toByteArray()));
			
			for (Iterator i = ConstraintSeverity.getAllInstances().iterator(); i.hasNext();) {
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
