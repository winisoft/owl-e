package com.wini.owle.dto

import com.wini.owle.dto.workflows.WorkflowRequirement


data class ExecutionBlueprint(



    private val headWorkflowId: ULong,

    /**
     * The id of a record in the python_versions table, which points to the `id` column of some record in the
     * `versions` table, for which we know there is in fact a released python version that Owl-E supports
     */
    private val pythonVersionId: ULong,

    /**
     *
     */
    private val headWorkflowName: String,

    /**
     *
     */
    private val headWorkflowDesc: String,

    /**
     * The dependenci
     */
    private val headWorkflowPackageRequirements: List<WorkflowRequirement>


)
