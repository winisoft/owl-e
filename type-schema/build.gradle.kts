group = "com.wini.owle"
version = "unspecified"

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation("com.google.devtools.ksp:symbol-processing-api:2.1.0-1.0.29")
    implementation(libs.kotlinx.serialization.json)
    
    // OpenAPI spec generation
    implementation("io.swagger.core.v3:swagger-core:2.2.20")
    implementation("io.swagger.core.v3:swagger-models:2.2.20")
}

sourceSets.main {
    kotlin.srcDir("build/generated/ksp/main/kotlin")
    resources.srcDir("build/generated/ksp/main/resources")
}

sourceSets.test {
    kotlin.srcDir("build/generated/ksp/test/kotlin")
}