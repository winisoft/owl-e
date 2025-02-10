package com.wini.owle.air.api.operations

import kotlinx.serialization.Serializable

@Serializable
data class Consume(
    val mediaType: String
)