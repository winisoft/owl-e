package com.wini.owle.dto.typing.enums

import com.wini.owle.dto.DbToken


enum class TypeNodeKind(override val dbValue: String) : DbToken {

    BASE("base"),

    /**
     * Represent a parameterized generic at runtime (e.g. List[int]). They refer back to their “base” via base_node_id.
     */
    @Suppress("KDocUnresolvedReference")
    INSTANTIATION("instantiation"),

    /**
     * represent a TypeVar (e.g. T).
     */
    TYPEVAR("typevar"),

    /**
     * Present to represent a type node for a Union[...] (e.g. Union[int,str]) and will have multiple rows in union_members.
     */
    UNION("union"),

    /**
     * Represent among Type Nodes function signatures (the “callable type” itself).
     */
    CALLABLE("callable"),

    METHOD("method"),

    ATTRIBUTE("attribute"),

    CLASS("class"),

    GENERIC("generic"),

    PRIMITIVE("primitive"),

    FUNCTION("function")

}