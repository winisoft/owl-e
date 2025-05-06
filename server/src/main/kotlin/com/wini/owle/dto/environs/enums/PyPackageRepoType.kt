package com.wini.owle.dto.environs.enums

import kotlinx.serialization.Serializable

@Serializable
enum class PyPackageRepoType {

    PYPI,

    CONDA,

    CUSTOM,

    GITHUB,

    LOCAL

}