/******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/
package org.eclipse.emf.validation.internal.service;

import java.util.Collection;

import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IModelConstraintProvider;
import org.eclipse.emf.validation.util.FilteredCollection;

/**
 * <p>
 * Encapsulates a request to get "batch" constraints for a particular validation
 * of an EMF object.
 * </p>
 *
 * @see org.eclipse.emf.validation.service.IModelConstraintProvider
 * @see org.eclipse.emf.validation.service.ModelValidationService
 *
 * @author Christian W. Damus (cdamus)
 */
public class GetBatchConstraintsOperation extends AbstractGetConstraintsOperation {

	private final boolean batchOnly;

	/**
	 * Initializes me with the <CODE>eObject</CODE> for which we are to get the
	 * batch constraints.
	 *
	 * @param batchOnly whether to get only batch mode constraints
	 *                  (<code>true</code>), or also live mode constraints
	 *                  (<code>false</code>)
	 */
	public GetBatchConstraintsOperation(boolean batchOnly) {
		this.batchOnly = batchOnly;
	}

	// implements the inherited method
	@Override
	protected void executeImpl(IModelConstraintProvider provider, Collection<IModelConstraint> constraints) {
		assert provider != null;

		provider.getBatchConstraints(getEObject(), constraints);
	}

	// implements the inherited method
	@Override
	protected AbstractValidationContext createContext() {
		class BatchOnlyFilter implements FilteredCollection.Filter<IModelConstraint> {
			private final FilteredCollection.Filter<IModelConstraint> delegate;

			BatchOnlyFilter(FilteredCollection.Filter<IModelConstraint> delegate) {
				this.delegate = delegate;
			}

			// additionally exclude any live mode constraints
			@Override
			public boolean accept(IModelConstraint element) {
				return element.getDescriptor().getEvaluationMode().isBatchOnly() && delegate.accept(element);
			}
		}

		return new AbstractValidationContext(this) {
			// overrides the inherited method to provide a filter that
			// additionally excludes "live" mode constraints if we are
			// only looking for batch mode
			@Override
			public FilteredCollection.Filter<IModelConstraint> getConstraintFilter() {
				if (!batchOnly) {
					return super.getConstraintFilter();
				} else {
					return new BatchOnlyFilter(super.getConstraintFilter());
				}
			}
		};
	}
}
