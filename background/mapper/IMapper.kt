package com.wini.owle.mapper

import com.wini.owle.dto.BaseDto

/**
 * Interface for mapping between database entities and DTOs.
 * This interface defines methods for converting an entity to a DTO
 * and vice versa, enabling a consistent and structured approach to
 * managing data flow between the application and database.
 *
 * @param DTO The Data Transfer Object (DTO) type that represents the entity data
 * @param Entity The database entity type that corresponds to a record in the database
 */
interface IDbMapper<DTO : BaseDto, Entity> {
    /**
     * Converts a given database entity to a Data Transfer Object (DTO).
     *
     * @param entity The entity to be converted
     * @return The corresponding DTO representation of the entity
     */
    fun toDto(entity: Entity?): DTO?

    /**
     * Converts a given Data Transfer Object (DTO) to a database entity.
     *
     * @param dto The DTO to be converted
     * @return The corresponding entity representation of the DTO
     */
    fun toEntity(dto: DTO?): Entity?

}

/**
 * Interface for mapping between models and DTOs.
 * Used for converting between a model type used in the application logic
 * and a DTO type used for data transport. This interface defines a one-way
 * conversion, typically used when DTOs are populated from the model.
 *
 * @param DTO The Data Transfer Object (DTO) type that represents the model data
 * @param T The model type used in the application layer
 */
interface IModelMapper<DTO : BaseDto, T> {
    /**
     * Converts a given model to a Data Transfer Object (DTO).
     *
     * @param model The model to be converted
     * @return The corresponding DTO representation of the model
     */
    fun toDto(model: T): DTO

    /**
     * Converts a given entity's Data Transfer Object to a presentation-layer model repesentation
     *
     * @param dto The DTO to be converted
     * @return The corresponding 'View Model' representation of the DTO
     */
    fun toModel(dto: DTO): T
}

/**
 * Interface for mapping between enumerated DTOs and enumerated database entities.
 * Designed for enums that represent similar concepts across different layers,
 * enabling conversions between an enum used in the database (or transport layer)
 * and an enum used in application logic.
 *
 * @param DTO The Data Transfer Object (DTO) type representing the enum data
 * @param T2 The entity enum type used in the application or database layer
 */
interface IDbEnumMapper<DTO, T2> {
    /**
     * Converts a given enumerated DTO to its corresponding application or database enum.
     *
     * @param dto The DTO enum to be converted
     * @return The corresponding application or database enum representation of the DTO
     */
    fun toEntity(dto: DTO): T2

    /**
     * Converts a given application or database enum to its corresponding DTO enum.
     *
     * @param entity The entity enum to be converted
     * @return The corresponding DTO representation of the enum
     */
    fun toDto(entity: T2): DTO
}

/**
 * Interface for mapping between enumerated DTOs and application-level enumerated models.
 * Useful for converting between enums used in data transport (DTO) and enums that represent
 * model states or types within the application logic.
 *
 * @param DTO The Data Transfer Object (DTO) type representing the enum data
 * @param T2 The model enum type used within the application layer
 */
interface IModelEnumMapper<DTO, T2> {
    /**
     * Converts a given enumerated DTO to its corresponding application model enum.
     *
     * @param dto The DTO enum to be converted
     * @return The corresponding model representation of the DTO enum
     */
    fun toModel(dto: DTO): T2

    /**
     * Converts a given application model enum to its corresponding DTO enum.
     *
     * @param model The model enum to be converted
     * @return The corresponding DTO representation of the model enum
     */
    fun toDto(model: T2): DTO
}
