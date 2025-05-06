package com.wini.owle.content.decorator

import com.wini.owle.error.InconsistentOAuthId
import com.wini.owle.error.InvalidOAuthProvider
import com.wini.owle.error.PreventsTimeParadoxes


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PreventsTimeParadoxes(
    val happensFirstField: String,
    val happensSecondField: String,
    val errorCode: String = PreventsTimeParadoxes.ERROR_CODE,
    val message: String = PreventsTimeParadoxes.MESSAGE
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class InconsistentOAuthId(
    val oauthProviderName: String = InconsistentOAuthId.ERROR_CODE,
    val oauthIdName: String = InconsistentOAuthId.MESSAGE
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OAuthProviderEnum(
    val errorCode: String = InvalidOAuthProvider.ERROR_CODE,
    val message: String = InvalidOAuthProvider.MESSAGE
)