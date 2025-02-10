package com.wini.owle.air.domain.repo

import com.wini.owle.air.domain.model.item.LoItem

sealed class LoError {

    abstract val message: String?
    abstract val throwable: Throwable?

    object ExpectedDbValueNotFound: LoError() {
        override val message: String?
            get() = null
        override val throwable: Throwable?
            get() = null
    }

    object OpenApiSpecLoadFailed: LoError() {
        override val message: String
            get() = "Failed to load the html document to display the OpenAPI spec"
        override val throwable: Throwable?
            get() = null
    }

    object ServerStartFailed: LoError() {
        override val message: String
            get() = "Failed to start the LackOffice server before timing out. Please file a report."
        override val throwable: Throwable?
            get() = null
    }

    object ServerStateLoRootIsEnabledSyncError: LoError() {
        override val message: String
            get() = "The behavior that controls the app server state and the db LoRootItem have come out of sync"
        override val throwable: Throwable?
            get() = null
    }

    object ServerStopFailed: LoError() {
        override val message: String
            get() = "Failed to stop the LackOffice server before timing out. Please kill the process."
        override val throwable: Throwable?
            get() = null
    }

    data class SaveFailed(val item: LoItem): LoError() {
        override val message: String
            get() = "Failed to save ${item.typeName} with id ${item.loid}"
        override val throwable: Throwable?
            get() = null
    }

    data class ViewDetailFailed(val item: LoItem): LoError() {
        override val message: String
            get() = "Failed to display details of ${item.typeName} with id ${item.loid}"
        override val throwable: Throwable?
            get() = null
    }

    data class LoApiMisuse(
        override val message: String?
    ): LoError() {
        override val throwable: Throwable? get() = null
    }

    data class GeneratorTimeout(
        override val message: String = "The Java process spawned for OpenAPI generation failed during execution",
        override val throwable: Throwable = RuntimeException().fillInStackTrace()
    ): LoError()

    data class GeneratorProcessFailed(
        override val message: String,
        override val throwable: Throwable = RuntimeException().fillInStackTrace()
    ): LoError()

    data class SqlException(
        override val message: String? = """
            A SqlException occurred, which can only happen in a database operation, i.e., one of the *Repository
            classes in the database module attempted an operation in the SqlDelight database but failed. Most likely,
             the database itself and the code-generated adapter for it fell out of parity during development.
        """.trimIndent(),
        override val throwable: Throwable?
    ): LoError()

    companion object {

        fun fromThrowable(throwable: Throwable?): LoError {
            return ExpectedDbValueNotFound
        }
    }
}