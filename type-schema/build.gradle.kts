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
            implementation(libs.junit.jupiter)
            implementation(libs.kotlin.test.junit)
            implementation(libs.hamcrest.hamcrest)
            implementation(libs.mockk)
            implementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
            implementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
        }
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
