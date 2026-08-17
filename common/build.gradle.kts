plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
}

group = "com.wini.owle"
version = "unspecified"

// `type-schema` is applied as a processor to both this module and `server`, and its processors
// are not interchangeable between them — generated code lands in whichever module ran it and
// sees only that module's classpath. This tells the processors which side they're on; see
// com.wini.owle.ksp.OwleTarget.
ksp {
    arg("owle.target", "common")
}

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