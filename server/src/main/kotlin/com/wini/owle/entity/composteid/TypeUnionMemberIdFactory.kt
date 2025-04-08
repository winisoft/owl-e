package com.wini.owle.entity.composteid

import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.entity.TypeUnionMembers
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column

object TypeUnionMemberIdFactory : CompositeIdFactoryImpl<TypeUnionMemberDto>() {

    override fun fromDtoInstance(dto: TypeUnionMemberDto): CompositeID? {
        return CompositeID {
            it[TypeUnionMembers.unionTypeDeclaration] = dto.unionTypeDeclarationId
            it[TypeUnionMembers.memberTypeDeclaration] = dto.memberTypeDeclarationId
        }
    }

    override val fkReferences: Map<Column<EntityID<Long>>, String> = buildMap {
        TypeUnionMembers.unionTypeDeclaration to "unionTypeId"
        TypeUnionMembers.memberTypeDeclaration to "memberTypeId"
    }
}