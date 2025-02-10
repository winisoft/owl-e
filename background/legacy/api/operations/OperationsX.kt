package com.wini.owle.air.api.operations

import kotlinx.serialization.Serializable

@Serializable
data class OperationsX(
    val classname: String,
    val operation: List<Operation>,
    val pathPrefix: String
)