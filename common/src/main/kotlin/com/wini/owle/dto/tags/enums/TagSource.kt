package com.wini.owle.dto.tags.enums

import com.wini.owle.dto.DbToken


enum class TagSource(override val dbValue: String) : DbToken {

    /**
     * Owl-E defined (internal base categories)
     */
    SYSTEM("system"),

    /**
     * From external PyPI classifiers
     */
    PYPI("pypi"),

    /**
     * User-created tags
     */
    USER("user")
}


