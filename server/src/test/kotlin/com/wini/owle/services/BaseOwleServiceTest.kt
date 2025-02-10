package com.wini.owle.services

import com.wini.owle.dto.BaseDto
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module


abstract class BaseOwleServiceTest<ID: Any, T: BaseDto>: OwleServiceTest<ID, T> {

    @BeforeEach
    fun setUp() {
        stopKoin()
        startKoin { modules(testModule) }
    }

}