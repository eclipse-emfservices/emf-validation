/**
 * Copyright (c) 2003, 2007 IBM Corporation and others.
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

import java.util.Collections;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.model.ConstraintSeverity;
import org.eclipse.emf.validation.model.ConstraintStatus;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;

import junit.framework.TestCase;
import ordersystem.OrderSystemFactory;

/**
 * JUnit tests for the {@link ConstraintStatus} class.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintStatusTest extends TestCase {
	private static final String TEST_NAME = "Testing";//$NON-NLS-1$
	private static final String TEST_ID = "test.id";//$NON-NLS-1$
	private static final String TEST_PLUGIN = "test.plugin";//$NON-NLS-1$
	private static final String TEST_MESSAGE = "Error happened";//$NON-NLS-1$
	private static final int TEST_CODE = 1000;
	private static final ConstraintSeverity TEST_SEVERITY = ConstraintSeverity.WARNING;

	private static final EObject TEST_TARGET = OrderSystemFactory.eINSTANCE.createWarehouse();

	private static final Set<EObject> TEST_RESULTLOCUS = Collections.singleton(TEST_TARGET);

	private static FixtureConstraint fixtureConstraint;
	private static ConstraintStatus successFixture;
	private static ConstraintStatus failedFixture;

	private static class FixtureConstraint implements IModelConstraint {
		private final String name;
		private final String id;
		private final String pluginId;
		private final ConstraintSeverity severity;
		private final int code;

		FixtureConstraint(String name, String id, String pluginId, ConstraintSeverity severity, int code) {
			this.name = name;
			this.id = id;
			this.pluginId = pluginId;
			this.severity = severity;
			this.code = code;
		}

		public IConstraintDescriptor getDescriptor() {
			return new IConstraintDescriptor() {

				public String getName() {
					return name;
				}

				public String getId() {
					return id;
				}

				public String getPluginId() {
					return pluginId;
				}

				public ConstraintSeverity getSeverity() {
					return severity;
				}

				public int getStatusCode() {
					return code;
				}

				public String getDescription() {
					return null;
				}

				public String getBody() {
					return null;
				}

				public boolean isError() {
					return false;
				}

				public boolean isEnabled() {
					return true;
				}

				public void setEnabled(boolean enabled) {
					// do nothing
				}

				public Throwable getException() {
					return null;
				}

				public EvaluationMode<?> getEvaluationMode() {
					return null;
				}

				public boolean targetsTypeOf(EObject eObject) {
					return false;
				}

				public boolean targetsEvent(Notification notification) {
					return false;
				}

				public boolean isBatch() {
					return false;
				}

				public boolean isLive() {
					return false;
				}

				public void setError(Throwable exception) {
					// do nothing
				}

				public Set<Category> getCategories() {
					return Collections.emptySet();
				}

				public void addCategory(Category category) {
					// do nothing
				}

				public void removeCategory(Category category) {
					// do nothing
				}

				public String getMessagePattern() {
					return TEST_MESSAGE;
				}
			};
		}

		public IStatus validate(IValidationContext ctx) {
			return null;
		}
	}

	private static FixtureConstraint getFixtureConstraint() {
		if (fixtureConstraint == null) {
			fixtureConstraint = new FixtureConstraint(TEST_NAME, TEST_ID, TEST_PLUGIN, TEST_SEVERITY, TEST_CODE);
		}

		return fixtureConstraint;
	}

	private static ConstraintStatus getSuccessFixture() {
		if (successFixture == null) {
			successFixture = new ConstraintStatus(getFixtureConstraint(), TEST_TARGET);
		}

		return successFixture;
	}

	private static ConstraintStatus getFailedFixture() {
		if (failedFixture == null) {
			failedFixture = new ConstraintStatus(getFixtureConstraint(), TEST_TARGET, TEST_MESSAGE, TEST_RESULTLOCUS);
		}

		return failedFixture;
	}

	public void test_getConstraint() {
		assertSame(getFixtureConstraint(), getSuccessFixture().getConstraint());
		assertSame(getFixtureConstraint(), getFailedFixture().getConstraint());
	}

	public void test_getTarget() {
		assertEquals(TEST_TARGET, getSuccessFixture().getTarget());
		assertEquals(TEST_TARGET, getFailedFixture().getTarget());
	}

	public void test_getResultLocus() {
		assertTrue(getSuccessFixture().getResultLocus().isEmpty());
		assertTrue(getFailedFixture().getResultLocus().containsAll(TEST_RESULTLOCUS));
	}

	public void test_getChildren() {
		assertTrue(getSuccessFixture().getChildren().length == 0);
		assertTrue(getFailedFixture().getChildren().length == 0);
	}

	public void test_getCode() {
		assertEquals(EMFModelValidationStatusCodes.ALL_CONSTRAINTS_PASSED, getSuccessFixture().getCode());
		assertEquals(TEST_CODE, getFailedFixture().getCode());
	}

	public void test_getMessage() {
		assertEquals(EMFModelValidationStatusCodes.CONSTRAINT_SUCCESS_MSG, getSuccessFixture().getMessage());
		assertEquals(TEST_MESSAGE, getFailedFixture().getMessage());
	}

	public void test_getPlugin() {
		assertEquals(TEST_PLUGIN, getSuccessFixture().getPlugin());
		assertEquals(TEST_PLUGIN, getFailedFixture().getPlugin());
	}

	public void test_getSeverity() {
		assertEquals(IStatus.OK, getSuccessFixture().getSeverity());
		assertEquals(TEST_SEVERITY.toIStatusSeverity(), getFailedFixture().getSeverity());
	}

	public void test_isMultiStatus() {
		assertFalse(getSuccessFixture().isMultiStatus());
		assertFalse(getFailedFixture().isMultiStatus());
	}
}
