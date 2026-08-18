package com.wini.owle.content.decorator


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PreventsTimeParadoxes(
    val happensFirstField: String,
    val happensSecondField: String,
    val errorCode: String,
    val message: String
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class InconsistentOAuthId(
    val oauthProviderName: String,
    val oauthIdName: String
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OAuthProviderEnum(
    val errorCode: String,
    val message: String
)