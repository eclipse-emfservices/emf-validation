/**
 * Copyright (c) 2003, 2001 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.tests;

import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.AbstractConstraintProvider;
import org.eclipse.emf.validation.util.XmlConfig;

/**
 * <p>
 * Custom constraint provider used for testing bad provider configurations. It
 * fails to initialize if the <tt>&lt;constraints&gt;</tt> element is absent
 * from the XML (which is not a general rule), and all of the provider methods
 * throw {@link RuntimeException}s.
 * </p>
 * <p>
 * Each separately registered instance of this provider can be retrieved by
 * looking up the URI namespace prefix associated with it in the XML.
 * </p>
 *
 * @see #getInstance
 *
 * @author Christian W. Damus (cdamus)
 */
public class TestBadXmlConfigProvider extends AbstractConstraintProvider {
	private static final java.util.Map<String, TestBadXmlConfigProvider> instanceMap = new java.util.HashMap<>();

	/**
	 * Always throws.
	 *
	 * @throws RuntimeException always
	 */
	@Override
	public Collection<IModelConstraint> getLiveConstraints(Notification notification,
			Collection<IModelConstraint> constraints) {
		if (AllTests.isExecutingUnitTests()) {
			// only throw if we are actually executing the unit tests
			throw new RuntimeException("I am supposed to abend."); //$NON-NLS-1$
		}

		// otherwise, just be harmless by delegating to super's no-op impl
		return super.getLiveConstraints(notification, constraints);
	}

	/**
	 * Always throws.
	 *
	 * @throws RuntimeException always
	 */
	@Override
	public Collection<IModelConstraint> getBatchConstraints(EObject eObject, Collection<IModelConstraint> constraints) {
		if (AllTests.isExecutingUnitTests()) {
			// only throw if we are actually executing the unit tests
			throw new RuntimeException("I am supposed to abend."); //$NON-NLS-1$
		}

		// otherwise, just be harmless by delegating to super's no-op impl
		return super.getBatchConstraints(eObject, constraints);
	}

	/**
	 * Throws if the <tt>&lt;constraints&gt;</tt> element is absent.
	 *
	 * @throws CoreException if no <tt>&lt;constraints&gt;</tt> element is found
	 */
	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		super.setInitializationData(config, propertyName, data);

		if (AllTests.isExecutingUnitTests()) {
			// only test this condition (which is sure to fail) if we are
			// actually executing unit tests. Other run-times should not
			// care because I am only intended for unit testing
			if (config.getChildren(XmlConfig.E_CONSTRAINTS).length == 0) {
				throw new CoreException(new Status(IStatus.ERROR, "org.eclipse.emf.validation.tests", //$NON-NLS-1$
						1, "Missing <constraints> element", //$NON-NLS-1$
						null));
			}
		}

		// put this instance into the map
		String[] uris = getNamespaceUris();

		for (String element : uris) {
			registerInstance(element, this);
		}
	}

	/**
	 * Obtains an instance for the specified URI namespace prefix.
	 *
	 * @param uriNsPrefix a URI namespace prefix
	 * @return the corresponding instance
	 */
	public static TestBadXmlConfigProvider getInstance(String uriNsPrefix) {
		return instanceMap.get(uriNsPrefix);
	}

	private static synchronized void registerInstance(String uriNsPrefix, TestBadXmlConfigProvider instance) {
		instanceMap.put(uriNsPrefix, instance);
	}
}
