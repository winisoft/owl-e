package com.wini.owle.air.ktor.di

import com.wini.owle.air.ktor.feature.mocker.IResponseMocker
import com.wini.owle.air.ktor.feature.mocker.ResponseMocker
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.Module
import org.koin.dsl.module
import com.wini.owle.air.ktor.feature.routing.RoutingFeatureImpl

object ServerModule: (CoroutineScope) -> Module {

    override fun invoke(scope: CoroutineScope): Module = module {
        single<IResponseMocker> { ResponseMocker(get(), get(), get(), get(), get(), get()) }
        single { RoutingFeatureImpl(get()) }
    }

}