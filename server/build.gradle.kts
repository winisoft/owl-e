plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
}

group = "com.wini.owle"
version = "1.0.0"

sourceSets {
    main {
        dependencies {
            implementation(projects.shared)
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
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.stripe.java)

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
            testImplementation("org.mockito:mockito-core:5.6.0") // Adjust to latest version
            testImplementation("org.mockito:mockito-inline:5.2.0") // For mocking final classes
            testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}