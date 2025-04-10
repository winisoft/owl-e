package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.RevisionDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class RevisionApiTest : BaseLongPkApiTest<RevisionDto, RevisionApi>() {

    override val route: String = "/api/revision"

    override fun getDto(id: Long): RevisionDto = DtoTestInstanceFactory.revisionDto(
        id = id
    )

    override val serializer: KSerializer<RevisionDto> = RevisionDto.serializer()
    
    override val listSerializer: KSerializer<List<RevisionDto>> = ListSerializer(RevisionDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, RevisionDto>,
        validator: DtoValidator<RevisionDto>
    ): RevisionApi = RevisionApi(dll, validator)

}