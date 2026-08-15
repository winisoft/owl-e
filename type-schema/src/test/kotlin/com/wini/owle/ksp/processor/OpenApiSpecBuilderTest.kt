package com.wini.owle.ksp.processor

import com.google.devtools.ksp.processing.KSPLogger
import com.wini.owle.content.oas.*
import com.wini.owle.content.oas.schema.ObjectSchemaDoc
import com.wini.owle.content.oas.schema.PrimitiveSchemaDoc
import kotlinx.serialization.json.*

/*
class OpenApiSpecBuilderTest {

    private val logger: KSPLogger = mockk(relaxed = true)
    private val specBuilder = OpenApiSpecBuilder(logger)

    @Test
    fun `fromEndpoints should produce empty paths and tags for no endpoints`() {
        val spec = specBuilder.fromEndpoints(emptyList())
        assertTrue(spec.paths.isEmpty())
        assertTrue(spec.tags.isEmpty())
    }

    @Test
    fun `fromEndpoints should correctly map endpoint and operation data`() {
        val endpoint1 = ApiEndpointDoc(
            name = "TestEndpoint",
            description = "Endpoint for testing",
            basePath = "/test",
            routes = listOf(
                ApiRouteDoc(
                    path = "/test/items",
                    operations = listOf(
                        ApiOperationDoc(
                            method = "get",
                            summary = "Get all items",
                            description = "Retrieves a list of all items.",
                            responses = mapOf(
                                200 to ApiResponseDoc(description = "Successful operation"),
                                404 to ApiResponseDoc(description = "Items not found")
                            ),
                            tags = listOf("items", "reading")
                        ),
                        ApiOperationDoc(
                            method = "post",
                            summary = "Create an item",
                            description = "Creates a new item.",
                            responses = mapOf(
                                201 to ApiResponseDoc(description = "Item created"),
                                400 to ApiResponseDoc(description = "Invalid input")
                            ),
                            tags = listOf("items", "writing"),
                            requestBody = ApiRequestBodyDoc(
                                description = "Item to create",
                                required = true,
                                content = mapOf(
                                    "application/json" to ApiMediaTypeDoc(
                                        schema = ObjectSchemaDoc(
                                            properties = mapOf(
                                                "id" to PrimitiveSchemaDoc(type = "string")
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            ),
            dtoType = "com.wini.owle.test.ItemDto",
            tags = listOf("items", "test")
        )

        val spec = specBuilder.fromEndpoints(listOf(endpoint1))

        // Verify Tags
        assertEquals(2, spec.tags.size)
        assertTrue(spec.tags.any { it.name == "items" && it.description == "" })
        assertTrue(spec.tags.any { it.name == "test" && it.description == "" })

        // Verify Paths
        assertEquals(1, spec.paths.size)
        assertTrue(spec.paths.containsKey("/test/items"))

        val pathItem = spec.paths["/test/items"]!!
        assertEquals(2, pathItem.size) // get and post methods

        // Verify GET operation
        val getOperation = pathItem["get"]!!.jsonObject
        assertEquals("Get all items", getOperation["summary"]!!.jsonPrimitive.content)
        assertEquals("Retrieves a list of all items.", getOperation["description"]!!.jsonPrimitive.content)
        val getTags = getOperation["tags"]!!.jsonArray
        assertEquals(2, getTags.size)
        assertTrue(getTags.contains(JsonPrimitive("items")))
        assertTrue(getTags.contains(JsonPrimitive("reading")))

        assertTrue(getOperation.containsKey("responses"))
        val getResponses = getOperation["responses"]!!.jsonObject
        assertEquals(2, getResponses.size)
        assertTrue(getResponses.containsKey("200"))
        assertEquals("Successful operation", getResponses["200"]!!.jsonObject["description"]!!.jsonPrimitive.content)

        // Verify POST operation
        val postOperation = pathItem["post"]!!.jsonObject
        assertEquals("Create an item", postOperation["summary"]!!.jsonPrimitive.content)
        assertTrue(postOperation.containsKey("requestBody"))
        val requestBody = postOperation["requestBody"]!!.jsonObject
        assertEquals("Item to create", requestBody["description"]!!.jsonPrimitive.content)
        assertTrue(requestBody["required"]!!.jsonPrimitive.boolean)
        val requestBodyContentSchema = requestBody["content"]!!.jsonObject["application/json"]!!.jsonObject["schema"]!!.jsonObject
        assertEquals("object", requestBodyContentSchema["type"]!!.jsonPrimitive.content)
        assertTrue(requestBodyContentSchema.containsKey("properties"))
        val props = requestBodyContentSchema["properties"]!!.jsonObject
        assertTrue(props.containsKey("id"))
        assertEquals("string", props["id"]!!.jsonObject["type"]!!.jsonPrimitive.content)
    }

    @Test
    fun `fromEndpoints should correctly create tags from multiple endpoints`() {
        val endpoint1 = ApiEndpointDoc(
            name = "Endpoint1",
            description = "Description 1",
            basePath = "/e1",
            routes = emptyList(),
            dtoType = "dto1",
            tags = listOf("tag1", "commonTag")
        )
        val endpoint2 = ApiEndpointDoc(
            name = "Endpoint2",
            description = "Description 2",
            basePath = "/e2",
            routes = emptyList(),
            dtoType = "dto2",
            tags = listOf("tag2", "commonTag")
        )

        val spec = specBuilder.fromEndpoints(listOf(endpoint1, endpoint2))

        assertEquals(3, spec.tags.size)
        assertTrue(spec.tags.contains(OasTag(name = "tag1", description = "")))
        assertTrue(spec.tags.contains(OasTag(name = "tag2", description = "")))
        assertTrue(spec.tags.contains(OasTag(name = "commonTag", description = "")))
    }
}


 */