package com.wini.owle.entity.steps

import com.wini.owle.entity.typing.TypeNodeEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a function filter in an error watch, specifying a function
 * name and position that must appear in the call stack for the error watch to trigger.
 */
class ErrorWatchFnEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<ErrorWatchFnEntity>(ErrorWatchFns)

    /**
     * The error watch that this function filter is associated with.
     * Deletes cascade to this function filter.
     */
    var errorWatch by ErrorWatchEntity referencedOn ErrorWatchFns.errorWatch

    /**
     * Name of the function that should appear in the call stack to activate the error watch.
     */
    var functionNode by TypeNodeEntity referencedOn ErrorWatchFns.functionNode

    /**
     * Specifies the required position in the call stack for the function name, used to match errors accurately.
     */
    var position by ErrorWatchFns.position
}