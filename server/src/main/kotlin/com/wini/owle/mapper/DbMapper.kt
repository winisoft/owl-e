package com.wini.owle.mapper

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.StringDto
import com.wini.owle.dto.ULongDto
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


interface DbMapper<ID: Any, DTO: BaseDto, E: Entity<ID>> {

    fun toEntity(dto: DTO): E

    fun toDto(entity: E): DTO

    fun toEntities(dtos: List<DTO>): List<E> = dtos.map { toEntity(it) }

    fun toDtos(entities: List<E>): List<DTO> = entities.map { toDto(it) }

}


//abstract class StringIdMapper<DTO: StringDto, E: Entity<String>>: DbMapper<String, DTO, E>
//
//abstract class ULongIdMapper<DTO: ULongDto, E: ULongEntity>: DbMapper<ULong, DTO, E>
//
//abstract class CompositeIdMapper<DTO: CompositeDto, E: CompositeEntity>: DbMapper<CompositeID, DTO, E>
//
//interface CompositeIdDbMapper<DTO: CompositeDto, E: CompositeEntity>: DbMapper<CompositeID, DTO, E>
//
//interface ULongIdDbMapper<DTO: ULongDto, E: ULongEntity>: DbMapper<ULong, DTO, E>