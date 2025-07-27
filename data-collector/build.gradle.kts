plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
}

group = "com.wini.owle"
version = "1.0.0"


sourceSets {
    main {
        dependencies {
            implementation("com.google.devtools.ksp:symbol-processing-api:2.1.0-1.0.29")
            implementation(projects.common)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.mysql.connector.java)
            implementation(libs.koin.core)
            implementation(libs.koin.ktor)
        }
    }
    test {
        dependencies {
            implementation(libs.junit.jupiter)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.mockk)
            implementation(libs.hamcrest.hamcrest)
            implementation(libs.mysql)
            implementation(libs.koin.test.junit5)
            implementation(libs.assertk.jvm)
            implementation(libs.ktor.server.tests)
            implementation(libs.kotlin.test.junit)
            implementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
            implementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}