package com.wini.owle.services

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.BaseDto


interface Service<ID: Any, Dto: BaseDto> {

    fun getAll(): AsyncResult<List<Dto>>

    fun getById(id: ID): AsyncResult<Dto?>

    fun create(dto: Dto): AsyncResult<Dto>

    fun create(dtos: List<Dto>): AsyncResult<List<Dto>>

    fun update(dto: Dto): AsyncResult<Dto>

    fun update(dtos: List<Dto>): AsyncResult<List<Dto>>

    fun delete(id: ID): AsyncResult<Boolean>

    fun delete(ids: List<ID>): AsyncResult<Boolean>

}