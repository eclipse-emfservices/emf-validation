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

package org.eclipse.emf.validation.tests;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;


/**
 * Plug-in lifecycle class, required for correct bundle activation.
 *
 * @author Christian W. Damus (cdamus)
 */
public class TestPlugin
	extends Plugin {

    private static LogCapture log;
    
	/**
	 * Initializes me.
	 */
	public TestPlugin() {
		super();
	}
	
	public static LogCapture getLogCapture() {
	    return log;
	}
	
	@Override
    public void start(BundleContext context)
	    throws Exception {
	    
	    super.start(context);
	    
	    log = new LogCapture();
	}
	
	@Override
    public void stop(BundleContext context)
	    throws Exception {
	    
	    log.stop();
	    log = null;
	    
	    super.stop(context);
	}
}
