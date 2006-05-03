/**
 * <copyright>
 *
 * Copyright (c) 2003-2005 IBM Corporation and others.
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

import java.util.Arrays;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.core.runtime.IPlatformRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * Test suite encapsulating all of the JUnit tests in this test plug-in.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class AllTests extends TestCase implements IPlatformRunnable {
	public static boolean executingUnitTests = false;
	
	static {
		// register the .ordersystem extension for loading the example XMI file
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map m = reg.getExtensionToFactoryMap();
		m.put("ordersystem", new XMIResourceFactoryImpl()); //$NON-NLS-1$
	}
	
	/**
	 * Queries whether this run-time session is executing JUnit tests.  This is
	 * important for those extension points that should only be active during
	 * unit test execution, to not interfere with the normal operation of the
	 * application when this plug-in happens to be in the workbench run-time
	 * configuration.
	 *   
	 * @return whether this plug-in is executing unit tests
	 */
	public static boolean isExecutingUnitTests() {
		return executingUnitTests;
	}
	
	/**
	 * Creates the test suite.
	 * 
	 * @return the test suite
	 */
	public static Test suite() {
		// Show validation problems on the console.
		Preferences mslui = Platform.getPreferencesService().getRootNode()
			.node("instance").node("org.eclipse.gmf.runtime.emf.ui"); //$NON-NLS-1$ //$NON-NLS-2$
		mslui.putInt("Validation.liveProblemsDisplay", 1); //$NON-NLS-1$
		try {
			mslui.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}

		// this method is called if and only if unit tests are being executed
		TestSuite suite = new TestSuite(
				"Test for org.eclipse.emf.validation plug-in") { //$NON-NLS-1$
			public void run(TestResult result) {
				// ensure that the tests know that they are running
				executingUnitTests = true;
				
				super.run(result);
				
				executingUnitTests = false;
			}};

		
		suite.addTest(new org.eclipse.emf.validation.internal.model.tests.AllTests());
		suite.addTest(new org.eclipse.emf.validation.internal.util.tests.AllTests());
		suite.addTest(new org.eclipse.emf.validation.internal.service.impl.tests.AllTests());
		suite.addTest(new org.eclipse.emf.validation.internal.xml.tests.AllTests());
		suite.addTest(new org.eclipse.emf.validation.internal.service.tests.AllTests());
		
		suite.addTestSuite(FrameworkTest.class);

		return suite;
	}

	/**
	 * Implements the interface method to execute the suite.
	 */
	public Object run(Object args) throws Exception {
		TestRunner.run(suite());

		return Arrays.asList(new String[]{
			"Please see raw test suite output for details."}); //$NON-NLS-1$
	}
}
