package com.wini.owle.air.api.openapi

import io.swagger.v3.oas.models.security.SecurityScheme


sealed class ApiAction {

    data class ShowAuthorizations(val scheme: SecurityScheme) : ApiAction()

    object ShowSpecJson: ApiAction()

    object ShowSpecYaml: ApiAction()

    data class SchemeChanged(val focused: SecurityScheme): ApiAction()

    data class FollowHyperlink(val url: String?): ApiAction()
}