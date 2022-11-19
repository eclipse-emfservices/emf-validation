/******************************************************************************
 * Copyright (c) 2009 SAP AG and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    SAP AG - initial API and implementation 
 ****************************************************************************/
package org.eclipse.emf.validation.internal.modeled;

import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.emf.validation.internal.modeled.model.validation.Category;

/**
 * Operation implementations for the validation meta-model.
 * 
 * @author Boris Gruschko
 * @since 1.4
 *
 */
public class ValidationModelOperationsImpl {
	static final String SLASH = "/"; //$NON-NLS-1$

	/**
	 * Computes the path of the given category.
	 * 
	 * @param category category for which the path is to be computed.
	 * 
	 * @return path to the given category
	 */
	public static String categoryGetPath(Category category) {
		StringBuilder builder = new StringBuilder(50);

		LinkedList<Category> pathToTop = new LinkedList<Category>();

		for (Category cat = category; cat != null; cat = cat.getParentCategory())
			pathToTop.addFirst(cat);

		for (Iterator<Category> i = pathToTop.iterator(); i.hasNext();) {
			Category cat = i.next();

			builder.append(cat.getId());

			if (i.hasNext()) {
				builder.append(SLASH);
			}
		}

		return builder.toString();
	}
}
