package com.wini.owle.air.api.models

import com.wini.owle.air.api.models.CodegenModel
import kotlinx.serialization.Serializable

@Serializable
data class ModelsItem(
    val importPath: String,
    val model: CodegenModel,
    val schema: Schema
)