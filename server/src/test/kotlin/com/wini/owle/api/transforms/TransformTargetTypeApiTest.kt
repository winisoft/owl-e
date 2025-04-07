// server/src/test/kotlin/com/wini/owle/api/transforms/TransformTargetTypeApiTest.kt
package com.wini.owle.api.transforms

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID


open class TransformTargetTypeApiTest : BaseCompositePkApiTest<TransformationTargetTypeDto, TransformTargetTypeApi>() {

    override val route: String = "/api/transform-target-type"

    override fun getDto(): TransformationTargetTypeDto = DtoTestInstanceFactory.transformationTargetTypeDto()

    override val serializer: KSerializer<TransformationTargetTypeDto> = TransformationTargetTypeDto.serializer()
    
    override val listSerializer: KSerializer<List<TransformationTargetTypeDto>> = ListSerializer(TransformationTargetTypeDto.serializer())

    override fun getApiInstance(dll: DLL<CompositeID, TransformationTargetTypeDto>): TransformTargetTypeApi = TransformTargetTypeApi(dll)
}