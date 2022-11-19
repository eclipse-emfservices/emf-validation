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

/**
 * Interface implemented by objects that know how to execute
 * {@link IProviderOperation provider operations}. Used by the
 * {@link org.eclipse.emf.validation.service.IValidator} implementations in this
 * package to execute operations in the context of the object that created them.
 *
 * @param <T> the result type of the operation that I execute
 *
 * @author Christian W. Damus (cdamus)
 */
public interface IProviderOperationExecutor {
	/**
	 * Executes the specified <code>op</code>eration.
	 * 
	 * @param op the operation to execute. The execution result must be obtained
	 *           from it
	 */
	<T> T execute(IProviderOperation<? extends T> op);
}
