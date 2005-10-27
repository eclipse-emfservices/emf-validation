/******************************************************************************
 * Copyright (c) 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.internal.service;


/**
 * Interface implemented by objects that know how to execute
 * {@link IProviderOperation provider operations}.  Used by the
 * {@link org.eclipse.emf.validation.service.IValidator}
 * implementations in this package to execute operations
 * in the context of the object that created them.
 *
 * @author Christian W. Damus (cdamus)
 */
public interface IProviderOperationExecutor {
	/**
	 * Executes the specified <code>op</code>eration.
	 * 
	 * @param op the operation to execute.  The execution result must be
	 *     obtained from it
	 */
	void execute(IProviderOperation op);
}