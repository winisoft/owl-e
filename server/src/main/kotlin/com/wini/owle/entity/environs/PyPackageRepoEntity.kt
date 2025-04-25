package com.wini.owle.entity.environs

import com.wini.owle.entity.PackageRepositories
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents potential sources or repositories for packages that a workflow step can utilize.
 * Examples include official repositories (like PyPI or Conda) and custom or local sources.
 */
class PyPackageRepoEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PyPackageRepoEntity>(PackageRepositories)

    /**
     * Enum value that specifies the type of repository:
     */
    var sourceType by PackageRepositories.sourceType

    /**
     * URL of the repository where the package can be sourced.
     * Required, even for custom or local sources, to ensure accessibility.
     */
    var sourceUrl by PackageRepositories.sourceUrl
}