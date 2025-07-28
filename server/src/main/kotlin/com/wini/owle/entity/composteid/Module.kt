package com.wini.owle.entity.composteid

import com.wini.owle.dto.environs.ToolPyPackDependency
import com.wini.owle.dto.steps.ProvisionDataPoint
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.dto.user.tags.TagRelation
import org.koin.dsl.module

val compositeIdFactoryModule = module {

    single<CompositeIdFactory<ProvisionDataPoint>> { ProvisionDataPointIdFactory }
    single<CompositeIdFactory<TagRelation>> { TagRelationIdFactory }
    single<CompositeIdFactory<ToolPyPackDependency>> { ToolPackageDepIdFactory }
    single<CompositeIdFactory<TransformTargetType>> { TransformTargetTypeIdFactory }
    single<CompositeIdFactory<TypeUnionMember>> { TypeUnionMemberIdFactory }

    single<ProvisionDataPointIdFactory> { ProvisionDataPointIdFactory }
    single<TagRelationIdFactory> { TagRelationIdFactory }
    single<ToolPackageDepIdFactory> { ToolPackageDepIdFactory }
    single<TransformTargetTypeIdFactory> { TransformTargetTypeIdFactory }
    single<TypeUnionMemberIdFactory> { TypeUnionMemberIdFactory }

}