package com.wini.owle.api.transforms

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.transforms.AppliedTransformArg
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class AppliedTransformEntityEntityArgApiTest : BaseLongPkApiTest<AppliedTransformArg, AppliedTransformArgApi>() {

    override val route: String = "/api/applied-transform-arg"

    override fun getDto(id: Long): AppliedTransformArg = DtoTestInstanceFactory.appliedTransformArgDto(
        id = id
    )

    override val serializer: KSerializer<AppliedTransformArg> = AppliedTransformArg.serializer()
    
    override val listSerializer: KSerializer<List<AppliedTransformArg>> = ListSerializer(AppliedTransformArg.serializer())

    override fun getApiInstance(
        dll: DLL<Long, AppliedTransformArg>,
        validator: BaseValidator<AppliedTransformArg>
    ): AppliedTransformArgApi = AppliedTransformArgApi(dll, validator)

}