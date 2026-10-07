plugins {
    id("com.gradleup.shadow") version "8.3.5"
    id("qupath-conventions")
}

qupathExtension {
    name = "qupath-extension-magee"
    group = "ca.ualberta.magee"
    version = "0.1.0"
    description = "Magee Equation Calculator, annotation export and detection import for QuPath"
    automaticModule = "ca.ualberta.magee"
}

dependencies {
    // Provided by QuPath at runtime, so not bundled into the jar
    shadow(libs.bundles.qupath)
    shadow(libs.bundles.logging)
    shadow(libs.qupath.fxtras)
}
