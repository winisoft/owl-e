package com.wini.owle

import com.wini.owle.dto.DtoTest
import com.wini.owle.error.ConstraintErrorTest
import com.wini.owle.validators.DtoValidatorTest
import com.wini.owle.services.environs.marker.EnvMarkerComparisonServiceTest
import com.wini.owle.wiring.DiGraphTest
import com.wini.owle.repo.RepositoryTest
import com.wini.owle.services.ServiceTest
import com.wini.owle.services.ServiceTestSuite
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

    @Nested
    @DisplayName("Constraint Rules")
    inner class ConstraintRules : ConstraintErrorTest()

    @Nested
    @DisplayName("Validator Mechanism")
    inner class ValidatorMechanism : DtoValidatorTest()

    @Nested
    @DisplayName("Generated Layer")
    inner class GeneratedLayer : com.wini.owle.wiring.GeneratedLayerTest()

    @Nested
    @DisplayName("Version Specifiers")
    inner class VersionSpecifiers : com.wini.owle.environs.VersionSpecTest()

    @Nested
    @DisplayName("Marker Evaluation")
    inner class MarkerEvaluation : com.wini.owle.environs.MarkerEvaluationTest()

    @Nested
    @DisplayName("DLL Delegation")
    inner class DllDelegation : com.wini.owle.dll.DllDelegationTest()

    @Nested
    @DisplayName("Endpoint Id Parsing")
    inner class EndpointIds : com.wini.owle.api.EndpointIdTest()

    @Nested
    @DisplayName("Service Layer")
    inner class ServiceLayer : EnvMarkerComparisonServiceTest()

    @Nested
    @DisplayName("DI Graph")
    inner class DiGraph : DiGraphTest()

    @Nested
    @DisplayName("Service Tests")
    inner class ServiceTests: ServiceTestSuite()
}
