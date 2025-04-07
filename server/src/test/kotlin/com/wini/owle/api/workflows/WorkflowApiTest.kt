package com.wini.owle.api.workflows

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dll.workflows.WorkflowDll
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.WorkflowDto
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass


class WorkflowApiTest: BaseLongPkApiTest<WorkflowDto, WorkflowApi>() {

    override val clazz: KClass<WorkflowDto> = WorkflowDto::class
    override val localSerializer: KSerializer<WorkflowDto> = WorkflowDto.serializer()
    override val routeBase: String = "/api/workflow"

    override fun createApiInstance(dll: DLL<Long, WorkflowDto>): WorkflowApi = WorkflowApi(dll = mockk())
    override fun generateSampleDto(id: Long?): WorkflowDto = DtoTestInstanceFactory.workflowDto()
    override fun MockKMatcherScope.anyDto(): WorkflowDto = any<WorkflowDto>()
    override fun generateSampleDtoList(count: Int): List<WorkflowDto> = listOf(
        DtoTestInstanceFactory.workflowDto(1L), DtoTestInstanceFactory.workflowDto(2L)
    )

}