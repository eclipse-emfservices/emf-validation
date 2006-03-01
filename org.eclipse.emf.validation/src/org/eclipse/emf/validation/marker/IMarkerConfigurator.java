/**
 * <copyright>
 *
 * Copyright (c) 2005 IBM Corporation and others.
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

package org.eclipse.emf.validation.marker;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.validation.model.IConstraintStatus;

/**
 * A special configurator that will populate a marker that is a subtype of
 *  validationProblem with subtype specific attributes.
 *  
 *  @see IMarker
 *  @see IConstraintStatus
 *  
 *  @author cmcgee
 */
public interface IMarkerConfigurator {
	/**
	 * Appends to the marker configuration with marker subtype specific
	 *  information. The marker will already have been populated with
	 *  validationProblem information by the time that this call is made.
	 *  
	 * @param marker A validationProblem subType marker.
	 * 
	 * @param status A validation constraint status of a validation warning
	 *  or failure.
	 * @throws CoreException A core exception is thrown if there were any problems 
	 *  working with the marker.
	 */
	void appendMarkerConfiguration(IMarker marker, IConstraintStatus status) throws CoreException;
}
