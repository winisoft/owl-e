package com.wini.owle.ui.actions

import com.wini.owle.air.domain.api.Api
import com.wini.owle.air.domain.cal.Calibration
import com.wini.owle.air.domain.car.CarProp
import com.wini.owle.air.domain.endpoint.Endpoint
import com.wini.owle.air.domain.prefs.SharedPref
import com.wini.owle.air.domain.sysprop.SystemProperty


sealed class ApiAction
data class ApiSelected(val api: Api): ApiAction()

sealed class CalibrationAction
data class CalibrationSelected(val calibration: Calibration): CalibrationAction()

sealed class CarPropertyAction
data class CarPropertySelected(val carProp: CarProp): CarPropertyAction()

sealed class SystemPropertyAction
data class SystemPropertySelected(val systemProp: SystemProperty): SystemPropertyAction()

sealed class SharedPreferenceAction
data class SharedPreferenceSelected(val sharedPref: SharedPref): SharedPreferenceAction()

sealed class EndpointAction
data class EndpointSelected(val endpoint: Endpoint): EndpointAction()

