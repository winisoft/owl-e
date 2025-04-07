package com.wini.owle.mapper

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.dto.user.TagRelationDto
import com.wini.owle.entity.environs.ToolPackageDependencyDao
import com.wini.owle.entity.steps.ProvisionDataPointDao
import com.wini.owle.entity.transforms.TransformTargetTypeDao
import com.wini.owle.entity.typing.TypeUnionMemberDao
import com.wini.owle.entity.user.TagRelationDao
import org.jetbrains.exposed.dao.id.CompositeID


interface CompositeIdFactory<T: BaseDto> {

    /**
     * Constructs a CompositeID from a map of named request parameters.
     * Returns null if any required ID is missing or invalid.
     */
    fun idFromDto(dto: T): CompositeID

}

object ToolPackageDependencyIdFactory : CompositeIdFactory<ToolPackageDependencyDto> {
    override fun idFromDto(dto: ToolPackageDependencyDto): CompositeID {
        return ToolPackageDependencyDao.buildId(dto.toolId, dto.packageId, dto.versionSpecId)
    }
}

object TypeUnionMemberIdFactory : CompositeIdFactory<TypeUnionMemberDto> {
    override fun idFromDto(dto: TypeUnionMemberDto): CompositeID {
        return TypeUnionMemberDao.buildId(dto.unionTypeDeclarationId, dto.memberTypeDeclarationId)
    }
}

object TransformTargetTypeIdFactory: CompositeIdFactory<TransformationTargetTypeDto> {
    override fun idFromDto(dto: TransformationTargetTypeDto): CompositeID {
        return TransformTargetTypeDao.buildId(dto.transformationId, dto.typeDeclarationId)
    }
}

object ProvisionDataPointIdFactory: CompositeIdFactory<ProvisionDataPointDto> {
    override fun idFromDto(dto: ProvisionDataPointDto): CompositeID {
        return ProvisionDataPointDao.buildId(dto.provisionId, dto.dataPointId)
    }
}

object TagRelationIdFactory: CompositeIdFactory<TagRelationDto> {
    override fun idFromDto(dto: TagRelationDto): CompositeID {
        return TagRelationDao.buildId(dto.parentTagId, dto.childTagId)
    }
}