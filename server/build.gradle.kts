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


// Counterpart to `common`'s declaration. Processors gated to OwleTarget.SERVER run here — the
// ones whose output needs Exposed entities, Ktor routing, or the DLL, none of which `common`
// can see. The DTO-derived processors (validators, model/API docs) are gated to COMMON and
// no-op here, so applying the same processor artifact to both modules is safe.
ksp {
    arg("owle.target", "server")
}

sourceSets {
    main {

        kotlin.srcDir("build/generated/ksp/main/kotlin")
        resources.srcDir("build/generated/ksp/main/resources")

        dependencies {
            implementation(projects.common)
            implementation(projects.typeSchema)
            ksp(projects.typeSchema)
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

        // Both must be set. `java.setSrcDirs` alone leaves Kotlin's conventional `src/test/kotlin`
        // on the compile path, which is how a stale duplicate of RepositoryTest kept building.
        java.setSrcDirs(listOf("src/test/unit", "src/test/integration"))
        kotlin.setSrcDirs(listOf("src/test/unit", "src/test/integration"))

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
            implementation(libs.junitPlatform.suite)
            implementation(libs.junitPlatform.suite.api)
            implementation(libs.junitPlatform.suite.engine)
            implementation(libs.junitPlatform.launcher)
            implementation(libs.junitPlatform.runner)
            implementation(libs.junitPlatform.commons)

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
        // Filtering by path cannot work: both source dirs declare the same packages, so the
        // `unit`/`integration` segment never reaches the compiled output and this task matched
        // zero classes while reporting success. The split is carried by a JUnit tag instead.
        useJUnitPlatform { excludeTags("integration") }
        testClassesDirs = sourceSets["test"].output.classesDirs
        classpath = sourceSets["test"].runtimeClasspath
    }

    val integrationTest by registering(Test::class) {
        description = "Runs integration tests (from src/test/integration only)"
        group = "verification"
        useJUnitPlatform { includeTags("integration") }
        testClassesDirs = sourceSets["test"].output.classesDirs
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
