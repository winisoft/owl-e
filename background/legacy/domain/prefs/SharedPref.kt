package com.wini.owle.air.domain.prefs


import com.wini.owle.air.domain.model.prop.DataType
import com.wini.owle.air.inlines.DefaultValue
import com.wini.owle.air.inlines.Description
import com.wini.owle.air.inlines.SharedPrefId
import com.wini.owle.air.inlines.SharedPrefKey

data class SharedPref(
    val sharedPrefId: SharedPrefId,
    val source: String,
    val key: SharedPrefKey,
    val defaultValue: DefaultValue?,
    val dataType: DataType,
    val description: Description?
)