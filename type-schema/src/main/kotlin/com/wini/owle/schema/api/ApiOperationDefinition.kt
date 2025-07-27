package com.wini.owle.schema.api


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class ApiOperationDefinition(
    val opResKey: String,
    val method: ApiOperationHttpMethod,
    val result: ApiOperationCardinality
) {

    companion object {
        const val QUALIFIED_NAME = "com.wini.owle.schema.api.ApiOperationDefinition"
    }



}