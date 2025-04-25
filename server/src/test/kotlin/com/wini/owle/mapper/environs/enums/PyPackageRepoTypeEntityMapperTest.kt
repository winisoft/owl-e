package com.wini.owle.mapper.environs.enums

import com.wini.owle.dto.environs.enums.PyPackageRepoType
import com.wini.owle.entity.environs.enums.PackageRepoTypeEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

open class PyPackageRepoTypeEntityMapperTest {

    private val mapper = PackageRepoTypeMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(PackageRepoTypeEntity.PYPI, mapper.toEntity(PyPackageRepoType.Pypi))
        assertEquals(PackageRepoTypeEntity.LOCAL, mapper.toEntity(PyPackageRepoType.Local))
        assertEquals(PackageRepoTypeEntity.GITHUB, mapper.toEntity(PyPackageRepoType.Github))
        assertEquals(PackageRepoTypeEntity.CONDA, mapper.toEntity(PyPackageRepoType.Conda))
        assertEquals(PackageRepoTypeEntity.CUSTOM, mapper.toEntity(PyPackageRepoType.Custom))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(PyPackageRepoType.Pypi, mapper.toDto(PackageRepoTypeEntity.PYPI))
        assertEquals(PyPackageRepoType.Custom, mapper.toDto(PackageRepoTypeEntity.CUSTOM))
        assertEquals(PyPackageRepoType.Conda, mapper.toDto(PackageRepoTypeEntity.CONDA))
        assertEquals(PyPackageRepoType.Github, mapper.toDto(PackageRepoTypeEntity.GITHUB))
        assertEquals(PyPackageRepoType.Local, mapper.toDto(PackageRepoTypeEntity.LOCAL))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            PyPackageRepoType.Pypi,
            PyPackageRepoType.Custom,
            PyPackageRepoType.Github,
            PyPackageRepoType.Conda,
            PyPackageRepoType.Local
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = PackageRepoTypeEntity.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}