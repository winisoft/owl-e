package com.wini.owle.mapper

import com.wini.owle.dto.BaseDto
import org.jetbrains.exposed.dao.Entity


interface BaseDbMapper<Dto: BaseDto, T: Any> {

    fun Dto.toEntity(): T

    fun T.toDto(): Dto

}

abstract class DbMapper<Dto: BaseDto, T: Any>: BaseDbMapper<Dto, T> {

    abstract val applyProps: T.(Dto) -> Unit

    override fun Dto.toEntity(): T {
        throw NotImplementedError("Subclasses must implement this")
    }

    override fun T.toDto(): Dto {
        throw NotImplementedError("Subclasses must implement this")
    }

    fun List<Dto>.toEntities(): List<T> = map { it.toEntity() }

    fun List<T>.toDtos(): List<Dto> = map { it.toDto() }

}