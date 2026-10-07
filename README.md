# QuPath Magee Extension

Adds the Magee Equation Calculator, annotation export and detection import to an
**unmodified** QuPath install.

## For users: install

Requires **QuPath 0.7.0**.

1. Download `qupath-extension-magee-<version>.jar` from the
   [Releases](../../releases) page (just that one file; ignore any `-sources` or `-javadoc` jars).
2. Drag the jar onto the QuPath window. If prompted, create a user directory.
3. Restart QuPath.

The tools appear under **Extensions > Magee Tools** and as three toolbar buttons
(Export annotations, Import detections, Magee equation calculator).

**Updating:** close QuPath, delete the old jar from the extensions folder
(Extensions > Manage extensions > open extensions directory), copy in the new one, restart.
Don't keep two versions of the jar in that folder.

## For developers: build

Requires JDK 21. Build on a local disk (not a network drive).

    ./gradlew build

The jar is written to `build/libs/qupath-extension-magee-<version>.jar`.

## Making a release

1. Set the release `version` in `build.gradle.kts` (no `-SNAPSHOT`) and commit.
2. On GitHub: **Actions > Make draft release > Run workflow**. This builds the extension
   and creates a *draft* release with the jar attached.
3. Open the draft under **Releases**, add release notes (include "Requires QuPath 0.7.0"),
   and publish.
4. After publishing, bump the version for the next round of development.

## Upgrading QuPath

Change `qupath { version = "..." }` in `settings.gradle.kts` and `QUPATH_VERSION`
in `MageeExtension.java`, rebuild, and retest before releasing.
