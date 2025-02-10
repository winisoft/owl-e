package com.wini.owle.air.api.operations


abstract class AbstractParameter(
    val `in`: String? = null,
    val name: String? = null,
    override val description: String? = null,
    val access: String? = null,
    val pattern: String? = null,
    val readOnly: Boolean? = null,
    override val vendorExtensions: Map<String, Boolean> = LinkedHashMap(),
    override val required: Boolean = false
) : Parameter