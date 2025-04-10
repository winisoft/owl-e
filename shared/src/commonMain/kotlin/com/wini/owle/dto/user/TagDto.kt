package com.wini.owle.dto.user

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class TagDto(
    override val id: Long,
    val name: String,
    val description: String? = null,
    val user: UserDto
) : StandardDto