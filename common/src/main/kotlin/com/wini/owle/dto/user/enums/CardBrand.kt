package com.wini.owle.dto.user.enums

import com.wini.owle.dto.DbToken
import kotlinx.serialization.Serializable


enum class CardBrand(override val dbValue: String) : DbToken {

    
    AMERICAN_EXPRESS("American Express"),

    
    DINERS_CLUB("Diners Club"),

    
    DISCOVER("Discover"),

    
    EFTPOS_AUSTRALIA("Eftpos Australia"),

    
    GIROCARD("Girocard"),

    
    JCB("JCB"),

    
    MASTERCARD("MasterCard"),

    
    UNIONPAY("UnionPay"),

    
    VISA("Visa"),

    
    UNKNOWN("Unknown")

}