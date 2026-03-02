/**
 * Copyright (c) 2003, 2026 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.internal.model.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

import org.eclipse.emf.validation.model.ConstraintSeverity;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the {@link ConstraintSeverity} class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintSeverityTest {

	@Test
	public void getInstance() {
		assertSame(ConstraintSeverity.NULL, ConstraintSeverity.getInstance(""));
		assertSame(ConstraintSeverity.INFO, ConstraintSeverity.getInstance(ConstraintSeverity.INFO.getName()));
		assertSame(ConstraintSeverity.WARNING, ConstraintSeverity.getInstance(ConstraintSeverity.WARNING.getName()));
		assertSame(ConstraintSeverity.ERROR, ConstraintSeverity.getInstance(ConstraintSeverity.ERROR.getName()));
	}

	@Test
	public void getAllInstances() {
		List<ConstraintSeverity> instances = ConstraintSeverity.getAllInstances();

		assertTrue(instances.contains(ConstraintSeverity.NULL));
		assertTrue(instances.contains(ConstraintSeverity.INFO));
		assertTrue(instances.contains(ConstraintSeverity.WARNING));
		assertTrue(instances.contains(ConstraintSeverity.ERROR));
	}

	@Test
	public void getName() {
		assertEquals("none", ConstraintSeverity.NULL.getName());
		assertEquals("INFO", ConstraintSeverity.INFO.getName());
		assertEquals("WARNING", ConstraintSeverity.WARNING.getName());
		assertEquals("ERROR", ConstraintSeverity.ERROR.getName());
	}

	@Test
	public void isNull() {
		assertTrue(ConstraintSeverity.NULL.isNull());
		assertFalse(ConstraintSeverity.INFO.isNull());
		assertFalse(ConstraintSeverity.WARNING.isNull());
		assertFalse(ConstraintSeverity.ERROR.isNull());
	}

	@Test
	public void readResolve() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		try (ObjectOutput output = new ObjectOutputStream(stream)) {
			for (ConstraintSeverity next : ConstraintSeverity.getAllInstances()) {
				output.writeObject(next);
			}
			output.flush();
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		}

		try (ObjectInput input =  new ObjectInputStream(new ByteArrayInputStream(stream.toByteArray()))) {
			for (ConstraintSeverity next : ConstraintSeverity.getAllInstances()) {
				assertSame(next, input.readObject());
			}
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		}
	}
}
