package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.environs.pkg.PackageExtra
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.environs.pkg.PackageExtraEntity
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


object PackageExtraDaoDelegate: CompositeIdDaoDelegate<
        PackageExtra,
        PackageExtraEntity,
        >(PackageExtraEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: PackageExtra): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}