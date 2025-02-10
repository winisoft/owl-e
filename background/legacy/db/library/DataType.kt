package com.wini.owle.air.db.library

import kotlin.String as StringKt
import kotlin.Boolean as BooleanKt

sealed class DataType(
    val id: Int,
    val platformName: StringKt = "",
    val codeGenSupported: BooleanKt = true,
    val commonName: StringKt = "",
    val isOas2Compliant: BooleanKt = false,
    val isOas3Compliant: BooleanKt = false,
    val additionalDefinitions: List<StringKt> = emptyList(),
    val adbOption: StringKt = ""
) {

    fun getName(type: com.wini.owle.air.db.library.DataType): kotlin.String = type::class.simpleName!!

    object Invalid: com.wini.owle.air.db.library.DataType(Int.MIN_VALUE, "INVALID")

    object String: com.wini.owle.air.db.library.DataType(10, "String") {
        override fun toString(): kotlin.String = getName(this)
    }

    object Boolean: com.wini.owle.air.db.library.DataType(20, "") {
        override fun toString(): kotlin.String = getName(this)
    }

    object Integer: com.wini.owle.air.db.library.DataType(40, "") {
        override fun toString(): kotlin.String = getName(this)
    }

    object Long: com.wini.owle.air.db.library.DataType(50, "") {
        override fun toString(): kotlin.String = getName(this)
    }

    object Float: com.wini.owle.air.db.library.DataType(60, "") {
        override fun toString(): kotlin.String = getName(this)
    }

    object Bytes: com.wini.owle.air.db.library.DataType(70, "") {
        override fun toString(): kotlin.String = getName(this)
    }

    object ArrayOfString: com.wini.owle.air.db.library.DataType(80, "String Array") {
        override fun toString(): kotlin.String = "String Array"
    }

    object ArrayOfInteger: com.wini.owle.air.db.library.DataType(81, "Int Array") {
        override fun toString(): kotlin.String = "Int Array"
    }

    object ArrayOfFloat: com.wini.owle.air.db.library.DataType(82, "Float Array") {
        override fun toString(): kotlin.String = "Float Array"
    }

    object Todo: com.wini.owle.air.db.library.DataType(89, "Todo") {
        override fun toString(): kotlin.String = "Todo"
    }

    companion object  {

        fun fromCalType(calibrationType: Int): com.wini.owle.air.db.library.DataType =
            com.wini.owle.air.db.library.DataType.Companion.fromInt(calibrationType)

        fun fromHexString(value: kotlin.String): com.wini.owle.air.db.library.DataType =
            com.wini.owle.air.db.library.DataType.Companion.fromInt(value.substring(4..5).toInt())

        fun fromInt(value: Int): com.wini.owle.air.db.library.DataType = when (value) {
            com.wini.owle.air.db.library.DataType.String.id -> com.wini.owle.air.db.library.DataType.String
            com.wini.owle.air.db.library.DataType.Boolean.id -> com.wini.owle.air.db.library.DataType.Boolean
            com.wini.owle.air.db.library.DataType.Integer.id -> com.wini.owle.air.db.library.DataType.Integer
            com.wini.owle.air.db.library.DataType.Long.id -> com.wini.owle.air.db.library.DataType.Long
            com.wini.owle.air.db.library.DataType.Float.id -> com.wini.owle.air.db.library.DataType.Float
            com.wini.owle.air.db.library.DataType.Bytes.id -> com.wini.owle.air.db.library.DataType.Bytes
            else -> error("unanticipated DataType value: $value")
        }
    }
}