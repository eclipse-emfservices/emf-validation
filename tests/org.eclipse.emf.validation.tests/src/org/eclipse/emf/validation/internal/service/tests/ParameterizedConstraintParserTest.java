/**
 * Copyright (c) 2006, 2007 IBM Corporation and others.
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

import java.util.Collections;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
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

import ordersystem.Order;
import ordersystem.OrderSystemFactory;

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
	static final String TEST_ID1 = "parser-test.id1"; //$NON-NLS-1$
	static final String TEST_ID2 = "parser-test.id2"; //$NON-NLS-1$
	static final String TEST_PARAMETER = "testparm"; //$NON-NLS-1$
	static final String TEST_VALUE = "New parser worked"; //$NON-NLS-1$
	static final String JAVA_MESSAGE = "Java parser worked"; //$NON-NLS-1$
	static final String XML_MESSAGE = "XML provider worked"; //$NON-NLS-1$

	public ParameterizedConstraintParserTest(String name) {
		super(name);
	}

	/**
	 * Tests that a new-fangled constraint provider (using the new descriptor API)
	 * can provide constraints for a language implemented by a new-fangled parser.
	 */
	public void test_provideNonXMLConstraintDescriptorsForCustomLanguage_165455() {
		Order order = OrderSystemFactory.eINSTANCE.createOrder();
		order.getItem().add(OrderSystemFactory.eINSTANCE.createLineItem());

		Constraint.enabled = true;

		IStatus[] statuses = getStatuses(treeValidator.validate(order));

		Constraint.enabled = false;

		assertAllConstraintsPresent("batch", //$NON-NLS-1$
				statuses, TEST_ID1);

		IStatus status = getStatus(statuses, TEST_ID1);

		assertEquals(IStatus.WARNING, status.getSeverity());
		assertEquals(TEST_VALUE, status.getMessage());
		assertAllTargetsPresent("batch", //$NON-NLS-1$
				new IStatus[] { status }, Collections.singleton(order));
	}

	/**
	 * Tests that a new-fangled constraint provider (using the new descriptor API)
	 * can provide constraints for a language implemented by a legacy parser such as
	 * Java.
	 */
	public void test_provideNonXMLConstraintDescriptorsForJavaLanguage_165455() {
		Order order = OrderSystemFactory.eINSTANCE.createOrder();
		order.getItem().add(OrderSystemFactory.eINSTANCE.createLineItem());

		JavaConstraint.enabled = true;

		IStatus[] statuses = getStatuses(treeValidator.validate(order));

		JavaConstraint.enabled = false;

		assertAllConstraintsPresent("batch", //$NON-NLS-1$
				statuses, TEST_ID2);

		IStatus status = getStatus(statuses, TEST_ID2);

		assertEquals(IStatus.WARNING, status.getSeverity());
		assertEquals(JAVA_MESSAGE, status.getMessage());
		assertAllTargetsPresent("batch", //$NON-NLS-1$
				new IStatus[] { status }, Collections.singleton(order));
	}

	/**
	 * Tests that a legacy constraint provider (using plugin.xml) can provide
	 * constraints for a language implemented by a new-fangled parser.
	 */
	public void test_provideXMLConstraintDescriptorsForCustomLanguage_165455() {
		Order order = OrderSystemFactory.eINSTANCE.createOrder();
		order.getItem().add(OrderSystemFactory.eINSTANCE.createLineItem());

		Constraint.enabled = true;

		IStatus[] statuses = getStatuses(treeValidator.validate(order));

		Constraint.enabled = false;

		assertAllConstraintsPresent("batch", //$NON-NLS-1$
				statuses, ID_PREFIX + "order.newParserAPI"); //$NON-NLS-1$

		IStatus status = getStatus(statuses, ID_PREFIX + "order.newParserAPI"); //$NON-NLS-1$

		// implicit severity in XML is ERROR
		assertEquals(IStatus.ERROR, status.getSeverity());
		assertEquals(XML_MESSAGE, status.getMessage());
		assertAllTargetsPresent("batch", //$NON-NLS-1$
				new IStatus[] { status }, Collections.singleton(order));
	}

	public static final class Provider extends AbstractConstraintProvider {
		public Provider() {
			Descriptor desc = new Descriptor(TEST_ID1, TEST_LANGUAGE);
			IModelConstraint proxy = createModelConstraintProxy(desc);
			getConstraints().add(proxy);

			desc = new Descriptor(TEST_ID2, "Java"); //$NON-NLS-1$
			proxy = createModelConstraintProxy(desc);
			getConstraints().add(proxy);
		}
	}

	public static final class Parser implements IParameterizedConstraintParser {

		@Override
		public IModelConstraint parseConstraint(IParameterizedConstraintDescriptor descriptor)
				throws ConstraintParserException {
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
		}

		@Override
		public IConstraintDescriptor getDescriptor() {
			return descriptor;
		}

		@Override
		public IStatus validate(IValidationContext ctx) {
			assertTrue(ctx.getTarget() instanceof Order);

			if (!enabled) {
				return ctx.createSuccessStatus();
			}

			return ctx.createFailureStatus(new Object[] { errorMessage });
		}
	}

	public static final class JavaConstraint extends AbstractModelConstraint {
		static boolean enabled = false;

		@Override
		public IStatus validate(IValidationContext ctx) {
			assertTrue(ctx.getTarget() instanceof Order);

			if (!enabled) {
				return ctx.createSuccessStatus();
			}

			return ctx.createFailureStatus(new Object[] { JAVA_MESSAGE });
		}

	}

	private static class Descriptor extends AbstractConstraintDescriptor implements IParameterizedConstraintDescriptor {

		private final String id;
		private final String lang;

		Descriptor(String id, String lang) {
			this.id = id;
			this.lang = lang;

			addCategory(CategoryManager.getInstance().findCategory("junit/validation")); //$NON-NLS-1$
		}

		@Override
		public String getLanguage() {
			return lang;
		}

		@Override
		public String getParameterValue(String name) {
			if (TEST_PARAMETER.equals(name)) {
				return TEST_VALUE;
			} else if (IParameterizedConstraintDescriptor.CLASS_PARAMETER.equals(name)) {
				return JavaConstraint.class.getName();
			} else if (IParameterizedConstraintDescriptor.BUNDLE_PARAMETER.equals(name)) {
				return getPluginId();
			}

			return null;
		}

		@Override
		public String getBody() {
			return null;
		}

		@Override
		public String getDescription() {
			return "Test constraint"; //$NON-NLS-1$
		}

		@Override
		public EvaluationMode<?> getEvaluationMode() {
			return EvaluationMode.BATCH;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public String getMessagePattern() {
			return "{0}"; //$NON-NLS-1$
		}

		@Override
		public String getName() {
			return "test constraint"; //$NON-NLS-1$
		}

		@Override
		public String getPluginId() {
			return "org.eclipse.emf.validation.tests"; //$NON-NLS-1$
		}

		@Override
		public ConstraintSeverity getSeverity() {
			return ConstraintSeverity.WARNING;
		}

		@Override
		public int getStatusCode() {
			return 1;
		}

		@Override
		public boolean targetsEvent(Notification notification) {
			return notification.getNotifier() instanceof Order;
		}

		@Override
		public boolean targetsTypeOf(EObject eObject) {
			return eObject instanceof Order;
		}
	}
}
