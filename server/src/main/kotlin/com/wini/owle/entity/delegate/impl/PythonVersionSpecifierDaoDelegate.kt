package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.environs.version.PythonVersionSpecifier
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.environs.version.PythonVersionSpecifierEntity
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


object PythonVersionSpecifierDaoDelegate: CompositeIdDaoDelegate<
        PythonVersionSpecifier,
        PythonVersionSpecifierEntity,
        >(PythonVersionSpecifierEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: PythonVersionSpecifier): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}