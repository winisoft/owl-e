package com.wini.owle

import com.wini.owle.api.environsApiModule
import com.wini.owle.dll.environsDllsModule
import com.wini.owle.entity.environsEntitiesModule
import com.wini.owle.api.runsApiModule
import com.wini.owle.dll.runsDllsModule
import com.wini.owle.entity.runEntitiesModule
import com.wini.owle.api.stepsApiModule
import com.wini.owle.dll.stepDllsModule
import com.wini.owle.entity.stepEntitiesModule
import com.wini.owle.api.tagsApiModule
import com.wini.owle.dll.tagDllsModule
import com.wini.owle.entity.tagEntitiesModule
import com.wini.owle.api.transformApiModule
import com.wini.owle.dll.transformDllsModule
import com.wini.owle.entity.transformEntitiesModule
import com.wini.owle.api.typingApiModule
import com.wini.owle.dll.typingDllsModule
import com.wini.owle.entity.typingEntitiesModule
import com.wini.owle.api.userApiModule
import com.wini.owle.dll.userDllsModule
import com.wini.owle.entity.userEntitiesModule
import com.wini.owle.api.workflowApiModule
import com.wini.owle.dll.workflowDllsModule
import com.wini.owle.entity.workflowEntitiesModule
import com.wini.owle.validators.validatorsModule
import org.koin.dsl.module
import com.wini.owle.mapper.generated.generatedMapperModule
import com.wini.owle.mapper.generated.generatedRepoModule
import com.wini.owle.mapper.generated.generatedServiceModule

val allModules = listOf(
    generatedMapperModule,
    generatedRepoModule,
    generatedServiceModule,
    environsEntitiesModule,
    runEntitiesModule,
    stepEntitiesModule,
    tagEntitiesModule,
    transformEntitiesModule,
    typingEntitiesModule,
    userEntitiesModule,
    workflowEntitiesModule,

    environsApiModule,
    runsApiModule,
    stepsApiModule,
    tagsApiModule,
    transformApiModule,
    typingApiModule,
    userApiModule,
    workflowApiModule,

    environsDllsModule,
    runsDllsModule,
    stepDllsModule,
    tagDllsModule,
    transformDllsModule,
    typingDllsModule,
    userDllsModule,
    workflowDllsModule,

    validatorsModule
)

