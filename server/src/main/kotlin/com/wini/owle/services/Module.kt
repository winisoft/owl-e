package com.wini.owle.services

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.StringDto
import com.wini.owle.dto.ULongDto
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.repo.OwleRepo
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.koin.core.module.Module
import org.koin.core.scope.Scope


/**
 * One [GenericServiceImpl] binding per id kind. The per-type call sites are generated - see
 * `com.wini.owle.mapper.generated.generatedServiceModule`.
 */
inline fun <
        reified DTO : StringDto,
        reified E : Entity<String>
        > Module.bindGenericStringIdService(
    noinline mapper: Scope.() -> DbMapper<String, DTO, E>
) {
    single<GenericServiceImpl<String, DTO, E>> {
        GenericServiceImpl(
            repository = get<OwleRepo<String, E>>(),
            mapper = mapper(),
            daoDelegate = get<DaoDelegate<String, DTO, E>>()
        )
    }
    single<Service<String, DTO>> { get<GenericServiceImpl<String, DTO, E>>() }
}

inline fun <
        reified DTO : ULongDto,
        reified E : ULongEntity
        > Module.bindGenericULongService(
    noinline mapper: Scope.() -> DbMapper<ULong, DTO, E>
) {
    single<GenericServiceImpl<ULong, DTO, E>> {
        GenericServiceImpl(
            repository = get<OwleRepo<ULong, E>>(),
            mapper = mapper(),
            daoDelegate = get<DaoDelegate<ULong, DTO, E>>()
        )
    }
    single<Service<ULong, DTO>> { get<GenericServiceImpl<ULong, DTO, E>>() }
}

inline fun <
        reified DTO : CompositeDto,
        reified E : CompositeEntity
        > Module.bindCompositeIdGenericService(
    noinline mapper: Scope.() -> DbMapper<CompositeID, DTO, E>
) {
    single<GenericServiceImpl<CompositeID, DTO, E>> {
        GenericServiceImpl(
            repository = get<OwleRepo<CompositeID, E>>(),
            mapper = mapper(),
            daoDelegate = get<DaoDelegate<CompositeID, DTO, E>>()
        )
    }
    single<Service<CompositeID, DTO>> { get<GenericServiceImpl<CompositeID, DTO, E>>() }
}
