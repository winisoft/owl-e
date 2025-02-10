package com.wini.owle.models

sealed class StripePaymentMethods {

    data object AchDDirectDebit: StripePaymentMethods()


}