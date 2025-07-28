package com.wini.owle.api.transforms

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.TransformArg
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class TransformationArgApiTest : BaseLongPkApiTest<TransformArg, TransformationArgApi>() {

    override val route: String = "/api/transformation-arg"

    override fun getDto(id: Long): TransformArg = DtoTestInstanceFactory.transformationArgDto(
        id = id
    )

    override val serializer: KSerializer<TransformArg> = TransformArg.serializer()
    
    override val listSerializer: KSerializer<List<TransformArg>> = ListSerializer(TransformArg.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TransformArg>,
        validator: DtoValidator<TransformArg>
    ): TransformationArgApi = TransformationArgApi(dll, validator)

}