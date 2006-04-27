package org.eclipse.emf.validation.examples.internal.wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.validation.examples.internal.ValidationExamplesPlugin;


public class GeneralExampleWizard
	extends AbstractExampleWizard {
	
	protected Collection getProjectDescriptors() {
		// We need the general example to be unzipped along with the
		// EMF library example model, edit and editor examples
		List projects = new ArrayList(4);
		projects.add(new ProjectDescriptor("org.eclipse.emf.validation.examples", "zips/library.zip", "org.eclipse.emf.examples.library"));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
		projects.add(new ProjectDescriptor("org.eclipse.emf.validation.examples", "zips/libraryEdit.zip", "org.eclipse.emf.examples.library.edit")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		projects.add(new ProjectDescriptor("org.eclipse.emf.validation.examples", "zips/libraryEditor.zip", "org.eclipse.emf.examples.library.editor"));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
		projects.add(new ProjectDescriptor("org.eclipse.emf.validation.examples", "zips/general.zip", "org.eclipse.emf.validation.examples.general")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		
		return projects;
	}
	
	protected void log(Exception e) {
		if (e instanceof CoreException) {
			ValidationExamplesPlugin.getDefault().getLog().log(((CoreException)e).getStatus());
		} else {
			ValidationExamplesPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, ValidationExamplesPlugin.getDefault().getBundle().getSymbolicName(),IStatus.ERROR, e.getMessage(),e));
		}
	}
}
