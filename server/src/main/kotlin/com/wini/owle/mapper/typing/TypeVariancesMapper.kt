package com.wini.owle.mapper.typing

import com.wini.owle.dto.TypeVarianceDto
import com.wini.owle.entity.VarianceCardinality
import com.wini.owle.entity.typing.TypeVarianceDao
import com.wini.owle.mapper.DbMapper


class TypeVariancesMapper(
    private val typeDeclarationsMapper: TypeDeclarationsMapper
) : DbMapper<TypeVarianceDto, TypeVarianceDao>() {

    override val applyProps: TypeVarianceDao.(TypeVarianceDto) -> Unit = { dto ->
        typeVariableDeclaration = typeDeclarationsMapper.run { dto.typeVariableDeclaration.toEntity() }
    }

    override fun TypeVarianceDto.toEntity(): TypeVarianceDao {
        return if (id == 0L) {
            TypeVarianceDao.new { applyProps(this@toEntity) }
        } else {
            TypeVarianceDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("TypeVariance with id=$id not found")
        }
    }

    override fun TypeVarianceDao.toDto(): TypeVarianceDto {
        return TypeVarianceDto(
            id = id.value,
            typeVariableDeclaration = typeDeclarationsMapper.run { typeVariableDeclaration.toDto() },
            varianceCardinality = when (varianceCardinality) {
                VarianceCardinality.COVARIANT -> com.wini.owle.dto.enums.VarianceCardinality.Covariant
                VarianceCardinality.CONTRAVARIANT -> com.wini.owle.dto.enums.VarianceCardinality.Contravariant
                VarianceCardinality.INVARIANT -> com.wini.owle.dto.enums.VarianceCardinality.Invariant
            }
        )
    }
}