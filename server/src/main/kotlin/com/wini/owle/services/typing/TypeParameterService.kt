package com.wini.owle.services.typing

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.typing.TypeParameterDto
import com.wini.owle.entity.typing.TypeParameterDao
import com.wini.owle.mapper.typing.TypeParametersMapper
import com.wini.owle.repo.TypeParameterIdFactory
import com.wini.owle.repo.typing.TypeParameterRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory
import org.jetbrains.exposed.dao.id.EntityID


class TypeParameterService(
    private val repository: TypeParameterRepository,
    private val mapper: TypeParametersMapper,
    entityIdFactory: EntityIdFactory<Long>
): BaseServiceImpl<Long, TypeParameterDto, TypeParameterDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
) {

    fun getByTypeDeclarationIdOrderByPosition(typeDeclarationId: Long): AsyncResult<List<TypeParameterDto>> {
        return runCatching {
            val id = EntityID(typeDeclarationId, repository.entityClass.table)
            repository.getByTypeDeclarationIdOrderByPosition(id)
        }.fold(
            onSuccess = { list ->
                val data = mapper.toDtos(list)
                return AsyncResult.Success(data)
            },
            onFailure = { exception ->
                AsyncError.Database(exception.message ?: "Unable to get TypeParameter entities").asFailure()
            }
        )
    }
}