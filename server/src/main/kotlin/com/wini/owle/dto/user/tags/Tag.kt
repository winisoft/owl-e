package com.wini.owle.dto.user.tags

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.user.User
import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    override val id: Long,
    val name: String,
    val description: String? = null,
    val user: User
) : StandardDto