package com.wini.owle.dto.environs

import kotlinx.serialization.Serializable

@Serializable
sealed class PackageRepoTypeDto(val id: String) {

    @Serializable
    data object Pypi : PackageRepoTypeDto("PYPI")

    @Serializable
    data object Conda : PackageRepoTypeDto("CONDA")

    @Serializable
    data object Custom : PackageRepoTypeDto("CUSTOM")

    @Serializable
    data object Github : PackageRepoTypeDto("GITHUB")

    @Serializable
    data object Local : PackageRepoTypeDto("LOCAL")
}