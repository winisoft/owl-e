package com.wini.owle.api

import com.wini.owle.dto.BaseDto
import org.jetbrains.exposed.dao.id.CompositeID


abstract class BaseCompositePkApiTest<T: BaseDto, R: RoutedApi>: BaseEndpointTest<CompositeID, T, R>()