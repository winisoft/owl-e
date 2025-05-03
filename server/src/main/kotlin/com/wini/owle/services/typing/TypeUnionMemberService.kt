package com.wini.owle.services.typing

import com.wini.owle.error.OwleError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.entity.typing.TypeUnionMemberEntity
import com.wini.owle.error.DatabaseError
import com.wini.owle.mapper.typing.TypeUnionMembersMapper
import com.wini.owle.repo.typing.TypeUnionMemberRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.TypeUnionMemberIdFactory
import org.jetbrains.exposed.dao.id.CompositeID


class TypeUnionMemberService(
    private val repository: TypeUnionMemberRepository,
    private val mapper: TypeUnionMembersMapper,
    entityIdFactory: TypeUnionMemberIdFactory
): BaseServiceImpl<CompositeID, TypeUnionMember, TypeUnionMemberEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
) {

    fun getByUnionTypeDeclarationId(typeDeclarationId: Long): AsyncResult<List<TypeUnionMember>> {
        return runCatching {
            repository.getByUnionTypeDeclarationId(typeDeclarationId)
        }.fold(
            onSuccess = { list ->
                val data = mapper.toDtos(list)
                return AsyncResult.Success(data)
            },
            onFailure = { exception ->
                DatabaseError.Generic(exception.message ?: "Unable to delete entities").asFailure()
            }
        )
    }
}