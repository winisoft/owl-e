package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.DataPointSave
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class RevisionApiTest : BaseLongPkApiTest<DataPointSave, RevisionApi>() {

    override val route: String = "/api/revision"

    override fun getDto(id: Long): DataPointSave = DtoTestInstanceFactory.revisionDto(
        id = id
    )

    override val serializer: KSerializer<DataPointSave> = DataPointSave.serializer()
    
    override val listSerializer: KSerializer<List<DataPointSave>> = ListSerializer(DataPointSave.serializer())

    override fun getApiInstance(
        dll: DLL<Long, DataPointSave>,
        validator: BaseValidator<DataPointSave>
    ): RevisionApi = RevisionApi(dll, validator)

}