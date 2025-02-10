package com.wini.owle.air.api.openapi

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.wini.owle.ui.style.LoTheme
import com.wini.owle.ui.main.composed.common.Hyperlink
import com.wini.owle.air.api.openapi.junk.Description
import io.swagger.v3.oas.models.info.Info

@Composable
fun Information(info: Info, openApi: String, apiAction: (ApiAction) -> Unit) {
    Column {
        Main(info.title, info.version, openApi)
        Description(info.description ?: "")
        Hyperlink(
            text = "Terms of service",
            modifier = Modifier.alpha(if (info.termsOfService.isNullOrEmpty()) 0.0f else 1.0f),
            url = info.termsOfService,
            onClicked = { apiAction(ApiAction.FollowHyperlink(info.termsOfService)) }
        )
        Hyperlink(
            text = "Contact the developer${info.contact?.name?.run { if (isNullOrEmpty()) "" else ": $this" } }",
            modifier = Modifier.alpha(if (info.termsOfService.isNullOrEmpty()) 0.0f else 1.0f),
            url = info.contact?.email ?: "",
            onClicked = { apiAction(ApiAction.FollowHyperlink(info.contact.email)) }
        )
        Hyperlink(
            text = info.license.name.run { ifEmpty { "License" } },
            modifier = Modifier.alpha(if (info.termsOfService.isNullOrEmpty()) 0.0f else 1.0f),
            url = info.license.url,
            onClicked = { apiAction(ApiAction.FollowHyperlink(info.license.url)) }
        )
    }
}

@Composable
fun Main(title: String, version: String, specVersion: String) {
    Title(title, version, specVersion)
    Row {
        Hyperlink("JSON Spec", null ?: "", onClicked = { ApiAction.ShowSpecJson })
        Hyperlink("YAML Spec", null ?: "", onClicked = { ApiAction.ShowSpecYaml })
    }
}

@Preview
@Composable
fun MainPreview() {
    Main("Swagger Petstore - OpenAPI 3.0", "1.0.11", "OAS3")
}

@Composable
fun Title(title: String, specVersion: String, swaggerVersion: String) {

    Row(
        modifier = Modifier.padding(LoTheme.dimensions.paddingLarge)
    ) {
        Text(
            text = title,
            style = LoTheme.typography.h1,
            color = LoTheme.colors.textPrimary
        )

        Text(
            text = specVersion,
            color = LoTheme.colors.onPrimary,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(LoTheme.colors.primary)
                .align(Alignment.Top)
        )

        Text(
            text = if (swaggerVersion == "1.2") "OAS3" else "",
            color = LoTheme.colors.onSecondary,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(LoTheme.colors.secondary)
                .align(Alignment.Top)
                .alpha(if (swaggerVersion == "1.2") 1.0F else 0.0F)
        )
    }
}