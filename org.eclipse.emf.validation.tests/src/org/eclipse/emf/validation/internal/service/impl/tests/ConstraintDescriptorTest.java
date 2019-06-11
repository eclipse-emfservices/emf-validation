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

package org.eclipse.emf.validation.internal.service.impl.tests;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;
import ordersystem.OrderSystemFactory;

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

/**
 * JUnit tests for the {@link XmlConstraintDescriptor} class.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintDescriptorTest extends TestCase {
	private static final String TEST_NAME = "Testing";//$NON-NLS-1$
	private static final String TEST_PLUGIN = "org.eclipse.emf.validation.tests";//$NON-NLS-1$
	private static final String TEST_ID = TEST_PLUGIN + '.' + "test.id";//$NON-NLS-1$
	private static final String TEST_DESCRIPTION = "Test description";//$NON-NLS-1$
	private static final String TEST_MESSAGE = "Error in {0}";//$NON-NLS-1$
	private static final String TEST_LANG = "OCL";//$NON-NLS-1$
	private static final String TEST_BODY = "self.name = 'Ottawa'";//$NON-NLS-1$
	private static final int TEST_CODE = 1000;
	private static final ConstraintSeverity TEST_SEVERITY = ConstraintSeverity.WARNING;
	private static final EvaluationMode<Notification> TEST_MODE = EvaluationMode.LIVE;
	private static final String TEST_NAMESPACE_URI = "http:///ordersystem.ecore"; //$NON-NLS-1$
	private static final String TEST_CLASS = "Warehouse"; //$NON-NLS-1$
	private static final String TEST_EVENT = "Set"; //$NON-NLS-1$
	private static final Category TEST_CATEGORY =
		CategoryManager.getInstance().getCategory("test/descriptor"); //$NON-NLS-1$

	private static XmlConstraintDescriptor fixture;
	private static FixtureElement fixtureConfig;
	
	/** 
	 * Handy implementation of the Eclipse extension configuration element. 
	 */
	public static class FixtureElement implements IConfigurationElement {
		private final Map<String, String> attributes = new java.util.HashMap<String, String>();
		private final List<IConfigurationElement> children = new java.util.ArrayList<IConfigurationElement>();
		private Object parent;
		private final String myName;
		private String value = ""; //$NON-NLS-1$
		
		public FixtureElement(String name) {
			this.myName = name;
		}

		public static FixtureElement build(String name, String[][] attributes) {
			return build(name, null, attributes);
		}
		
		public static FixtureElement build(String name, String value) {
			return build(name, value, null);
		}
		
		public static FixtureElement build(
				String name,
				String value,
				String[][] attributes) {
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
		public String getAttribute(String name) {
			return attributes.get(name);
		}
		
		public FixtureElement putAttribute(String name, String newValue) {
			attributes.put(name, newValue);
			
			return this;
		}

		// implements/extends the inherited method
		public String getAttributeAsIs(String name) {
			return getAttribute(name);
		}

		// implements/extends the inherited method
		public String[] getAttributeNames() {
			return attributes.keySet().toArray(new String[attributes.size()]);
		}

		// implements/extends the inherited method
		public IConfigurationElement[] getChildren() {
			return children.toArray(new IConfigurationElement[children.size()]);
		}

		// implements/extends the inherited method
		public IConfigurationElement[] getChildren(String name) {
			List<IConfigurationElement> result = new java.util.ArrayList<IConfigurationElement>();
			
			for (IConfigurationElement next : children) {
				if (next.getName().equals(name)) {
					result.add(next);
				}
			}
			
			return result.toArray(new IConfigurationElement[result.size()]);
		}
		
		public FixtureElement addChild(IConfigurationElement child) {
			children.add(child);
			
			if (child instanceof FixtureElement) {
				((FixtureElement)child).setParent(this);
			}
			
			return this;
		}

		// implements/extends the inherited method
		public String getName() {
			return myName;
		}

		// implements/extends the inherited method
		public String getValue() {
			return value;
		}

		// implements/extends the inherited method
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
		@Deprecated
		public IExtension getDeclaringExtension() {
			return new IExtension() {
				
				/* (non-Javadoc)
				 * Redefines the inherited method.
				 */
				public String getNamespace() {
					return TEST_PLUGIN;
				}

				public String getNamespaceIdentifier() throws InvalidRegistryObjectException {
					return TEST_PLUGIN;
				}

				public IConfigurationElement[] getConfigurationElements() throws InvalidRegistryObjectException {
					return null;
				}

				public String getExtensionPointUniqueIdentifier() throws InvalidRegistryObjectException {
					return null;
				}

				public String getLabel() throws InvalidRegistryObjectException {
					return TEST_PLUGIN;
				}

				public String getSimpleIdentifier() throws InvalidRegistryObjectException {
					return null;
				}

				public String getUniqueIdentifier() throws InvalidRegistryObjectException {
					return null;
				}

				public boolean isValid() {
					return false;
				}

				public IContributor getContributor() throws InvalidRegistryObjectException {
					return null;
				}

				public String getLabel(String locale)
						throws InvalidRegistryObjectException {
					return TEST_PLUGIN;
				}				
			};
		}
		
		public Object createExecutableExtension(String propertyName)
				throws CoreException {
			try {
				Object result =
					Class.forName(getAttribute(propertyName)).newInstance();
				
				if (result instanceof IExecutableExtension) {
					((IExecutableExtension)result).setInitializationData(
							this,
							propertyName,
							null);
				}
				
				return result;
			} catch (Exception e) {
				throw new CoreException(
						new Status(
								IStatus.ERROR,
								"org.eclipse.emf.validation.tests", //$NON-NLS-1$
								1,
								"Failed to create executable extension", //$NON-NLS-1$
								e));
			}
		}

		/* (non-Javadoc)
		 * @see org.eclipse.core.runtime.IConfigurationElement#getParent()
		 */
		public Object getParent() {
			return parent;
		}
		
		void setParent(Object parent) {
			this.parent = parent;
		}

		public String getNamespace() throws InvalidRegistryObjectException {
			return null;
		}

		public boolean isValid() {
			return false;
		}

		public String getNamespaceIdentifier() throws InvalidRegistryObjectException {
			return null;
		}

		public IContributor getContributor() throws InvalidRegistryObjectException {
			return null;
		}

		public String getAttribute(String attrName, String locale)
				throws InvalidRegistryObjectException {
			return null;
		}

		public String getValue(String locale)
				throws InvalidRegistryObjectException {
			return null;
		}

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
				fail("Constraint already exists: " + e.getLocalizedMessage()); //$NON-NLS-1$
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
	
	public void test_hashCode() {
		assertEquals(TEST_ID.hashCode(), getFixture().hashCode());
	}

	public void test_registry() {
		assertSame(
				getFixture(),
				ConstraintRegistry.getInstance().getDescriptor(
						TEST_PLUGIN,
						TEST_ID));
	}

	public void test_getConfig() {
		assertSame(getFixtureConfig(), getFixture().getConfig());
	}

	public void test_getDescriptor() {
		assertSame(getFixture(), getFixture().getDescriptor());
	}

	public void test_getName() {
		assertEquals(TEST_NAME, getFixture().getName());
	}

	public void test_getId() {
		assertEquals(TEST_ID, getFixture().getId());
	}

	public void test_getPluginId() {
		assertEquals(TEST_PLUGIN, getFixture().getPluginId());
	}

	public void test_getDescription() {
		assertEquals(TEST_DESCRIPTION, getFixture().getDescription());
	}

	public void test_getSeverity() {
		assertSame(TEST_SEVERITY, getFixture().getSeverity());
	}

	public void test_getStatusCode() {
		assertEquals(TEST_CODE, getFixture().getStatusCode());
	}

	public void test_getEvaluationMode() {
		assertSame(TEST_MODE, getFixture().getEvaluationMode());
	}

	public void test_isBatch() {
		assertFalse(getFixture().getEvaluationMode().isBatchOnly());
	}

	public void test_isLive() {
		assertTrue(getFixture().getEvaluationMode().isLive());
	}

	public void test_isEnabled_isError_getException() {
		assertTrue(getFixture().isEnabled());
		assertFalse(getFixture().isError());
		assertNull(getFixture().getException());
		
		Exception e = new Exception();
		
		getFixture().setError(e);
		
		assertFalse(getFixture().isEnabled());
		assertTrue(getFixture().isError());
		assertSame(e, getFixture().getException());
	}

	public void test_getCategories() {
		Set<Category> categories = Collections.singleton(
				CategoryManager.getInstance().getDefaultCategory());
		
		assertEquals(categories, getFixture().getCategories());
	}

	public void test_addCategory_removeCategory() {
		getFixture().addCategory(TEST_CATEGORY);
		
		assertEquals(
				Collections.singleton(TEST_CATEGORY),
				getFixture().getCategories());
		
		getFixture().removeCategory(TEST_CATEGORY);
		
		assertEquals(
				Collections.singleton(CategoryManager.getInstance().getDefaultCategory()),
				getFixture().getCategories());
	}

	public void test_resolveTargetTypes() {
		getFixture().resolveTargetTypes(new String[] {TEST_NAMESPACE_URI});
		
		assertTrue(getFixture().targetsTypeOf(
						OrderSystemFactory.eINSTANCE.createWarehouse()));
	}

	public void test_targetsTypeOf() {
		getFixture().resolveTargetTypes(new String[] {TEST_NAMESPACE_URI});
		
		assertTrue(getFixture().targetsTypeOf(
						OrderSystemFactory.eINSTANCE.createWarehouse()));
	}

	public void test_targetsEvent() {
		getFixture().resolveTargetTypes(new String[] {TEST_NAMESPACE_URI});
		
		assertTrue(getFixture().targetsEvent(new TestNotification(
						OrderSystemFactory.eINSTANCE.createWarehouse(),
						EMFEventType.getInstance(TEST_EVENT).toNotificationType())));
	}

	public void test_getMessagePattern() {
		assertEquals(TEST_MESSAGE, getFixture().getMessagePattern());
	}

	public void test_getBody() {
		assertEquals(TEST_BODY, getFixture().getBody());
	}

	public void test_equalsObject() {
		assertEquals(getFixture(), getFixture());
	}
}
