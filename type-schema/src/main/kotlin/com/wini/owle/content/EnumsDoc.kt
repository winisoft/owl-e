package com.wini.owle.content

data class EnumDoc(
    val description: String?,
    val values: List<EnumValueDoc>
)

data class EnumValueDoc(
    val name: String,
    val doc: String?
)
