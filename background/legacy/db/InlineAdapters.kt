package com.wini.owle.air.db

import app.cash.sqldelight.ColumnAdapter
import com.wini.owle.air.inlines.*

object InlineAdapters {

    object TypeId {
        val systemPropId = Long.SimpleLongAdapter({ SystemPropId(it) }, { value })
        val systemPropOverride = Long.SimpleLongAdapter({ SystemPropOverrideId(it) }, { value })
        val sharedPrefOverrideId = Long.SimpleLongAdapter({ SharedPrefOverrideId(it) }, { value })
        val carOverrideId = Long.SimpleLongAdapter({ CarOverrideId(it) }, { value })
        val carPropId = Long.SimpleLongAdapter({ CarPropId(it) }, { value })
        val endpointId = Long.SimpleLongAdapter({ EndpointId(it) }, { value })
        val endpointOverrideId = Long.SimpleLongAdapter({ EndpointOverrideId(it) }, { value })
        val callId = Long.SimpleLongAdapter({ CallId(it) }, { value })
        val calOverrideId = Long.SimpleLongAdapter({ CalOverrideId(it) }, { value })
        val apiId = Long.SimpleLongAdapter({ ApiId(it) }, { value })
        val apiOverrideId = Long.SimpleLongAdapter({ ApiOverrideId(it) }, { value })
        val environmentId = Long.SimpleLongAdapter({ EnvironmentId(it) }, { value })
        val environmentGroupId = Long.SimpleLongAdapter({ EnvironmentGroupId(it) }, { value })
        val groupId = Long.SimpleLongAdapter({ GroupId(it) }, { value })
        val sharedPrefId = Long.SimpleLongAdapter({ SharedPrefId(it) }, { value })
    }

    object Bool {
        val isHorizontalDisabled =
            SimpleBoolAdapter({ IsHorizontalDisabled(it) },
                { if (value) 1L else 0L })
        val calOverridden =
            SimpleBoolAdapter({ CalIsOverride(it) }, { if (value) 1L else 0L })
        val isEnabled =
            SimpleBoolAdapter({ IsEnabled(it) }, { if (value) 1L else 0L })
        val isExpanded =
            SimpleBoolAdapter({ IsExpanded(it) }, { if (value) 1L else 0L })
        val autoRun =
            SimpleBoolAdapter({ AutoRun(it) }, { if (value) 1L else 0L })
        val closeBtnMinimizes = SimpleBoolAdapter({ CloseBtnMinimizes(it) },
            { if (value) 1L else 0L })

        class SimpleBoolAdapter<T: Any>(val ctor: (Boolean) -> T, val getValue: T.() -> kotlin.Long):
            ColumnAdapter<T, kotlin.Long> {
            override fun decode(databaseValue: kotlin.Long): T = ctor(databaseValue == 1L)
            override fun encode(value: T): kotlin.Long = getValue(value)
        }
    }

    object String {
        val utilityAdapter = String.SimpleStringAdapter({ Utility(it) }, { value })
        val environmentName =
            String.SimpleStringAdapter({ EnvironmentName(it) }, { value })
        val defaultValue =
            String.SimpleStringAdapter({ DefaultValue(it) }, { value ?: "" })
        val sharedPrefKey =
            String.SimpleStringAdapter({ SharedPrefKey(it) }, { value })
        val sysPropKey = String.SimpleStringAdapter({ SysPropKey(it) }, { value })
        val sharedPrefRepoId =
            String.SimpleStringAdapter({ SharedPrefRepoId(it) }, { value })
        val overrideValue =
            String.SimpleStringAdapter({ OverrideValue(it) }, { value })
        val calEnumId = String.SimpleStringAdapter({ CalEnumId(it) }, { value })
        val encoding = String.SimpleStringAdapter({ CalEncoding(it) }, { value })
        val calVersion = String.SimpleStringAdapter({ CalId(it) }, { value })
        val calIdAdapter =
            String.SimpleStringAdapter({ CalibrationId(it) }, { value })
        val overrideName =
            String.SimpleStringAdapter({ OverrideName("") }, { value })
        val loRoute = String.SimpleStringAdapter({ LoRoute(it) }, { value })
        val calDefFileName =
            String.SimpleStringAdapter({ CalDefFileName(it) }, { value })
        val enumId = String.SimpleStringAdapter({ CalEnumId(it) }, { value ?: "" })
        val endpointPath =
            String.SimpleStringAdapter({ EndpointPath(it) }, { value })
        val name = String.SimpleStringAdapter({ Name(it) }, { value ?: "" })
        val calName = String.SimpleStringAdapter({ CalName(it) }, { value })
        val description =
            String.SimpleStringAdapter({ Description(it) }, { value ?: "" })
        val calibrationEntity =
            String.SimpleStringAdapter({ CalibrationId(it) }, { value })
        val calValue = String.SimpleStringAdapter({ CalValue(it) }, { value ?: "" })

        class SimpleStringAdapter<T: Any>(
            val ctor: (kotlin.String) -> T,
            val getValue: T.() -> kotlin.String
        ): ColumnAdapter<T, kotlin.String> {
            override fun decode(databaseValue: kotlin.String): T = ctor(databaseValue)
            override fun encode(value: T): kotlin.String = getValue(value)
        }
    }

    object Long {
        val loRootItemId: ColumnAdapter<LoRootItemId, kotlin.Long> = Long.SimpleLongAdapter({ LoRootItemId(it) }, { value })
        val environment = Long.SimpleLongAdapter({ EnvironmentId(it) }, { value })
        val api = Long.SimpleLongAdapter({ ApiId(it) }, { value })
        val apiOverride = Long.SimpleLongAdapter({ ApiOverrideId(it) }, { value })
        val header = Long.SimpleLongAdapter({ HeaderId(it) }, { value })
        val arrayLen = Long.SimpleLongAdapter({ CalArrayLen(it) }, { value ?: -1 })
        val calType = Long.SimpleLongAdapter({ CalType(it) }, { value })
        val calVersion = Long.SimpleLongAdapter({ CalVersion(it) }, { value })
        val calDefFileVersion =
            Long.SimpleLongAdapter({ CalDefFileVersion(it) }, { value })
        val carPropEntity = Long.SimpleLongAdapter({ CarPropId(it) }, { value })
        val endpointEntity = Long.SimpleLongAdapter({ EndpointId(it) }, { value })
        val endpointOverride =
            Long.SimpleLongAdapter({ EndpointOverrideId(it) }, { value })
        val delayMillis = Long.SimpleLongAdapter({ DelayMillis(it) }, { value })
        val fireIntensity = Long.SimpleLongAdapter({ FireIntensity(it) }, { value })
        val textScale = Long.SimpleLongAdapter({ TextScale(it) }, { value })

        class SimpleLongAdapter<T: Any>(val ctor: (kotlin.Long) -> T, val getValue: T.() -> kotlin.Long):
            ColumnAdapter<T, kotlin.Long> {
            override fun decode(databaseValue: kotlin.Long): T = ctor(databaseValue)
            override fun encode(value: T): kotlin.Long = getValue(value)
        }
    }

}