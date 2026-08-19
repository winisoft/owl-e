package com.wini.owle.dto.typing.enums

import com.wini.owle.dto.DbToken



enum class SchemaMembership(override val dbValue: String) : DbToken {

    /**
     * Just a data attribute
     */
    ATTRIBUTE("attribute"),

    /**
     * there should be a matching callable_signatures row (look up by parent_schema_ref_id, name)
     */
    METHOD("method"),

    /**
     * a @property (zero-arg method returning something)
     */
    PROPERTY("property"),

    /**
     * a class variable with a type
     */
    CLASSVAR("classvar")

}