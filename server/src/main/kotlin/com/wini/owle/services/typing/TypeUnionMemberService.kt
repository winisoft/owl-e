package com.wini.owle.services.typing

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.TypeUnionMemberDto
import com.wini.owle.entity.typing.TypeUnionMemberDao
import com.wini.owle.mapper.typing.TypeUnionMembersMapper
import com.wini.owle.repo.typing.TypeUnionMemberRepository
import com.wini.owle.services.BaseServiceImpl
import org.jetbrains.exposed.dao.id.CompositeID


class TypeUnionMemberService(
    private val repository: TypeUnionMemberRepository,
    private val mapper: TypeUnionMembersMapper
): BaseServiceImpl<CompositeID, TypeUnionMemberDto, TypeUnionMemberDao>(repository, mapper) {

    fun getByUnionTypeDeclarationId(typeDeclarationId: Long): AsyncResult<List<TypeUnionMemberDto>> {
        return runCatching {
            repository.getByUnionTypeDeclarationId(typeDeclarationId)
        }.fold(
            onSuccess = { list ->
                list.map { it.toDto() }.let {
                    AsyncResult.Success(it)
                }
            },
            onFailure = { exception ->
                AsyncError.DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
            }
        )
    }
}