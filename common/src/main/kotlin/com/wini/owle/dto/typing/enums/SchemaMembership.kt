package com.wini.owle.dto.typing.enums



enum class SchemaMembership {

    /**
     * Just a data attribute
     */
    ATTRIBUTE,

    /**
     * there should be a matching callable_signatures row (look up by parent_schema_ref_id, name)
     */
    METHOD,

    /**
     * a @property (zero-arg method returning something)
     */
    PROPERTY,

    /**
     * a class variable with a type
     */
    CLASSVAR

}