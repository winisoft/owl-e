package com.wini.owle.mapper.steps

import com.wini.owle.dto.DtoTestInstanceFactory.dataPointDto
import com.wini.owle.dto.DtoTestInstanceFactory.revisionDto
import com.wini.owle.dto.DtoTestInstanceFactory.stepDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDeclarationDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDefinitionDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowDto
import com.wini.owle.dto.RevisionDto
import com.wini.owle.entity.EntityTestInstanceFactory.dataPointDao
import com.wini.owle.entity.EntityTestInstanceFactory.revisionDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.StoreMethod.Companion.toDto
import com.wini.owle.entity.steps.RevisionDao
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance

import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals

//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//open class RevisionMapperTest: BaseOwleMapperTest<RevisionDto, RevisionDao, RevisionsMapper>() {
//
//    private val realMapper by KoinJavaComponent.inject<RevisionsMapper>(RevisionsMapper::class.java)
//
//    override fun instantiateMapper(): RevisionsMapper { return realMapper }
//
//    override fun Transaction.dto(id: Long): RevisionDto {
//        val dataPointDao = dataPointDao()
//        val stepDao = stepDao()
//        val workflowDao = workflowDao()
//        val typeDeclarationDao = typeDeclarationDao()
//        return revisionDto(
//            id = id,
//            dataPoint = dataPointDto(
//                id = dataPointDao.id.value,
//                name = dataPointDao.name,
//                description = dataPointDao.description,
//                presetValue = dataPointDao.presetValue,
//                storeMethod = dataPointDao.storeMethod.toDto(),
//                typeDeclaration = typeDeclarationDto(
//                    id = typeDeclarationDao.id.value,
//                    typeDefinition = typeDefinitionDto()
//                ),
//                workflow = workflowDto(
//                    id = workflowDao.id.value
//                )
//            ),
//            step = stepDto(
//                id = stepDao.id.value
//            )
//        )
//    }
//
//    override fun Transaction.existingDao(): RevisionDao {
//        return revisionDao()
//    }
//
//    override fun Transaction.existingDaoDto(): Pair<RevisionDao, RevisionDto> {
//        val dao = existingDao()
//        val dataPointDao = dataPointDao()
//        val stepDao = stepDao()
//        val workflowDao = workflowDao()
//        val typeDeclarationDao = typeDeclarationDao()
//        val dto = revisionDto(
//            id = dao.id.value,
//            dataPoint = dataPointDto(
//                id = dataPointDao.id.value,
//                name = dataPointDao.name,
//                description = dataPointDao.description,
//                presetValue = dataPointDao.presetValue,
//                storeMethod = dataPointDao.storeMethod.toDto(),
//                typeDeclaration = typeDeclarationDto(
//                    id = typeDeclarationDao.id.value,
//                    typeDefinition = typeDefinitionDto()
//                ),
//                workflow = workflowDto(
//                    id = workflowDao.id.value
//                )
//            ),
//            step = stepDto(
//                id = stepDao.id.value
//            )
//        )
//        return dao to dto
//    }
//
//    override fun assertDaoPropsExpectedMatch(dao: RevisionDao, dto: RevisionDto) {
//        assertEquals(dao.dataPoint.id.value, dto.dataPoint.id)
//        assertEquals(dao.step.id.value, dto.step.id)
//    }
//
//    override fun assertDtoPropsExpectedMatch(dto: RevisionDto, dao: RevisionDao) {
//        assertEquals(dto.dataPoint.id, dao.dataPoint.id.value)
//        assertEquals(dto.step.id, dao.step.id.value)
//    }
//
//}