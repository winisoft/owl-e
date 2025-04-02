package com.wini.owle.entity.environs

import com.wini.owle.dto.environs.PackageRepoTypeDto

enum class PackageRepoType {
    PYPI, CONDA, CUSTOM, GITHUB, LOCAL;

    companion object {
        fun fromDto(dto: PackageRepoTypeDto): PackageRepoType {
            return when (dto) {
                PackageRepoTypeDto.Pypi -> PYPI
                PackageRepoTypeDto.Conda -> CONDA
                PackageRepoTypeDto.Custom -> CUSTOM
                PackageRepoTypeDto.Github -> GITHUB
                PackageRepoTypeDto.Local -> LOCAL
            }
        }
        fun PackageRepoType.toDto(): PackageRepoTypeDto {
            return when (this) {
                PYPI -> PackageRepoTypeDto.Pypi
                CONDA -> PackageRepoTypeDto.Conda
                CUSTOM -> PackageRepoTypeDto.Custom
                GITHUB -> PackageRepoTypeDto.Github
                LOCAL -> PackageRepoTypeDto.Local
            }
        }
    }
}