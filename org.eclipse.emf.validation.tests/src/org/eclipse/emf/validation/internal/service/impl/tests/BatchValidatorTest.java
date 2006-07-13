/**
 * <copyright>
 *
 * Copyright (c) 2004, 2006 IBM Corporation and others.
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

package org.eclipse.emf.validation.internal.service.impl.tests;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;

import ordersystem.OrderSystemFactory;

import org.eclipse.emf.validation.internal.service.BatchValidator;
import org.eclipse.emf.validation.internal.service.IProviderOperation;
import org.eclipse.emf.validation.internal.service.IProviderOperationExecutor;
import org.eclipse.emf.validation.model.EvaluationMode;

import junit.framework.TestCase;

/**
 * Tests for {@link BatchValidator}.
 *
 * @author Christian W. Damus (cdamus)
 */
public class BatchValidatorTest extends TestCase {
	private BatchValidator validator;
	
	/**
	 * Constructor for BatchValidatorTest.
	 * @param name
	 */
	public BatchValidatorTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * Extends the inherited method.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		validator = new BatchValidator(new TestExecutor());
	}
	
	private BatchValidator getValidator() {
		return validator;
	}

	public void test_getEvaluationMode() {
		assertSame(
				"Wrong evaluation mode", //$NON-NLS-1$
				EvaluationMode.BATCH,
				validator.getEvaluationMode());
	}

	public void test_isReportSuccesses() {
		getValidator().setReportSuccesses(true);
		assertTrue(
				"Not reporting successes", //$NON-NLS-1$
				getValidator().isReportSuccesses());
		
		getValidator().setReportSuccesses(false);
		assertFalse(
				"Should not report successes", //$NON-NLS-1$
				getValidator().isReportSuccesses());
	}

	/*
	 * Class to test for IStatus validate(Object)
	 */
	public void test_validate_object() {
		Object target = OrderSystemFactory.eINSTANCE.createProduct();
		
		try {
			getValidator().validate(target);
		} catch (Exception e) {
			fail("Should not throw."); //$NON-NLS-1$
		}
		
		try {
			getValidator().validate("foo"); //$NON-NLS-1$
			fail("Should throw."); //$NON-NLS-1$
		} catch (Exception e) {
			// good
		}
	}

	/*
	 * Class to test for IStatus validate(Collection)
	 */
	public void test_validateCollection() {
		Object target = OrderSystemFactory.eINSTANCE.createProduct();
		
		try {
			getValidator().validate(Collections.singleton(target));
			getValidator().validate(Collections.EMPTY_SET);
		} catch (Exception e) {
			fail("Should not throw."); //$NON-NLS-1$
		}
		
		try {
			getValidator().validate(Collections.singleton("foo")); //$NON-NLS-1$
			fail("Should throw."); //$NON-NLS-1$
		} catch (Exception e) {
			// good
		}
	}
	
	public void test_isIncludeLiveConstraints() {
		getValidator().setIncludeLiveConstraints(true);
		assertTrue(
				"Not including live constraints", //$NON-NLS-1$
				getValidator().isIncludeLiveConstraints());
		
		getValidator().setIncludeLiveConstraints(false);
		assertFalse(
				"Should not include live constraints", //$NON-NLS-1$
				getValidator().isIncludeLiveConstraints());
	}

	/*
	 * Class to test for IStatus validate(EObject, IProgressMonitor)
	 */
	public void test_validate_EObject_IProgressMonitor() {
		TestMonitor monitor = new TestMonitor();
		
		EObject target = OrderSystemFactory.eINSTANCE.createProduct();
		
		try {
			getValidator().validate(target, monitor);
		} catch (Exception e) {
			fail("Should not throw."); //$NON-NLS-1$
		}
		
		assertTrue("Monitor not done", monitor.isDone()); //$NON-NLS-1$
		assertTrue("Monitor has zero total work", monitor.getTotalWork() > 0); //$NON-NLS-1$
		assertTrue("Monitor did no work", monitor.getWorked() > 0); //$NON-NLS-1$
		assertEquals(
				"Monitor did not work total", //$NON-NLS-1$
				monitor.getTotalWork(),
				monitor.getWorked(),
				0.1);
	}

	/*
	 * Class to test for IStatus validate(Collection, IProgressMonitor)
	 */
	public void test_validate_Collection_IProgressMonitor() {
		TestMonitor monitor = new TestMonitor();
		
		Collection targets = new java.util.LinkedList();
		targets.add(OrderSystemFactory.eINSTANCE.createProduct());
		targets.add(OrderSystemFactory.eINSTANCE.createProduct());
		
		try {
			getValidator().validate(targets, monitor);
		} catch (Exception e) {
			fail("Should not throw."); //$NON-NLS-1$
		}
		
		assertTrue("Monitor not done", monitor.isDone()); //$NON-NLS-1$
		assertTrue("Monitor has zero total work", monitor.getTotalWork() > 0); //$NON-NLS-1$
		assertTrue("Monitor did no work", monitor.getWorked() > 0); //$NON-NLS-1$
		assertEquals(
				"Monitor did not work total", //$NON-NLS-1$
				monitor.getTotalWork(),
				monitor.getWorked(),
				0.1);
	}
	
	static class TestMonitor implements IProgressMonitor {
		private int totalWork;
		private double worked;
		private boolean done;
		
		public void beginTask(String name, int totalTaskWork) {
			this.totalWork = totalTaskWork;
		}

		int getTotalWork() {
			return totalWork;
		}
		
		public void done() {
			done = true;
		}

		boolean isDone() {
			return done;
		}
		
		public void internalWorked(double work) {
			worked += work;
		}

		public boolean isCanceled() {
			return false;
		}

		public void setCanceled(boolean value) {
			// no need to do anything for this test fixture
		}

		public void setTaskName(String name) {
			// no need to do anything for this test fixture
		}

		public void subTask(String name) {
			// no need to do anything for this test fixture
		}

		public void worked(int work) {
			internalWorked(work);
		}
		
		double getWorked() {
			return worked;
		}
	}
	
	static class TestExecutor implements IProviderOperationExecutor {
		/* (non-Javadoc)
		 * Implements the inherited method.
		 */
		public void execute(IProviderOperation op) {
			// don't need to do anything
		}
	}
}
