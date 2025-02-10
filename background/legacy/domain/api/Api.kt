package com.wini.owle.air.domain.api


import com.wini.owle.air.inlines.*
import io.swagger.v3.oas.models.OpenAPI

data class Api(
    val id: ApiId,
    val name: Name,
    val loRoute: LoRoute,
    val openApi: String?
)