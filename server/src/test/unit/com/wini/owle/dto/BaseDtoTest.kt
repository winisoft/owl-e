package com.wini.owle.dto

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test


@OptIn(InternalSerializationApi::class)
abstract class BaseDtoTest<T : BaseDto>(
    private val dtoClass: Class<T>
) {

    private val exampleInstance = DtoTestInstanceFactory.createDtoInstance(dtoClass)

    @Test
    @OptIn(InternalSerializationApi::class)
    fun `serialization should produce valid JSON`() {
        val json = Json.encodeToString(dtoClass.kotlin.serializer(), exampleInstance)
        assertNotNull(json, "JSON serialization returned null")
    }

    @Test
    @OptIn(InternalSerializationApi::class)
    fun `deserialization should recreate original object`() {
        val json = Json.encodeToString(dtoClass.kotlin.serializer(), exampleInstance)
        val deserialized = Json.decodeFromString(dtoClass.kotlin.serializer(), json)
        assertEquals(exampleInstance, deserialized, "Deserialized object does not match the original")
    }

    @Test
    @OptIn(InternalSerializationApi::class)
    fun `round-trip serialization-deserialization should be consistent`() {
        val json = Json.encodeToString(dtoClass.kotlin.serializer(), exampleInstance)
        val deserialized = Json.decodeFromString(dtoClass.kotlin.serializer(), json)
        val reserialized = Json.encodeToString(dtoClass.kotlin.serializer(), deserialized)
        assertEquals(json, reserialized, "Round-trip serialization did not produce consistent JSON")
    }
}








