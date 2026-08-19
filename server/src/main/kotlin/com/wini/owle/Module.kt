package com.wini.owle

import com.wini.owle.entity.environsEntitiesModule
import com.wini.owle.validators.validatorsModule
import org.koin.dsl.module
import com.wini.owle.mapper.generated.generatedMapperModule
import com.wini.owle.generated.generatedLayerModule
import com.wini.owle.services.bespokeServicesModule

val allModules = listOf(
    generatedMapperModule,
    generatedLayerModule,
    bespokeServicesModule,
    environsEntitiesModule,

    validatorsModule
)

