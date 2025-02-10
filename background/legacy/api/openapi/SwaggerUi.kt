package com.wini.owle.air.api.openapi

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wini.owle.air.db.backoffice.api.openapi.OpenAPI
import com.wini.owle.ui.openapi.Operation
import com.wini.owle.ui.style.LoTheme
import java.lang.reflect.Modifier


@Composable
fun SwaggerUi(openApi: OpenAPI, apiAction: (ApiAction) -> Unit) {
    Column(modifier = Modifier.background(LoTheme.colors.background)) {
        Information(openApi.info, openApi.openapi, apiAction)
        Scheme(openApi.components?.securitySchemes, apiAction)
        LazyColumn {
            val operations = openApi.paths.values.map { it.get }
            items(operations.size) {
                operations[it].apply {
                    Operation(
                        tags.toTypedArray(),
                        summary,
                        description,
                        externalDocs,
                        operationId,
                        parameters,
                        requestBody,
                        io.swagger.models.Response(),
                        callbacks,
                        deprecated = deprecated,
                        emptyArray(),
                        servers.toTypedArray()
                    )
                }
            }
        }
//        Models()
//        Validator()
    }
}

@Preview
@Composable
fun SwaggerUiPreview(){
    val petStore = PetStore.instance.run { sequenceOf(this) }
    SwaggerUi(petStore.first(), {})
}