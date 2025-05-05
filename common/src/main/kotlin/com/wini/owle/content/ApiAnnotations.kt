package com.wini.owle.content


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OwleApiEndpoint(
    val description: String,
    val tags: Array<String>
)

@Target(AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
annotation class ApiMethod(
    val summary: String,
    val description: String
)

@Target(AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
annotation class RequestSuccessExample(
    val code: Int = 200,
    val description: String = "successful operation",
)

@Target(AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
annotation class ApiMethodResponseCase(
    val httpCode: Int,
    val description: String
)

@Target(AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
annotation class ApiMethodSuccessCase(
    val json: String,
    val httpCode: Int = 200,
    val description: String= "successful operation"
)
