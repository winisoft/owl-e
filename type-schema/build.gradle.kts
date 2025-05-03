group = "com.wini.owle"
version = "unspecified"

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation("com.google.devtools.ksp:symbol-processing-api:2.1.0-1.0.29")
}

sourceSets.main {
    kotlin.srcDir("build/generated/ksp/main/kotlin")
    resources.srcDir("build/generated/ksp/main/resources")
}

sourceSets.test {
    kotlin.srcDir("build/generated/ksp/test/kotlin")
}