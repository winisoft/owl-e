package com.wini.owle.api.user

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.user.tags.Tag
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl

class TagApiImpl(
    dll: DLL<Long, Tag>,
    validator: DtoValidator<Tag>
): LongIdEndpointImpl<Tag>(
    dll = dll,
    serializer = Tag.serializer(),
    validator = validator
) {

}