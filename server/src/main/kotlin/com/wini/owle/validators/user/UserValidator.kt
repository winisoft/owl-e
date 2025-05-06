package com.wini.owle.validators.user


import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.InconsistentOAuthId
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.user.User
import com.wini.owle.validators.BaseValidator


@OwleValidator(User::class)
@InconsistentOAuthId( "oauthProvider", "oauthId")
class UserValidator : BaseValidator<User>() {

    companion object {
        const val MAX_EMAIL_LENGTH = 255
        const val MIN_PASSWORD_LENGTH = 127
        const val MAX_PASSWORD_LENGTH = 255
        const val MAX_RESET_TOKEN_LENGTH = 255
        const val MAX_OAUTH_ID_LENGTH = 255
        const val MAX_API_KEY_LENGTH = 255
    }

    override fun fieldValidations(dto: User):List<FieldConstraintError> {

        return listOf()
    }
}