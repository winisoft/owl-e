package com.wini.owle.mapper.transforms

import com.wini.owle.entity.DbLayerIntegrationTests.ImplicatedTables
import com.wini.owle.dto.AppliedTransformArgDto
import com.wini.owle.dto.DtoTestInstanceFactory.appliedTransformArgDto
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.appliedTransformArgDao
import com.wini.owle.entity.transforms.AppliedTransformArgDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.mapperModule
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class AppliedTransformArgMapperTest: BaseOwleMapperTest<AppliedTransformArgDto, AppliedTransformArgDao, AppliedTransformArgsMapper>() {

    private val realMapper by KoinJavaComponent.inject<AppliedTransformArgsMapper>(AppliedTransformArgsMapper::class.java)

        override val implicatedTables: List<IdTable<*>> = ImplicatedTables.TransformsModule.AppliedTransformArg

    override fun instantiateMapper(): AppliedTransformArgsMapper { return realMapper }

    override fun Transaction.dto(id: Long): AppliedTransformArgDto { return appliedTransformArgDto(id = id) }

    override fun Transaction.existingDao(): AppliedTransformArgDao { return appliedTransformArgDao() }

    override fun Transaction.existingDaoDto(): Pair<AppliedTransformArgDao, AppliedTransformArgDto> {
        val dao = existingDao()
        val dto = appliedTransformArgDto(id = dao.id.value)
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: AppliedTransformArgDao, dto: AppliedTransformArgDto) {
        assertEquals(dao.appliedTransform.id.value, dto.appliedTransform.id)
        assertEquals(dao.transformationArg.id.value, dto.transformationArg.id)
        assertEquals(dao.value, dto.value)
    }

    override fun assertDtoPropsExpectedMatch(dto: AppliedTransformArgDto, dao: AppliedTransformArgDao) {
        assertEquals(dto.appliedTransform.id, dao.appliedTransform.id.value)
        assertEquals(dto.transformationArg.id, dao.transformationArg.id.value)
        assertEquals(dto.value, dao.value)
    }

}