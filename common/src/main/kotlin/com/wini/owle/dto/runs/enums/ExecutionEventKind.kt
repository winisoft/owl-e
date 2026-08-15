package com.wini.owle.dto.runs.enums

import kotlinx.serialization.Serializable

@Serializable
enum class ExecutionEventKind {

    RUN_STARTED,

    WORKFLOW_ENTERED,

    STEP_ENTERED,

    PARAM_RESOLVED,

    TOOL_INVOKED,

    TOOL_RETURNED,

    GUARDED_OK,

    ERROR_WATCH_FIRED,

    DATA_POINT_WRITTEN,

    INQUISITION_EVALUATED,

    ROUTE_TAKEN,

    JUNCTION_TAKEN,

    INJECTION_MIGRATED,

    WORKFLOW_ENDED,

    MULTIFLOW_COMPLETED,

    RUN_FAILED,

    LOG

}
