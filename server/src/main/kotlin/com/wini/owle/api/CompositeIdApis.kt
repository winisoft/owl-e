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
            routing.apply {
                get("/api/provision-datapoint") { getAll(context.request.call) }
                get("/api/provision-datapoint/{id}") { getById(context.request.call) }
                post("/api/provision-datapoint") { create(context.request.call) }
                post("/api/provision-datapoint/batch") { createBatch(context.request.call) }
                put("/api/provision-datapoint") { update(context.request.call) }
                put("/api/provision-datapoint/batch") { updateBatch(context.request.call) }
                delete("/api/provision-datapoint/{id}") { super.delete(context.request.call) }
                delete("/api/provision-datapoint/batch") { deleteBatch(context.request.call) }
            }
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
            routing.apply {
                get("/api/type-union-member") { getAll(context.request.call) }
                get("/api/type-union-member/{id}") { getById(context.request.call) }
                post("/api/type-union-member") { create(context.request.call) }
                post("/api/type-union-member/batch") { createBatch(context.request.call) }
                put("/api/type-union-member") { update(context.request.call) }
                put("/api/type-union-member/batch") { updateBatch(context.request.call) }
                delete("/api/type-union-member/{id}") { super.delete(context.request.call) }
                delete("/api/type-union-member/batch") { deleteBatch(context.request.call) }
            }
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
            routing.apply {
                get("/api/tag-relation") { getAll(context.request.call) }
                get("/api/tag-relation/{id}") { getById(context.request.call) }
                post("/api/tag-relation") { create(context.request.call) }
                post("/api/tag-relation/batch") { createBatch(context.request.call) }
                put("/api/tag-relation") { update(context.request.call) }
                put("/api/tag-relation/batch") { updateBatch(context.request.call) }
                delete("/api/tag-relation/{id}") { super.delete(context.request.call) }
                delete("/api/tag-relation/batch") { deleteBatch(context.request.call) }
            }
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
            routing.apply {
                get("/api/transform-target-type") { getAll(context.request.call) }
                get("/api/transform-target-type/{id}") { getById(context.request.call) }
                post("/api/transform-target-type") { create(context.request.call) }
                post("/api/transform-target-type/batch") { createBatch(context.request.call) }
                put("/api/transform-target-type") { update(context.request.call) }
                put("/api/transform-target-type/batch") { updateBatch(context.request.call) }
                delete("/api/transform-target-type/{id}") { super.delete(context.request.call) }
                delete("/api/transform-target-type/batch") { deleteBatch(context.request.call) }
            }
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
            routing.apply {
                get("/api/tool-package-dep") { getAll(context.request.call) }
                get("/api/tool-package-dep/{id}") { getById(context.request.call) }
                post("/api/tool-package-dep") { create(context.request.call) }
                post("/api/tool-package-dep/batch") { createBatch(context.request.call) }
                put("/api/tool-package-dep") { update(context.request.call) }
                put("/api/tool-package-dep/batch") { updateBatch(context.request.call) }
                delete("/api/tool-package-dep/{id}") { super.delete(context.request.call) }
                delete("/api/tool-package-dep/batch") { deleteBatch(context.request.call) }
            }
        }
    }
}

