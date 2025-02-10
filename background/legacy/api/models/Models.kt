package com.wini.owle.air.api.models

import kotlinx.serialization.Serializable

@Serializable
class Models(val items: List<ModelsItem>): List<ModelsItem> by items