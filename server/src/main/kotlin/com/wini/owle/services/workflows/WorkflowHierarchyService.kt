package com.wini.owle.services.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.model.workflows.WorkflowFolderNode


/**
 * Orchestrates everything you need around Workflow and WorkflowFolder.
 */
interface WorkflowHierarchyService {

    /**
     * Return the entire folder→workflow tree, up to [maxDepth].
     */
    suspend fun getHierarchy(maxDepth: Int = 5): AsyncResult<List<WorkflowFolderNode>>

    /**
     * Return only the Workflow DTOs inside the given folder.
     */
    suspend fun getByFolder(folderId: ULong?): AsyncResult<List<Workflow>>

}
