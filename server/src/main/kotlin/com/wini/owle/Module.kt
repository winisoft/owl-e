package com.wini.owle

import com.wini.owle.api.environsApiModule
import com.wini.owle.dll.environsDllsModule
import com.wini.owle.entity.environsEntitiesModule
import com.wini.owle.mapper.environsMapperModule
import com.wini.owle.repo.environsReposModule
import com.wini.owle.services.environsServicesModule
import com.wini.owle.api.runsApiModule
import com.wini.owle.dll.runsDllsModule
import com.wini.owle.entity.runEntitiesModule
import com.wini.owle.mapper.runsMappersModule
import com.wini.owle.repo.runsReposModule
import com.wini.owle.services.runsServicesModule
import com.wini.owle.api.stepsApiModule
import com.wini.owle.dll.stepDllsModule
import com.wini.owle.entity.stepEntitiesModule
import com.wini.owle.mapper.stepMappersModule
import com.wini.owle.repo.stepsReposModule
import com.wini.owle.services.stepServicesModule
import com.wini.owle.api.tagsApiModule
import com.wini.owle.dll.tagDllsModule
import com.wini.owle.entity.tagEntitiesModule
import com.wini.owle.mapper.tagMappersModule
import com.wini.owle.repo.tagReposModule
import com.wini.owle.services.tagServicesModule
import com.wini.owle.api.transformApiModule
import com.wini.owle.dll.transformDllsModule
import com.wini.owle.entity.transformEntitiesModule
import com.wini.owle.mapper.transformMappersModule
import com.wini.owle.repo.transformsReposModule
import com.wini.owle.services.transformServicesModule
import com.wini.owle.api.typingApiModule
import com.wini.owle.dll.typingDllsModule
import com.wini.owle.entity.typingEntitiesModule
import com.wini.owle.mapper.typingMappersModule
import com.wini.owle.repo.typingReposModule
import com.wini.owle.services.typingServicesModule
import com.wini.owle.api.userApiModule
import com.wini.owle.dll.userDllsModule
import com.wini.owle.entity.userEntitiesModule
import com.wini.owle.mapper.userMappersModule
import com.wini.owle.repo.userReposModule
import com.wini.owle.services.userServicesModule
import com.wini.owle.api.workflowApiModule
import com.wini.owle.dll.workflowDllsModule
import com.wini.owle.entity.workflowEntitiesModule
import com.wini.owle.mapper.workflowMappersModule
import com.wini.owle.repo.workflowReposModule
import com.wini.owle.services.workflowServicesModule
import com.wini.owle.validators.validatorsModule
import org.koin.dsl.module


val allModules = listOf(
    environsEntitiesModule,
    runEntitiesModule,
    stepEntitiesModule,
    tagEntitiesModule,
    transformEntitiesModule,
    typingEntitiesModule,
    userEntitiesModule,
    workflowEntitiesModule,

    environsReposModule,
    runsReposModule,
    stepsReposModule,
    tagReposModule,
    transformsReposModule,
    typingReposModule,
    userReposModule,
    workflowReposModule,

    environsMapperModule,
    runsMappersModule,
    stepMappersModule,
    tagMappersModule,
    transformMappersModule,
    typingMappersModule,
    userMappersModule,
    workflowMappersModule,

    environsServicesModule,
    runsServicesModule,
    stepServicesModule,
    tagServicesModule,
    transformServicesModule,
    typingServicesModule,
    userServicesModule,
    workflowServicesModule,

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

