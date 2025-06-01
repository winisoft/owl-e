package com.wini.owle.services.workflows.impl

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.workflows.WorkflowFolder

import com.wini.owle.model.workflows.WorkflowFolderNode
import com.wini.owle.repo.OwleRepo
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.error.GenericError
import com.wini.owle.mapper.DbMapper
import com.wini.owle.services.workflows.WorkflowHierarchyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WorkflowHierarchyServiceImpl(
    private val folderRepo: OwleRepo<ULong, WorkflowFolderEntity>,
    private val workflowRepo: OwleRepo<ULong, WorkflowEntity>,
    private val folderMapper: DbMapper<ULong, WorkflowFolder, WorkflowFolderEntity>,
    private val workflowMapper: DbMapper<ULong, Workflow, WorkflowEntity>
) : WorkflowHierarchyService {

    override suspend fun getHierarchy(maxDepth: Int): AsyncResult<List<WorkflowFolderNode>> =
        runCatching {
            withContext(Dispatchers.IO) {
                buildRecursive(parentId = null, 0, maxDepth)
            }
        }.fold(
            onSuccess = { AsyncResult.Success(it) },
            onFailure = {
                GenericError("").asFailure()
            }
        )

    override suspend fun getByFolder(folderId: ULong?): AsyncResult<List<Workflow>> =
        runCatching {
            withContext(Dispatchers.IO) {
                workflowRepo
                    .getWhere { it.parentFolder?.id?.value == folderId }
                    .map(workflowMapper::toDto)
            }
        }.fold(
            onSuccess  = { AsyncResult.Success(it) },
            onFailure  = {
                GenericError("").asFailure()
            }
        )

    private fun buildRecursive(
        parentId: ULong?,
        depth: Int,
        max: Int
    ): List<WorkflowFolderNode> {
        if (depth >= max) return emptyList()

        return folderRepo
            .getWhere { it.parentFolder?.id?.value == parentId }
            .map { folderEntity ->
                val dto      = folderMapper.toDto(folderEntity)
                val children = buildRecursive(folderEntity.id.value, depth + 1, max)
                val flows    = workflowRepo
                    .getWhere { it.parentFolder?.id?.value == folderEntity.id.value }
                    .map(workflowMapper::toDto)

                WorkflowFolderNode(dto, children, flows)
            }
    }
}