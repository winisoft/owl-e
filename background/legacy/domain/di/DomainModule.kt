package com.wini.owle.air.domain.di

import com.wini.owle.air.db.DbModule
import com.wini.owle.air.domain.api.ApiAccessor
import com.wini.owle.air.domain.api.ApiOverrideAccessor
import com.wini.owle.air.domain.cal.CalOverrideAccessor
import com.wini.owle.air.domain.cal.CalibrationAccessor
import com.wini.owle.air.domain.call.CallAccessor
import com.wini.owle.air.domain.car.CarPropAccessor
import com.wini.owle.air.domain.car.CarPropOverrideAccessor
import com.wini.owle.air.domain.endpoint.EndpointAccessor
import com.wini.owle.air.domain.endpoint.EndpointOverrideAccessor
import com.wini.owle.air.domain.environment.EnvironmentAccessor
import com.wini.owle.air.domain.environment.EnvironmentGroupAccessor
import com.wini.owle.air.domain.prefs.SharedPrefAccessor
import com.wini.owle.air.domain.prefs.SharedPrefOverrideAccessor
import com.wini.owle.air.domain.repo.Domain
import com.wini.owle.air.domain.repo.LoDomain
import com.wini.owle.air.domain.root.RootItem
import com.wini.owle.air.domain.settings.SettingsAccessor
import com.wini.owle.air.domain.sysprop.SystemPropAccessor
import com.wini.owle.air.domain.sysprop.SystemPropOverrideAccessor
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule: (CoroutineScope) -> Module {

    override fun invoke(scope: CoroutineScope): Module = module {

        single { RootItem(get()) }
        single { ApiAccessor(get()) }
        single { ApiOverrideAccessor(get()) }
        single { CalOverrideAccessor(get()) }
        single { CalibrationAccessor(get()) }
        single { CarPropAccessor(get()) }
        single { CarPropOverrideAccessor(get()) }
        single { CallAccessor(get()) }
        single { EndpointAccessor(get()) }
        single { EndpointOverrideAccessor(get()) }
        single { SystemPropAccessor(get()) }
        single { SystemPropOverrideAccessor(get()) }
        single { SharedPrefAccessor(get()) }
        single { SharedPrefOverrideAccessor(get()) }
        single { EnvironmentAccessor(get()) }
        single { EnvironmentGroupAccessor(get()) }
        single { SettingsAccessor(get()) }

        single<LoDomain> {
            Domain(
                get(), get(),get(),get(),
                get(),get(),get(),get(),
                get(),get(),get(),get(),
                get(),get(),get(),get(), get()
            )
        }
    }
}
