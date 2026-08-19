package com.wini.owle

import com.wini.owle.entity.environsEntitiesModule
import com.wini.owle.entity.runEntitiesModule
import com.wini.owle.entity.stepEntitiesModule
import com.wini.owle.entity.tagEntitiesModule
import com.wini.owle.entity.transformEntitiesModule
import com.wini.owle.entity.typingEntitiesModule
import com.wini.owle.entity.userEntitiesModule
import com.wini.owle.entity.workflowEntitiesModule
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
    runEntitiesModule,
    stepEntitiesModule,
    tagEntitiesModule,
    transformEntitiesModule,
    typingEntitiesModule,
    userEntitiesModule,
    workflowEntitiesModule,

    validatorsModule
)

