package com.wini.owle.dll.user

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.UserDto
import com.wini.owle.services.user.UserService

class UserDll(
    private val service: UserService
): DLL<Long, UserDto> {

    override fun getAll(): AsyncResult<List<UserDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<UserDto?> = service.getById(id)
    override fun create(dto: UserDto): AsyncResult<UserDto> = service.create(dto)
    override fun create(dtos: List<UserDto>): AsyncResult<List<UserDto>> = service.create(dtos)
    override fun update(dto: UserDto): AsyncResult<UserDto> = service.update(dto)
    override fun update(dtos: List<UserDto>): AsyncResult<List<UserDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}