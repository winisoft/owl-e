package com.wini.owle.air.domain.action

import com.wini.owle.air.db.api.ApiOverrideEntity
import com.wini.owle.air.db.cal.CalOverrideEntity
import com.wini.owle.air.db.call.CallEntity
import com.wini.owle.air.db.car.CarOverrideEntity
import com.wini.owle.air.db.endpoint.EndpointOverrideEntity
import com.wini.owle.air.db.environment.EnvironmentEntity
import com.wini.owle.air.db.prefs.SharedPrefOverrideEntity
import com.wini.owle.air.db.sysprop.SystemPropEntity
import com.wini.owle.air.db.sysprop.SystemPropOverrideEntity
import com.wini.owle.air.domain.model.item.EndpointOverride


sealed interface SaveActions<T> {
    abstract val entity: T
}

data class SaveEnvironment(override val entity: EnvironmentEntity): SaveActions<EnvironmentEntity>

data class SaveCalOverride(override val entity: CalOverrideEntity): SaveActions<CalOverrideEntity>

data class SaveCarOverride(override val entity: CarOverrideEntity): SaveActions<CarOverrideEntity>

data class SaveSystemPropOverride(override val entity: SystemPropOverrideEntity): SaveActions<SystemPropOverrideEntity>

data class SaveSharedPrefOverride(override val entity: SharedPrefOverrideEntity): SaveActions<SharedPrefOverrideEntity>

data class SaveApiOverride(override val entity: ApiOverrideEntity): SaveActions<ApiOverrideEntity>

data class SaveEndpointOverride(override val entity: EndpointOverrideEntity): SaveActions<EndpointOverrideEntity>

data class SaveCall(override val entity: CallEntity): SaveActions<CallEntity>