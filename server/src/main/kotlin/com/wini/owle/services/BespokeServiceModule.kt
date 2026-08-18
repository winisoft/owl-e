package com.wini.owle.services

import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.mapper.generated.TransformMapper
import com.wini.owle.mapper.generated.WorkflowFolderMapper
import com.wini.owle.mapper.generated.WorkflowMapper
import com.wini.owle.repo.GenericRepoImpl
import com.wini.owle.repo.OwleRepo
import com.wini.owle.services.transform.TransformService
import com.wini.owle.services.transform.TransformServiceImpl
import com.wini.owle.services.workflows.WorkflowHierarchyService
import com.wini.owle.services.workflows.impl.WorkflowHierarchyServiceImpl
import org.koin.dsl.module


/**
 * Services that do something other than the generic CRUD, and so cannot be generated.
 *
 * `generatedServiceModule` binds one [GenericServiceImpl] per mapped type. Anything with its own
 * behaviour has to be registered by hand, and until now nothing registered these two: both were
 * implemented, neither was ever resolvable. `WorkflowHierarchyService` failed at every call site
 * with "No definition found", which is what the integration tests were reporting.
 *
 * Every dependency below is already bound by the generated repo and mapper modules.
 */
val bespokeServicesModule = module {

    single<WorkflowHierarchyService> {
        WorkflowHierarchyServiceImpl(
            folderRepo = get<OwleRepo<ULong, WorkflowFolderEntity>>(),
            workflowRepo = get<OwleRepo<ULong, WorkflowEntity>>(),
            folderMapper = get<WorkflowFolderMapper>(),
            workflowMapper = get<WorkflowMapper>()
        )
    }

    single<TransformService> {
        TransformServiceImpl(
            transformRepo = get<GenericRepoImpl<ULong, TransformEntity>>(),
            transformMapper = get<TransformMapper>()
        )
    }
}
