/******************************************************************************
 * Copyright (c) 2004, 2006 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.emf.validation.internal.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.osgi.util.NLS;

/**
 * Utilities for working with text in EMF models. So far, the following
 * capabilities are available:
 * <ul>
 * <li>obtaining text representations of model elements for use in the UI (see
 * {@link #getText(EObject)})</li>
 * <li>formatting error messages (see
 * {@link #formatMessage(String, Object[])})</li>
 * </ul>
 *
 * @author Christian W. Damus (cdamus)
 */
public class TextUtils {
	// back-up for unregistered packages
	private static AdapterFactory defaultFactory = new ReflectiveItemProviderAdapterFactory();

	private static AdapterFactory factory = new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

	/**
	 * Not instantiable by clients.
	 */
	private TextUtils() {
		super();
	}

	/**
	 * Obtains a textual representation of the specified model element, as for
	 * display in error messages. If no suitable factory is registered, then the EMF
	 * reflective item provider is used.
	 * 
	 * @param eObject the model element for which to get text
	 * @return the corresponding text
	 */
	public static String getText(EObject eObject) {
		IItemLabelProvider provider = (IItemLabelProvider) factory.adapt(eObject, IItemLabelProvider.class);

		if (provider == null) {
			// for backward compatibility, try looking in the resource set
			provider = (IItemLabelProvider) getRegisteredAdapter(eObject, IItemLabelProvider.class);
		}

		if (provider == null) {
			provider = (IItemLabelProvider) defaultFactory.adapt(eObject, IItemLabelProvider.class);
		}

		String result = provider.getText(eObject);

		if (result != null) {
			// don't want leading or trailing blanks in messages
			result = result.trim();
		}

		return result;
	}

	/**
	 * Similar to the {@link EcoreUtil#getRegisteredAdapter(EObject, Object)}
	 * method, attempts to adapt the given <code>eObject</code> to the specified
	 * <code>type</code> using adapter factories registered on its resource set. The
	 * difference is, that this method anticipates that adapter factories from
	 * multiple disjoint metamodels may be registered, that adapt different kinds of
	 * objects to the same types. This method will try them all until it either gets
	 * a successful adaptation or runs out of factories.
	 * 
	 * @param eObject the model element to adapt
	 * @param type    indicates the type of adapter to obtain
	 * @return the available registered adapter, or <code>null</code> if no suitable
	 *         adapter factory is found
	 */
	private static Object getRegisteredAdapter(EObject eObject, Object type) {
		Object result = EcoreUtil.getExistingAdapter(eObject, type);

		if (result == null) {
			Resource resource = eObject.eResource();

			if (resource != null) {
				ResourceSet resourceSet = resource.getResourceSet();

				if (resourceSet != null) {
					List<AdapterFactory> factories = resourceSet.getAdapterFactories();

					// iterate only as long as we don't find an adapter factory
					// that successfully adapted the eObject
					for (Iterator<AdapterFactory> iter = factories.iterator(); iter.hasNext() && (result == null);) {

						AdapterFactory next = iter.next();

						if (next.isFactoryForType(type)) {
							result = next.adapt(eObject, type);
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * Applies the specified arguments to my message pattern.
	 * 
	 * @param messagePattern the message pattern
	 * @param inputArg       the pattern arguments
	 * @return the formatted message string
	 * 
	 * @since 1.1
	 */
	public static String formatMessage(String messagePattern, Object... inputArg) {
		Object[] args = new Object[inputArg.length];

		for (int i = 0; i < args.length; i++) {
			Object next = inputArg[i];

			if (next instanceof Collection) {
				next = formatMultiValue((Collection<?>) next);
			} else if (next instanceof Object[]) {
				next = formatMultiValue(Arrays.asList((Object[]) next));
			} else {
				next = formatScalarValue(next);
			}

			args[i] = next;
		}

		return NLS.bind(messagePattern, args);
	}

	/**
	 * Helper method which converts multiple objects into a list as prescribed by
	 * locale-specific conventions.
	 * 
	 * @param multiValuedArg the multiple objects
	 * @return the string representation of the list
	 */
	private static String formatMultiValue(Collection<?> multiValuedArg) {
		List<Object> args = new java.util.ArrayList<Object>(multiValuedArg);

		for (ListIterator<Object> iter = args.listIterator(); iter.hasNext();) {
			iter.set(formatScalarValue(iter.next()));
		}

		return EMFModelValidationPlugin.formatList(args);
	}

	/**
	 * Helper method which converts a single object to a string. Model objects are
	 * represented by their names, other objects by their default string
	 * representation.
	 * 
	 * @param value the object to convert
	 * @return the string conversion
	 */
	private static String formatScalarValue(Object value) {
		if (value instanceof EObject) {
			return TextUtils.getText((EObject) value);
		} else {
			return String.valueOf(value);
		}
	}
}
