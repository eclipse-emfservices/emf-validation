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

package org.eclipse.emf.validation.examples.general.actions;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.examples.extlibrary.presentation.EXTLibraryEditor;
import org.eclipse.emf.validation.examples.general.internal.l10n.ValidationMessages;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

/**
 * This action delegate calls upon the validation service to provide a batch
 *  validation of the selected EObjects and their children.
 * 
 */
public class EnableLiveValidationDelegate
	implements IEditorActionDelegate, IActionDelegate2 {

	/**
	 * Error message to display when an exception occured
	 */
	protected static final String MESSAGE_EXCEPTION = ValidationMessages.message_exception;

	/**
	 * The shell this action is hosted in
	 */
	protected Shell shell = null;

	/**
	 * The active editor
	 */
	protected EXTLibraryEditor editor = null;

	/**
	 * Selected EObjects
	 */
	protected Collection selectedEObjects = null;

	String title = ValidationMessages.EnableLiveValidationDelegate_title;


	/*
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, final ISelection selection) {
		this.selectedEObjects = null;
		try {
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				this.selectedEObjects = structuredSelection.toList();
			}
		} catch (Exception e) {
			// Exceptions are not expected
			MessageDialog.openInformation(shell, title, MESSAGE_EXCEPTION);
			throw new RuntimeException(e);
		} finally {
			action.setEnabled((null != selectedEObjects));
		}
		
		for (Iterator i = selectedEObjects.iterator(); i.hasNext();) {
			Object o = i.next();
			if (!(o instanceof Resource)) {
				action.setEnabled(false);
			} else if (resourceHasAdapter((Resource)o)) {
				action.setEnabled(false);
			}
		}
	}

	/*
	 * @see org.eclipse.ui.IActionDelegate2#dispose()
	 */
	public void dispose() {
		//No-op
	}

	/*
	 * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.editor = (EXTLibraryEditor) targetEditor;
		if ( targetEditor != null ) {
			this.shell = targetEditor.getSite().getShell();
		}
	}

	/*
	 * @see org.eclipse.ui.IActionDelegate2#init(org.eclipse.jface.action.IAction)
	 */
	public void init(IAction action) {
		// No-op
	}

	/*
	 * @see org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action.IAction,
	 *      org.eclipse.swt.widgets.Event)
	 */
	public void runWithEvent(IAction action, Event event) {
		run(action);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		for (Iterator i = selectedEObjects.iterator(); i.hasNext();) {
			Resource r = (Resource)i.next();
			
			if (!resourceHasAdapter(r)) {
				EContentAdapter liveValidationContentAdapter = new LiveValidationContentAdapter(this);
				r.eAdapters().add(liveValidationContentAdapter);
			}
		}
	}
	
	private boolean resourceHasAdapter(Resource r) {
		Collection adapters = r.eAdapters();
		for (Iterator j = adapters.iterator(); j.hasNext();) {
			Object o = j.next();
			if (o instanceof LiveValidationContentAdapter) {
				return true;
			}
		}
		
		return false;
	}
}