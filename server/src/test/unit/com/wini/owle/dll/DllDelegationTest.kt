package com.wini.owle.dll

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.services.Service
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


/**
 * The DLL layer, which is delegation and nothing else.
 *
 * There is no per-type behaviour here and no id-kind behaviour either — the id is passed through
 * untouched — so this runs once rather than once per generated `<Type>Dll`. What it can catch is
 * the one thing delegation can get wrong: reaching the wrong member. `createAll` and `updateAll`
 * call *overloads* of `create` and `update` that differ only in taking a list, and a batch call
 * wired to the single-item overload would still compile.
 *
 * That the generated DLLs are distinct instances over their own services is
 * [com.wini.owle.wiring.DiGraphTest]'s job.
 */
@DisplayName("DLL delegation")
abstract class DllDelegationTest {

    private lateinit var service: Service<ULong, Workflow>
    private lateinit var dll: GenericDllImpl<ULong, Workflow>

    // the DTOs are only ever passed through, so what they hold is irrelevant here - mocking them
    // keeps this test from breaking every time Workflow gains a field
    private val one: Workflow = mockk()
    private val many = listOf(one, mockk<Workflow>())

    @BeforeEach
    fun setUp() {
        service = mockk(relaxed = true)
        dll = GenericDllImpl(service)
    }

    /**
     * GIVEN a DLL over a service
     * WHEN each read is called
     * THEN it returns the service's answer unchanged
     */
    @Test
    fun `reads return what the service returned`() {
        every { service.getAll() } returns AsyncResult.Success(many)
        every { service.getById(1UL) } returns AsyncResult.Success(one)

        assertEquals(AsyncResult.Success(many), dll.getAll())
        assertEquals(AsyncResult.Success(one), dll.getById(1UL))
    }

    /**
     * GIVEN a DLL over a service
     * WHEN a single-item write is called
     * THEN the service's single-item member is the one that runs
     */
    @Test
    fun `single-item writes reach the single-item members`() {
        dll.create(one)
        dll.update(one)
        dll.delete(1UL)

        verify(exactly = 1) { service.create(one) }
        verify(exactly = 1) { service.update(one) }
        verify(exactly = 1) { service.delete(1UL) }
    }

    /**
     * GIVEN a DLL over a service
     * WHEN a batch write is called
     * THEN the service's list overload runs, not the single-item one
     *
     * This is the failure the layer is actually exposed to: the overloads differ only by argument
     * type, so a batch call wired to the wrong one type-checks.
     */
    @Test
    fun `batch writes reach the list overloads`() {
        dll.createAll(many)
        dll.updateAll(many)
        dll.deleteAll(listOf(1UL, 2UL))

        verify(exactly = 1) { service.create(many) }
        verify(exactly = 1) { service.update(many) }
        verify(exactly = 1) { service.delete(listOf(1UL, 2UL)) }

        verify(exactly = 0) { service.create(any<Workflow>()) }
        verify(exactly = 0) { service.update(any<Workflow>()) }
        verify(exactly = 0) { service.delete(any<ULong>()) }
    }
}
