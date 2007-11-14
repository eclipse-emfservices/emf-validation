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
 * $Id: MarkerUtilTest.java,v 1.2 2007/11/14 18:03:43 cdamus Exp $
 */

package org.eclipse.emf.validation.marker.tests;

import java.util.Collections;

import junit.framework.Test;
import junit.framework.TestSuite;

import ordersystem.Order;
import ordersystem.OrderSystemFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.validation.marker.MarkerUtil;
import org.eclipse.emf.validation.tests.TestBase;


/**
 * Tests for the {@link MarkerUtil} API.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class MarkerUtilTest
    extends TestBase {

    private IProject testProject;
    private IFile testFile;
    
    private Resource testResource;
    
    public MarkerUtilTest(String name) {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(MarkerUtilTest.class, "Marker Utility Tests"); //$NON-NLS-1$
    }


    public void test_createMarkersInPathWithSpaces_201788() {
        IStatus status = batchValidator.validate(testResource.getContents());

        // assert that there will be markers to create
        assertTrue(status.getSeverity() >= IStatus.WARNING);
        
        try {
            MarkerUtil.createMarkers(status);
        } catch (CoreException e) {
            fail("Failed to create markers: " + e.getLocalizedMessage()); //$NON-NLS-1$
        }
        
        try {
            IMarker[] markers = testFile.findMarkers(MarkerUtil.VALIDATION_MARKER_TYPE,
                true, 0);
            
            assertFalse("No markers created", markers.length == 0); //$NON-NLS-1$
        } catch (CoreException e) {
            fail("Failed to find markers: " + e.getLocalizedMessage()); //$NON-NLS-1$
        }
    }
    
    //
    // Framework methods
    //
    
    @Override
    protected void setUp()
        throws Exception {
        
        super.setUp();
        
        testProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
            "_Validation_Test"); //$NON-NLS-1$
        testProject.create(null);
        testProject.open(null);
        
        String fileName = "dir with spaces/test file.xmi"; //$NON-NLS-1$
        testFile = testProject.getFile(new Path(fileName));
        
        URI uri = URI.createPlatformResourceURI(testFile.getFullPath().toString(),
            true);
        
        ResourceSet rset = new ResourceSetImpl();
        testResource = rset.createResource(uri);
        
        // create an order with an invalid ID
        Order testOrder = OrderSystemFactory.eINSTANCE.createOrder();
        testOrder.setId("id"); //$NON-NLS-1$
        
        // include live constraints in batch validation and no need for successes
        batchValidator.setIncludeLiveConstraints(true);
        batchValidator.setReportSuccesses(false);
        
        testResource.getContents().add(testOrder);
        
        testResource.save(Collections.EMPTY_MAP);
    }
    
    @Override
    protected void tearDown()
        throws Exception {
        
        testResource.unload();
        testResource = null;
        
        testProject.delete(true, true, null);
        
        testFile = null;
        testProject = null;
        
        super.tearDown();
    }
}
