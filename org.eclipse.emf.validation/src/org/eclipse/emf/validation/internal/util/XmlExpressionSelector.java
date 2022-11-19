/******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
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

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.validation.model.IClientSelector;

/**
 * Implementation of a client context selector that is defined by the context
 * extension in the XML, using the Expressions language.
 *
 * @author Christian W. Damus
 */
public class XmlExpressionSelector implements IClientSelector {

	private final Expression expression;

	/**
	 * Initializes me with the <code>&lt;enablement&gt;</code> element from the
	 * extension.
	 *
	 * @param enablement the enablement element containing an expression
	 *
	 * @throws CoreException if the selector expression is invalid and I cannot,
	 *                       therefore, be initialized
	 */
	public XmlExpressionSelector(IConfigurationElement enablement) throws CoreException {
		expression = ExpressionConverter.getDefault().perform(enablement);
	}

	/**
	 * The argument to the XML Expression Selector is an {@link EvaluationContext}
	 * that has an {@link org.eclipse.emf.ecore.EObject} as the default variable.
	 *
	 * @param object an {@link EvaluationContext} on an
	 *               {@link org.eclipse.emf.ecore.EObject}
	 */
	@Override
	public boolean selects(Object object) {
		boolean result = false;

		EvaluationContext ctx = (EvaluationContext) object;

		try {
			EvaluationResult res = expression.evaluate(ctx);
			result = res.equals(EvaluationResult.TRUE);
		} catch (CoreException e) {
			Trace.catching(getClass(), "selects", e); //$NON-NLS-1$

			// re-throw the exception so that this selector and, therefore,
			// its client context will be invalidated
			RuntimeException re = new RuntimeException(e);

			Trace.throwing(getClass(), "selects", re); //$NON-NLS-1$
			throw re;
		}

		return result;
	}
}
