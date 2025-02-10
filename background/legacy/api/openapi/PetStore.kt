package com.wini.owle.air.api.openapi

import io.swagger.v3.oas.models.*
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.parameters.Parameter
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses
import io.swagger.v3.oas.models.servers.Server
import io.swagger.v3.oas.models.tags.Tag
import io.swagger.v3.oas.models.media.Schema

object PetStore {
    val instance = OpenAPI().apply {
        openapi = "3.0.2"
        info = Info().apply {
            version = "1.0.0"
            title = "OpenAPI V3 - Swagger Petstore"
            license = License().apply {
                name = "Apache 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.html"
            }
            contact = Contact().apply {
                name = "John Smith"
                email = "johnsmith@johnsmith.com"
                url = "johnsmith.com"
            }
            description =
                "This is a sample Pet Store Server based on the OpenAPI 3.0 specification.  You can find out more about\\nSwagger at [http://swagger.io](http://swagger.io). In the third iteration of the pet store, we've switched to the design first approach!\\nYou can now help us improve the API whether it's by making changes to the definition itself or to the code.\\nThat way, with time, we can improve the API in general, and expose some of the new features in OAS3.\\n\\nSome useful links:\\n- [The Pet Store repository](https://github.com/swagger-api/swagger-petstore)\\n- [The source API definition for the Pet Store](https://github.com/swagger-api/swagger-petstore/blob/master/src/main/resources/openapi.yaml)"
            termsOfService = "http://swagger.io/terms/"
        }
        externalDocs = ExternalDocumentation().apply {
            description = "Find out more about Swagger"
            url = "http://swagger.io"
        }
        servers = mutableListOf(
            Server().apply {
                url = "/api/v3"
            }
        )
        tags = mutableListOf(
            Tag().apply {
                name = "pet"
                description = "Everything about your pets"
                externalDocs = ExternalDocumentation().apply {
                    description = "Find out more"
                    url = "http://swagger.io"
                }
            },
            Tag().apply {
                name = "store"
                description = "Access to Petstore orders"
                externalDocs = ExternalDocumentation().apply {
                    description = "Find out more about our store"
                    url = "http://swagger.io"
                }
            },
            Tag().apply {
                name = "user"
                description = "Operations about user"
            }
        )
        paths = Paths().apply {
            PathItem().apply {
                get = Operation().apply {
                    tags = mutableListOf(
                        "user"
                    )
                    summary = "Get user by user name"
                    description = ""
                    operationId = "getUserByName"
                    parameters = listOf(
                        Parameter().apply {
                            name = "username"
                            `in` = "path"
                            description =
                                "The name that needs to be fetched. Use user1 for testing. "
                            required = true
//                            schema = Schema().apply {
//                                type = "string"
//                            }
                        }
                    )
                    responses = ApiResponses().apply {
                        default = ApiResponse().apply {
                            description = "successful operation"
                            content = Content().apply {
                                Schema<MediaType>()
                                    .apply { `$ref` = "#/components/schemas/User" }
                                    .apply { addMediaType("application/xml", default) }
                                Schema<MediaType>()
                                    .apply { `$ref` = "#/components/schemas/User" }
                                    .apply { addMediaType("application/json", default) }
                            }
                        }
                    }
                }
                put = Operation().apply {

                }
                delete = Operation().apply {

                }
                servers = listOf(
                    Server().apply {

                    }
                )
                parameters = listOf(

                )
                `$ref` = null
                extensions = null
            }
        }
    }
}

//                Responses
//            }
//            "/pet": {
//            "put": {
//            "tags": [
//            "pet"
//            ],
//            "summary": "Update an existing pet",
//            "description": "Update an existing pet by Id",
//            "operationId": "updatePet",
//            "requestBody": {
//            "description": "Update an existent pet in the store",
//            "content": {
//            "application/json": {
//            "schema": {
//            "${'$'}ref": "#/components/schemas/Pet"
//        }
//        },
//            "application/xml": {
//            "schema": {
//            "${'$'}ref": "#/components/schemas/Pet"
//        }
//        },
//            "application/x-www-form-urlencoded": {
//            "schema": {
//            "${'$'}ref": "#/components/schemas/Pet"
//        }
//        }
//        },
//            "required": true
//        },
//            "responses": {
//            "200": {
//            "description": "Successful operation",
//            "content": {
//            "application/xml": {
//            "schema": {
//            "${'$'}ref": "#/components/schemas/Pet"
//        }
//        },
//            "application/json": {
//            "schema": {
//            "${'$'}ref": "#/components/schemas/Pet"
//        }
//        }
//        }
//        },
//            "400": {
//            "description": "Invalid ID supplied"
//        },
//            "404": {
//            "description": "Pet not found"
//        },
//            "405": {
//            "description": "Validation exception"
//        }
//        },
//            "security": [
//            {
//                "petstore_auth": [
//                "write:pets",
//                "read:pets"
//                ]
//            }
//            ]
//        },
//            "post": {
//            "tags": [
//            "pet"
//            ],
//            "summary": "Add a new pet to the store",
//            "description": "Add a new pet to the store",
//            "operationId": "addPet",
//            "requestBody": {
//            "description": "Create a new pet in the store",
//            "content": {
//            "application/json": {
//            "schema": {
//            "${'$'}ref": "#/components/schemas/Pet"
//        }
//        },
//            "application/xml": {
//            "schema": {
//            "${'$'}ref": "#/components/schemas/Pet"
//        }
//        },
//            "application/x-www-form-urlencoded": {
//            "schema": {
//            "${'$'}ref": "#/components/schemas/Pet"
//        }
//        }
//        },
//            "required": true
//        },
//            "responses": {
//            "200": {
//            "description": "Successful operation",
//            "content": {
//            "application/xml": {
//            "schema": {
//            "${'$'}ref": "#/components/schemas/Pet"
//        }
//        },
//            "application/json": {
//            "schema": {
//            "${'$'}ref": "#/components/schemas/Pet"
//        }
//        }
//        }
//        },
//            "405": {
//            "description": "Invalid input"
//        }
//        },
//            "security": [
//            {
//                "petstore_auth": [
//                "write:pets",
//                "read:pets"
//                ]
//            }
//            ]
//        }
//        },
//        }
//    }

    val yaml: String = """
openapi: "3.0.0"
info:
  version: 1.0.0
  title: Swagger Petstore
  license:
    name: MIT
servers:
  - url: http://petstore.swagger.io/v1
paths:
  /pets:
    get:
      summary: List all pets
      operationId: listPets
      tags:
        - pets
      parameters:
        - name: limit
          in: query
          description: How many items to return at one time (max 100)
          required: false
          schema:
            type: integer
            format: int32
      responses:
        '200':
          description: A paged array of pets
          headers:
            x-next:
              description: A link to the next page of responses
              schema:
                type: string
          content:
            application/json:    
              schema:
                ${'$'}ref: "#/components/schemas/Pets"
        default:
          description: unexpected error
          content:
            application/json:
              schema:
                ${'$'}ref: "#/components/schemas/Error"
    post:
      summary: Create a pet
      operationId: createPets
      tags:
        - pets
      responses:
        '201':
          description: Null response
        default:
          description: unexpected error
          content:
            application/json:
              schema:
                ${'$'}ref: "#/components/schemas/Error"
  /pets/{petId}:
    get:
      summary: Info for a specific pet
      operationId: showPetById
      tags:
        - pets
      parameters:
        - name: petId
          in: path
          required: true
          description: The id of the pet to retrieve
          schema:
            type: string
      responses:
        '200':
          description: Expected response to a valid request
          content:
            application/json:
              schema:
                ${'$'}ref: "#/components/schemas/Pet"
        default:
          description: unexpected error
          content:
            application/json:
              schema:
                ${'$'}ref: "#/components/schemas/Error"
components:
  schemas:
    Pet:
      type: object
      required:
        - id
        - name
      properties:
        id:
          type: integer
          format: int64
        name:
          type: string
        tag:
          type: string
    Pets:
      type: array
      items:
        ${'$'}ref: "#/components/schemas/Pet"
    Error:
      type: object
      required:
        - code
        - message
      properties:
        code:
          type: integer
          format: int32
        message:
          type: string
    """.trimIndent()