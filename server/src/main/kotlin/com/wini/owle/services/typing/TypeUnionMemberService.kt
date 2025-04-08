package com.wini.owle.services.typing

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.entity.typing.TypeUnionMemberDao
import com.wini.owle.mapper.typing.TypeUnionMembersMapper
import com.wini.owle.repo.TypeUnionMemberIdFactory
import com.wini.owle.repo.typing.TypeUnionMemberRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory
import org.jetbrains.exposed.dao.id.CompositeID


class TypeUnionMemberService(
    private val repository: TypeUnionMemberRepository,
    private val mapper: TypeUnionMembersMapper,
    entityIdFactory: EntityIdFactory<CompositeID>
): BaseServiceImpl<CompositeID, TypeUnionMemberDto, TypeUnionMemberDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
) {

    fun getByUnionTypeDeclarationId(typeDeclarationId: Long): AsyncResult<List<TypeUnionMemberDto>> {
        return runCatching {
            repository.getByUnionTypeDeclarationId(typeDeclarationId)
        }.fold(
            onSuccess = { list ->
                val data = mapper.toDtos(list)
                return AsyncResult.Success(data)
            },
            onFailure = { exception ->
                AsyncError.DatabaseError.Unspecified(exception.message ?: "Unable to delete entities").asFailure()
            }
        )
    }
}