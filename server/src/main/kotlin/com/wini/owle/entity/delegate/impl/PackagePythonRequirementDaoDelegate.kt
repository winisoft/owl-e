package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.environs.pkg.PackagePythonRequirement
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.environs.pkg.PackagePythonRequirementEntity
import com.wini.owle.entity.environs.pkg.PackagePythonRequirements
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.Column

object PackagePythonRequirementDaoDelegate: CompositeIdDaoDelegate<
        PackagePythonRequirement,
        PackagePythonRequirementEntity
        >(PackagePythonRequirementEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: PackagePythonRequirement): CompositeID {
        return CompositeID {
            it[PackagePythonRequirements.distribution] = dto.distribution.name
            it[PackagePythonRequirements.version] = dto.version.id
        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(
        "version" to  PackagePythonRequirements.version,
        "specifier" to PackagePythonRequirements.version
    )

}