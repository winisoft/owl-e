package com.wini.owle.repo

import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.EntityClass
import org.koin.core.module.Module


/**
 * Binds the three shapes a repository is asked for: the entity class itself, the concrete
 * [GenericRepoImpl], and the [OwleRepo] interface over it.
 *
 * The per-entity call sites are generated - see `com.wini.owle.mapper.generated.generatedRepoModule`.
 */
inline fun <reified ID : Any, reified T : Entity<ID>> Module.bindGenericRepo(
    entityClass: EntityClass<ID, T>
) {
    single<EntityClass<ID, T>> { entityClass }
    single<GenericRepoImpl<ID, T>> { GenericRepoImpl(get()) }
    single<OwleRepo<ID, T>> { get<GenericRepoImpl<ID, T>>() }
}
