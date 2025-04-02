package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.TransformationArgDto
import com.wini.owle.entity.transforms.TransformationArgDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
// Removed TypeDefinitionsMapper import as it's not directly used here


class TransformationArgsMapper(
    private val daoFactory: DaoFactory<TransformationArgDao, Long>,
    private val transformationsMapper: TransformationsMapper,
    private val typeDeclarationsMapper: TypeDeclarationsMapper
): DbMapper<TransformationArgDto, TransformationArgDao>() {

    override fun toEntity(dto: TransformationArgDto): TransformationArgDao {
        val applyProps: TransformationArgDao.() -> Unit = {
            transformation = transformationsMapper.toEntity(dto.transformation)
            typeDeclaration = typeDeclarationsMapper.toEntity(dto.typeDeclaration)
            name = dto.name
            description = dto.description
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TransformationArgDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TransformationArg with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TransformationArgDao): TransformationArgDto {
        return TransformationArgDto(
            id = entity.id.value,
            transformation = transformationsMapper.toDto(entity.transformation),
            typeDeclaration = typeDeclarationsMapper.toDto(entity.typeDeclaration),
            name = entity.name,
            description = entity.description
        )
    }
}