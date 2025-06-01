package com.wini.owle.dto.tags.enums


enum class TagSource {

    /**
     * Owl-E defined (internal base categories)
     */
    SYSTEM,

    /**
     * From external PyPI classifiers
     */
    PYPI,

    /**
     * User-created tags
     */
    USER
}


