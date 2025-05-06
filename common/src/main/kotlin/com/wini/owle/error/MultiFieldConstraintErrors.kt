package com.wini.owle.error

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.mapper.DateTimeConverter
import kotlinx.serialization.Serializable
import kotlin.reflect.KProperty1


sealed class MultiFieldConstraintError: OwleError, Constraint

/** Fails if both workflow and step are null. */
@Serializable
data class TestCaseSubject<T : BaseDto>(
    val workflow: KProperty1<T, Workflow?>,
    val step: KProperty1<T, Step?>,
    val receiver: T
) : MultiFieldConstraintError() {

    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE

    private val workflowValue: Workflow? = workflow.get(receiver)
    private val stepValue: Step? = step.get(receiver)

    override val isInvalid: Boolean get() = workflowValue == null && stepValue == null
    override val detail: String get() = if (workflowValue == null) "workflow was null." else "step was null"

    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_TEST_CASE_FOCUS"
        const val MESSAGE: String = "TestCase must specify either a workflow or step subject."
    }

}

/** Fails if all version constraint values are null. */
@Serializable
data class VersionSpecUndefined<T : BaseDto>(
    val versionMinField: KProperty1<T, String?>,
    val versionMaxField: KProperty1<T, String?>,
    val versionExactField: KProperty1<T, String?>,
    val receiver: T
) : MultiFieldConstraintError() {

    private val versionMinValue: String? get() = versionMinField.get(receiver)
    private val versionMaxValue: String? get() = versionMinField.get(receiver)
    private val versionExactValue: String? get() = versionMinField.get(receiver)
    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE
    override val detail: String get() = receiver.idText
    override val isInvalid: Boolean get() = versionMinValue == null && versionMaxValue == null && versionExactValue == null

    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_VERSION_SPEC_UNDEFINED"
        const val MESSAGE: String = "At least one version constraint must be specified."
    }
}

/** Fails if `completedAt` exists and is before `startedAt`. */
@Serializable
data class PreventsTimeParadoxes<T : BaseDto>(
    val happensFirstField: KProperty1<T, String>,
    val happensSecondField: KProperty1<T, String?>,
    val receiver: T
) : MultiFieldConstraintError() {
    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${happensFirstField.name}=$beforeTimestamp,::${happensSecondField.name}=${afterTimestamp}"

    private val beforeTimestamp: String get() = happensFirstField.get(receiver)
    private val afterTimestamp: String? get() = happensSecondField.get(receiver)

    override val isInvalid: Boolean
        get() = afterTimestamp?.let {
            DateTimeConverter.fromString(it) < DateTimeConverter.fromString(beforeTimestamp)
        } ?: false

    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_DT2B4DT1"
        const val MESSAGE: String = "The relative timestamps of events that must occur before and after one another are incoherent."
    }
}

@Serializable
data class InconsistentOAuthId<T : BaseDto>(
    val providerField: KProperty1<T, String?>,
    val idField: KProperty1<T, String?>,
    val receiver: T,
) : MultiFieldConstraintError() {

    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE

    override val detail: String get() = "${receiver.idText}::${providerField.name}=$provider, ${idField.name}=$id"
    override val isInvalid: Boolean
        get() = (provider == null && id == null) || (id != null && provider == null)

    private val id = idField.get(receiver)
    private val provider = providerField.get(receiver)

    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_OAUTH_ID_COMBINATION"
        const val MESSAGE: String = "OAuth ID and provider must be set together."
    }

}
