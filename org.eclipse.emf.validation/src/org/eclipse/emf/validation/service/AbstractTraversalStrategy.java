/******************************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.emf.validation.service;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;

/**
 * An iterator-based partial implementation of the {@link ITraversalStrategy}
 * interface. Subclasses need only compute how many elements are covered by the
 * set of traversal roots, and an iterator that provides them all.
 *
 * @author Christian W. Damus (cdamus)
 */
public abstract class AbstractTraversalStrategy implements ITraversalStrategy {
	/** Message for the "Validating ..." progress task. */
	private static final String VALIDATING_TASK = ValidationMessages.progress_task_validating;

	/**
	 * Factor by which to scale progress monitoring steps in order that recursion
	 * through sub-progrss-monitors results in visible progress (i.e., the progress
	 * doesn't go to 100% in one step) and a decent response time to a user cancel
	 * request.
	 */
	private static final int PROGRESS_EXPANSION_COEFFICIENT = 8;

	/** Don't allow client's progress to count more steps than this number. */
	private static final int MAX_PROGRESS_TICKS = 2048;

	private Iterator<? extends EObject> iterator;
	private IProgressMonitor monitor;

	/**
	 * Initializes me.
	 */
	public AbstractTraversalStrategy() {
		super();
	}

	/**
	 * Initializes the traversal by asking the subclass to count the number of
	 * elements that will be validated and to create an iterator that will provide
	 * them all. The progress monitor is initialized with a work unit per element
	 * (scaled to a reasonable maximum to avoid too many GUI updates).
	 */
	public void startTraversal(Collection<? extends EObject> traversalRoots, IProgressMonitor progressMonitor) {

		int taskSize = countElements(traversalRoots);
		int scaledSize = taskSize;

		while (scaledSize > MAX_PROGRESS_TICKS) {
			// scale the size back to a reasonable number
			scaledSize = scaledSize / PROGRESS_EXPANSION_COEFFICIENT;
		}

		progressMonitor.beginTask(getTaskLabel(), scaledSize);

		if (scaledSize == taskSize) {
			// no need for a sub-progress monitor
			monitor = progressMonitor;
		} else {
			monitor = new SubProgressMonitor(progressMonitor, scaledSize, // covers the whole progress
					SubProgressMonitor.SUPPRESS_SUBTASK_LABEL);

			monitor.beginTask("", //$NON-NLS-1$
					taskSize); // recursion counts one element at a time
		}

		iterator = createIterator(traversalRoots);
	}

	/**
	 * Can be redefined by subclasses to provide the task label for the progress
	 * monitor.
	 * 
	 * @return the task label
	 */
	protected String getTaskLabel() {
		return AbstractTraversalStrategy.VALIDATING_TASK;
	}

	/**
	 * Implemented by subclasses to compute the number of elements that will be
	 * validated within the scope of the specified root elements.
	 * 
	 * @param traversalRoots the roots of the traversal sub-trees
	 * @return the total number of elements to be validated within these sub-trees
	 */
	protected abstract int countElements(Collection<? extends EObject> traversalRoots);

	/**
	 * Implemented by subclasses to return an iterator that provides all of the
	 * elements to be validated, within the scope of the specified root elements.
	 * 
	 * @param traversalRoots the roots of the traversal sub-trees
	 * @return an iterator that covers all of the elements to be validated
	 */
	protected abstract Iterator<? extends EObject> createIterator(Collection<? extends EObject> traversalRoots);

	/**
	 * Provides subclasses with access to the progress monitor, if they want it (for
	 * example, to set sub-task labels)
	 * 
	 * @return the progress monitor
	 */
	protected IProgressMonitor getProgressMonitor() {
		return monitor;
	}

	/**
	 * Just determines whether the subclass-provided iterator has a next element.
	 * 
	 * @see #createIterator
	 */
	public boolean hasNext() {
		return iterator.hasNext();
	}

	/**
	 * Returns the subclass-provided iterator's next element.
	 * 
	 * @see #createIterator
	 */
	public EObject next() {
		return iterator.next();
	}

	/**
	 * This implementation is pessimistic, always returning <code>true</code> to
	 * indicate that the client context should be recomputed for each object
	 * traversed.
	 */
	public boolean isClientContextChanged() {
		return true;
	}

	/**
	 * Implements the interface method by advancing the progress monitor by a single
	 * work unit.
	 */
	public void elementValidated(EObject element, IStatus status) {
		getProgressMonitor().worked(1);
	}
}
