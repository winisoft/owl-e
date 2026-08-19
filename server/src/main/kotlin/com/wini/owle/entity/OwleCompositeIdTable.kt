package com.wini.owle.entity

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IdTable


/**
 * A composite-keyed table whose key parts are foreign keys.
 *
 * Declaring `PrimaryKey(a, b)` does not make `a` and `b` this table's *identity* columns. Exposed
 * tracks those in a separate set that only `Column.entityId()` and `addIdColumn` write to, and
 * `reference()` calls neither — it routes to the `entityId(name, table)` overload, which registers
 * an ordinary column. All twenty-three composite tables here are keyed on references, so all
 * twenty-three had an empty id-column set and `idColumns` threw
 * "Table definition must include id columns" on the first read.
 *
 * That is not a corner: `CompositeIdDaoDelegate` derives both `columnsByRequestParam` and
 * `buildId` from `idColumns`, so no composite endpoint could turn a request into an id at all.
 * Nothing caught it because nothing exercised the endpoint layer until it had tests.
 *
 * [keyReference] does the registration as part of declaring the column, so a key part cannot be
 * added without it. `.entityId()` is not an option here — these columns are already `EntityID`
 * typed, and applying it would wrap them twice.
 */
open class OwleCompositeIdTable(name: String) : CompositeIdTable(name) {

    /** A foreign key that is also one of this table's key columns. */
    protected fun <T : Any> keyReference(
        name: String,
        foreign: IdTable<T>,
        onDelete: ReferenceOption? = null,
        onUpdate: ReferenceOption? = null
    ): Column<EntityID<T>> =
        reference(name, foreign, onDelete, onUpdate).also { addIdColumn(it) }

    /** A non-reference column that is one of this table's key columns. */
    protected fun <T : Any> Column<T>.asKey(): Column<EntityID<T>> = entityId()
}
