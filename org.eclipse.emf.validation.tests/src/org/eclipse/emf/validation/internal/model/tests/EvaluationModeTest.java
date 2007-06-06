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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.model.EvaluationMode;

/**
 * JUnit tests for the {@link EvaluationMode} class.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class EvaluationModeTest extends TestCase {

	public void test_getInstance() {
		assertSame(
				EvaluationMode.BATCH,
				EvaluationMode.getInstance(EvaluationMode.BATCH.getName()));
		assertSame(
				EvaluationMode.LIVE,
				EvaluationMode.getInstance(EvaluationMode.LIVE.getName()));
		assertSame(
				EvaluationMode.NULL,
				EvaluationMode.getInstance("")); //$NON-NLS-1$
	}

	public void test_getAllInstances() {
		List instances = EvaluationMode.getAllInstances();
		
		assertTrue(instances.contains(EvaluationMode.BATCH));
		assertTrue(instances.contains(EvaluationMode.LIVE));
		assertTrue(instances.contains(EvaluationMode.NULL));
	}

	public void test_getName() {
		assertEquals("Batch", EvaluationMode.BATCH.getName()); //$NON-NLS-1$
		assertEquals("Live", EvaluationMode.LIVE.getName()); //$NON-NLS-1$
		assertEquals("none", EvaluationMode.NULL.getName()); //$NON-NLS-1$
	}

	public void test_getLocalizedName() {
		assertEquals(
				ValidationMessages.mode_batch,
				EvaluationMode.BATCH.getLocalizedName());
		assertEquals(
				ValidationMessages.mode_live,
				EvaluationMode.LIVE.getLocalizedName());
		assertEquals(
				ValidationMessages.mode_unknown,
				EvaluationMode.NULL.getLocalizedName());
	}

	public void test_isNull() {
		assertTrue(EvaluationMode.NULL.isNull());
		assertFalse(EvaluationMode.BATCH.isNull());
		assertFalse(EvaluationMode.LIVE.isNull());
	}

	public void test_isLive() {
		assertFalse(EvaluationMode.NULL.isLive());
		assertFalse(EvaluationMode.BATCH.isLive());
		assertTrue(EvaluationMode.LIVE.isLive());
	}

	public void test_isBatch() {
		assertFalse(EvaluationMode.NULL.isBatch());
		assertTrue(EvaluationMode.BATCH.isBatch());
		assertTrue(EvaluationMode.LIVE.isBatch()); // live implies batch
	}

	public void test_isBatchOnly() {
		assertFalse(EvaluationMode.NULL.isBatchOnly());
		assertTrue(EvaluationMode.BATCH.isBatchOnly());
		assertFalse(EvaluationMode.LIVE.isBatchOnly());
	}

	public void test_readResolve() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		ObjectOutput output = null;
		ObjectInput input = null;
		
		try {
			output = new ObjectOutputStream(stream);
			
			for (Iterator i = EvaluationMode.getAllInstances().iterator(); i.hasNext();) {
				output.writeObject(i.next());
			}
			
			output.flush();

			input =	new ObjectInputStream(
					new ByteArrayInputStream(stream.toByteArray()));
			
			for (Iterator i = EvaluationMode.getAllInstances().iterator(); i.hasNext();) {
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
