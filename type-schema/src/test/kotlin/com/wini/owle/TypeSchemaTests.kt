package com.wini.owle

import com.wini.owle.schema.wrapper.annotation.ApiEndpointAnnotationTest
import com.wini.owle.schema.wrapper.annotation.ApiOperationAnnotationTest
import com.wini.owle.schema.wrapper.annotation.ModelAnnotationTest
import com.wini.owle.schema.wrapper.annotation.error.ConstraintAnnotationTest
import com.wini.owle.schema.wrapper.annotation.error.ErrorAnnotationTest
import com.wini.owle.schema.wrapper.declaration.ApiEndpointDeclarationTest
import com.wini.owle.schema.wrapper.declaration.ApiOperationDeclarationTest
import com.wini.owle.schema.wrapper.declaration.ConstrainedFieldTest
import com.wini.owle.schema.wrapper.declaration.ModelDeclarationTest
import com.wini.owle.schema.wrapper.declaration.error.ErrorDeclarationTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested


class TypeSchemaTests {

    @DisplayName("")
    @Nested
    inner class SchemaWrapperTests {

        @Nested
        inner class ModelDeclarationTests: ModelDeclarationTest()

        @Nested
        inner class EndpointDeclarationTests: ApiEndpointDeclarationTest()

        @Nested
        open inner class ConstrainedFieldTests: ConstrainedFieldTest()

        @Nested
        inner class ApiOperationDeclarationTests: ApiOperationDeclarationTest()

        @Nested
        inner class ErrorDeclarationTests: ErrorDeclarationTest()

        @Nested
        inner class ModelAnnotationTests: ModelAnnotationTest()

        @Nested
        inner class ApiOperationAnnotationTests: ApiOperationAnnotationTest()

        @Nested
        inner class ApiEndpointAnnotationTests: ApiEndpointAnnotationTest()

        @Nested
        inner class ErrorAnnotationTests: ErrorAnnotationTest()

        @Nested
        inner class ConstraintAnnotationImplTests: ConstraintAnnotationTest()

        @Nested
        inner class ModelAnnotationImplTests: ModelAnnotationTest()
    }


}