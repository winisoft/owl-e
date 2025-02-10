package com.wini.owle.air.db.library


import com.wini.owle.air.inlines.CalMax
import com.wini.owle.air.inlines.CalMin
import com.squareup.sqldelight.ColumnAdapter
import com.wini.owle.air.db.InlineAdapters
import com.wini.owle.air.db.cal.CalOverrideEntity
import com.wini.owle.air.db.cal.CalibrationEntity
import com.wini.owle.air.db.car.CarOverrideEntity
import com.wini.owle.air.db.car.CarPropEntity
import com.wini.owle.air.db.library.car.PropertySource
import com.wini.owle.air.db.prefs.SharedPrefEntity
import com.wini.owle.air.db.prefs.SharedPrefOverrideEntity
import com.wini.owle.air.db.sysprop.SystemPropEntity
import com.wini.owle.air.db.sysprop.SystemPropOverrideEntity


object PropDbAdapters {

    val calibrationEntityAdapter = CalibrationEntity.Adapter(
        calIdAdapter = InlineAdapters.String.calIdAdapter,
        calNameAdapter = InlineAdapters.String.calName,
        calValueAdapter = InlineAdapters.String.calValue,
        calDefFileNameAdapter = InlineAdapters.String.calDefFileName,
        calDefFileVersionAdapter = InlineAdapters.Long.calDefFileVersion,
        calVersionAdapter = InlineAdapters.Long.calVersion,
        calTypeAdapter = InlineAdapters.Long.calType,
        arrayLenAdapter = InlineAdapters.Long.arrayLen,
        enumIdAdapter = InlineAdapters.String.enumId,
        encodingAdapter = InlineAdapters.String.encoding,
        overriddenAdapter = InlineAdapters.Bool.calOverridden,
        minAdapter = object: ColumnAdapter<CalMin, String>, app.cash.sqldelight.ColumnAdapter<CalMin, String> {
            override fun decode(databaseValue: String): CalMin = when {
                databaseValue == "null" -> CalMin(null)
                databaseValue.contains(".") -> CalMin(databaseValue.toFloat())
                else -> CalMin(databaseValue.toFloat())
            }
            override fun encode(value: CalMin): String = when {
                value.value == null -> "null"
                else -> value.value.toString()
            }
        },
        maxAdapter = object: ColumnAdapter<CalMax, String>, app.cash.sqldelight.ColumnAdapter<CalMax, String> {
            override fun decode(databaseValue: String): CalMax = when {
                databaseValue == "null" -> CalMax(null)
                databaseValue.contains(".") -> CalMax(databaseValue.toFloat())
                else -> CalMax(databaseValue.toFloat())
            }
            override fun encode(value: CalMax): String = when {
                value.value == null -> "null"
                else -> value.value.toString()
            }
        }
    )
//
    val calOverrideEntityAdapter = CalOverrideEntity.Adapter(
        idAdapter = InlineAdapters.TypeId.calOverrideId,
        calIdAdapter = InlineAdapters.String.calIdAdapter,
        environmentIdAdapter = InlineAdapters.TypeId.environmentId,
        overrideNameAdapter = InlineAdapters.String.overrideName,
        overrideValueAdapter = InlineAdapters.String.overrideValue,
        descriptionAdapter = InlineAdapters.String.description,
        isEnabledAdapter = InlineAdapters.Bool.isEnabled,
        isHorizontalDisabledAdapter = InlineAdapters.Bool.isHorizontalDisabled
    )
//
//
    val carPropEntityAdapter = CarPropEntity.Adapter(
        idAdapter = InlineAdapters.TypeId.carPropId,
        nameAdapter = InlineAdapters.String.name,
        propertySourceAdapter = object: ColumnAdapter<PropertySource, Long>,
            app.cash.sqldelight.ColumnAdapter<PropertySource, Long> {
            override fun decode(databaseValue: Long): PropertySource = PropertySource.fromKey(databaseValue)
            override fun encode(value: PropertySource): Long = value.key
        }
    )
//
    val carOverrideEntityAdapter = CarOverrideEntity.Adapter(
        idAdapter = InlineAdapters.TypeId.carOverrideId,
        carPropIdAdapter = InlineAdapters.TypeId.carPropId,
        environmentIdAdapter = InlineAdapters.TypeId.environmentId,
        overrideValueAdapter = InlineAdapters.String.overrideValue,
        descriptionAdapter = InlineAdapters.String.description,
        isEnabledAdapter = InlineAdapters.Bool.isEnabled,
        overrideNameAdapter = InlineAdapters.String.overrideName,
        isHorizontalDisabledAdapter = InlineAdapters.Bool.isHorizontalDisabled
    )
//
    val systemPropEntityAdapter = SystemPropEntity.Adapter(
        idAdapter = InlineAdapters.TypeId.systemPropId,
        dataTypeAdapter = DataTypeAdapter,
        descriptionAdapter = InlineAdapters.String.description,
        keyAdapter = InlineAdapters.String.sysPropKey
    )
//
    val systemPropOverrideEntityAdapter = SystemPropOverrideEntity.Adapter(
        idAdapter = InlineAdapters.TypeId.systemPropOverride,
        systemPropIdAdapter = InlineAdapters.TypeId.systemPropId,
        environmentIdAdapter = InlineAdapters.TypeId.environmentId,
        overrideValueAdapter = InlineAdapters.String.overrideValue,
        isEnabledAdapter = InlineAdapters.Bool.isEnabled,
        descriptionAdapter = InlineAdapters.String.description,
        isHorizontalDisabledAdapter = InlineAdapters.Bool.isHorizontalDisabled
    )
//
    val sharedPrefEntityAdapter = SharedPrefEntity.Adapter(
        idAdapter = InlineAdapters.TypeId.sharedPrefId,
        sharedPrefRepoIdAdapter = InlineAdapters.String.sharedPrefRepoId,
        keyAdapter = InlineAdapters.String.sharedPrefKey,
        defaultValueAdapter = InlineAdapters.String.defaultValue,
        descriptionAdapter = InlineAdapters.String.description,
        dataTypeAdapter = DataTypeAdapter
    )
//
    val sharedPrefOverrideEntityAdapter = SharedPrefOverrideEntity.Adapter(
        idAdapter = InlineAdapters.TypeId.sharedPrefOverrideId,
        environmentIdAdapter = InlineAdapters.TypeId.environmentId,
        sharedPrefIdAdapter = InlineAdapters.TypeId.sharedPrefId,
        overrideValueAdapter = InlineAdapters.String.overrideValue,
        isEnabledAdapter = InlineAdapters.Bool.isEnabled,
        descriptionAdapter = InlineAdapters.String.description,
        isHorizontalDisabledAdapter = InlineAdapters.Bool.isHorizontalDisabled
    )

    object DataTypeAdapter: ColumnAdapter<DataType, Long>, app.cash.sqldelight.ColumnAdapter<DataType, Long> {

        override fun decode(databaseValue: Long): DataType {
            return DataType.Companion.fromInt(databaseValue.toInt())
        }

        override fun encode(value: DataType): Long {
            return value.id.toLong()
        }

    }
}