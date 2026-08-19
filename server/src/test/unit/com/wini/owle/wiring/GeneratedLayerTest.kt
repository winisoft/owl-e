package com.wini.owle.wiring

import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.delegate.DaoDelegate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File


/**
 * What the generator produced, checked against what it was supposed to produce.
 *
 * Generation failures in this project have all had the same shape: they are silent. The mapper
 * generator once emitted nothing for all seventy-three types because KSP hands back only dirty
 * files on an incremental round. The validator generator dropped every nullable field's
 * constraints and reported success. Twenty-four hand-written composite delegates carried an empty
 * `CompositeID { }` body that compiled and threw only when something called it. None of these
 * failed a build.
 *
 * So the assertions here are about presence and usability rather than content: that a layer exists
 * for every mapped type, and that every composite delegate can actually name its key columns.
 * The second is the one with teeth — it was false for all twenty-three composite tables until
 * their key columns were registered, because `PrimaryKey(a, b)` declares a constraint while
 * Exposed's identity columns are a separate set that only `entityId()`/`addIdColumn` write to.
 */
@DisplayName("Generated layer")
abstract class GeneratedLayerTest {

    /**
     * GIVEN the generated layer classes on the classpath
     * WHEN they are counted against the generated mappers
     * THEN there is exactly one layer per mapped type
     *
     * A round that emits nothing, or emits for only the handful of files KSP considered dirty,
     * fails here instead of surfacing as unresolved references somewhere downstream.
     */
    @Test
    fun `every mapped type has a generated layer`() {
        val mappers = generatedNamesEndingIn("Mapper", "com/wini/owle/mapper/generated")
        val delegates = generatedNamesEndingIn("DaoDelegate", "com/wini/owle/generated")

        // The failure this guards is a truncated round - KSP emitting only for the handful of
        // files it considered dirty, which produces single digits. Measuring against the entity
        // count rather than a literal keeps it from needing an edit every time a type is added or
        // retired; not every entity is mapped, so this is a floor and not an equality.
        val entities = entityClassCount()
        assertTrue(
            mappers.size >= entities / 2,
            "KSP emitted ${mappers.size} mappers against $entities entity classes - a round that " +
                "saw only dirty files looks exactly like this"
        )
        assertEquals(
            mappers, delegates,
            "every mapped type needs a layer; missing ${mappers - delegates}, " +
                "unexpected ${delegates - mappers}"
        )
    }

    /**
     * GIVEN every generated composite delegate
     * WHEN each is asked for the columns its key is made of
     * THEN each answers with at least one
     *
     * `CompositeIdDaoDelegate` derives both `columnsByRequestParam` and `buildId` from the table's
     * `idColumns`, so a table that registered none leaves its endpoint unable to turn a request
     * into an id at all. Reading it here is the whole test: it threw for all twenty-three.
     */
    @Test
    fun `every composite delegate can name its key columns`() {
        val composites = delegateInstances().filterValues { it is CompositeIdDaoDelegate<*, *> }
        assertTrue(composites.size >= 20, "expected the composite delegates, found ${composites.size}")

        val problems = composites.mapNotNull { (name, delegate) ->
            runCatching { (delegate as CompositeIdDaoDelegate<*, *>).columnsByRequestParam }.fold(
                onSuccess = { columns ->
                    when {
                        columns.isEmpty() -> "$name reports no key columns"
                        columns.keys.any { it.isBlank() } -> "$name has an unnamed key column"
                        else -> null
                    }
                },
                onFailure = { "$name could not name its key columns: ${it.message}" }
            )
        }

        assertTrue(problems.isEmpty(), "composite delegates unusable:\n" + problems.joinToString("\n"))
    }

    /** The generated delegates, instantiated, keyed by the type they serve. */
    private fun delegateInstances(): Map<String, DaoDelegate<*, *, *>> =
        generatedNamesEndingIn("DaoDelegate", "com/wini/owle/generated").associateWith { type ->
            Class.forName("com.wini.owle.generated.${type}DaoDelegate")
                .getDeclaredConstructor()
                .newInstance() as DaoDelegate<*, *, *>
        }

    /** How many Exposed entity classes exist, as an independent measure of the expected scale. */
    private fun entityClassCount(): Int {
        val marker = com.wini.owle.entity.workflows.WorkflowEntity::class.java
        val root = File(marker.protectionDomain.codeSource.location.toURI())
            .resolve("com/wini/owle/entity")
        return root.walkTopDown()
            .count { it.isFile && it.name.endsWith("Entity.class") && !it.name.contains('$') }
    }

    /** Type names of generated classes with the given suffix, found by scanning the classpath. */
    private fun generatedNamesEndingIn(suffix: String, packagePath: String): Set<String> {
        val marker = com.wini.owle.generated.WorkflowRepo::class.java
        val dir = File(marker.protectionDomain.codeSource.location.toURI()).resolve(packagePath)
        return dir.listFiles { f: File -> f.name.endsWith("$suffix.class") }
            .orEmpty()
            .map { it.name.removeSuffix(".class").removeSuffix(suffix) }
            .filter { it.isNotEmpty() && !it.contains('$') }
            .toSet()
    }
}
