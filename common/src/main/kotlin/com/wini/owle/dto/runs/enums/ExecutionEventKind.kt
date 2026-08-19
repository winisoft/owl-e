package com.wini.owle.dto.runs.enums

import com.wini.owle.dto.DbToken
import kotlinx.serialization.Serializable

@Serializable
enum class ExecutionEventKind(override val dbValue: String) : DbToken {

    RUN_STARTED("run_started"),

    WORKFLOW_ENTERED("workflow_entered"),

    STEP_ENTERED("step_entered"),

    PARAM_RESOLVED("param_resolved"),

    TOOL_INVOKED("tool_invoked"),

    TOOL_RETURNED("tool_returned"),

    GUARDED_OK("guarded_ok"),

    ERROR_WATCH_FIRED("error_watch_fired"),

    DATA_POINT_WRITTEN("data_point_written"),

    INQUISITION_EVALUATED("inquisition_evaluated"),

    ROUTE_TAKEN("route_taken"),

    JUNCTION_TAKEN("junction_taken"),

    INJECTION_MIGRATED("injection_migrated"),

    WORKFLOW_ENDED("workflow_ended"),

    MULTIFLOW_COMPLETED("multiflow_completed"),

    RUN_FAILED("run_failed"),

    LOG("log")

}
