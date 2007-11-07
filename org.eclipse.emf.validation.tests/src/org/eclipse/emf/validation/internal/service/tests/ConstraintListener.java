/**
 * <copyright>
 *
 * Copyright (c) 2007 IBM Corporation and others.
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
 * $Id: ConstraintListener.java,v 1.2 2007/11/07 17:30:08 cdamus Exp $
 */
package org.eclipse.emf.validation.internal.service.tests;

import org.eclipse.emf.validation.service.ConstraintChangeEvent;
import org.eclipse.emf.validation.service.IConstraintListener;

public class ConstraintListener implements IConstraintListener {

    private int eventCount = 0;
	private ConstraintChangeEvent lastEvent = null;
	private boolean enabled = false;
	
	private static ConstraintListener instance;
	
	private ConstraintListener() {
		super();
	}
	
	public static ConstraintListener getInstance() {
		if (instance == null) {
			instance = new ConstraintListener();
		}
		return instance;
	}
	
	public void constraintChanged(ConstraintChangeEvent event) {
		if (isEnabled()) {
			this.lastEvent = event;
			eventCount++;
		}
	}
	
	public ConstraintChangeEvent getLastEvent() {
		return this.lastEvent;
	}
	
	public int getEventCount() {
	    return eventCount;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setLastEvent(ConstraintChangeEvent lastEvent) {
		this.lastEvent = lastEvent;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void reset() {
	    lastEvent = null;
	    eventCount = 0;
	}
	
}
