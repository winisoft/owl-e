package com.wini.owle.services.workflows.impl

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.workflows.WorkflowFolder

import com.wini.owle.model.workflows.WorkflowFolderNode
import com.wini.owle.repo.ExposedTransactionExecutor
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.TransactionExecutor
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
    private val workflowMapper: DbMapper<ULong, Workflow, WorkflowEntity>,
    private val transactions: TransactionExecutor = ExposedTransactionExecutor
) : WorkflowHierarchyService {

    // Every method here reads entity properties after querying, and the repository closes its
    // own transaction before returning. Mapping outside that scope throws "Can't init value
    // outside the transaction" the moment a result is non-empty, so query and mapping have to
    // share one transaction. Exposed joins a nested `transaction` to the enclosing one, so the
    // repository's internal scope simply nests inside this.

    override suspend fun getHierarchy(maxDepth: Int): AsyncResult<List<WorkflowFolderNode>> =
        runCatching {
            withContext(Dispatchers.IO) {
                transactions.run { buildRecursive(parentId = null, 0, maxDepth) }
            }
        }.fold(
            onSuccess = { AsyncResult.Success(it) },
            onFailure = {
                GenericError("${it::class.simpleName}: ${it.message}").asFailure()
            }
        )

    override suspend fun getByFolder(folderId: ULong?): AsyncResult<List<Workflow>> =
        runCatching {
            withContext(Dispatchers.IO) {
                transactions.run {
                    workflowRepo
                        .getWhere { it.parentFolder?.id?.value == folderId }
                        .map(workflowMapper::toDto)
                }
            }
        }.fold(
            onSuccess  = { AsyncResult.Success(it) },
            onFailure  = {
                GenericError("${it::class.simpleName}: ${it.message}").asFailure()
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