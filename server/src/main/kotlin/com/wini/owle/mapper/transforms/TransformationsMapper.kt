package com.wini.owle.mapper.transforms

import com.wini.owle.dto.TransformationDto
import com.wini.owle.entity.TransformTargetTypes // Table definition, keep if needed
import com.wini.owle.entity.TransformationArgs // Table definition, keep if needed
import com.wini.owle.entity.Transformations // Table definition, keep if needed
import com.wini.owle.entity.TypeDeclarations // Table definition, keep if needed
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.transforms.TransformTargetTypeDao // Not directly used here
import com.wini.owle.entity.transforms.TransformationArgDao // Not directly used here
import com.wini.owle.entity.transforms.TransformationDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import org.jetbrains.exposed.dao.id.EntityID


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