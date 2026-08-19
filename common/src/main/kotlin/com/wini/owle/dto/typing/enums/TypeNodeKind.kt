package com.wini.owle.dto.typing.enums

import com.wini.owle.dto.DbToken


/**
 * The form a type expression takes.
 *
 * One axis only: how the type is *written*, which determines which satellite table holds its
 * detail. A member of a class is not a form of type expression - it is a name bound to a type on an
 * owner, which is what `type_schema_refs.member_kind` records - so `method` and `attribute` are not
 * here. Neither are `class` (indistinguishable from [BASE]), `primitive` (primitives are classes in
 * Python), `generic` (the `is_generic` column already says so), or `function` (a signature is
 * [CALLABLE]).
 */
enum class TypeNodeKind(override val dbValue: String) : DbToken {

    /** A named class or type - `int`, `asyncio.Task`. Located by namespace and name. */
    BASE("base"),

    /**
     * A parameterized generic - `list[int]`, `dict[str, int]`. Decomposes into an origin and
     * ordered arguments via `type_instantiations` and `type_instantiation_args`.
     */
    INSTANTIATION("instantiation"),

    /** A `TypeVar`. Its bound, constraints and variance live in the three `typevar_*` tables. */
    TYPEVAR("typevar"),

    /**
     * A union - `str | int`. Members are held order-independently in `type_union_members`, because
     * `str | int` and `int | str` are the same type.
     */
    UNION("union"),

    /** A function signature as a type. Detail in `callable_signatures` and `callable_parameters`. */
    CALLABLE("callable")
}
