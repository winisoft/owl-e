package com.wini.owle.air.domain.repo

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
import com.wini.owle.air.domain.root.RootItem
import com.wini.owle.air.domain.settings.SettingsAccessor
import com.wini.owle.air.domain.sysprop.SystemPropAccessor
import com.wini.owle.air.domain.sysprop.SystemPropOverrideAccessor

class Domain(
    override val rootItems: RootItem,
    override val apis: ApiAccessor,
    override val apiOverrides: ApiOverrideAccessor,
    override val endpoints: EndpointAccessor,
    override val endpointOverrides: EndpointOverrideAccessor,
    override val calibrations: CalibrationAccessor,
    override val calOverrides: CalOverrideAccessor,
    override val carProperties: CarPropAccessor,
    override val carPropOverrides: CarPropOverrideAccessor,
    override val systemProperties: SystemPropAccessor,
    override val systemPropOverrides: SystemPropOverrideAccessor,
    override val sharedPreferences: SharedPrefAccessor,
    override val sharedPrefOverrides: SharedPrefOverrideAccessor,
    override val environments: EnvironmentAccessor,
    override val calls: CallAccessor,
    override val envGroups: EnvironmentGroupAccessor,
    override val settings: SettingsAccessor
): LoDomain