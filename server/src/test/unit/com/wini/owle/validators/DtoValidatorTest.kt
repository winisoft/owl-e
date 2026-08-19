package com.wini.owle.validators

import com.wini.owle.dto.ULongDto
import com.wini.owle.error.ConstraintError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


/**
 * [DtoValidator]'s own mechanism, as distinct from the rules it carries.
 *
 * The generated validators only supply `fieldValidations`; everything that decides what a
 * violation *is* lives in the default `validate` and `validateAll` here. Two behaviours are worth
 * pinning down, because both are easy to get subtly wrong and neither is visible from a rule:
 * that a constraint which did not fire is dropped rather than reported, and that `validateAll`
 * reports every DTO's violations rather than stopping at the first bad one.
 */
@DisplayName("DtoValidator")
abstract class DtoValidatorTest {

    private data class Probe(
        override val id: ULong = 1UL,
        val text: String? = null
    ) : ULongDto

    /** A validator carrying exactly the rules handed to it. */
    private fun validatorOf(rules: (Probe) -> List<ConstraintError>) =
        object : DtoValidator<Probe> {
            override fun fieldValidations(dto: Probe): List<ConstraintError> = rules(dto)
        }

    private val notEmptyAndNotBlank = validatorOf { dto ->
        listOf(StringNotEmptyError(Probe::text, dto), StringNotBlankError(Probe::text, dto))
    }

    @Test
    fun `validate reports nothing when no rule fires`() {
        val result = notEmptyAndNotBlank.validate(Probe(text = "fine"))

        assertTrue(result.isValid)
        assertTrue(result.violations.isEmpty())
    }

    @Test
    fun `validate keeps only the rules that actually fired`() {
        // "   " is blank but not empty, so exactly one of the two rules applies
        val result = notEmptyAndNotBlank.validate(Probe(text = "   "))

        assertFalse(result.isValid)
        assertEquals(1, result.violations.size, "a rule that did not fire must not be reported")
        assertTrue(result.violations.single() is StringNotBlankError<*>)
    }

    @Test
    fun `validate reports every rule that fired`() {
        val result = notEmptyAndNotBlank.validate(Probe(text = ""))

        assertEquals(2, result.violations.size, "empty is both empty and blank")
    }

    @Test
    fun `validateAll accumulates across every DTO rather than stopping at the first`() {
        val result = notEmptyAndNotBlank.validateAll(
            listOf(Probe(text = "fine"), Probe(text = ""), Probe(text = "   "))
        )

        assertFalse(result.isValid)
        assertEquals(
            3, result.violations.size,
            "two from the empty one, one from the blank one; the valid one contributes nothing"
        )
    }

    @Test
    fun `validateAll on an all-valid list is valid`() {
        val result = notEmptyAndNotBlank.validateAll(listOf(Probe(text = "a"), Probe(text = "b")))

        assertTrue(result.isValid)
    }

    @Test
    fun `validateAll on an empty list is valid`() {
        assertTrue(notEmptyAndNotBlank.validateAll(emptyList()).isValid)
    }

    @Test
    fun `alwaysValid passes anything`() {
        val validator = DtoValidator.alwaysValid<Probe>()

        assertTrue(validator.validate(Probe(text = "")).isValid)
        assertTrue(validator.validateAll(listOf(Probe(text = ""), Probe())).isValid)
    }

    @Test
    fun `an invalid result converts to a failure carrying its violations`() {
        val result = notEmptyAndNotBlank.validate(Probe(text = ""))
        val failure = result.asFailure()

        assertTrue(failure.error is com.wini.owle.error.MultipleTypeValidations)
        assertEquals(
            2,
            (failure.error as com.wini.owle.error.MultipleTypeValidations).violations.size
        )
    }
}
