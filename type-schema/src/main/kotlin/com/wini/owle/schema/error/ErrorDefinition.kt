package com.wini.owle.schema.error


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ErrorDefinition(
    val errorResKey: String,
    val ignoredInDocs: Boolean = false,
    val includedProperties: Array<String> = ["errorCode","message"]
) {

    companion object {
        const val QUALIFIED_NAME = "com.wini.owle.schema.error.ErrorDefinition"
        const val FQN_STARTS_WITH = "com.wini.owle.schema.error.Constrain"
    }

}