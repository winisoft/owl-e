package com.wini.owle.dll.user

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.tags.TagRelation
import com.wini.owle.services.user.TagRelationService
import org.jetbrains.exposed.dao.id.CompositeID

class TagRelationDll(
    private val service: TagRelationService
): DLL<CompositeID, TagRelation> {

    override fun getAll(): AsyncResult<List<TagRelation>> = service.getAll()
    override fun getById(id: CompositeID): AsyncResult<TagRelation> = service.getById(id)
    override fun create(dto: TagRelation): AsyncResult<TagRelation> = service.create(dto)
    override fun createAll(dtos: List<TagRelation>): AsyncResult<List<TagRelation>> = service.create(dtos)
    override fun update(dto: TagRelation): AsyncResult<TagRelation> = service.update(dto)
    override fun updateAll(dtos: List<TagRelation>): AsyncResult<List<TagRelation>> = service.update(dtos)
    override fun delete(id: CompositeID): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<CompositeID>): AsyncResult<Unit> = service.delete(ids)

}