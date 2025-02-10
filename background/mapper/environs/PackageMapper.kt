package com.wini.owle.mapper.environs

import com.wini.owle.dto.PackageDto
import com.wini.owle.entity.environs.PackageEntity
import com.wini.owle.mapper.BaseDbMapper

/**
 * A sealed class defining mapping strategies for converting between PackageDto and PackageEntity.
 * The PackageMapper includes a variety of approaches to handling different data requirements,
 * ensuring that complex relationships such as versions and disambiguations are properly represented.
 */
sealed class PackageMapper : BaseDbMapper<PackageDto, PackageEntity>() {

    /**
     * Provides an undefined instance of PackageEntity, used as a starting point for transformations.
     */
    override val undefinedEntity: () -> PackageEntity = {
        PackageEntity()
    }

    /**
     * Defines the minimum viable DTO, covering basic and essential properties from PackageEntity.
     */
    override val minViableDto: (PackageEntity) -> PackageDto = { entity ->
        PackageDto(
            id = entity.id,
            name = entity.name,
            disambiguation = entity.disambiguation,
            versionSpecId = entity.versionSpecId
        )
    }

    /**
     * Defines the minimum viable Entity, mapping basic fields from the PackageDto.
     */
    override val minViableEntity: PackageEntity.(PackageDto) -> PackageEntity = { dto ->
        id = dto.id
        name = dto.name
        disambiguation = dto.disambiguation
        versionSpecId = dto.versionSpecId
        this
    }

    /**
     * Represents a basic mapping strategy that uses only core package fields, without any extensions.
     */
    data object PrimitiveValues : PackageMapper() {
        /**
         * Defines the steps for converting a PackageDto to a PackageEntity, using only core fields.
         */
        override val toEntitySteps: List<PackageEntity.(PackageDto) -> Unit> = listOf { dto ->
            minViableEntity(dto)
        }

        /**
         * Defines the steps for converting a PackageEntity to a PackageDto, using only core fields.
         */
        override val toDtoSteps: List<(PackageDto, PackageEntity) -> PackageDto> = listOf { _, entity ->
            minViableDto(entity)
        }
    }

    /**
     * Represents a mapping strategy that includes the mapping of the package's version specification in detail.
     */
    data object PackageWithVersionDetails : PackageMapper() {
        /**
         * Extends the core sequence to include version specification details when converting PackageDto to PackageEntity.
         */
        private val versionDetailsToEntity: PackageEntity.(PackageDto) -> Unit = { dto ->
            versionSpec = VersionSpecMapper.toEntity(dto.versionSpec)
        }

        /**
         * Extends the core sequence to include version specification details when converting PackageEntity to PackageDto.
         */
        private val versionDetailsToDto: (PackageDto, PackageEntity) -> PackageDto = { dto, entity ->
            dto.copy(
                versionSpec = entity.versionSpec?.let {
                    VersionSpecMapper.toDto(it)
                } ?: throw IllegalStateException("VersionSpec must not be null")
            )
        }

        /**
         * Extends the core sequence of steps to include version details when converting a PackageDto to PackageEntity.
         */
        override val toEntitySteps: List<PackageEntity.(PackageDto) -> Unit>
            get() = listOf({ minViableEntity(it) }, versionDetailsToEntity)

        /**
         * Extends the core sequence of steps to include version details when converting a PackageEntity to PackageDto.
         */
        override val toDtoSteps: List<(PackageDto, PackageEntity) -> PackageDto>
            get() = listOf({ _, entity -> minViableDto(entity) }, versionDetailsToDto)
    }
}
