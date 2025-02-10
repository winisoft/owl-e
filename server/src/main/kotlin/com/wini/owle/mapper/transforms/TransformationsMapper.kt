package com.wini.owle.mapper.transforms

import com.wini.owle.dto.TransformationDto
import com.wini.owle.entity.TransformTargetTypes
import com.wini.owle.entity.TransformationArgs
import com.wini.owle.entity.Transformations
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.transforms.TransformTargetTypeDao
import com.wini.owle.entity.transforms.TransformationArgDao
import com.wini.owle.entity.transforms.TransformationDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import org.jetbrains.exposed.dao.id.EntityID


class TransformationsMapper(
    private val typeDeclarationMapper: TypeDeclarationsMapper
) : DbMapper<TransformationDto, TransformationDao>() {

    override val applyProps: TransformationDao.(TransformationDto) -> Unit = { dto ->
        name = dto.name
        description = dto.description
        examples = dto.examples
        returnsTypeDeclaration = typeDeclarationMapper.run { dto.returnsType?.toEntity() }
    }

    override fun TransformationDto.toEntity(): TransformationDao {
        return if (id == 0L) {
            TransformationDao.new { applyProps(this@toEntity) }
        } else {
            TransformationDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("Transformation with id=$id not found")
        }
    }

    override fun TransformationDao.toDto(): TransformationDto {
        return TransformationDto(
            id = id.value,
            name = name,
            description = description,
            examples = examples,
            returnsType = typeDeclarationMapper.run { returnsTypeDeclaration?.toDto() }
        )
    }
}