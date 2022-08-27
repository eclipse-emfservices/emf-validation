# Eclipse EMF Validation: Release Notes

This page describes the noteworthy improvements provided by each release of Eclipse EMF Validation.

## 1.13.2

Released on 2022-08-27.

This version is identical to 1.13.1, but rebuilt using Tycho 2.7.2 (instead of 2.7.4) to avoid a regression in the content of the p2 repo produced.

## 1.13.1

Released on 2022-08-15.

* #2: Remove the dependency on the deprecated ICU4J/com.ibm.icu
* #5: Drop support for Eclipse versions older than 2020-09
* #6: Move BREE to JavaSE-11

## 1.13.0

Released on 2022-08-10.

* Releng improvements
  * Move to recent(ish) version of Tychos, CBI plug-ins, etc.
  * Generate Eclipse-SourceReference
  * Support building against multiple target platforms (including a "canary")
  * Remove unused dependencies
  * Use HTTPS only for all target platforms
* Drop support for Oxygen and earlier. The oldest version of Eclipse supported is Photon.
* Move to EPL 2.0
* Move to Java 8 as minimum version
* Move to GitHub
  * As part of this, all remaining Bugzilla issues have been closed. Please reopen on GitHub Issues the ones you think are still relevant.
* Ensure support for recent versions of the Eclipse platform
* Fix some compilation warnings
* Actual code changes:
  * [559767] Fix compilation errors under Photon and later
  * [322079] Fix potential NPE in EMFModelValidationPlugin.getPluginId()
  * [397327] IXmlConstraintDescriptor should not be deprecated
  * [397332] Use exact case matching for nsURI comparison
  * Remove version constraints for com.ibm.icu package imports
