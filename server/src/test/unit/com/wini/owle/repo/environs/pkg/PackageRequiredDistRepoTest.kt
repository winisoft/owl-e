package com.wini.owle.repo.environs.pkg

import com.wini.owle.entity.environs.pkg.PackageDistRequirementEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test



open class PackageRequiredDistRepoTest : ULongIdGenericRepoTest<PackageDistRequirementEntity>() {

    override fun mockEntity(): PackageDistRequirementEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): PackageDistRequirementEntity {
        return any<PackageDistRequirementEntity>()
    }

    

}

