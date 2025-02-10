package com.wini.owle.dll.user

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.TagDto
import com.wini.owle.services.user.TagService


class TagDll(
    private val service: TagService
): DLL<Long, TagDto> {

    override fun getAll(): AsyncResult<List<TagDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TagDto?> = service.getById(id)
    override fun create(dto: TagDto): AsyncResult<TagDto> = service.create(dto)
    override fun create(dtos: List<TagDto>): AsyncResult<List<TagDto>> = service.create(dtos)
    override fun update(dto: TagDto): AsyncResult<TagDto> = service.update(dto)
    override fun update(dtos: List<TagDto>): AsyncResult<List<TagDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}