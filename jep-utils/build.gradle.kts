import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
}

group = "com.wini.owle"
version = "1.0.0"

repositories {
    mavenCentral()
    flatDir {
        dirs(rootProject.file("lib"))
    }
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:2.1.0-1.0.29")
    implementation(projects.common)
    implementation(libs.kotlinx.serialization.json)
    implementation(files("lib\\jep.jar"))
    implementation("black.ninia:jep:4.2.2")

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.hamcrest.hamcrest)
    testImplementation(libs.koin.test.junit5)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.assertk.jvm)
    testImplementation(libs.mockk)
    testImplementation(libs.mysql)
}

tasks.test {
    useJUnitPlatform()
    jvmArgs = listOf(
        "-Djava.library.path=C:/Users/wini/miniconda3/Lib/site-packages/jep-4.2.2-py3.12-win-amd64.egg/jep"
    )
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.value(JvmTarget.JVM_17)
    }
}
