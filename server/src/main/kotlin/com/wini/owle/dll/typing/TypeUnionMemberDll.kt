package com.wini.owle.dll.typing

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.services.typing.TypeUnionMemberService
import org.jetbrains.exposed.dao.id.CompositeID

class TypeUnionMemberDll(
    private val service: TypeUnionMemberService
): DLL<CompositeID, TypeUnionMember> {

    override fun getAll(): AsyncResult<List<TypeUnionMember>> = service.getAll()
    override fun getById(id: CompositeID): AsyncResult<TypeUnionMember> = service.getById(id)
    override fun create(dto: TypeUnionMember): AsyncResult<TypeUnionMember> = service.create(dto)
    override fun createAll(dtos: List<TypeUnionMember>): AsyncResult<List<TypeUnionMember>> = service.create(dtos)
    override fun update(dto: TypeUnionMember): AsyncResult<TypeUnionMember> = service.update(dto)
    override fun updateAll(dtos: List<TypeUnionMember>): AsyncResult<List<TypeUnionMember>> = service.update(dtos)
    override fun delete(id: CompositeID): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<CompositeID>): AsyncResult<Unit> = service.delete(ids)

}