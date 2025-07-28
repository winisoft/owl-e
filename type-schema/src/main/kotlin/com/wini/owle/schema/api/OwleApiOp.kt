package com.wini.owle.schema.api

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class OwleApiOp(
    val target: String,
    val method: OpMethod,
    val result: OpResult
)