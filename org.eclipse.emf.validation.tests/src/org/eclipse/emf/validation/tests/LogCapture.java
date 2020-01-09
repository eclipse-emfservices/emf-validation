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
package org.eclipse.emf.validation.tests;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * A log listener that captures the last entry (if any) logged by a specified
 * bundle during an interval.
 *
 * @author Christian W. Damus (cdamus)
 */
public class LogCapture {
	private final Bundle targetBundle;
	private final ILogListener listener = new ILogListener() {
		public void logging(IStatus status, String plugin) {
			if (status.getPlugin().equals(targetBundle.getSymbolicName())) {
				record(status);
			}
		}};
	
	private final List<IStatus> logs = new java.util.ArrayList<IStatus>();
	private IStatus lastLog;
    
    /**
     * Initializes me to capture logs from the EMF Validation Core bundle.
     */
    public LogCapture() {
        this(Platform.getBundle("org.eclipse.emf.validation")); //$NON-NLS-1$
    }
	
	/**
	 * Initializes me to capture logs from the specified bundle.
	 * 
	 * @param targetBundle the bundle to listen to
	 */
	public LogCapture(Bundle targetBundle) {
		this.targetBundle = targetBundle;
		
		Platform.addLogListener(listener);
	}
	
	/**
	 * Stops me, detaching my log listener from the platform.
	 */
	public void stop() {
		Platform.removeLogListener(listener);
	}
	
	/**
	 * Gets the last log, if any, from my target bundle.
	 * 
	 * @return the last log, or <code>null</code> if none
	 */
	public IStatus getLastLog() {
		return lastLog;
	}
	
	/**
	 * Obtains the list of logs from my target bundle.
	 * 
	 * @return a list (possibly empty) of {@link IStatus}es
	 */
	public List<IStatus> getLogs() {
		return logs;
	}
	
	/**
	 * Obtains the list of log entries from my target bundle that bear the
	 * specified status code.
	 * 
	 * @param statusCode the status code to filter for
	 * 
	 * @return the matching log entries
	 */
	public List<IStatus> getLogs(int statusCode) {
	    List<IStatus> result = new java.util.ArrayList<IStatus>();
	    
	    for (IStatus next : logs) {
	        if (next.getCode() == statusCode) {
	            result.add(next);
	        }
	    }
	    
	    return result;
	}
	
	/**
	 * Asserts that I captured a status that logged the specified throwable.
	 * 
	 * @param throwable a throwable that should have been logged
	 */
	public void assertLogged(Throwable throwable) {
        IStatus log = getLastLog();
        Assert.assertNotNull(log);
        log = findStatus(log, throwable);
        Assert.assertNotNull(log);
	}
	
	private void record(IStatus log) {
		logs.add(log);
		lastLog = log;
	}
	
	/**
	 * Finds the status in a (potentially multi-) status that carries the
	 * specified exception.
	 * 
	 * @param status a status
	 * @param exception a throwable to look for
	 * 
	 * @return the matching status, or <code>null</code> if not found
	 */
	private IStatus findStatus(IStatus status, Throwable exception) {
		IStatus result = (status.getException() == exception)? status : null;

		if (status.isMultiStatus()) {
			IStatus[] children = status.getChildren();
			
			for (int i = 0; (result == null) && (i < children.length); i++) {
				result = findStatus(children[i], exception);
			}
		}
		
		return result;
	}
}
