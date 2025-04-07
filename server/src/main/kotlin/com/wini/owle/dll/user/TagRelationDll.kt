package com.wini.owle.dll.user

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.TagRelationDto
import com.wini.owle.services.user.TagRelationService
import org.jetbrains.exposed.dao.id.CompositeID

class TagRelationDll(
    private val service: TagRelationService
): DLL<CompositeID, TagRelationDto> {

    override fun getAll(): AsyncResult<List<TagRelationDto>> = service.getAll()
    override fun getById(id: CompositeID): AsyncResult<TagRelationDto> = service.getById(id)
    override fun create(dto: TagRelationDto): AsyncResult<TagRelationDto> = service.create(dto)
    override fun createAll(dtos: List<TagRelationDto>): AsyncResult<List<TagRelationDto>> = service.create(dtos)
    override fun update(dto: TagRelationDto): AsyncResult<TagRelationDto> = service.update(dto)
    override fun updateAll(dtos: List<TagRelationDto>): AsyncResult<List<TagRelationDto>> = service.update(dtos)
    override fun delete(id: CompositeID): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<CompositeID>): AsyncResult<Unit> = service.delete(ids)

}