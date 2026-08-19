package com.wini.owle.dto.workflows.enums

import com.wini.owle.dto.DbToken
import kotlinx.serialization.Serializable

@Serializable
enum class ModificationTerms(override val dbValue: String) : DbToken {
    PROHIBITED("PROHIBITED"),
    ALLOW_MODIFICATION("ALLOW_MODIFICATION"),
    ALLOW_MODIFICATION_REDISTRIBUTION("ALLOW_MODIFICATION_REDISTRIBUTION")
}