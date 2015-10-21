        Application Description
      ~~~~~~~~~~~~~~~~~~~~~~~~~~~

// TODO 


        Directory Structure
      ~~~~~~~~~~~~~~~~~~~~~~~

build/
        Contains the compiled 'classes' and the generated 'javadoc' files.
        This directory should not be included in version control (Subversion).

dist/
        Contains the distributable Application JAR file.
        This directory should not be included in version control (Subversion).

docs/
        General documentation for the Application (description, instructions, ...)

lib/
        External libraries specifically required by this Application.
        The filename should include the version as such: libname-vX.x.jar

setup/
        Application setup files include server configuration, database creation, ...

src/conf/
        The config files include the manifest file and other dynamic configurations.

src/java/
        The Java sources in packages: esac.archive.absi.tmpl.jar-app[.whatever]

test/
        Application test files include the JUnit test sources in parallel packages.

build.properties
        User specific build property file. Supersedes the defaults property file.
        This file should not be included in version control (Subversion).

build.properties.defaults
        Defaults build property file. Defines the properties for a standard build.

build.properties.dev
        Dev build property file. Defines the properties for a development target.

build.properties.local
        Local build property file. Defines the properties for a local host target.

build.properties.oper
        Oper build property file. Defines the properties for an operational target.

build.xml
        ANT build script for the Application. Run 'ant -projecthelp' for more info.

README
        This file. Describes the Application and its directory structure.
