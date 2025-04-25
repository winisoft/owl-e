package com.wini.owle.dll.user

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.tags.Tag
import com.wini.owle.services.user.TagService


class TagDll(
    private val service: TagService
): DLL<Long, Tag> {

    override fun getAll(): AsyncResult<List<Tag>> = service.getAll()
    override fun getById(id: Long): AsyncResult<Tag> = service.getById(id)
    override fun create(dto: Tag): AsyncResult<Tag> = service.create(dto)
    override fun createAll(dtos: List<Tag>): AsyncResult<List<Tag>> = service.create(dtos)
    override fun update(dto: Tag): AsyncResult<Tag> = service.update(dto)
    override fun updateAll(dtos: List<Tag>): AsyncResult<List<Tag>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}