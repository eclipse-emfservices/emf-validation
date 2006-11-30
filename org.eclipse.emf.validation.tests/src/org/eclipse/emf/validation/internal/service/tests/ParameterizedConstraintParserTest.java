/**
 * <copyright>
 *
 * Copyright (c) 2006 IBM Corporation and others.
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
 * $Id: ParameterizedConstraintParserTest.java,v 1.1 2006/11/30 22:53:54 cdamus Exp $
 */
package org.eclipse.emf.validation.internal.service.tests;

import java.util.Arrays;
import java.util.Collections;

import ordersystem.Order;
import ordersystem.OrderSystemFactory;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.CategoryManager;
import org.eclipse.emf.validation.model.ConstraintSeverity;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.AbstractConstraintDescriptor;
import org.eclipse.emf.validation.service.AbstractConstraintProvider;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IParameterizedConstraintDescriptor;
import org.eclipse.emf.validation.service.IParameterizedConstraintParser;
import org.eclipse.emf.validation.tests.TestBase;
import org.eclipse.emf.validation.xml.ConstraintParserException;

/**
 * Tests the {@link IParameterizedConstraintDescriptor} and
 * {@link IParameterizedConstraintParser} APIs for implementing custom
 * constraint providers.
 *
 * @author Christian W. Damus (cdamus)
 * 
 * @since 1.1
 */
public class ParameterizedConstraintParserTest extends TestBase {
	static final String TEST_LANGUAGE = "***test-lang***"; //$NON-NLS-1$
	static final String TEST_ID = "parser-test.id"; //$NON-NLS-1$
	static final String TEST_PARAMETER = "testparm"; //$NON-NLS-1$
	static final String TEST_VALUE = "This parser worked"; //$NON-NLS-1$
	
	
	public ParameterizedConstraintParserTest(String name) {
		super(name);
	}

	public void test_provideNonXMLConstraintDescriptors_165455() {
		Order order = OrderSystemFactory.eINSTANCE.createOrder();
		order.getItem().add(OrderSystemFactory.eINSTANCE.createLineItem());
		
		Constraint.enabled = true;
		
		IStatus[] statuses = getStatuses(treeValidator.validate(order));
		
		Constraint.enabled = false;
		
		assertAllConstraintsPresent(
				"batch", //$NON-NLS-1$
				statuses,
				Arrays.asList(new String[] {TEST_ID}));
		
		IStatus status = getStatus(statuses, TEST_ID);
		
		assertEquals(IStatus.WARNING, status.getSeverity());
		assertEquals(TEST_VALUE, status.getMessage());
		assertAllTargetsPresent(
				"batch", //$NON-NLS-1$
				new IStatus[] {status},
				Collections.singleton(order));
	}
	
	public static final class Provider extends AbstractConstraintProvider {
		public Provider() {
			Descriptor desc = new Descriptor();
			IModelConstraint proxy = createModelConstraintProxy(desc);
			
			getConstraints().add(proxy);
		}
	}
	
	public static final class Parser implements IParameterizedConstraintParser {

		public IModelConstraint parseConstraint(IParameterizedConstraintDescriptor descriptor) throws ConstraintParserException {
			return new Constraint(descriptor, descriptor.getParameterValue(TEST_PARAMETER));
		}
		
	}
	
	private static final class Constraint implements IModelConstraint {
		static boolean enabled = false;
		
		private final IConstraintDescriptor descriptor;
		private final String errorMessage;
		
		Constraint(IConstraintDescriptor descriptor, String errorMessage) {
			this.descriptor = descriptor;
			this.errorMessage = errorMessage;
			
			assertEquals(TEST_VALUE, errorMessage);
		}
		
		public IConstraintDescriptor getDescriptor() {
			return descriptor;
		}

		public IStatus validate(IValidationContext ctx) {
			assertTrue(ctx.getTarget() instanceof Order);
			
			if (!enabled) {
				return ctx.createSuccessStatus();
			}
			
			return ctx.createFailureStatus(new Object[] {errorMessage});
		}
		
	}
	
	private static class Descriptor
			extends AbstractConstraintDescriptor
			implements IParameterizedConstraintDescriptor {

		Descriptor() {
			addCategory(CategoryManager.getInstance().findCategory(
					"junit/validation")); //$NON-NLS-1$
		}
		
		public String getLanguage() {
			return TEST_LANGUAGE;
		}

		public String getParameterValue(String name) {
			if (TEST_PARAMETER.equals(name)) {
				return TEST_VALUE;
			}
			
			return null;
		}

		public String getBody() {
			return null;
		}

		public String getDescription() {
			return "Test constraint"; //$NON-NLS-1$
		}

		public EvaluationMode getEvaluationMode() {
			return EvaluationMode.BATCH;
		}

		public String getId() {
			return TEST_ID;
		}

		public String getMessagePattern() {
			return "{0}"; //$NON-NLS-1$
		}

		public String getName() {
			return "test constraint"; //$NON-NLS-1$
		}

		public String getPluginId() {
			return "org.eclipse.emf.validation.tests"; //$NON-NLS-1$
		}

		public ConstraintSeverity getSeverity() {
			return ConstraintSeverity.WARNING;
		}

		public int getStatusCode() {
			return 1;
		}

		public boolean targetsEvent(Notification notification) {
			return notification.getNotifier() instanceof Order;
		}

		public boolean targetsTypeOf(EObject eObject) {
			return eObject instanceof Order;
		}
	}
}
