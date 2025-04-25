package com.wini.owle.validators.user

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.user.User
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class UserValidator : BaseValidator<User>() {
    companion object {
        const val MAX_EMAIL_LENGTH = 255
        const val MAX_PASSWORD_LENGTH = 255
        const val MAX_RESET_TOKEN_LENGTH = 255
        const val MAX_OAUTH_PROVIDER_LENGTH = 50
        const val MAX_OAUTH_ID_LENGTH = 255
        const val MAX_API_KEY_LENGTH = 255
    }

    override fun validate(dto: User): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // email is required, must be valid email format, and has max length
        if (dto.email.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "email"))
        }
        if (dto.email.length > MAX_EMAIL_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "email", MAX_EMAIL_LENGTH))
        }
        if (!isValidEmail(dto.email)) {
            violations.add(TypeValidation.InvalidEmail(dto.typeName, dto.idToString(), "email"))
        }

        // password is required and has max length
        if (dto.password.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "password"))
        }
        if (dto.password.length > MAX_PASSWORD_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "password", MAX_PASSWORD_LENGTH))
        }

        // resetToken is optional but has max length if present
        dto.resetToken?.let { token ->
            if (token.length > MAX_RESET_TOKEN_LENGTH) {
                violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "resetToken", MAX_RESET_TOKEN_LENGTH))
            }
        }

        // oauthProvider is optional but has max length if present
        dto.oauthProvider?.let { provider ->
            if (provider.length > MAX_OAUTH_PROVIDER_LENGTH) {
                violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "oauthProvider", MAX_OAUTH_PROVIDER_LENGTH))
            }
        }

        // oauthId is optional but has max length if present
        dto.oauthId?.let { id ->
            if (id.length > MAX_OAUTH_ID_LENGTH) {
                violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "oauthId", MAX_OAUTH_ID_LENGTH))
            }
        }

        // apiKey is optional but has max length if present
        dto.apiKey?.let { key ->
            if (key.length > MAX_API_KEY_LENGTH) {
                violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "apiKey", MAX_API_KEY_LENGTH))
            }
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return emailRegex.matches(email)
    }
}