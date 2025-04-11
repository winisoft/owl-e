package com.wini.owle.entity.composteid

import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.dto.user.TagRelationDto
import org.koin.dsl.module
import com.wini.owle.entity.composteid.TypeUnionMemberIdFactory

val compositeIdFactoryModule = module {

    single<CompositeIdFactory<ProvisionDataPointDto>> { ProvisionDataPointIdFactory }
    single<CompositeIdFactory<TagRelationDto>> { TagRelationIdFactory }
    single<CompositeIdFactory<ToolPackageDependencyDto>> { ToolPackageDepIdFactory }
    single<CompositeIdFactory<TransformationTargetTypeDto>> { TransformTargetTypeIdFactory }
    single<CompositeIdFactory<TypeUnionMemberDto>> { TypeUnionMemberIdFactory }

    single<ProvisionDataPointIdFactory> { ProvisionDataPointIdFactory }
    single<TagRelationIdFactory> { TagRelationIdFactory }
    single<ToolPackageDepIdFactory> { ToolPackageDepIdFactory }
    single<TransformTargetTypeIdFactory> { TransformTargetTypeIdFactory }
    single<TypeUnionMemberIdFactory> { TypeUnionMemberIdFactory }

}