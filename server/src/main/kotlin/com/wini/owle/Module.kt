package com.wini.owle

import com.wini.owle.api.apiModule
import com.wini.owle.repo.repositoryModule
import com.wini.owle.services.serviceModule
import com.wini.owle.dll.dllModule
import com.wini.owle.validators.validatorsModule

import org.koin.dsl.module


val appModule = module {
    includes(
        repositoryModule,
        serviceModule,
        dllModule,
        apiModule
    )
}

