/**
 * <copyright>
 *
 * Copyright (c) 2005, 2007 IBM Corporation and others.
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.examples.extlibrary.presentation.EXTLibraryEditor;
import org.eclipse.emf.validation.examples.general.ValidationPlugin;
import org.eclipse.emf.validation.examples.general.constraints.ValidationDelegateClientSelector;
import org.eclipse.emf.validation.examples.general.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.marker.MarkerUtil;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ListDialog;

/**
 * This action delegate calls upon the validation service to provide a batch
 *  validation of the selected EObjects and their children.
 * 
 */
public class BatchValidationDelegate
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

	/**
	 * The InputDialog title
	 */
	private String title = ValidationMessages.BatchValidationDelegate_title;

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
			if (!(i.next() instanceof EObject)) {
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
		ValidationDelegateClientSelector.running = true;
		
		IBatchValidator validator = (IBatchValidator)ModelValidationService.getInstance()
			.newValidator(EvaluationMode.BATCH);
		validator.setIncludeLiveConstraints(true);
		
		final IStatus status = validator.validate(selectedEObjects);
		
		if (status.isOK()) {
			MessageDialog.openInformation(shell, title,
				ValidationMessages.BatchValidationDelegate_successMessage);
			return;
		} else {
			ListDialog dialog = new ListDialog(shell);
			dialog.setInput(status);
			dialog.setTitle(title);
			dialog.setContentProvider(new IStructuredContentProvider() {
				public void dispose() {
					// nothing to dispose
				}

				public Object[] getElements(Object inputElement) {
					if (status != null && status.isMultiStatus() && status == inputElement) {
						return status.getChildren();
					} else if (status != null && status == inputElement) {
						return new Object[] {status};
					}
					return new Object[0];
				}

				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
					// Do nothing.
				}
			});
			dialog.setLabelProvider(new LabelProvider() {
				public String getText(Object element) {
					if (element instanceof IStatus) {
						return ((IStatus)element).getMessage();
					}
					return null;
				}
			});
			dialog.setBlockOnOpen(true);
			dialog.setMessage(ValidationMessages.BatchValidationDelegate_errorMessage);
			
			if (ListDialog.OK == dialog.open()) {
				Set errorSelections = new HashSet();
				if (!status.isMultiStatus()) {
					IConstraintStatus cstatus = (IConstraintStatus)status;
					errorSelections.add(cstatus.getTarget());
				} else {
					IStatus[] children = status.getChildren();
					for (int i = 0; i<children.length; i++) {
						IConstraintStatus cstatus = (IConstraintStatus)children[i];
						errorSelections.add(cstatus.getTarget());
					}
				}
				editor.setSelectionToViewer(errorSelections);
			}
		}
		
		ValidationDelegateClientSelector.running = false;
		
		// Create problem markers on the resources with validation failures/warnings.
		try {
			MarkerUtil.createMarkers(status);
		} catch (CoreException e) {
			ValidationPlugin.getDefault().getLog().log(e.getStatus());
		}
	}
}
