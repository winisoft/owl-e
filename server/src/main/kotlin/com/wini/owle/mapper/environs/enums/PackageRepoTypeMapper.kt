package com.wini.owle.mapper.environs.enums

import com.wini.owle.dto.environs.enums.PyPackageRepoType
import com.wini.owle.entity.environs.enums.PackageRepoTypeEntity
import com.wini.owle.entity.environs.enums.PackageRepoTypeEntity.*

class PackageRepoTypeMapper {

    fun toEntity(dto: PyPackageRepoType): PackageRepoTypeEntity {
        return when (dto) {
            PyPackageRepoType.PYPI -> PYPI
            PyPackageRepoType.CONDA -> CONDA
            PyPackageRepoType.CUSTOM -> CUSTOM
            PyPackageRepoType.GITHUB -> GITHUB
            PyPackageRepoType.LOCAL -> LOCAL
        }
    }

    fun toDto(entity: PackageRepoTypeEntity): PyPackageRepoType {
        return when (entity) {
            PYPI -> PyPackageRepoType.PYPI
            CONDA -> PyPackageRepoType.CONDA
            CUSTOM -> PyPackageRepoType.CUSTOM
            GITHUB -> PyPackageRepoType.GITHUB
            LOCAL -> PyPackageRepoType.LOCAL
        }
    }
}