package com.wini.owle.services.user

import com.wini.owle.dto.user.UserDto
import com.wini.owle.entity.user.UserDao
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.repo.UserIdFactory
import com.wini.owle.repo.user.UserRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class UserService(
    repository: UserRepository,
    mapper: UsersMapper,
    entityIdFactory: EntityIdFactory<Long>
): BaseServiceImpl<Long, UserDto, UserDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)