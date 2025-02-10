package com.wini.owle.air.domain.endpoint


import com.wini.owle.air.inlines.ApiId
import com.wini.owle.air.inlines.EndpointId
import com.wini.owle.air.inlines.EndpointPath
import com.wini.owle.air.inlines.Name

data class Endpoint(
    val id: EndpointId,
    val apiId: ApiId,
    val path: EndpointPath,
    val name: Name
)