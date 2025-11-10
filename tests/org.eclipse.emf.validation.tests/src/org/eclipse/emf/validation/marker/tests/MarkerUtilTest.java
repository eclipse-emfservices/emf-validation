/**
 * Copyright (c) 2007, 2026 IBM Corporation, Zeligsoft Inc., and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *   Zeligsoft - Bug 218765
 */
package org.eclipse.emf.validation.marker.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.service.impl.tests.ConstraintDescriptorTest;
import org.eclipse.emf.validation.internal.util.XmlConstraintDescriptor;
import org.eclipse.emf.validation.marker.MarkerUtil;
import org.eclipse.emf.validation.model.ConstraintStatus;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.model.ModelConstraint;
import org.eclipse.emf.validation.service.ConstraintExistsException;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.emf.validation.tests.TestBase;
import org.eclipse.emf.validation.util.XmlConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ordersystem.Order;
import ordersystem.OrderSystemFactory;

/**
 * Tests for the {@link MarkerUtil} API.
 *
 * @author Christian W. Damus (cdamus)
 */
public class MarkerUtilTest extends TestBase {
	private IProject testProject;
	private IFile testFile;
	private Resource testResource;

	@Before
	public void setUp() throws CoreException, IOException {
		testProject = ResourcesPlugin.getWorkspace().getRoot().getProject("_Validation_Test");
		testProject.create(null);
		testProject.open(null);

		String fileName = "dir with spaces/test file.xmi";
		testFile = testProject.getFile(new Path(fileName));

		URI uri = URI.createPlatformResourceURI(testFile.getFullPath().toString(), true);

		ResourceSet rset = new ResourceSetImpl();
		testResource = rset.createResource(uri);

		// create an order with an invalid ID
		Order testOrder = OrderSystemFactory.eINSTANCE.createOrder();
		testOrder.setId("id");

		// include live constraints in batch validation and no need for successes
		batchValidator.setIncludeLiveConstraints(true);
		batchValidator.setReportSuccesses(false);

		testResource.getContents().add(testOrder);

		testResource.save(Collections.emptyMap());
	}

	@After
	public void tearDown() throws CoreException {
		testResource.unload();
		testResource = null;
		testProject.delete(true, true, null);
		testFile = null;
		testProject = null;
	}

	@Test
	public void test_createMarkersInPathWithSpaces_201788() {
		IStatus status = batchValidator.validate(testResource.getContents());

		// assert that there will be markers to create
		assertTrue(status.getSeverity() >= IStatus.WARNING);

		try {
			MarkerUtil.createMarkers(status);
		} catch (CoreException e) {
			fail("Failed to create markers: " + e.getLocalizedMessage());
		}

		try {
			IMarker[] markers = testFile.findMarkers(MarkerUtil.VALIDATION_MARKER_TYPE, true, 0);

			assertFalse("No markers created", markers.length == 0);
		} catch (CoreException e) {
			fail("Failed to find markers: " + e.getLocalizedMessage());
		}
	}

	@Test
	public void test_clearMarkersFromCleanResource_218765() {
		// track resources that were validated
		batchValidator.setOption(IBatchValidator.OPTION_TRACK_RESOURCES, Boolean.TRUE);

		IStatus status = batchValidator.validate(testResource.getContents());

		// assert that there will be markers to create
		assertTrue(status.getSeverity() >= IStatus.WARNING);

		try {
			MarkerUtil.updateMarkers(status);
		} catch (CoreException e) {
			fail("Failed to create markers: " + e.getLocalizedMessage());
		}

		// add a clean object
		EAnnotation annot = EcoreFactory.eINSTANCE.createEAnnotation();
		annot.setSource("clean");
		testResource.getContents().add(annot);

		// validate the clean object
		status = batchValidator.validate(annot);

		assertEquals(IStatus.OK, status.getSeverity());

		try {
			MarkerUtil.updateMarkers(status);
		} catch (CoreException e) {
			fail("Failed to update markers: " + e.getLocalizedMessage());
		}

		try {
			IMarker[] markers = testFile.findMarkers(MarkerUtil.VALIDATION_MARKER_TYPE, true, 0);

			assertEquals("Markers not cleared", 0, markers.length);
		} catch (CoreException e) {
			fail("Failed to find markers: " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testMarkerCreationOnNestedStatus_295841() {
		ConstraintDescriptorTest.FixtureElement config = ConstraintDescriptorTest.newFixtureConfig();

		config.putAttribute(XmlConfig.A_ID, "aGetConOpTest@" + System.identityHashCode(config))
				.putAttribute(XmlConfig.A_MODE, EvaluationMode.BATCH.getName());

		IModelConstraint constraint;
		try {
			constraint = new ModelConstraint(new XmlConstraintDescriptor(config)) {

				@Override
				public IStatus validate(IValidationContext ctx) {
					return Status.CANCEL_STATUS;
				}
			};
		} catch (ConstraintExistsException e) {
			throw new RuntimeException(e);
		}

		EObject target = testResource.getContents().get(0);

		ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH).validate(target);

		ConstraintStatus status = new ConstraintStatus(constraint, target, "fail", null);
		MultiStatus ms1 = new MultiStatus("org.eclipse.validation", 0,
				new IStatus[] { new MultiStatus("org.eclipse.validation", 0, new IStatus[] { status }, "m2", null) },
				"m1", null);

		try {
			MarkerUtil.createMarkers(ms1);
			IMarker[] markers = testFile.findMarkers(MarkerUtil.VALIDATION_MARKER_TYPE, true, 0);

			assertNotNull(markers);
			assertEquals(1, markers.length);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}

	}
}
