/**
 * Copyright (c) 2006, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.examples.internal.wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.validation.examples.internal.ValidationExamplesPlugin;

public class GeneralExampleWizard extends AbstractExampleWizard {

	@Override
	protected Collection<ProjectDescriptor> getProjectDescriptors() {
		// We need the general example to be unzipped along with the
		// EMF library example model, edit and editor examples
		List<ProjectDescriptor> projects = new ArrayList<>(4);
		projects.add(new ProjectDescriptor("org.eclipse.emf.validation.examples", "zips/library.zip", //$NON-NLS-1$//$NON-NLS-2$
				"org.eclipse.emf.examples.library")); //$NON-NLS-1$
		projects.add(new ProjectDescriptor("org.eclipse.emf.validation.examples", "zips/libraryEdit.zip", //$NON-NLS-1$ //$NON-NLS-2$
				"org.eclipse.emf.examples.library.edit")); //$NON-NLS-1$
		projects.add(new ProjectDescriptor("org.eclipse.emf.validation.examples", "zips/libraryEditor.zip", //$NON-NLS-1$//$NON-NLS-2$
				"org.eclipse.emf.examples.library.editor")); //$NON-NLS-1$
		projects.add(new ProjectDescriptor("org.eclipse.emf.validation.examples", "zips/general.zip", //$NON-NLS-1$ //$NON-NLS-2$
				"org.eclipse.emf.validation.examples.general")); //$NON-NLS-1$

		return projects;
	}

	@Override
	protected void log(Exception e) {
		if (e instanceof CoreException) {
			ValidationExamplesPlugin.getDefault().getLog().log(((CoreException) e).getStatus());
		} else {
			ValidationExamplesPlugin.getDefault().getLog()
					.log(new Status(IStatus.ERROR, ValidationExamplesPlugin.getDefault().getBundle().getSymbolicName(),
							IStatus.ERROR, e.getMessage(), e));
		}
	}
}
