package com.wini.owle

import com.wini.owle.dto.DtoTest
import com.wini.owle.repo.RepositoryTest
import com.wini.owle.services.ServiceTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested


/**
 * Everything that runs without a database.
 *
 * Abstract for the same reason as [OwleIntegrationTestSuite]: [OwleTest] is the only entry point,
 * so nothing here is discovered twice.
 */
@DisplayName("Unit")
abstract class OwleUnitTestSuite {

    @Nested
    @DisplayName("DTO Serialization Tests")
    inner class DtoTests : DtoTest()

    @Nested
    @DisplayName("Repository Tests")
    inner class RepositoryTests : RepositoryTest()

//    @Nested
//    @DisplayName("Service Tests")
//    inner class ServiceTests: ServiceTest()
}
