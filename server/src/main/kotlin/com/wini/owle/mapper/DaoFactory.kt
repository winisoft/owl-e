package com.wini.owle.mapper

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass


interface DaoFactory<E : Entity<ID>, ID : Any> {
    fun create(): E
}

class ExposedDaoFactory<E : Entity<ID>, ID : Any, C : EntityClass<ID, E>>(
    private val entityClass: C
) : DaoFactory<E, ID> {

    override fun create(): E {
        return entityClass.new {}
    }
}


