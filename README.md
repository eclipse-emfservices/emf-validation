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

By default the build uses a Oxygen-based target platform to ensure compatibility.
You can specify a different platform from the ones available in platforms available in `org.eclipse.emf.validation.target`.
For example `mvn clean package -Dplatform=2020-12` to build against a Target Platform which corresponds to Eclipse 2020-12.

### Continuous Integration

The official builds are run in the Eclipse Foundation's infrastructure, at https://ci.eclipse.org/emfservices/.

### Update Sites

Update Sites (p2 repositories) are available at:
* https://download.eclipse.org/modeling/emf/validation/updates/interim: nightly builds
* https://download.eclipse.org/modeling/emf/validation/updates/milestones: milestone builds
* https://download.eclipse.org/modeling/emf/validation/updates/releases: official releases

### License

[Eclipse Public License 2.0](https://www.eclipse.org/legal/epl-2.0/)
