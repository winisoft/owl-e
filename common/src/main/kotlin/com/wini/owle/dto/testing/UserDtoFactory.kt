package com.wini.owle.dto.testing

import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.user.User
import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.dto.user.enums.UserStatus

/**
 * Canned [user] DTO instances for tests.
 *
 * One factory per DTO package, composed by [DtoTestInstanceFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object UserDtoFactory {

    fun paymentMethodDto(
        id: ULong = 1UL,
        stripePaymentMethodId: String? = "1AF002B1B22200",
        user: User = userDto(),
        cardBrand: CardBrand? = CardBrand.VISA,
        lastFour: String? = "4103",
        isDefault: Boolean = true,
        createdAt: String = "2024-12-21T15:00:00",
        updatedAt: String = "2024-12-22T15:00:00"
    ) : PaymentMethod {
        return PaymentMethod(
            id = id,
            stripePaymentMethodId = stripePaymentMethodId,
            user = user,
            cardBrand = cardBrand,
            lastFour = lastFour,
            isDefault = isDefault,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun userDto(
        id: ULong = 1UL,
        email: String = "jim@jim.com",
        password: String = "secretStuff1!",
        resetToken: String? = "3ij3oi2joi342j",
        oauthProvider: String? = "Google",
        oauthId: String? = "IROEJOWejofdjsojfoaj3ioj439u2jiof",
        userStatus: UserStatus = UserStatus.REGISTERED,
        apiKey: String? = "jowejrojoewjrew",
        createdAt: String = "2024-12-24T15:00:00",
        updatedAt: String = "2024-12-25T15:00:00",
        paymentMethods: List<PaymentMethod> = emptyList()
    ) : User {
        return User(
            id = id,
            email = email,
            password = password,
            resetToken = resetToken,
            oauthProvider = oauthProvider,
            oauthId = oauthId,
            userStatus = userStatus,
            apiKey = apiKey,
            createdAt = createdAt,
            updatedAt = updatedAt,
            paymentMethods = paymentMethods
        )
    }
}
