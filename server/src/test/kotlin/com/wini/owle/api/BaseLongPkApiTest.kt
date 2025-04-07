package com.wini.owle.api

import com.wini.owle.dto.BaseDto


abstract class BaseLongPkApiTest<ID: Any, T: BaseDto, R: RoutedApi>: BaseEndpointTest<ID, T, R>()

