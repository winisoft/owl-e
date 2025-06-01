package com.wini.owle.mapper

import com.wini.owle.dto.CompositeDto
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.koin.core.module.Module
import org.koin.core.scope.Scope


import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
//
//inline fun <reified E : ULongEntity, reified C : ULongEntityClass<E>> Module.bindULongFactory(
//    entityClass: C
//) {
//    single<ULongIdDaoFactory<E, C>> {
//        ULongIdDaoFactoryImpl(entityClass)
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    single<Dapo<E, ULong>> {
//        get<ULongIdDaoFactory<E, C>> as DaoFactory<E, ULong>
//    }
//}
//
//inline fun <reified E : CompositeEntity, reified C : CompositeEntityClass<E>> Module.bindCompositeFactory(
//    entityClass: C,
//    compositeIdFactory: CompositeIdFactory<CompositeDto>
//) {
//    single<CompositeIdDaoFactory<E>> {
//        CompositeIdDaoFactoryImpl(entityClass, compositeIdFactory)
//    }
//    single<DaoFactory<E, CompositeID>> {
//        get<CompositeIdDaoFactory<E>>
//    }
//}