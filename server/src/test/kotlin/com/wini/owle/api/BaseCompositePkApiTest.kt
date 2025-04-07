package com.wini.owle.api

import com.wini.owle.dto.BaseDto


abstract class BaseCompositePkApiTest<ID: Any, T: BaseDto, R: RoutedApi>: BaseEndpointTest<ID, T, R>()