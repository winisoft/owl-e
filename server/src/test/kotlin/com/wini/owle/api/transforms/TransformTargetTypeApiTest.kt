package com.wini.owle.api.transforms

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import kotlinx.serialization.KSerializer


class TransformTargetTypeApiTest : BaseCompositePkApiTest<Long, TransformationTargetTypeDto, TransformTargetTypeApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): TransformationTargetTypeDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<TransformationTargetTypeDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TransformationTargetTypeDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TransformationTargetTypeDto>): TransformTargetTypeApi {
        TODO("Not yet implemented")
    }

}