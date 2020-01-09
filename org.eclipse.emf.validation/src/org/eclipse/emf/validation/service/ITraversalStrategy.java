/******************************************************************************
 * Copyright (c) 2004, 2007 IBM Corporation and others.
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
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;


/**
 * <p>
 * A strategy for traversal of one or more model sub-trees in batch validation.
 * The traversal strategy is responsible for providing elements to an
 * {@link IBatchValidator} in whatever order the implementation defines, and
 * also for updating the validation progress monitor periodically.
 * </p>
 * <p>
 * Note that traversal is performed over some number (possibly greater than one)
 * of <i>traversal roots</i>.  The validation system makes no assumption about
 * the relationships between these roots (e.g., containment), so it is up to
 * the strategy implementation to determine whether any optimization can be
 * made in processing these sub-trees (e.g., determining that some roots will be
 * covered by traversal of others).
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 * 
 * @see IBatchValidator#setTraversalStrategy
 */
public interface ITraversalStrategy {
	/**
	 * An implementation of the {@link ITraversalStrategy} interface that is
	 * non-recursive:  it simply iterates the root elements.
	 *
	 * @author Christian W. Damus (cdamus)
	 */
	final class Flat extends AbstractTraversalStrategy {
		/**
		 * Initializes me.
		 */
		public Flat() {
			super();
		}
		
		/* (non-Javadoc)
		 * Implements the inherited method.
		 */
		@Override
        protected int countElements(Collection<? extends EObject> traversalRoots) {
			return traversalRoots.size();
		}

		/* (non-Javadoc)
		 * Implements the inherited method.
		 */
		@Override
        protected Iterator<? extends EObject> createIterator(
                Collection<? extends EObject> traversalRoots) {
			return traversalRoots.iterator();
		}
	}
	
	/**
	 * An implementation of the {@link ITraversalStrategy} interface that is
	 * recursive:  it iterates over the content trees of the roots, according
	 * to the basic EMF {@link EObject#eAllContents()} tree-iterator.  One bit
	 * of value-add that it offers, though, is that it avoids duplication of
	 * sub-trees where one of the traversal roots is actually contained within
	 * another.
	 * <p>
	 * This traversal strategy assumes that only the root elements of the
	 * traversal may be in different client contexts, not any contents of their
	 * sub-trees.
	 * </p>
	 *
	 * @author Christian W. Damus (cdamus)
	 */
	final class Recursive extends AbstractTraversalStrategy {
		private Collection<EObject> roots;
		private boolean contextChanged = true;
		
		/**
		 * Initializes me.
		 */
		public Recursive() {
			super();
		}
		
		@Override
        public void startTraversal(
				Collection<? extends EObject> traversalRoots,
				IProgressMonitor progressMonitor) {
			
			roots = makeTargetsDisjoint(traversalRoots);
			
			super.startTraversal(traversalRoots, progressMonitor);
		}
		
		private Collection<EObject> getRoots() {
			return roots;
		}
		
		/* (non-Javadoc)
		 * Implements the inherited method.
		 */
		@Override
        protected int countElements(Collection<? extends EObject> ignored) {
			return countRecursive(getRoots());
		}
		
		private int countRecursive(Collection<? extends EObject> elements) {
			int result = 0;
			
			result = elements.size();
			
			for (EObject next : elements) {
				result = result + countRecursive(next.eContents());
			}
			
			return result;
		}
		
		/* (non-Javadoc)
		 * Implements the inherited method.
		 */
		@Override
        protected Iterator<? extends EObject> createIterator(
                Collection<? extends EObject> ignored) {
		    
			return new EcoreUtil.ContentTreeIterator<EObject>(getRoots()) {
				private static final long serialVersionUID = -5653134989235663973L;

				@Override
                public Iterator<EObject> getChildren(Object obj) {
					if (obj == getRoots()) {
						return new Iterator<EObject>() {
							private final Iterator<EObject> delegate =
								getRoots().iterator();
							
							public boolean hasNext() {
								return delegate.hasNext();
							}

							public EObject next() {
								// if I'm being asked for my next element, then
								//    we are stepping to another traversal root
								contextChanged = true;
								
								return delegate.next();
							}

							public void remove() {
								delegate.remove();
							}};
					} else {
						return super.getChildren(obj);
					}
				}
				
				@Override
                public EObject next() {
					// this will be set to true again the next time we test hasNext() at
					//    the traversal root level
					contextChanged = false;
					
					return super.next();
				}};
		}
		
		@Override
        public boolean isClientContextChanged() {
			return contextChanged;
		}
		
		private Set<EObject> makeTargetsDisjoint(Collection<? extends EObject> objects) {
			Set<EObject> result = new java.util.HashSet<EObject>();
			
			// ensure that any contained (descendent) elements of other elements
			//    that we include are not included, because they will be
			//    traversed by recursion, anyway
			
	        for (EObject target : objects) {
	            // EcoreUtil uses the InternalEObject interface to check
	            // containment, so we do the same.  Also, we kip up a level to
	            // the immediate container for efficiency:  an object is its
	            // own ancestor, so we can "pre-step" up a level to avoid the
	            // cost of doing it individually for every miss in the collection
	            if (!EcoreUtil.isAncestor(objects,
	                    ((InternalEObject) target).eInternalContainer())) {
	                result.add(target);
	            }
	        }
			
			return result;
		}};
	
	/**
	 * Called at the start of validation to provide the sub-trees that are to
	 * be validated and a progress monitor to track the operation's progress.
	 * The receiver must initialize the <code>monitor</code> by invoking the
	 * {@link IProgressMonitor#beginTask(java.lang.String, int)} method on it.
	 * Periodically, the receiver should also update the progress via the
	 * {@link IProgressMonitor#worked(int)} method.  The validator will not
	 * make any attempt to update the progress, except that it will call
	 * {@link IProgressMonitor#done()} when validation is complete (the
	 * traversal strategy should not call <code>done()</code>).
	 * 
	 * @param traversalRoots a collection of one or more {@link EObject}s.
	 *     It is never empty, but neither is it modifiable
	 * @param monitor the progress monitor used to track progress.  The receiver
	 *     may retain this progress monitor for the duration of the traversal
	 */
	void startTraversal(Collection<? extends EObject> traversalRoots,
	        IProgressMonitor monitor);
	
	/**
	 * Queries whether there is another element to be validated.
	 * 
	 * @return <code>true</code> if another element can be obtained from the
	 *     {@link #next()} method; <code>false</code>, otherwise
	 */
	boolean hasNext();
	
	/**
	 * Obtains the next element in the traversal.  Should throw if
	 * {@link #hasNext()} would return <code>false</code>.
	 * 
	 * @return the next element in the traversal
	 * 
	 * @throws java.util.NoSuchElementException if there are no more elements
	 *     in the traversal
	 */
	EObject next();
	
	/**
	 * Queries whether the next element in the traversal will
	 * <em>potentially</em> be in a different
	 * {@linkplain org.eclipse.emf.validation.internal.service.IClientContext client context}
	 * than the one before.  The validation framework will then re-compute the
	 * current client context.  If there is no next element, then this method's
	 * return value is undefined.
	 * <p>
	 * At a minimum, every traversal root passed into the
	 * {@link #startTraversal(Collection, IProgressMonitor)} method should be
	 * considered as a new client context. 
	 * </p>
	 * <p>
	 * A pessimistic implementation of this method may always return
	 * <code>true</code>.  This results in absolutely correct validation, but
	 * may hamper performance where assumptions can be made about the
	 * delineation of client contexts.
	 * </p>
	 * <p>
	 * A good rule-of-thumb for determining when the client context may change
	 * is as follows:
	 * <ul>
	 *   <li>every traversal root defines a client context (see above)</li>
	 *   <li>any object traversed by a non-containment reference may change
	 *       the client context</li>
	 *   <li>any object traversed by a containment relationship does not
	 *       change the client context (it is in the same sub-tree), unless
	 *       that element is an {@link org.eclipse.emf.ecore.EAnnotation}</li>
	 *   <li><code>EAnnotation</code>s are commonly used to bridge between
	 *       metamodels.  It is probably a good idea to return <code>true</code>
	 *       for annotations and their contents (as no assumption about the
	 *       nature of the metamodel and its client applications can be made)</li>
	 * </ul>
	 * Individual traversal strategies may be more or less optimistic than this
	 * rough guide, especially when they are contributed in tandem with
	 * client contexts by the same client application.
	 * </p>
	 * 
	 * @return <code>true</code> if the next object to be returned by the
	 *    {@link #next()} method is potentially in a different client context
	 *    than the previous (thereby requiring the validation framework to
	 *    recompute the context); <code>false</code> if the object can be
	 *    assumed to be in the same context as the previous
	 */
	boolean isClientContextChanged();
	
	/**
	 * Called by the validation system to indicate that another element has
	 * been validated.  The receiver is encouraged to take this opportunity to
	 * update the progress monitor (the number of work units may vary each
	 * time).
	 * 
	 * @param element the element that was validated
	 * @param status the <code>element</code>'s validation status, indicating
	 *     success or failure of its constraints.  The receiver may with use
	 *     this information to exclude or include portions of the model based
	 *     on the status of the <code>element</code>
	 */
	void elementValidated(EObject element, IStatus status);
}
