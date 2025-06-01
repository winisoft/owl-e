package com.wini.owle.services

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.BaseDto


interface Service<ID: Any, DTO: BaseDto> {

    fun getAll(): AsyncResult<List<DTO>>

    fun getById(id: ID): AsyncResult<DTO?>

    fun getWhere(predicate: (DTO) -> Boolean): AsyncResult<List<DTO>>

    fun create(dto: DTO): AsyncResult<DTO>

    fun create(dtos: List<DTO>): AsyncResult<List<DTO>>

    fun update(dto: DTO): AsyncResult<DTO>

    fun update(dtos: List<DTO>): AsyncResult<List<DTO>>

    fun delete(id: ID): AsyncResult<Unit>

    fun delete(ids: List<ID>): AsyncResult<Unit>

}