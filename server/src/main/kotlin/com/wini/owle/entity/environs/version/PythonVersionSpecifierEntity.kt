package com.wini.owle.entity.environs.version

import com.wini.owle.entity.environs.PythonVersionSpecifiers
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * A reusable (version, operator, wildcard) triple — e.g. `>=3.9.*`. The whole triple is the key,
 * so identical constraints across thousands of packages are one row.
 */
class PythonVersionSpecifierEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<PythonVersionSpecifierEntity>(PythonVersionSpecifiers)

    var pythonVersion by PythonVersionEntity referencedOn PythonVersionSpecifiers.pythonVersion
}
