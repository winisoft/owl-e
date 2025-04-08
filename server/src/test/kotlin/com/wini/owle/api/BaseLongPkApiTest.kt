package com.wini.owle.api

import com.wini.owle.dto.BaseDto


abstract class BaseLongPkApiTest<T: BaseDto, R: RoutedApi>: BaseEndpointTest<Long, T, R>()