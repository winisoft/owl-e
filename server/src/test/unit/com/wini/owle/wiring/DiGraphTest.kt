package com.wini.owle.wiring

import com.wini.owle.allModules
import com.wini.owle.repo.OwleRepo
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.Entity
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import com.wini.owle.api.IOwleApi


/**
 * The dependency graph, asserted as a whole.
 *
 * This is what replaced forty-six near-identical repository suites, and it is worth being precise
 * about why: those suites mocked the `EntityClass`, so a repository wired to the wrong table
 * passed every assertion in them. That is exactly what was happening. Koin keys definitions by
 * `KClass` and Kotlin generics erase, so every `single<OwleRepo<ULong, …>>` registered under one
 * key and the last one won — asking for the Workflow, WorkflowFolder or BenchmarkReport repository
 * all returned a single object pointed at `tagged_workflows`. Services, DLLs and endpoints
 * collapsed the same way.
 *
 * No amount of per-type mocking can see that. Resolving the real graph can, and does it in one
 * test rather than once per type.
 */
@DisplayName("DI graph")
abstract class DiGraphTest {

    private lateinit var koin: Koin

    @BeforeEach
    fun start() {
        stopKoin()
        koin = startKoin { modules(allModules) }.koin
    }

    @AfterEach
    fun stop() = stopKoin()

    /**
     * GIVEN the whole application module set
     * WHEN every generated repository is resolved
     * THEN each is a distinct instance bound to its own table
     */
    @Test
    fun `every generated repository resolves to its own table`() {
        val repos = generatedClassesEndingIn("Repo")
            .associateWith { koin.get<Any>(it) as OwleRepo<*, *> }

        assertTrue(repos.size > 40, "expected the generated repositories, found ${repos.size}")

        val tablesByRepo = repos.mapValues { (_, repo) -> repo.entityClass.table.tableName }
        val duplicated = tablesByRepo.values.groupingBy { it }.eachCount().filterValues { it > 1 }

        assertTrue(
            duplicated.isEmpty(),
            "these tables are served by more than one repository, which means the bindings " +
                "collided rather than resolving separately: $duplicated"
        )
        assertEquals(
            repos.size, repos.values.distinct().size,
            "every repository must be its own instance"
        )
    }

    /**
     * GIVEN the whole application module set
     * WHEN a repository is resolved by its generated class
     * THEN it serves the table its own name implies, not merely some table
     */
    @Test
    fun `a repository serves the table its name implies`() {
        val workflow = koin.get<com.wini.owle.generated.WorkflowRepo>()
        val folder = koin.get<com.wini.owle.generated.WorkflowFolderRepo>()

        assertEquals("workflows", workflow.entityClass.table.tableName)
        assertEquals("workflow_folders", folder.entityClass.table.tableName)
    }

    /**
     * GIVEN the whole application module set
     * WHEN every generated service and DLL is resolved
     * THEN each resolves without error and is its own instance
     */
    @Test
    fun `every generated service and DLL resolves distinctly`() {
        listOf("Service", "Dll").forEach { suffix ->
            val resolved = generatedClassesEndingIn(suffix).map { koin.get<Any>(it) }
            assertTrue(resolved.size > 40, "expected the generated $suffix classes, found ${resolved.size}")
            assertEquals(
                resolved.size, resolved.distinct().size,
                "$suffix bindings collided: ${resolved.size} definitions produced " +
                    "${resolved.distinct().size} instances"
            )
        }
    }

    /**
     * GIVEN the whole application module set
     * WHEN every generated endpoint is resolved
     * THEN no two endpoints claim the same route
     */
    @Test
    fun `no two endpoints claim the same route`() {
        // read through the interface rather than by reflection: `basePath` is a computed getter
        // over `endpointPath`, and walking these classes reflectively recurses into it
        val paths = generatedClassesEndingIn("Api")
            .map { (koin.get<Any>(it) as IOwleApi).basePath }
        val collisions = paths.groupingBy { it }.eachCount().filterValues { it > 1 }

        assertTrue(collisions.isEmpty(), "two endpoints answer on the same route: $collisions")
    }

    /** The generated layer classes, found by name so the test cannot drift from the generator. */
    private fun generatedClassesEndingIn(suffix: String): List<kotlin.reflect.KClass<*>> {
        val marker = com.wini.owle.generated.WorkflowRepo::class.java
        val dir = java.io.File(marker.protectionDomain.codeSource.location.toURI())
            .resolve("com/wini/owle/generated")
        return dir.listFiles { f: java.io.File -> f.name.endsWith("$suffix.class") }
            .orEmpty()
            .map { Class.forName("com.wini.owle.generated.${it.name.removeSuffix(".class")}").kotlin }
    }
}
