package com.wini.owle.schema.error


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class OwleDocsError(
    val id: String,
    val ignoredInDocs: Boolean = false,
    val includedProperties: Array<String> = ["errorCode","message"]
)