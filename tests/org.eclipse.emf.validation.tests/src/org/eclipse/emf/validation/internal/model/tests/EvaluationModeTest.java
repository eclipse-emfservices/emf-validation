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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.junit.Test;

/**
 * JUnit tests for the {@link EvaluationMode} class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class EvaluationModeTest {

	@Test
	public void getInstance() {
		assertSame(EvaluationMode.BATCH, EvaluationMode.getInstance(EvaluationMode.BATCH.getName()));
		assertSame(EvaluationMode.LIVE, EvaluationMode.getInstance(EvaluationMode.LIVE.getName()));
		assertSame(EvaluationMode.NULL, EvaluationMode.getInstance(""));
	}

	@Test
	public void getAllInstances() {
		List<EvaluationMode<?>> instances = EvaluationMode.getAllInstances();

		assertTrue(instances.contains(EvaluationMode.BATCH));
		assertTrue(instances.contains(EvaluationMode.LIVE));
		assertTrue(instances.contains(EvaluationMode.NULL));
	}

	@Test
	public void getName() {
		assertEquals("Batch", EvaluationMode.BATCH.getName());
		assertEquals("Live", EvaluationMode.LIVE.getName());
		assertEquals("none", EvaluationMode.NULL.getName());
	}

	@Test
	public void getLocalizedName() {
		assertEquals(ValidationMessages.mode_batch, EvaluationMode.BATCH.getLocalizedName());
		assertEquals(ValidationMessages.mode_live, EvaluationMode.LIVE.getLocalizedName());
		assertEquals(ValidationMessages.mode_unknown, EvaluationMode.NULL.getLocalizedName());
	}

	@Test
	public void isNull() {
		assertTrue(EvaluationMode.NULL.isNull());
		assertFalse(EvaluationMode.BATCH.isNull());
		assertFalse(EvaluationMode.LIVE.isNull());
	}

	@Test
	public void isLive() {
		assertFalse(EvaluationMode.NULL.isLive());
		assertFalse(EvaluationMode.BATCH.isLive());
		assertTrue(EvaluationMode.LIVE.isLive());
	}

	@Test
	public void isBatch() {
		assertFalse(EvaluationMode.NULL.isBatch());
		assertTrue(EvaluationMode.BATCH.isBatch());
		assertTrue(EvaluationMode.LIVE.isBatch()); // live implies batch
	}

	@Test
	public void isBatchOnly() {
		assertFalse(EvaluationMode.NULL.isBatchOnly());
		assertTrue(EvaluationMode.BATCH.isBatchOnly());
		assertFalse(EvaluationMode.LIVE.isBatchOnly());
	}

	@Test
	public void readResolve() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		try (ObjectOutput output = new ObjectOutputStream(stream)){
			for (EvaluationMode<?> next : EvaluationMode.getAllInstances()) {
				output.writeObject(next);
			}
			output.flush();
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		}

		try (ObjectInput input = new ObjectInputStream(new ByteArrayInputStream(stream.toByteArray()))) {
			for (EvaluationMode<?> next : EvaluationMode.getAllInstances()) {
				assertSame(next, input.readObject());
			}
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		}
	}
}
