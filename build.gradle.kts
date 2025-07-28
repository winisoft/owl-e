plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.ktor) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.openApiGenerator) apply false
    alias(libs.plugins.ksp) apply false
}

buildscript {
    dependencies {
        classpath(kotlin("gradle-plugin", version = "2.1.0"))
    }
}

