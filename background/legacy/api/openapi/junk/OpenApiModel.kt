package com.wini.owle.air.api.openapi.junk

import com.wini.owle.ui.util.commonMarkToHtml
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info


class OpenApiModel(val openApi: OpenAPI): OpenAPI() {

    val infoModel = InfoModel(isV3Spec = openApi.openapi.startsWith("3"))
}

class InfoModel(val isV3Spec: Boolean): Info() {

    val termsLabel: String? = if (termsOfService.isNullOrEmpty()) "Terms of Service" else null
    val contactLabel: String? = if (contact.email.isNullOrEmpty()) "Contact the Developer" else null
    val licenseLabel: String? = if (license.url.isNullOrEmpty()) license.name else null
    val descriptionHtml: String = commonMarkToHtml(description)

}