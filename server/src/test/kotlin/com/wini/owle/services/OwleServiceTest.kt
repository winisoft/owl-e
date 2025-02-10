package com.wini.owle.services

import com.wini.owle.dto.BaseDto
import org.koin.core.module.Module
import org.koin.test.KoinTest


interface OwleServiceTest<ID: Any, T>: KoinTest where T : BaseDto {

    val testModule: Module
    fun getInstance(): T
    fun getService(): Service<ID, T>
}