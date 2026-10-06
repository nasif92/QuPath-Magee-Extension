# QuPath Magee Extension

Adds the Magee Equation Calculator, annotation export and detection import to an
**unmodified** QuPath 0.7.0 install.

## Build

Requires JDK 21 (QuPath 0.7.0's Java version).

    ./gradlew build

The jar is written to `build/libs/qupath-extension-magee-0.1.0.jar`.

## Install

1. Install the official QuPath 0.7.0 release.
2. Drag the jar onto the open QuPath window (or copy it into the QuPath
   extensions folder, see Extensions > Manage extensions), then restart QuPath.

The tools appear under **Extensions > Magee Tools** and as three toolbar
buttons: Export annotations, Import detections, Magee equation calculator.

## Upgrading QuPath

Change `qupath { version = "..." }` in `settings.gradle.kts` and
`QUPATH_VERSION` in `MageeExtension.java`, rebuild, and retest before rollout.
