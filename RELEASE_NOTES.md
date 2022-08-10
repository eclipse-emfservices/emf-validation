# Eclipse EMF Validation: Release Notes

This page describes the noteworthy improvements provided by each release of Eclipse EMF Services.

## EMF Validation 1.13.0

* Releng improvements
  * Move to recent(ish) version of Tychos, CBI plug-ins, etc.
  * Generate Eclipse-SourceReference
  * Support building against multiple target platforms (including a "canary")
  * Remove unused dependencies
  * Use HTTPS only for all target platforms
* Drop support for Oxygen and later
* Move to EPL 2.0
* Move to Java 8
* Move to GitHub
  * As part of this, all remaining Bugzilla issues have been closed. Please reopen on GitHub Issues the ones you think are still relevant.
* Fix support for "recent" versions of the platform
* Fix some compilation warnings
* Actual code changes:
  * [559767] Fix compilation errors under Photon and later
  * [322079] Fix potential NPE in EMFModelValidationPlugin.getPluginId()
  * [397327] IXmlConstraintDescriptor should not be deprecated
  * [397332] Use exact case matching for nsURI comparison
  * Remove version constraints for com.ibm.icu package imports
