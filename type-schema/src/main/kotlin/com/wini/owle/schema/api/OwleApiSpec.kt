package com.wini.owle.schema.api

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class OwleApiSpec(
    val id: String,
    val tags: Array<String> = []
)