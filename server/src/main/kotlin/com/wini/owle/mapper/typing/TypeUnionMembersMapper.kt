package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.entity.typing.TypeUnionMemberDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID


class TypeUnionMembersMapper(
    private val daoFactory: DaoFactory<TypeUnionMemberDao, CompositeID>
) : DbMapper<TypeUnionMemberDto, TypeUnionMemberDao>() {

    // This mapper handles a relationship, likely without its own ID or updateable properties
    // beyond the relationship itself. applyProps might remain empty or minimal.
//    val applyProps: TypeUnionMemberDao.(TypeUnionMemberDto) -> TypeUnionMemberDao = {
//        // No direct properties to apply after creation/retrieval in this specific case.
//        // The relationship is defined by the IDs passed to create/find methods.
//    }

    // This entity represents a relationship (many-to-many link table entry likely)
    // It doesn't have a simple ID for findById/update. Creation is the primary operation.
    override fun toEntity(dto: TypeUnionMemberDto): TypeUnionMemberDao {
        // Assuming TypeUnionMemberDao.create handles finding or creating the link.
        // If duplicates should be prevented, create might need adjustment or prior checks.
        // This deviates from the standard pattern as there's no ID check for new vs update.
        return TypeUnionMemberDao.create(
            unionTypeId = dto.unionTypeDeclarationId,
            memberTypeId = dto.memberTypeDeclarationId
        )
        // If TypeUnionMemberDao implemented findById or similar based on composite key,
        // we could potentially implement update logic, but based on the original code,
        // it seems focused on creation.
    }

    override fun toDto(entity: TypeUnionMemberDao): TypeUnionMemberDto {
        // Assumes TypeUnionMemberDao holds references to the actual TypeDeclarationDao instances
        return TypeUnionMemberDto(
            unionTypeDeclarationId = entity.unionTypeDeclaration.id.value,
            memberTypeDeclarationId = entity.memberTypeDeclaration.id.value
        )
    }
}