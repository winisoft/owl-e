package com.wini.owle.entity.composteid

import com.wini.owle.dto.BaseDto
import org.jetbrains.exposed.dao.id.CompositeID


interface CompositeIdFactory<T: BaseDto> {

    /**
     * Constructs a CompositeID from a map of named request parameters.
     * Returns null if any required ID is missing or invalid.
     */
    fun fromRequestParams(params: Map<String, List<String>>): CompositeID?

    fun fromDtoInstance(dto: T): CompositeID?
}


