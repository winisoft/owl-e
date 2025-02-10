package com.wini.owle.mapper

/**
 * Abstract base class providing common functionality for mappers that convert between DTO and Entity types.
 * This class outlines the basic steps for transforming data between different representations, allowing for
 * extension and customization via specialized strategies.
 *
 * @param DTO The Data Transfer Object type.
 * @param Entity The Entity type.
 */
abstract class BaseDbMapper<DTO, Entity> : DbMapper<DTO, Entity> {

    /**
     * Defines a function that creates an undefined version of the Entity, typically used as the starting point.
     */
    protected abstract val undefinedEntity: () -> Entity

    /**
     * Defines the minimum viable DTO derived from an Entity, serving as a foundational representation of the DTO.
     */
    protected abstract val minViableDto: (Entity) -> DTO

    /**
     * Defines the minimum viable Entity derived from a DTO, establishing the foundational representation.
     */
    protected abstract val minViableEntity: Entity.(DTO) -> Entity

    /**
     * A list of steps applied to transform a DTO into an Entity. These steps incrementally build on the base representation.
     */
    protected abstract val toEntitySteps: List<Entity.(DTO) -> Unit>

    /**
     * A list of steps applied to transform an Entity into a DTO. These steps incrementally build on the base representation.
     */
    protected abstract val toDtoSteps: List<(DTO, Entity) -> DTO>

    /**
     * Converts a DTO to an Entity by applying each transformation step in sequence.
     *
     * @param dto The DTO to be transformed.
     * @return The resulting Entity.
     */
    override fun toEntity(dto: DTO): Entity {
        return undefinedEntity().apply {
            toEntitySteps.forEach { it(dto) }
        }
    }

    /**
     * Converts an Entity to a DTO by applying each transformation step in sequence.
     *
     * @param entity The Entity to be transformed.
     * @return The resulting DTO.
     */
    override fun toDto(entity: Entity): DTO {
        return minViableDto(entity).apply {
            toDtoSteps.forEach { it(this, entity) }
        }
    }
}