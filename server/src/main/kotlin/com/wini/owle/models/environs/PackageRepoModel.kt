package com.wini.owle.models.environs

import kotlinx.serialization.Serializable

@Serializable
data class PackageRepoModel(
    val id: Long,
    val name: String,
    val url: String,
    val description: String?,
    val credentials: String?,
    val isEnabled: Boolean = true,
    val priority: Int = 0,
    val tags: List<String> = emptyList()
) {
    // Computed properties
    val hasDescription: Boolean
        get() = !description.isNullOrBlank()

    val hasCredentials: Boolean
        get() = !credentials.isNullOrBlank()

    val hasTags: Boolean
        get() = tags.isNotEmpty()

    // Helper property to check if repo is valid for use
    val isUsable: Boolean
        get() = isEnabled && url.isNotBlank()
}