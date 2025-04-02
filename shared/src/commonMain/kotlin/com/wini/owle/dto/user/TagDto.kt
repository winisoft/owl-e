package com.wini.owle.dto.user

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class TagDto(
    val id: Long,
    val name: String,
    val description: String? = null,
    val user: UserDto
) : BaseDto