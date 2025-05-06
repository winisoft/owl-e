package com.wini.owle.content.decorator.model


/**
 * Defines an example for an OwleModel.
 */
@Target()
@Retention(AnnotationRetention.SOURCE)
annotation class OwleExample(
    val label: String,
    val json: String
)