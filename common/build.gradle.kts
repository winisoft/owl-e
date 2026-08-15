plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
}

group = "com.wini.owle"
version = "unspecified"

sourceSets.main {

    kotlin.srcDir("build/generated/ksp/main/kotlin")
    resources.srcDir("build/generated/ksp/main/resources")

    dependencies {
        implementation(projects.typeSchema)
        ksp(projects.typeSchema)
        implementation(libs.koin.core)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.kotlinx.datetime)
        implementation(kotlin("stdlib"))
    }
}

dependencies {

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation("com.google.devtools.ksp:symbol-processing-api:1.8.0-1.0.9")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<Copy>().all {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<Jar>().all {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}