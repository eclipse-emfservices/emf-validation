/******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/
package org.eclipse.emf.validation.xml;

import org.eclipse.emf.validation.service.AbstractConstraintDescriptor;

/**
 * <p>
 * This class provides an abstract implementation of an XML constraint
 * descriptor that is used to provide descriptions of a particular constraint
 * from an {@link org.eclipse.core.runtime.IConfigurationElement}.
 * </p>
 * <p>
 * This IConfigurationElement is then made available to any third-party that
 * requests direct inspection. The structure of this IConfigurationElement
 * should exactly reflect the constraint subtree of a properly formed
 * constraintProvider extension.
 * </p>
 * <p>
 * <b>See also</b> the constraintProvider extension point.
 * </p>
 * 
 * @author Chris McGee (cmcgee)
 */
public abstract class AbstractXmlConstraintDescriptor extends AbstractConstraintDescriptor
		implements IXmlConstraintDescriptor {

	// No implementation necessary.
}
