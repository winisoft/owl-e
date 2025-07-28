import com.google.devtools.ksp.gradle.KspTask
import com.google.devtools.ksp.gradle.model.Ksp

plugins {
    application
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.ksp)
}

group = "com.wini.owle"
version = "1.0.0"

sourceSets.main {
    kotlin.srcDir("build/generated/ksp/main/kotlin")
    resources.srcDir("build/generated/ksp/main/resources")
}

sourceSets.test {
    kotlin.srcDir("build/generated/ksp/test/kotlin")
}

sourceSets {
    main {
        dependencies {
            implementation(projects.common)
            implementation(projects.typeSchema)
            implementation(libs.logback)
            implementation(libs.ktor.server.core)
            implementation(libs.ktor.server.netty)
            implementation(libs.ktor.server.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.exposed.kotlin.datetime)
            implementation(libs.exposed.core)
            implementation(libs.exposed.dao)
            implementation(libs.exposed.jdbc)
            implementation(libs.exposed.json)
            implementation(libs.mysql.connector.java)
            implementation(libs.koin.core)
            implementation(libs.koin.ktor)
            implementation(libs.stripe.java)
            implementation("io.ktor:ktor-server-swagger:3.1.2")
            implementation("io.ktor:ktor-server-openapi:2.3.13")
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
            implementation(libs.mockk)
            implementation(libs.hamcrest.hamcrest)
            testImplementation(libs.ktor.server.tests)
            testImplementation(libs.kotlin.test.junit)
            implementation(libs.ktor.server.tests)
            implementation(libs.kotlin.test.junit)
            implementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
            implementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
            testImplementation("org.mockito:mockito-core:5.6.0")
            testImplementation("org.mockito:mockito-inline:5.2.0")
            testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
        }
    }
}

// Add task to generate example JSON files from DtoTestInstanceFactory
//tasks.register("generateExamples", JavaExec::class) {
//    description = "Generate example JSON files from DtoTestInstanceFactory"
//    group = "build"
//
//    // Configure classpath for the task
//    classpath = sourceSets["test"].runtimeClasspath
//    mainClass.set("com.wini.owle.tools.ExampleGenerator")
//
//    // Ensure this task runs before KSP
//    tasks.named("kspKotlin").configure {
//        dependsOn("generateExamples")
//    }
//}

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

application {
    mainClass.set("com.wini.owle.MainKt")
}
