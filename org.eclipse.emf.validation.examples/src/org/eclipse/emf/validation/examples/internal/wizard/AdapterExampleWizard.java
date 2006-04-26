package org.eclipse.emf.validation.examples.internal.wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class AdapterExampleWizard
	extends AbstractExampleWizard {
	
	protected Collection getProjectDescriptors() {
		// We need the adapter example to be unzipped along with the
		// EMF library example model, edit and editor examples
		List projects = new ArrayList(4);
		projects.add(new ProjectDescriptor("org.eclipse.emf.validation.examples", "zips/adapter.zip", "org.eclipse.emf.validation.examples.adapter")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		projects.add(new ProjectDescriptor("org.eclipse.emf.validation.examples", "zips/library.zip", "org.eclipse.emf.examples.library"));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
		projects.add(new ProjectDescriptor("org.eclipse.emf.validation.examples", "zips/libraryEdit.zip", "org.eclipse.emf.examples.library.edit")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		projects.add(new ProjectDescriptor("org.eclipse.emf.validation.examples", "zips/libraryEditor.zip", "org.eclipse.emf.examples.library.editor"));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
		
		return projects;
	}
}
