package com.wini.owle.content.decorator.model

/**
 * Marks a class as a model that should be included in API documentation.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class OwleModel(
    val description: String,
    val examples: Array<OwleExample>
)