package com.wini.owle.mapper.transforms

import com.wini.owle.dto.TransformationArgDto
import com.wini.owle.dto.TypeDeclarationDto
import com.wini.owle.entity.TransformationArgs
import com.wini.owle.entity.Transformations
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.transforms.TransformationArgDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import com.wini.owle.mapper.typing.TypeDefinitionsMapper
import org.jetbrains.exposed.dao.id.EntityID


class TransformationArgsMapper(
    private val transformationsMapper: TransformationsMapper,
    private val typeDeclarationsMapper: TypeDeclarationsMapper
) : DbMapper<TransformationArgDto, TransformationArgDao>() {

    override val applyProps: TransformationArgDao.(TransformationArgDto) -> Unit = { dto ->
        transformation = transformationsMapper.run { dto.transformation.toEntity() }
        typeDeclaration = typeDeclarationsMapper.run { dto.typeDeclaration.toEntity() }
        name = dto.name
        description = dto.description
    }

    override fun TransformationArgDto.toEntity(): TransformationArgDao {
        return if (id == 0L) {
            TransformationArgDao.new { applyProps(this@toEntity) }
        } else {
            TransformationArgDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("TransformationArg with id=$id not found")
        }
    }

    override fun TransformationArgDao.toDto(): TransformationArgDto {
        return TransformationArgDto(
            id = id.value,
            transformation = transformationsMapper.run { transformation.toDto() },
            typeDeclaration = typeDeclarationsMapper.run { typeDeclaration.toDto() },
            name = name,
            description = description
        )
    }
}