package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.TransformationDto
import com.wini.owle.entity.transforms.TransformationDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper


class TransformationsMapper(
    private val daoFactory: DaoFactory<TransformationDao, Long>,
    private val typeDeclarationMapper: TypeDeclarationsMapper
): DbMapper<TransformationDto, TransformationDao>() {

    override fun toEntity(dto: TransformationDto): TransformationDao {
        val applyProps: TransformationDao.() -> Unit = {
            name = dto.name
            description = dto.description
            examples = dto.examples
            returnsTypeDeclaration = dto.returnsType?.let { typeDeclarationMapper.toEntity(it) }
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TransformationDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Transformation with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TransformationDao): TransformationDto {
        return TransformationDto(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            examples = entity.examples,
            returnsType = entity.returnsTypeDeclaration?.let { typeDeclarationMapper.toDto(it) }
        )
    }
}