package com.wini.owle.mapper

/**
 * Interface for a core mapper that maps between DTO (Data Transfer Object) and Entity.
 *
 * @param DTO The Data Transfer Object type.
 * @param Entity The Entity type.
 */
interface DbMapper<DTO, Entity> {
    /**
     * Converts a DTO to an Entity.
     *
     * @param dto The DTO to be converted.
     * @return The corresponding Entity.
     */
    fun toEntity(dto: DTO): Entity

    /**
     * Converts an Entity to a DTO.
     *
     * @param entity The Entity to be converted.
     * @return The corresponding DTO.
     */
    fun toDto(entity: Entity): DTO
}