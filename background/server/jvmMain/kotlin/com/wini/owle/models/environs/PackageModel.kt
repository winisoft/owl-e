package com.wini.owle.models.environs

import kotlinx.serialization.Serializable


@Serializable
data class PackageModel(
    val id: Long,
    val name: String,
    val description: String?,
    val version: String?,
    val dependencies: List<String> = emptyList(),
    val maintainers: List<String> = emptyList()
) {
    // Computed properties
    val hasDescription: Boolean
        get() = !description.isNullOrBlank()

    val hasVersion: Boolean
        get() = !version.isNullOrBlank()

    val hasDependencies: Boolean
        get() = dependencies.isNotEmpty()

    val hasMaintainers: Boolean
        get() = maintainers.isNotEmpty()
}