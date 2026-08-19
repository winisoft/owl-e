package com.wini.owle.repo

import com.wini.owle.repo.tags.targets.StepTagRepoTest
import com.wini.owle.repo.workflows.WorkflowRepoTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested


/**
 * The repository layer.
 *
 * This used to hold one entry per entity — 48 of them, running [GenericRepoTest]'s assertions 720
 * times. They were not 48 tests: measured against each other, the subclasses came in exactly two
 * shapes, differing only in a type name, and the code under test is a single generic class with no
 * per-type behaviour. Forty-six of them proved nothing the other two did not.
 *
 * What is kept is one instantiation per id kind, because that is where behaviour genuinely
 * diverges: `ULong` ids are built from a real `EntityID`, composite ids cannot be and use mocks
 * compared by identity.
 *
 * What replaced the other forty-six is [com.wini.owle.wiring.DiGraphTest], which asserts that
 * every generated repository resolves to its own table. That is the failure those 720 assertions
 * could never see — they mock the `EntityClass`, so a repository pointed at the wrong table passes
 * every one of them.
 *
 * A type that grows behaviour of its own earns a file of its own; that is a one-file job when the
 * behaviour arrives, not something to pre-create 48 times.
 */
@DisplayName("Repository Tests")
abstract class RepositoryTest {

    @Nested
    @DisplayName("ULong-keyed (Workflow)")
    inner class ULongKeyed : WorkflowRepoTest()

    @Nested
    @DisplayName("Composite-keyed (StepTag)")
    inner class CompositeKeyed : StepTagRepoTest()
}
