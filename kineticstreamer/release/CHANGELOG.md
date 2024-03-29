# Version 8

- Adding logging for cache
- Allow users to define the order in which streamed fields must be (de)serialized
- Consolidate controllers into single KineticStreamController class

[kineticstreamer-v8.0.zip](https://github.com/lambdaprime/kineticstreamer/raw/master/kineticstreamer/release/kineticstreamer-v8.0.zip)

# Version 7

- Include field annotations to input/output kinetic streams

[kineticstreamer-v7.0.zip](https://github.com/lambdaprime/kineticstreamer/raw/master/kineticstreamer/release/kineticstreamer-v7.0.zip)

# Version 6

- Android build support
- Updating to xfunction v24.0-SNAPSHOT
- Adding float[] support
- Adding char and char[] support
- Update to gradle 8.1.1, adding records information

[kineticstreamer-v6.0.zip](https://github.com/lambdaprime/kineticstreamer/raw/master/kineticstreamer/release/kineticstreamer-v6.0.zip)

# Version 5

- Updating to xfunction v20 and formatting gradle files
- Updating to xfunction snapshot version
- Adding comments

[kineticstreamer-v5.0.zip](https://github.com/lambdaprime/kineticstreamer/raw/master/kineticstreamer/release/kineticstreamer-v5.0.zip)

# Version 4

- Updating README
- Adding support for long
- Adding support for reading fixed size arrays
- Adding readLongArray support and let read to return exact type and not Object
- Adding short support
- Adding KineticStreamReaderController
- Adding readList
- Removing @Streamed annotation and supporting transient fields
- Filtering final fields
- Consolidating all primitive types in KineticstreamerPrimitiveTypes
- Fixing ClassCastException
- Adding support for (de)serialization of nested classes
- Adding writeList support
- Removing list (de)serialization support since all use cases for it can be replaced with arrays
- Adding KineticStreamWriter
- Pass to controllers InputKineticStream/OutputKineticStream which give user better control over how to process next data from the stream instead of KineticStreamReader/KineticStreamWriter
- Fixing publish to Sonatype issues in Gradle
- Adding restrictions for streamed fields with null
- Increasing version to 4.0
- Integrating spotless
- Updating to xfunction 16
- Update to Java 17
- Adding support of String[] as a primitive type
- Passing field object to onNextObject instead of original
- Updating spotless
- Version 4

[kineticstreamer-v4.0.zip](https://github.com/lambdaprime/kineticstreamer/raw/master/kineticstreamer/release/kineticstreamer-v4.0.zip)

# Previous versions

Changelog for previous versions were published in github Releases but then migrated here.
