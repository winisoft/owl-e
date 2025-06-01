package com.wini.owle

import com.wini.owle.dto.DtoTest
import com.wini.owle.repo.RepositoryTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested


class OwleTest {

//    @Nested
//    @DisplayName("API Tests")
//    inner class ApiTests: ApiTest()

    @Nested
    @DisplayName("DTO Serialization Tests")
    inner class DtoTests: DtoTest()

    @Nested
    @DisplayName("Repository Tests")
    inner class RepositoryTests: RepositoryTest()

//    @Nested
//    @DisplayName("Mapper Tests")
//    inner class MappingTests: MapperTests()

//    @Nested
//    @DisplayName("Service Layer Tests")
//    inner class ServiceLayer: ServiceTestSuite()

}