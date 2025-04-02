package com.wini.owle.models.workflows

import com.wini.owle.dto.workflows.StoreMethodDto
import kotlinx.serialization.Serializable


@Serializable
data class DataPointModel(
    val id: Long,
    val workflowId: Long,
    val name: String,
    val presetValue: String?,
    val storeMethod: StoreMethodDto
) {

    val isReadOnly: Boolean
        get() = presetValue?.let{ true } ?: false

}


