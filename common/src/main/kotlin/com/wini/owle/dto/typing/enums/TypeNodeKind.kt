package com.wini.owle.dto.typing.enums


enum class TypeNodeKind {

    BASE,

    /**
     * Represent a parameterized generic at runtime (e.g. List[int]). They refer back to their “base” via base_node_id.
     */
    @Suppress("KDocUnresolvedReference")
    INSTANTIATION,

    /**
     * represent a TypeVar (e.g. T).
     */
    TYPEVAR,

    /**
     * Present to represent a type node for a Union[...] (e.g. Union[int,str]) and will have multiple rows in union_members.
     */
    UNION,

    /**
     * Represent among Type Nodes function signatures (the “callable type” itself).
     */
    CALLABLE

}