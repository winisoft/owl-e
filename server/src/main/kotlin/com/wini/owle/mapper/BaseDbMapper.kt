package com.wini.owle.mapper

import com.wini.owle.dto.BaseDto

interface BaseDbMapper<Dto: BaseDto, T: Any> {

    fun toEntity(dto: Dto): T

    fun toDto(entity: T): Dto

}

abstract class DbMapper<Dto: BaseDto, T: Any>: BaseDbMapper<Dto, T> {

    override fun toEntity(dto: Dto): T {
        throw NotImplementedError("Subclasses must implement this")
    }

    override fun toDto(entity: T): Dto {
        throw NotImplementedError("Subclasses must implement this")
    }

    fun toEntities(dtos: List<Dto>): List<T> = dtos.map { toEntity(it) }

    fun toDtos(entities: List<T>): List<Dto> = entities.map { toDto(it) }

}