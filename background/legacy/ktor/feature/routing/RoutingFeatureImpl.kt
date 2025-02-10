package com.wini.owle.air.ktor.feature.routing

import com.wini.owle.air.ktor.feature.ServerFeature
import com.wini.owle.air.ktor.feature.mocker.IResponseMocker
import io.ktor.server.application.Application


class RoutingFeatureImpl
constructor(
    private val responseMocker: IResponseMocker
) : ServerFeature {

    override fun install(application: Application) {

    }
}
