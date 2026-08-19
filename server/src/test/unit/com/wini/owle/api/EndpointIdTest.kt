package com.wini.owle.api

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.tags.targets.DistributionTag
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.entity.tags.DistributionTags
import com.wini.owle.error.MissingParameter
import com.wini.owle.error.MultipleParamFailure
import com.wini.owle.error.ParamValueMalformedLong
import com.wini.owle.generated.DistributionTagDaoDelegate
import io.ktor.http.parametersOf
import io.ktor.server.application.ApplicationCall
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


/**
 * Reading an id off a request, once per kind of key.
 *
 * This is the endpoint layer's only real logic - everything past it delegates - and it is the one
 * place the three id kinds genuinely diverge, so it is covered per kind rather than per type. The
 * generated endpoints are each one of these three classes with its type arguments pinned, and
 * [com.wini.owle.wiring.DiGraphTest] already proves each of those resolves separately.
 */
@DisplayName("Endpoint id parsing")
abstract class EndpointIdTest {

    private fun callWith(vararg params: Pair<String, String>): ApplicationCall = mockk {
        every { parameters } returns parametersOf(params.associate { it.first to listOf(it.second) })
    }

    @Nested
    @DisplayName("ULong id")
    inner class ULongId {

        private val endpoint = ULongIdEndpointImpl(
            dll = mockk(relaxed = true),
            endpointPath = "/x",
            serializer = Workflow.serializer()
        )

        private fun read(vararg params: Pair<String, String>) =
            with(endpoint) { with(callWith(*params)) { readId() } }

        /**
         * GIVEN a request carrying a numeric id
         * WHEN the id is read
         * THEN it is that number
         */
        @Test
        fun `reads a numeric id`() {
            assertEquals(AsyncResult.Success(42UL), read("id" to "42"))
        }

        /**
         * GIVEN a request with no id parameter
         * WHEN the id is read
         * THEN it fails naming the parameter rather than defaulting
         */
        @Test
        fun `a missing id is reported, not defaulted`() {
            val result = read("other" to "42")
            assertEquals(MissingParameter("id"), (result as AsyncResult.Failure).error)
        }

        /**
         * GIVEN a request whose id is not a number
         * WHEN the id is read
         * THEN it fails as malformed
         */
        @Test
        fun `a non-numeric id is malformed`() {
            val result = read("id" to "abc")
            assertInstanceOf(ParamValueMalformedLong::class.java, (result as AsyncResult.Failure).error)
        }

        /**
         * GIVEN a request whose id is negative
         * WHEN the id is read
         * THEN it fails cleanly rather than throwing
         *
         * The key is unsigned, so a negative is as malformed as a word is.
         */
        @Test
        fun `a negative id is malformed rather than an exception`() {
            val result = read("id" to "-5")
            assertInstanceOf(ParamValueMalformedLong::class.java, (result as AsyncResult.Failure).error)
        }
    }

    @Nested
    @DisplayName("String id")
    inner class StringId {

        private val endpoint = StringIdEndpointImpl(
            dll = mockk(relaxed = true),
            endpointPath = "/x",
            serializer = Distribution.serializer()
        )

        private fun read(vararg params: Pair<String, String>) =
            with(endpoint) { with(callWith(*params)) { readId() } }

        /**
         * GIVEN a request whose id is a package name
         * WHEN the id is read
         * THEN it is taken as sent
         *
         * `distributions` is keyed by name, so ids here are words and must not be number-checked.
         */
        @Test
        fun `reads a non-numeric id unchanged`() {
            assertEquals(AsyncResult.Success("requests"), read("id" to "requests"))
        }

        /**
         * GIVEN a request with no id parameter
         * WHEN the id is read
         * THEN it fails naming the parameter
         */
        @Test
        fun `a missing id is reported`() {
            val result = read("other" to "requests")
            assertEquals(MissingParameter("id"), (result as AsyncResult.Failure).error)
        }

        /**
         * GIVEN a request whose id is present but empty
         * WHEN the id is read
         * THEN it is treated as missing, since it addresses no row
         */
        @Test
        fun `a blank id counts as missing`() {
            val result = read("id" to "")
            assertEquals(MissingParameter("id"), (result as AsyncResult.Failure).error)
        }
    }

    @Nested
    @DisplayName("Composite id")
    inner class CompositeIdParts {

        // tagged_distributions keys on the distribution *name* and a numeric tag id, so one key
        // exercises both column kinds
        private val delegate = DistributionTagDaoDelegate()

        private val endpoint = CompositeIdEndpointImpl(
            dll = mockk(relaxed = true),
            validator = mockk(relaxed = true),
            daoDelegate = delegate,
            endpointPath = "/x",
            serializer = DistributionTag.serializer()
        )

        private fun read(vararg params: Pair<String, String>) =
            endpoint.compositeIdFromRequestParams(
                entityColumnsByReqParams = delegate.columnsByRequestParam,
                params = params.associate { it.first to listOf(it.second) }
            )

        private fun errorsOf(result: AsyncResult<*>) =
            ((result as AsyncResult.Failure).error as MultipleParamFailure).failed

        /**
         * GIVEN a key whose parts are a package name and a numeric tag id
         * WHEN both are supplied
         * THEN the id is built with each part as its own column stores it
         *
         * Every part used to be parsed as a number, so this request - the only kind the six
         * distribution-keyed endpoints ever receive - was rejected as malformed.
         */
        @Test
        fun `builds an id from a text part and a numeric part`() {
            val result = read("distribution" to "requests", "tag_id" to "7")
            assertInstanceOf(AsyncResult.Success::class.java, result, "got $result")

            val expected = CompositeID {
                it[DistributionTags.distribution] = "requests"
                it[DistributionTags.tag] = 7UL
            }
            assertEquals(expected, (result as AsyncResult.Success).data)
        }

        /**
         * GIVEN a key part that is missing
         * WHEN the id is read
         * THEN it fails naming that part
         */
        @Test
        fun `a missing key part is reported`() {
            assertEquals(listOf(MissingParameter("tag_id")), errorsOf(read("distribution" to "requests")))
        }

        /**
         * GIVEN a numeric key part that is negative
         * WHEN the id is read
         * THEN it fails cleanly
         *
         * This passed the old `toLongOrNull` check and then threw out of `toULong`.
         */
        @Test
        fun `a negative numeric part fails rather than throwing`() {
            val errors = errorsOf(read("distribution" to "requests", "tag_id" to "-1"))
            assertInstanceOf(ParamValueMalformedLong::class.java, errors.single())
        }

        /**
         * GIVEN a request wrong in more than one way
         * WHEN the id is read
         * THEN every fault is reported together
         */
        @Test
        fun `all bad parts are reported at once`() {
            val errors = errorsOf(read("tag_id" to "nope"))
            assertEquals(2, errors.size, "expected both parts to be reported, got $errors")
            assertTrue(errors.any { it is MissingParameter })
            assertTrue(errors.any { it is ParamValueMalformedLong })
        }
    }
}
