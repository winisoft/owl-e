package com.wini.owle.models.workflows

import com.wini.owle.models.StoreMethodModel
import kotlinx.serialization.Serializable


@Serializable
data class DataPointModel(
    val id: Long,
    val workflowId: Long,
    val name: String,
    val presetValue: String?,
    val storeMethod: StoreMethodModel
) {

    val isReadOnly: Boolean
        get() = presetValue?.let{ true } ?: false

}


