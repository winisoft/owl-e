package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.environs.pkg.PackagePythonRequirement
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.environs.PackagePythonRequirements
import com.wini.owle.entity.environs.pkg.PackagePythonRequirementEntity
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
            it[PackagePythonRequirements.packageVersion] = dto.version.id
        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(
        "distribution" to PackagePythonRequirements.distribution,
        "packageVersion" to PackagePythonRequirements.packageVersion,
        "pythonVersion" to PackagePythonRequirements.pythonVersion
    )

}