package com.wini.owle.dto.environs.enums


enum class EnvMarkerOperator {

    EQ, NE, LT, LE, GT, GE, IN, NOT_IN;

    override fun toString(): String = when (this) {
        EQ -> "=="
        NE -> "!="
        LT -> "<"
        LE -> "<="
        GT -> ">"
        GE -> ">="
        IN -> "in"
        NOT_IN -> "not in"
    }

}
