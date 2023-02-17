## Eclipse EMF Services - EMF Validation

_EMF Validation_ extends the core validation support of [EMF](https://www.eclipse.org/modeling/emf/) itself with:
* constraint definitions,
* customizable model traversal algorithms,
* constraint parsing for languages,
* configurable application-specific validation contexts,
* and validation listeners.

It is part of the [Eclipse EMF Services](https://projects.eclipse.org/projects/modeling.emfservices) project, which provides libraries that extend the core EMF framework with additional services or more powerful versions of services provided by EMF itself.

### Building

The build uses [Tycho](http://www.eclipse.org/tycho/). To launch a complete build, issue `mvn clean package`from the top-level directory.
The resulting update-site (p2 repository) can be found in `org.eclipse.emf.validation.repository/target/repository`.

You can specify a specific platform from the ones available in platforms available in `org.eclipse.emf.validation.target`.
For example `mvn clean package -Dplatform=2020-12` to build against a Target Platform which corresponds to Eclipse 2020-12.

### Continuous Integration

The official builds are run in the Eclipse Foundation's infrastructure, at <https://ci.eclipse.org/emfservices>.

### Update Sites

Update Sites (p2 repositories) are available at:
* <https://download.eclipse.org/modeling/emf/validation/updates/interim>: nightly builds
* <https://download.eclipse.org/modeling/emf/validation/updates/milestones>: milestone builds
* <https://download.eclipse.org/modeling/emf/validation/updates/releases>: releases

| Version             | Repository URL                                                                        |
|:--------------------|:--------------------------------------------------------------------------------------|
| **1.13.2** (latest) | <https://download.eclipse.org/modeling/emf/validation/updates/releases/R202208271102> |
| 1.13.1              | <https://download.eclipse.org/modeling/emf/validation/updates/releases/R202208151538> |
| 1.13.0              | <https://download.eclipse.org/modeling/emf/validation/updates/releases/R202208101528> |
| 1.12.2              | <https://download.eclipse.org/modeling/emf/validation/updates/releases/R202008210805> |
| 1.12.1              | <https://download.eclipse.org/modeling/emf/validation/updates/releases/R201812070911> |
| 1.12.0              | <https://download.eclipse.org/modeling/emf/validation/updates/releases/R201805030717> |
| 1.11.0              | <https://download.eclipse.org/modeling/emf/validation/updates/releases/R201706061352> |
| 1.10.0              | <https://download.eclipse.org/modeling/emf/validation/updates/releases/R201606071713> |
| 1.9.0               | <https://download.eclipse.org/modeling/emf/validation/updates/releases/R201505312255> |
| 1.8.0               | <https://download.eclipse.org/modeling/emf/validation/updates/releases/R201405281429> |
| 1.7.0               | <https://download.eclipse.org/modeling/emf/validation/updates/releases/R201306111341> |


### License

[Eclipse Public License 2.0](https://www.eclipse.org/legal/epl-2.0/)
