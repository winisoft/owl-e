package com.wini.owle.entity.composteid

import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import com.wini.owle.entity.TransformTargetTypes
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column


object TransformTargetTypeIdFactory: CompositeIdFactoryImpl<TransformationTargetTypeDto>() {

    override fun fromDtoInstance(dto: TransformationTargetTypeDto): CompositeID? {
        return CompositeID {
            it[TransformTargetTypes.transformation] to dto.transformationId
            it[TransformTargetTypes.eligibleTypeDeclaration] to dto.typeDeclarationId
        }
    }


    override val fkReferences: Map<Column<EntityID<Long>>, String> = buildMap {
        TransformTargetTypes.transformation to "transformationId"
        TransformTargetTypes.eligibleTypeDeclaration to "typeDeclarationId"
    }
}