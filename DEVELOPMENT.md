# Build

Building  module locally and making changes to it (this is optional and not intended for users).

## With Gradle

``` bash
gradle clean build
```

## With Eclipse

- Build Eclipse projects:

``` bash
gradle eclipse
```

- Import them into Eclipse

# Release steps

- Close version in gradle.properties
- Run `gradle clean build -b android/build.gradle`
- Run `gradle clean build javadoc`
- Publish
- Open next SNAPSHOT version
- Update CHANGELOG.md with new release (for changelog generation use `git log --format=%s`)
- Commit changes
- Push
- Upload documentation to website
