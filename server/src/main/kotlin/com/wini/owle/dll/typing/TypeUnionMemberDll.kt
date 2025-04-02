package com.wini.owle.dll.typing

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.services.typing.TypeUnionMemberService
import org.jetbrains.exposed.dao.id.CompositeID

class TypeUnionMemberDll(
    private val service: TypeUnionMemberService
): DLL<CompositeID, TypeUnionMemberDto> {

    override fun getAll(): AsyncResult<List<TypeUnionMemberDto>> = service.getAll()
    override fun getById(id: CompositeID): AsyncResult<TypeUnionMemberDto?> = service.getById(id)
    override fun create(dto: TypeUnionMemberDto): AsyncResult<TypeUnionMemberDto> = service.create(dto)
    override fun create(dtos: List<TypeUnionMemberDto>): AsyncResult<List<TypeUnionMemberDto>> = service.create(dtos)
    override fun update(dto: TypeUnionMemberDto): AsyncResult<TypeUnionMemberDto> = service.update(dto)
    override fun update(dtos: List<TypeUnionMemberDto>): AsyncResult<List<TypeUnionMemberDto>> = service.update(dtos)
    override fun delete(id: CompositeID): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<CompositeID>): AsyncResult<Boolean> = service.delete(ids)

}