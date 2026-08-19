group = "com.wini.owle"
version = "unspecified"

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
}

repositories {
    mavenCentral()
}

sourceSets {
    main {
        dependencies {
            implementation("com.google.devtools.ksp:symbol-processing-api:2.1.0-1.0.29")
            implementation(libs.kotlinx.serialization.json)
            implementation("com.squareup:kotlinpoet:2.2.0")

            // OpenAPI spec generation
            implementation("io.swagger.core.v3:swagger-core:2.2.20")
            implementation("io.swagger.core.v3:swagger-models:2.2.20")
        }

    }
    test {
        dependencies {
            // `kotlin.test` alone carries no engine, so `useJUnitPlatform()` discovered nothing
            // and the task reported success having run zero tests
            implementation(libs.junit.jupiter)
            implementation(libs.junit.jupiter.api)
            implementation(libs.junit.jupiter.engine)
            implementation(libs.junitPlatform.launcher)
            implementation(libs.kotlin.test.junit)
            implementation(libs.mockk)
        }
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()

    // this module ran zero tests for as long as it had them, and said BUILD SUCCESSFUL each time;
    // an empty run is now a failure rather than a quiet pass
    var executed = 0
    afterTest(KotlinClosure2<TestDescriptor, TestResult, Unit>({ _, _ -> executed++; Unit }))
    doLast {
        if (executed == 0) throw GradleException("no tests ran - the engine or the sources are missing")
    }
}
