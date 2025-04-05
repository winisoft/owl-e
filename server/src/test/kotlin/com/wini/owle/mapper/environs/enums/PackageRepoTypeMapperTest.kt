package com.wini.owle.mapper.environs.enums

import com.wini.owle.dto.environs.PackageRepoTypeDto
import com.wini.owle.entity.environs.enums.PackageRepoType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

open class PackageRepoTypeMapperTest {

    private val mapper = PackageRepoTypeMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(PackageRepoType.PYPI, mapper.toEntity(PackageRepoTypeDto.Pypi))
        assertEquals(PackageRepoType.LOCAL, mapper.toEntity(PackageRepoTypeDto.Local))
        assertEquals(PackageRepoType.GITHUB, mapper.toEntity(PackageRepoTypeDto.Github))
        assertEquals(PackageRepoType.CONDA, mapper.toEntity(PackageRepoTypeDto.Conda))
        assertEquals(PackageRepoType.CUSTOM, mapper.toEntity(PackageRepoTypeDto.Custom))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(PackageRepoTypeDto.Pypi, mapper.toDto(PackageRepoType.PYPI))
        assertEquals(PackageRepoTypeDto.Custom, mapper.toDto(PackageRepoType.CUSTOM))
        assertEquals(PackageRepoTypeDto.Conda, mapper.toDto(PackageRepoType.CONDA))
        assertEquals(PackageRepoTypeDto.Github, mapper.toDto(PackageRepoType.GITHUB))
        assertEquals(PackageRepoTypeDto.Local, mapper.toDto(PackageRepoType.LOCAL))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            PackageRepoTypeDto.Pypi,
            PackageRepoTypeDto.Custom,
            PackageRepoTypeDto.Github,
            PackageRepoTypeDto.Conda,
            PackageRepoTypeDto.Local
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = PackageRepoType.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}