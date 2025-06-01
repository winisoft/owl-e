import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.ksp)
}

group = "com.wini.owle"
version = "1.0.0"


sourceSets {
    main {
        dependencies {
            implementation(projects.common)
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
        }
    }
    test {

        java.setSrcDirs(listOf("src/test/unit", "src/test/integration"))

        dependencies {
            implementation(projects.common)
            implementation(libs.junit.jupiter)
            implementation(libs.junit.jupiter.api)
            implementation(libs.junit.jupiter.engine)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.hamcrest.hamcrest)
            implementation(libs.koin.test.junit5)
            implementation(libs.ktor.server.tests)
            implementation(libs.kotlin.test.junit)
            implementation(libs.assertk.jvm)
            implementation(libs.mockk)
            implementation(libs.mysql)
        }
    }
}

tasks {

    test {
        useJUnitPlatform()
    }

    val unitTest by registering(Test::class) {
        description = "Runs unit tests (from src/test/unit only)"
        group = "verification"
        useJUnitPlatform()
        testClassesDirs = files(
            fileTree("build/classes/kotlin/test").matching {
                include("**/unit/**")
            }
        )
        classpath = sourceSets["test"].runtimeClasspath
    }

    val integrationTest by registering(Test::class) {
        description = "Runs integration tests (from src/test/integration only)"
        group = "verification"
        useJUnitPlatform()
        testClassesDirs = files(
            fileTree("build/classes/kotlin/test").matching {
                include("**/integration/**")
            }
        )
        classpath = sourceSets["test"].runtimeClasspath
    }


    withType<KotlinCompile>().configureEach {
        compilerOptions.jvmTarget = JvmTarget.JVM_17
    }

}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("com.wini.owle.MainKt")
}
