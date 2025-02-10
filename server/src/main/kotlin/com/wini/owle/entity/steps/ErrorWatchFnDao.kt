package com.wini.owle.entity.steps

import com.wini.owle.entity.ErrorWatchFns
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a function filter in an error watch, specifying a function
 * name and position that must appear in the call stack for the error watch to trigger.
 */
class ErrorWatchFnDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ErrorWatchFnDao>(ErrorWatchFns)

    /**
     * The error watch that this function filter is associated with.
     * Deletes cascade to this function filter.
     */
    var errorWatch by ErrorWatchDao referencedOn ErrorWatchFns.errorWatch

    /**
     * Name of the function that should appear in the call stack to activate the error watch.
     */
    var functionName by ErrorWatchFns.functionName

    /**
     * Specifies the required position in the call stack for the function name, used to match errors accurately.
     */
    var position by ErrorWatchFns.position
}