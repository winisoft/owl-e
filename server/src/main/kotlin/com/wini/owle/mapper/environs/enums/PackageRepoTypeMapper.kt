package com.wini.owle.mapper.environs.enums

import com.wini.owle.dto.environs.PackageRepoTypeDto
import com.wini.owle.entity.environs.enums.PackageRepoType
import com.wini.owle.entity.environs.enums.PackageRepoType.*

class PackageRepoTypeMapper {

    fun toEntity(dto: PackageRepoTypeDto): PackageRepoType {
        return when (dto) {
            PackageRepoTypeDto.Pypi -> PYPI
            PackageRepoTypeDto.Conda -> CONDA
            PackageRepoTypeDto.Custom -> CUSTOM
            PackageRepoTypeDto.Github -> GITHUB
            PackageRepoTypeDto.Local -> LOCAL
            else -> throw IllegalArgumentException("Unsupported member for PackageRepoTypeDto: ${dto.id}")
        }
    }

    fun toDto(entity: PackageRepoType): PackageRepoTypeDto {
        return when (entity) {
            PYPI -> PackageRepoTypeDto.Pypi
            CONDA -> PackageRepoTypeDto.Conda
            CUSTOM -> PackageRepoTypeDto.Custom
            GITHUB -> PackageRepoTypeDto.Github
            LOCAL -> PackageRepoTypeDto.Local
        }
    }
}