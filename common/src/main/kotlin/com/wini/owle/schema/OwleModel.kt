package com.wini.owle.schema



@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class OwleModel(
    val skipValidation: Boolean = false,
    val description: String = "",
    val examples: Array<OwleExample> = []
)

annotation class OwleExample(
    val label: String,
    val json: String
)