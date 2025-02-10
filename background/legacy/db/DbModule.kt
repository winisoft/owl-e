package com.wini.owle.air.db

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.wini.owle.air.db.backoffice.BoDbAdapters
import com.wini.owle.air.db.backoffice.api.ApiOverrideRepository
import com.wini.owle.air.db.backoffice.api.ApiRepository
import com.wini.owle.air.db.backoffice.call.CallRepository
import com.wini.owle.air.db.backoffice.endpoint.EndpointOverrideRepository
import com.wini.owle.air.db.backoffice.endpoint.EndpointRepository
import com.wini.owle.air.db.environment.EnvDbAdapters
import com.wini.owle.air.db.environment.EnvironmentGroupRepository
import com.wini.owle.air.db.environment.EnvironmentRepository
import com.wini.owle.air.db.environment.GroupRepository
import com.wini.owle.air.db.library.PropDbAdapters
import com.wini.owle.air.db.library.cal.CalOverrideRepository
import com.wini.owle.air.db.library.cal.CalibrationRepository
import com.wini.owle.air.db.library.car.CarOverrideRepository
import com.wini.owle.air.db.library.car.CarPropRepository
import com.wini.owle.air.db.library.prefs.SharedPrefOverrideRepository
import com.wini.owle.air.db.library.prefs.SharedPrefRepository
import com.wini.owle.air.db.library.sysprop.SystemOverrideRepository
import com.wini.owle.air.db.library.sysprop.SystemPropRepository
import com.wini.owle.air.db.root.RootRepository
import com.wini.owle.air.db.root.RunningState
import com.wini.owle.air.db.settings.SettingsDbAdapter
import com.wini.owle.air.db.settings.SettingsRepository
import com.wini.owle.air.inlines.IsExpanded
import com.wini.owle.air.inlines.LoRootItemId
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

class DriverFactory {
    fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        OwleAirDb.Schema.create(driver)
        return driver
    }
}

object DbModule: (CoroutineScope) -> Module {

    private val dbDriver: SqlDriver
        get() = DriverFactory().createDriver()

    override fun invoke(scope: CoroutineScope): Module = module {

        single { RootRepository(get<OwleAirDb>().loRootItemQueries) }
        single { ApiRepository(get<OwleAirDb>().apiEntityQueries) }
        single { ApiOverrideRepository(get<OwleAirDb>().apiOverrideEntityQueries) }
        single { CalibrationRepository(get<OwleAirDb>().calibrationEntityQueries) }
        single { CalOverrideRepository(get<OwleAirDb>().calOverrideEntityQueries) }
        single { CallRepository(get<OwleAirDb>().callEntityQueries) }
        single { CarPropRepository(get<OwleAirDb>().carPropEntityQueries) }
        single { CarOverrideRepository(get<OwleAirDb>().carOverrideEntityQueries) }
        single { EndpointRepository(get<OwleAirDb>().endpointEntityQueries) }
        single { EndpointOverrideRepository(get<OwleAirDb>().endpointOverrideEntityQueries) }
        single { EnvironmentRepository(get<OwleAirDb>().environmentEntityQueries) }
        single { GroupRepository(get<OwleAirDb>().groupEntityQueries) }
        single { EnvironmentGroupRepository(get<OwleAirDb>().environmentGroupEntityQueries) }
        single { SharedPrefRepository(get<OwleAirDb>().sharedPrefEntityQueries) }
        single { SharedPrefOverrideRepository(get<OwleAirDb>().sharedPrefOverrideEntityQueries) }
        single { SystemPropRepository(get<OwleAirDb>().systemPropEntityQueries) }
        single { SystemOverrideRepository(get<OwleAirDb>().systemPropOverrideEntityQueries) }
        single { SettingsRepository(get<OwleAirDb>().settingsEntityQueries) }

        single {
            OwleAirDb(
                driver = dbDriver,
                loRootItemEntityAdapter = LoRootItemEntity.Adapter(
                    idAdapter = object : ColumnAdapter<LoRootItemId, Long> {
                        override fun decode(databaseValue: Long): LoRootItemId = LoRootItemId(databaseValue)
                        override fun encode(value: LoRootItemId): Long = value.value
                    },
                    runningStateAdapter = object : ColumnAdapter<RunningState, Long> {
                        override fun decode(databaseValue: Long): RunningState = if (databaseValue == RunningState.Running.key.toLong()) RunningState.Running else RunningState.Stopped
                        override fun encode(value: RunningState): Long = value.key.toLong()
                    },
                    isExpandedAdapter = object : ColumnAdapter<IsExpanded, Long> {
                        override fun decode(databaseValue: Long): IsExpanded = IsExpanded(if (databaseValue == 1L) true else false)
                        override fun encode(value: IsExpanded): Long = if (value.value) 1L else 0L
                    }
                ),
                environmentEntityAdapter = EnvDbAdapters.environmentEntityAdapter,
                groupEntityAdapter = EnvDbAdapters.groupEntityAdapter,
                environmentGroupEntityAdapter = EnvDbAdapters.environmentGroupEntityAdapter,
                apiEntityAdapter = BoDbAdapters.apiEntityAdapter,
                apiOverrideEntityAdapter = BoDbAdapters.apiOverrideEntityAdapter,
                endpointEntityAdapter = BoDbAdapters.endpointEntityAdapter,
                endpointOverrideEntityAdapter = BoDbAdapters.endpointOverrideEntityAdapter,
                callEntityAdapter = BoDbAdapters.callEntityAdapter,
                calibrationEntityAdapter = PropDbAdapters.calibrationEntityAdapter,
                calOverrideEntityAdapter = PropDbAdapters.calOverrideEntityAdapter,
                carPropEntityAdapter = PropDbAdapters.carPropEntityAdapter,
                carOverrideEntityAdapter = PropDbAdapters.carOverrideEntityAdapter,
                systemPropEntityAdapter = PropDbAdapters.systemPropEntityAdapter,
                systemPropOverrideEntityAdapter = PropDbAdapters.systemPropOverrideEntityAdapter,
                sharedPrefEntityAdapter = PropDbAdapters.sharedPrefEntityAdapter,
                sharedPrefOverrideEntityAdapter = PropDbAdapters.sharedPrefOverrideEntityAdapter,
                settingsEntityAdapter = SettingsDbAdapter.instance
            )
        }
    }
}

