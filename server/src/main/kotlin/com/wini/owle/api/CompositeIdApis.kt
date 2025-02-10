package com.wini.owle.api

import com.wini.owle.dll.environs.ToolPackageDependencyDll
import com.wini.owle.dll.steps.ProvisionDataPointDll
import com.wini.owle.dll.transforms.TransformTargetTypeDll
import com.wini.owle.dll.typing.TypeUnionMemberDll
import com.wini.owle.dll.user.TagRelationDll
import com.wini.owle.dto.*
import com.wini.owle.mapper.CompositeIdMap
import io.ktor.server.routing.*


class ProvisionDataPointApi(
    provisionDataPointDll: ProvisionDataPointDll,
    compositeIdMap: CompositeIdMap<ProvisionDataPointDto>
) : GenericCompositeIdApi<ProvisionDataPointDto>(
    dll = provisionDataPointDll,
    clazz = ProvisionDataPointDto::class,
    serializer = ProvisionDataPointDto.serializer(),
    compositeIdMapper = compositeIdMap
) {
    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }
}

class TypeUnionMemberApi(
    typeUnionMemberDll: TypeUnionMemberDll,
    compositeIdMap: CompositeIdMap<TypeUnionMemberDto>
) : GenericCompositeIdApi<TypeUnionMemberDto>(
    dll = typeUnionMemberDll,
    clazz = TypeUnionMemberDto::class,
    serializer = TypeUnionMemberDto.serializer(),
    compositeIdMapper = compositeIdMap
) {
    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }
}

class TagRelationApi(
    tagRelationDll: TagRelationDll,
    compositeIdMap: CompositeIdMap<TagRelationDto>
) : GenericCompositeIdApi<TagRelationDto>(
    dll = tagRelationDll,
    clazz = TagRelationDto::class,
    serializer = TagRelationDto.serializer(),
    compositeIdMapper = compositeIdMap
) {
    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }
}

class TransformTargetTypeApi(
    transformTargetTypeDll: TransformTargetTypeDll,
    compositeIdMap: CompositeIdMap<TransformationTargetTypeDto>
) : GenericCompositeIdApi<TransformationTargetTypeDto>(
    dll = transformTargetTypeDll,
    clazz = TransformationTargetTypeDto::class,
    serializer = TransformationTargetTypeDto.serializer(),
    compositeIdMapper = compositeIdMap
) {
    override fun register(routing: Routing) {
        routing.apply {

        }
    }
}

class ToolPackageDependencyApi(
    toolPackageDependencyDll: ToolPackageDependencyDll,
    compositeIdMap: CompositeIdMap<ToolPackageDependencyDto>
) : GenericCompositeIdApi<ToolPackageDependencyDto>(
    dll = toolPackageDependencyDll,
    clazz = ToolPackageDependencyDto::class,
    serializer = ToolPackageDependencyDto.serializer(),
    compositeIdMapper = compositeIdMap
) {
    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }
}

