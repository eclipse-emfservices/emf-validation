/******************************************************************************
 * Copyright (c) 2004, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.ui.internal.preferences;

import org.eclipse.emf.validation.ui.internal.ValidationUIPlugin;
import org.eclipse.emf.validation.ui.internal.l10n.ValidationUIMessages;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The preference page for Modeler validation controls.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ValidationPreferencePage 
		extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	
	private Composite outerComposite;
	private RadioGroupFieldEditor liveProblemDisplayField;
	
	private Composite innerComposite1, innerComposite2;
	private BooleanFieldEditor warningsInDialog;
	private BooleanFieldEditor showOutputView;
	
	/**
	 * Initializes me.
	 */
	public ValidationPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
	}

	/**
     * The field editors for this preference page are inserted in this method.
     */
    @Override
    protected void createFieldEditors() {
        final Composite parent = getFieldEditorParent();
        Composite panel = new Composite(parent, SWT.NONE);
        panel.setFont(parent.getFont());
        GridLayout panelLayout = new GridLayout();
        panelLayout.numColumns = 1;
        panel.setLayout(panelLayout);
        
		GridData blockData = new GridData();
		blockData.grabExcessHorizontalSpace = true;
		blockData.horizontalAlignment = GridData.FILL;
		blockData.horizontalSpan = 1;
		panel.setLayoutData(blockData);

		// create groups
		createLiveValidationProblemsGroup(panel);
		
		applyDialogFont(panel);
    }
    
    
	/**
	 * Extends the inherited method to set up the enablement of the checkboxes
	 * according to the current preference settings.
	 */
	@Override
    protected void initialize() {
		super.initialize();
		
        // initialize the enablement of the checkboxes after the current
		//   preferences have been applied to the controls
        setCheckboxesEnablement(ValidationUIPlugin.getPlugin().getPreferenceStore().getString(IPreferenceConstants.VALIDATION_LIVE_PROBLEMS_DISPLAY));
    }

    /**
     * Create the "Live validation problems" group of the preference page.
     * 
     * @param parent the parent composite
     */
    private void createLiveValidationProblemsGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(ValidationUIMessages.Validation_liveValidationGroupLabel);
        GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        group.setLayout(layout);
        
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.grabExcessHorizontalSpace = true;
        group.setLayoutData(data);
		
        outerComposite = new Composite(group, SWT.NONE);
        liveProblemDisplayField = new RadioGroupFieldEditor(
        	IPreferenceConstants.VALIDATION_LIVE_PROBLEMS_DISPLAY,
        	ValidationUIMessages.Validation_liveValidationDestinationPrompt,
        	1,
        	new String[][] {{ValidationUIMessages.Validation_liveValidationDestination_dialogComboItem, ValidationLiveProblemsDestination.DIALOG.getName()},
        		{ValidationUIMessages.Validation_liveValidationDestination_consoleComboItem, ValidationLiveProblemsDestination.CONSOLE.getName()}},
			outerComposite) {
        	
        	@Override
            protected void fireValueChanged(String property, Object oldValue, Object newValue) {
        		super.fireValueChanged(property, oldValue, newValue);
        		setCheckboxesEnablement((String)newValue);
        	}
        };
        
        addField(liveProblemDisplayField);

        innerComposite1 = new Composite(group, SWT.NONE);
        warningsInDialog =
            new BooleanFieldEditor(
                IPreferenceConstants.VALIDATION_LIVE_WARNINGS_IN_DIALOG,
                ValidationUIMessages.Validation_liveValidationWarnDialogPrompt,
                innerComposite1) {
        	
        	@Override
            protected void fireStateChanged(String property, boolean oldValue, boolean newValue) {
        		setCheckboxesEnablement(null);
        	}
        };
        
        addField(warningsInDialog);

        innerComposite2 = new Composite(group, SWT.NONE);
        showOutputView =
            new BooleanFieldEditor(
                IPreferenceConstants.VALIDATION_LIVE_SHOW_CONSOLE,
                ValidationUIMessages.Validation_liveValidationShowConsolePrompt,
                innerComposite2);
        addField(showOutputView);
	}
    
    /**
     * Sets the enablement of the "Show warnings in dialog ..." and
     * "Show Output view ..." checkboxes according to
     * whether the Output view is the destination of live validation problems.
     */
    void setCheckboxesEnablement(String newSelection) {
    	if (newSelection != null) {
	    	warningsInDialog.setEnabled(
	    		ValidationLiveProblemsDestination.DIALOG.getName().equals(newSelection),
	    		innerComposite1);
	    	
	    	showOutputView.setEnabled(
	    		ValidationLiveProblemsDestination.CONSOLE.getName().equals(newSelection)
	    		|| !warningsInDialog.getBooleanValue(), 
	    		innerComposite2);
	    	
    	} else {
    		showOutputView.setEnabled(!warningsInDialog.getBooleanValue(), innerComposite2);
    	}
    }

    /**
     * This method must be implemented to obtain the correct
     * location of the preference store, as it is called by
     * getPreferenceStore().
     * 
     * @return IPreferenceStore the returned preference store
     */
    @Override
    protected IPreferenceStore doGetPreferenceStore() {
        return ValidationUIPlugin.getPlugin().getPreferenceStore();
    }

	/**
     * When the user clicks OK, save the values in the field
     * editors by calling storeValues() and also in the preference
     * store.
     * This is the same as what is done in apply.
     * @return true since the ok function completed successfully.
     * If the saving does not complete successfully, for this
     * preference page, it was because of something the user
     * cannot fix, so return true anyway otherwise the page
     * will not close and becomes annoying.
     * <P>Looking at the samples, I don't think they expect this
     * to be false.
     */
    @Override
    public boolean performOk() {
        super.performOk();
        
        ValidationUIPlugin.getPlugin().savePluginPreferences();

        return true;
    }

	/* (non-Javadoc)
	 * Redefines/Implements/Extends the inherited method.
	 */
	public void init(IWorkbench workbench) {
		// Nothing to do in this implementation
	}
}
