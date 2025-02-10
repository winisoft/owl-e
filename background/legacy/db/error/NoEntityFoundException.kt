package com.wini.owle.air.db.error

class NoEntityFoundException(
    override val message: String = "No database entity could be found matching the provided id."
): Exception()