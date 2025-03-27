package com.wini.owle.mapper.transforms

import com.wini.owle.dto.AppliedTransformDto
import com.wini.owle.dto.DtoTestInstanceFactory.appliedTransformDto
import com.wini.owle.entity.EntityTestInstanceFactory.appliedTransformDao
import com.wini.owle.entity.TransformOccurrence.Companion.toDto
import com.wini.owle.entity.transforms.AppliedTransformDao
import com.wini.owle.mapper.BaseOwleMapperTest
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance

import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class AppliedTransformMapperTest: BaseOwleMapperTest<AppliedTransformDto, AppliedTransformDao, AppliedTransformsMapper>() {

    private val realMapper by KoinJavaComponent.inject<AppliedTransformsMapper>(AppliedTransformsMapper::class.java)

    override fun instantiateMapper(): AppliedTransformsMapper { return realMapper }

    override fun Transaction.dto(id: Long): AppliedTransformDto { return appliedTransformDto(id = id) }

    override fun Transaction.existingDao(): AppliedTransformDao { return appliedTransformDao() }

    override fun Transaction.existingDaoDto(): Pair<AppliedTransformDao, AppliedTransformDto> {
        val dao = existingDao()
        val dto = appliedTransformDto(id = dao.id.value)
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: AppliedTransformDao, dto: AppliedTransformDto) {
        assertEquals(dao.transformation.id.value, dto.transformation.id)
        assertEquals(dao.occurrence.toDto(), dto.occurrence)
        assertEquals(dao.resultType.id.value, dto.resultType.id)
        assertEquals(dao.dataPoint?.id?.value, dto.dataPoint?.id)
    }

    override fun assertDtoPropsExpectedMatch(dto: AppliedTransformDto, dao: AppliedTransformDao) {
        assertEquals(dto.transformation.id, dao.transformation.id.value, )
        assertEquals(dto.occurrence, dao.occurrence.toDto())
        assertEquals(dto.resultType.id, dao.resultType.id.value)
        assertEquals(dto.dataPoint?.id, dao.dataPoint?.id?.value)
    }

}