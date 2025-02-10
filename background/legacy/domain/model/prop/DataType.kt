package com.wini.owle.air.domain.model.prop

import com.wini.owle.air.inlines.CalType
import com.wini.owle.air.db.library.DataType as DbEntityType

sealed class DataType(val id: Int, val name: kotlin.String, val adbOption: kotlin.String) {

    override fun toString(): kotlin.String = name

    object Invalid: DataType(Int.MIN_VALUE, "INVALID", "")

    object String: DataType(10, "String", "")

    object Boolean: DataType(20, "Boolean", "")

    object Integer: DataType(40, "Integer", "")

    object Long: DataType(50, "Long", "")

    object Float: DataType(60, "Float", "")

    object Bytes: DataType(70, "Bytes","")

    object ArrayOfString: DataType(80, "String Array", "")

    object ArrayOfInteger: DataType(81, "Int Array", "")

    object ArrayOfFloat: DataType(82, "Float Array", "")

    object Todo: DataType(89, "Unknown", "")

    fun toEntity(): DbEntityType = when (this) {
        Invalid -> DbEntityType.Invalid
        String -> DbEntityType.String
        Boolean -> DbEntityType.Boolean
        Integer -> DbEntityType.Integer
        Long -> DbEntityType.Long
        Float -> DbEntityType.Float
        Bytes -> DbEntityType.Bytes
        ArrayOfFloat -> DbEntityType.ArrayOfFloat
        ArrayOfInteger -> DbEntityType.ArrayOfInteger
        ArrayOfString -> DbEntityType.ArrayOfString
        Todo -> DbEntityType.Todo
    }

    companion object  {

        fun fromHexString(value: kotlin.String?): DataType = value
            ?.run { fromInt(value.substring(4..5).toInt()) }
            ?: Invalid

        fun fromEntity(dataType: DbEntityType): DataType = fromInt(dataType.id)

        fun fromInt(value: Int): DataType = when (value) {
            String.id -> String
            Boolean.id -> Boolean
            Integer.id -> Integer
            Long.id -> Long
            Float.id -> Float
            Bytes.id -> Bytes
            else -> error("unanticipated DataType value: $value")
        }

        fun fromCalType(calDataType: CalType): DataType {
            return when (calDataType.value) {
                0L -> DataType.Boolean
                1L -> DataType.Integer
                2L -> DataType.Float
                3L -> DataType.String
                4L -> DataType.Todo
                6L -> DataType.ArrayOfInteger
                7L -> DataType.ArrayOfFloat
                8L -> DataType.Todo
                else -> error("Unanticipated cal type")
            }
        }
    }

}