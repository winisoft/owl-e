package com.wini.owle.repo.environs.pkg

import com.wini.owle.entity.environs.pkg.PackagePythonRequirementEntity
import com.wini.owle.entity.typing.TypeVarBoundEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test

/**
 * Specifies version constraints for a package, allowing for precise control
 * over the range or exact version of a dependency.
 */
class PackagePythonRequirementRepoTest(id: EntityID<CompositeID>): CompositeIdRepoTest<PackagePythonRequirementEntity>() {

    override fun mockEntity(): PackagePythonRequirementEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<CompositeID> {
        val id = mockk<EntityID<CompositeID>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<CompositeID> {
        val id = mockk<EntityID<CompositeID>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): PackagePythonRequirementEntity {
        return any<PackagePythonRequirementEntity>()
    }

    
}