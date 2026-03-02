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
package org.eclipse.emf.validation.internal.service.impl.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.internal.util.XmlConstraintDescriptor;
import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.model.CategoryManager;
import org.eclipse.emf.validation.model.ConstraintSeverity;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.ConstraintExistsException;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.tests.TestNotification;
import org.eclipse.emf.validation.util.XmlConfig;
import org.junit.jupiter.api.Test;

import ordersystem.OrderSystemFactory;

/**
 * JUnit tests for the {@link XmlConstraintDescriptor} class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintDescriptorTest {
	private static final String TEST_NAME = "Testing";
	private static final String TEST_PLUGIN = "org.eclipse.emf.validation.tests";
	private static final String TEST_ID = TEST_PLUGIN + '.' + "test.id";
	private static final String TEST_DESCRIPTION = "Test description";
	private static final String TEST_MESSAGE = "Error in {0}";
	private static final String TEST_LANG = "OCL";
	private static final String TEST_BODY = "self.name = 'Ottawa'";
	private static final int TEST_CODE = 1000;
	private static final ConstraintSeverity TEST_SEVERITY = ConstraintSeverity.WARNING;
	private static final EvaluationMode<Notification> TEST_MODE = EvaluationMode.LIVE;
	private static final String TEST_NAMESPACE_URI = "http:///ordersystem.ecore";
	private static final String TEST_CLASS = "Warehouse";
	private static final String TEST_EVENT = "Set";
	private static final Category TEST_CATEGORY = CategoryManager.getInstance().getCategory("test/descriptor");

	private static XmlConstraintDescriptor fixture;
	private static FixtureElement fixtureConfig;

	/**
	 * Handy implementation of the Eclipse extension configuration element.
	 */
	public static class FixtureElement implements IConfigurationElement {
		private final Map<String, String> attributes = new java.util.HashMap<>();
		private final List<IConfigurationElement> children = new java.util.ArrayList<>();
		private Object parent;
		private final String myName;
		private String value = "";

		public FixtureElement(String name) {
			this.myName = name;
		}

		public static FixtureElement build(String name, String[][] attributes) {
			return build(name, null, attributes);
		}

		public static FixtureElement build(String name, String value) {
			return build(name, value, null);
		}

		public static FixtureElement build(String name, String value, String[][] attributes) {
			FixtureElement result = new FixtureElement(name);

			if (attributes != null) {
				for (String[] element : attributes) {
					result.putAttribute(element[0], element[1]);
				}
			}

			result.setValue(value);

			return result;
		}

		// implements/extends the inherited method
		@Override
		public String getAttribute(String name) {
			return attributes.get(name);
		}

		public FixtureElement putAttribute(String name, String newValue) {
			attributes.put(name, newValue);

			return this;
		}

		// implements/extends the inherited method
		@Override
		public String getAttributeAsIs(String name) {
			return getAttribute(name);
		}

		// implements/extends the inherited method
		@Override
		public String[] getAttributeNames() {
			return attributes.keySet().toArray(new String[attributes.size()]);
		}

		// implements/extends the inherited method
		@Override
		public IConfigurationElement[] getChildren() {
			return children.toArray(new IConfigurationElement[children.size()]);
		}

		// implements/extends the inherited method
		@Override
		public IConfigurationElement[] getChildren(String name) {
			List<IConfigurationElement> result = new java.util.ArrayList<>();

			for (IConfigurationElement next : children) {
				if (next.getName().equals(name)) {
					result.add(next);
				}
			}

			return result.toArray(new IConfigurationElement[result.size()]);
		}

		public FixtureElement addChild(IConfigurationElement child) {
			children.add(child);

			if (child instanceof FixtureElement fixtureElement) {
				fixtureElement.setParent(this);
			}

			return this;
		}

		@Override
		public String getName() {
			return myName;
		}

		@Override
		public String getValue() {
			return value;
		}

		@Override
		public String getValueAsIs() {
			return getValue();
		}

		public FixtureElement setValue(String value) {
			this.value = value;

			return this;
		}

		// This method implemented solely so that the XmlConstraintDescriptor
		// constructor can find the plug-in ID that it needs from the
		// configuration element
		/** @deprecated */
		@Override
		@Deprecated
		public IExtension getDeclaringExtension() {
			return new IExtension() {

				@Override
				public String getNamespace() {
					return TEST_PLUGIN;
				}

				@Override
				public String getNamespaceIdentifier() throws InvalidRegistryObjectException {
					return TEST_PLUGIN;
				}

				@Override
				public IConfigurationElement[] getConfigurationElements() throws InvalidRegistryObjectException {
					return null;
				}

				@Override
				public String getExtensionPointUniqueIdentifier() throws InvalidRegistryObjectException {
					return null;
				}

				@Override
				public String getLabel() throws InvalidRegistryObjectException {
					return TEST_PLUGIN;
				}

				@Override
				public String getSimpleIdentifier() throws InvalidRegistryObjectException {
					return null;
				}

				@Override
				public String getUniqueIdentifier() throws InvalidRegistryObjectException {
					return null;
				}

				@Override
				public boolean isValid() {
					return false;
				}

				@Override
				public IContributor getContributor() throws InvalidRegistryObjectException {
					return null;
				}

				@Override
				public String getLabel(String locale) throws InvalidRegistryObjectException {
					return TEST_PLUGIN;
				}
			};
		}

		@Override
		public Object createExecutableExtension(String propertyName) throws CoreException {
			try {
				Object result = Class.forName(getAttribute(propertyName)).getDeclaredConstructor().newInstance();

				if (result instanceof IExecutableExtension executableExtension) {
					executableExtension.setInitializationData(this, propertyName, null);
				}

				return result;
			} catch (Exception e) {
				throw new CoreException(
						new Status(IStatus.ERROR, TEST_PLUGIN, 1, "Failed to create executable extension", e));
			}
		}

		@Override
		public Object getParent() {
			return parent;
		}

		void setParent(Object parent) {
			this.parent = parent;
		}

		@Override
		public String getNamespace() throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public boolean isValid() {
			return false;
		}

		@Override
		public String getNamespaceIdentifier() throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public IContributor getContributor() throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public String getAttribute(String attrName, String locale) throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public String getValue(String locale) throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public int getHandleId() {
			return 0;
		}
	}

	/** Make this accessible to the constraint implementation tests. */
	public static XmlConstraintDescriptor getFixture() {
		if (fixture == null) {
			try {
				fixture = new XmlConstraintDescriptor(getFixtureConfig());
			} catch (ConstraintExistsException e) {
				// shouldn't happen in this test
				fail("Constraint already exists: " + e.getLocalizedMessage());
			}
		}

		return fixture;
	}

	public static FixtureElement newFixtureConfig() {
		FixtureElement result = new FixtureElement(XmlConfig.E_CONSTRAINT);

		result.putAttribute(XmlConfig.A_ID, TEST_ID);
		result.putAttribute(XmlConfig.A_NAME, TEST_NAME);
		result.putAttribute(XmlConfig.A_LANG, TEST_LANG);
		result.putAttribute(XmlConfig.A_SEVERITY, TEST_SEVERITY.getName());
		result.putAttribute(XmlConfig.A_STATUS_CODE, String.valueOf(TEST_CODE));
		result.putAttribute(XmlConfig.A_MODE, TEST_MODE.getName());
		result.setValue(TEST_BODY);

		FixtureElement description = new FixtureElement(XmlConfig.E_DESCRIPTION);

		description.setValue(TEST_DESCRIPTION);

		FixtureElement message = new FixtureElement(XmlConfig.E_MESSAGE);

		message.setValue(TEST_MESSAGE);

		FixtureElement target = new FixtureElement(XmlConfig.E_TARGET);

		target.putAttribute(XmlConfig.A_CLASS, TEST_CLASS);

		FixtureElement event = new FixtureElement(XmlConfig.E_EVENT);

		event.putAttribute(XmlConfig.A_NAME, TEST_EVENT);

		target.addChild(event);

		result.addChild(description);
		result.addChild(message);
		result.addChild(target);

		return result;
	}

	static IConfigurationElement getFixtureConfig() {
		if (fixtureConfig == null) {
			fixtureConfig = newFixtureConfig();
		}

		return fixtureConfig;
	}

	@Test
	public void testHashCode() {
		assertEquals(TEST_ID.hashCode(), getFixture().hashCode());
	}

	@Test
	public void registry() {
		assertSame(getFixture(), ConstraintRegistry.getInstance().getDescriptor(TEST_PLUGIN, TEST_ID));
	}

	@Test
	public void getConfig() {
		assertSame(getFixtureConfig(), getFixture().getConfig());
	}

	@Test
	public void getDescriptor() {
		assertSame(getFixture(), getFixture().getDescriptor());
	}

	@Test
	public void getName() {
		assertEquals(TEST_NAME, getFixture().getName());
	}

	@Test
	public void getId() {
		assertEquals(TEST_ID, getFixture().getId());
	}

	@Test
	public void getPluginId() {
		assertEquals(TEST_PLUGIN, getFixture().getPluginId());
	}

	@Test
	public void getDescription() {
		assertEquals(TEST_DESCRIPTION, getFixture().getDescription());
	}

	@Test
	public void getSeverity() {
		assertSame(TEST_SEVERITY, getFixture().getSeverity());
	}

	@Test
	public void getStatusCode() {
		assertEquals(TEST_CODE, getFixture().getStatusCode());
	}

	@Test
	public void getEvaluationMode() {
		assertSame(TEST_MODE, getFixture().getEvaluationMode());
	}

	@Test
	public void isBatch() {
		assertFalse(getFixture().getEvaluationMode().isBatchOnly());
	}

	@Test
	public void isLive() {
		assertTrue(getFixture().getEvaluationMode().isLive());
	}

	@Test
	public void isEnabled_isError_getException() {
		assertTrue(getFixture().isEnabled());
		assertFalse(getFixture().isError());
		assertNull(getFixture().getException());

		Exception e = new Exception();

		getFixture().setError(e);

		assertFalse(getFixture().isEnabled());
		assertTrue(getFixture().isError());
		assertSame(e, getFixture().getException());
	}

	@Test
	public void getCategories() {
		Set<Category> categories = Collections.singleton(CategoryManager.getInstance().getDefaultCategory());

		assertEquals(categories, getFixture().getCategories());
	}

	@Test
	public void addCategory_removeCategory() {
		getFixture().addCategory(TEST_CATEGORY);

		assertEquals(Collections.singleton(TEST_CATEGORY), getFixture().getCategories());

		getFixture().removeCategory(TEST_CATEGORY);

		assertEquals(Collections.singleton(CategoryManager.getInstance().getDefaultCategory()),
				getFixture().getCategories());
	}

	@Test
	public void resolveTargetTypes() {
		getFixture().resolveTargetTypes(new String[] { TEST_NAMESPACE_URI });

		assertTrue(getFixture().targetsTypeOf(OrderSystemFactory.eINSTANCE.createWarehouse()));
	}

	@Test
	public void targetsTypeOf() {
		getFixture().resolveTargetTypes(new String[] { TEST_NAMESPACE_URI });

		assertTrue(getFixture().targetsTypeOf(OrderSystemFactory.eINSTANCE.createWarehouse()));
	}

	@Test
	public void targetsEvent() {
		getFixture().resolveTargetTypes(new String[] { TEST_NAMESPACE_URI });

		assertTrue(getFixture().targetsEvent(new TestNotification(OrderSystemFactory.eINSTANCE.createWarehouse(),
				EMFEventType.getInstance(TEST_EVENT).toNotificationType())));
	}

	@Test
	public void getMessagePattern() {
		assertEquals(TEST_MESSAGE, getFixture().getMessagePattern());
	}

	@Test
	public void getBody() {
		assertEquals(TEST_BODY, getFixture().getBody());
	}

	@Test
	public void equalsObject() {
		assertEquals(getFixture(), getFixture());
	}
}
