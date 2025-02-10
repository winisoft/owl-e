package com.wini.owle.mapper
//
//import com.wini.owle.dto.*
//import com.wini.owle.entity.*
//import com.wini.owle.entity.TagRelations.childTag
//import com.wini.owle.entity.TagRelations.parentTag
//import com.wini.owle.entity.TypeUnionMembers.memberTypeDeclaration
//import com.wini.owle.entity.TypeUnionMembers.unionTypeDeclaration
//import org.jetbrains.exposed.dao.id.CompositeID
//import org.jetbrains.exposed.dao.id.EntityID
//
//
//object TagRelationCompositeIdMap: CompositeIdMap<TagRelationDto> {
//    override fun buildId(dto: TagRelationDto): EntityID<CompositeID> {
//        val compositeId = CompositeID {
//            it[parentTag] = EntityID(dto.parentTagId, Tags)
//            it[childTag] = EntityID(dto.childTagId, Tags)
//        }
//        return EntityID(compositeId, TagRelations)
//    }
//}
//
//object TypeUnionMembersCompositeIdMap : CompositeIdMap<TypeUnionMemberDto> {
//    override fun buildId(dto: TypeUnionMemberDto): EntityID<CompositeID> {
//        val compositeId = CompositeID {
//            it[unionTypeDeclaration] = EntityID(dto.unionTypeDeclarationId, TypeDeclarations)
//            it[memberTypeDeclaration] = EntityID(dto.memberTypeDeclarationId, TypeDeclarations)
//        }
//
//        return EntityID(compositeId, TypeUnionMembers)
//    }
//}
//
//object TransformTargetTypeCompositeIdMap : CompositeIdMap<TransformationTargetTypeDto> {
//    override fun buildId(dto: TransformationTargetTypeDto): EntityID<CompositeID> {
//        val compositeId = CompositeID {
//            it[TransformTargetTypes.transformation] = EntityID(dto.transformationId, Transformations)
//            it[TransformTargetTypes.eligibleTypeDeclaration] = EntityID(dto.typeDeclarationId, TypeDeclarations)
//        }
//
//        return EntityID(compositeId, TypeUnionMembers)
//    }
//}
//
//object ToolPackageCompositeIdMap : CompositeIdMap<ToolPackageDependencyDto> {
//    override fun buildId(dto: ToolPackageDependencyDto): EntityID<CompositeID> {
//        val compositeId = CompositeID {
//            it[ToolPackageDependencies.tool] = EntityID(dto.toolId, Tools)
//            it[ToolPackageDependencies.packageEntity] = EntityID(dto.packageId, Packages)
//            it[ToolPackageDependencies.versionSpec] = EntityID(dto.versionSpecId, VersionSpecs)
//        }
//        return EntityID(compositeId, ToolPackageDependencies)
//    }
//}