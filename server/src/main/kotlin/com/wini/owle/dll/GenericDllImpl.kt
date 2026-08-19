package com.wini.owle.dll

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.BaseDto
import com.wini.owle.services.Service


open class GenericDllImpl<ID: Any, DTO: BaseDto>(
    private val service: Service<ID, DTO>
) : DLL<ID, DTO> {
    override fun getAll(): AsyncResult<List<DTO>>                         = service.getAll()
    override fun getById(id: ID): AsyncResult<DTO?>                       = service.getById(id)
    override fun create(dto: DTO): AsyncResult<DTO>                       = service.create(dto)
    override fun createAll(dtos: List<DTO>): AsyncResult<List<DTO>>       = service.create(dtos)
    override fun update(dto: DTO): AsyncResult<DTO>                       = service.update(dto)
    override fun updateAll(dtos: List<DTO>): AsyncResult<List<DTO>>       = service.update(dtos)
    override fun delete(id: ID): AsyncResult<Unit>                        = service.delete(id)
    override fun deleteAll(ids: List<ID>): AsyncResult<Unit>              = service.delete(ids)
}