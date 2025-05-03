
plugins {
    application
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.ktor)
    alias(libs.plugins.openApiGenerator)
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
            implementation(projects.common)//project(":common"))
            implementation(projects.typeSchema)//project(":type-schema"))
            ksp(projects.typeSchema) //(":type-schema"))
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
//
//tasks.dokkaHtml {
//    outputDirectory.set(buildDir.resolve("dokka"))
//}
//
//openApiGenerate {
//    validateSpec.set(true)
//    configFile.set("$rootDir/oai/configuraton.yaml")
//    apiPackage.set("com.wini.owle.api")
//    modelPackage.set("com.wini.owle.dto")
//    generateApiTests.set(true)
//    generateModelDocumentation.set(true)
//    generateApiDocumentation.set(true)
//    apiFilesConstrainedTo.add("$rootDir/main/kotlin/com/wini/owle/api")
//    modelFilesConstrainedTo.add("$rootDir/main/kotlin/com/wini/owle/dto")
//    globalProperties.putAll(
//        mutableMapOf(
//
//            "" to ""
//        )
//    )
//    generatorName.set("kotlin")
//    inputSpec.set("$rootDir/specs/petstore-v3.0.yaml")
//    outputDir.set("$buildDir/generated")
//    apiPackage.set("org.openapi.example.api")
//    invokerPackage.set("org.openapi.example.invoker")
//    modelPackage.set("org.openapi.example.model")
//    configOptions.put("dateLibrary", "java8")
//}

application {
    mainClass.set("com.wini.owle.MainKt")
}