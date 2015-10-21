        Application Description
      ~~~~~~~~~~~~~~~~~~~~~~~~~~~

Project structure template for an ESAVO VOSpace v2.0 Exceptions component


        Directory Structure
      ~~~~~~~~~~~~~~~~~~~~~~~

build/
        Contains the compiled 'classes' and the generated 'javadoc' files.
        This directory should not be included in version control (Subversion).

dist/
        Contains the distributable component JAR file.
        This directory should not be included in version control (Subversion).

docs/
        General documentation for the Application (description, instructions, ...)

final/
        Contains the final distributable component JAR file ant the external 
        libraries it depends on. 
        This directory should not be included in version control (Subversion).

lib/
        External libraries specifically required by this component.
        The filename should include the version as such: libname-vX.x.jar
	    This directory should not be included in version control (Subversion).

setup/
        Component setup files include server configuration, database creation, ...

src/conf/
        The config files include the manifest file and other dynamic configurations.

src/java/
        The Java sources in packages: esac.archive.eacs.template[.whatever]

test/
        Component test files include the JUnit test sources in parallel packages.

build.properties
        User specific build property file. Supersedes the defaults property file.
        This file should not be included in version control (Subversion).

build.properties.defaults
        Defaults build property file. Defines the properties for a standard build.

build.xml
        ANT build script for the component. Run 'ant -projecthelp' for more info.
	
ivy-tasks.xml
        ANT build script for the component. Contains ant tasks to allow the use 
	    of Ivy.
	
ivy.xml
        Ivy dependency file. Describes project details and library dependencies. 

README.txt
        This file describes the component and its directory structure.
