Errors
---
> Every failure Owl-E reports carries a stable code, a message stating the rule in general terms, and a detail naming the specific value that broke it. Codes are the contract: they are safe to branch on and will not change wording underneath you.

48 errors in 3 families.

## Request handling

> Raised before a request reaches the domain at all — the body, the parameters, or the caller's identity was not usable.

- ### `AUTHENTICATION_ERROR`
	The request could not be authenticated.
	- type: `AuthenticationError`

- ### `AUTHORIZATION_ERROR`
	The authenticated caller is not permitted to perform this operation.
	- type: `AuthorizationError`

- ### `BODY_EMPTY_LIST`
	Request body contained no items.
	- type: `BatchOpOfNoDataObjects`

- ### `MALFORMED_PARAM_VALUE`
	A parameter was provided in an invalid format.
	- type: `ParamValueMalformedLong`

- ### `MALFORMED_REQUEST_BODY`
	Request body is missing or not readable as JSON.
	- type: `RequestBodyMalformed`

- ### `MISSING_PARAM_VALUE`
	A required parameter was not provided.
	- type: `MissingParameter`

- ### `MULTI_PARAM_FAILURE`
	The required values were not provided in a valid format.
	- type: `MultipleParamFailure`

- ### `NO_SUCH_ID`
	No record exists with the requested id.
	- type: `NoSuchIdError`
	- notes: An error whose origin is in the repository layer, which presumably has faithfully informed that the id that had been referenced for selection or deletion did not exist. This indicates a disconnect between clients interacting with the database and the application's efficacy representing the concurrent operations globally

- ### `TYPE_INVALID`
	One or more of the constraints on valid type definitions was violated.
	- type: `MultipleTypeValidations`

## Field constraints

> Raised by validation against a DTO. Each names the rule that was broken; the offending value and the field it came from arrive in the error's detail.

- ### `TYPE_FAIL_PROP_INT32_MIN0`
	Value must be greater than or equal to zero.
	- type: `PositiveValueIntError`
	- notes: Fails if the value is negative.
	- raised by: `TypeParameter.position`

- ### `TYPE_FAIL_PROP_STR_LEN0`
	Value must not be an empty string.
	- type: `StringNotEmptyError`
	- notes: Fails if the value is the empty string. Null passes: on a nullable field null means "not set", which is a different and legitimate state from "set to nothing".
	- raised by: `CallableParameter.paramName`, `DataPoint.description`, `DataPoint.name`, `DataPoint.presetValue`, `DataPointInjection.description`, `DataPointInjection.name`, `Distribution.name`, `DistributionPackage.versionString`, `EnvMarkerComparison.value`, `ErrorWatch.errorType`, `ExecutionNotification.message`, `Grouper.description`, `Grouper.name`, `PackageExtra.name`, `Step.description`, `Step.name`, `Tag.description`, `Tag.name`, `TestCase.description`, `TestCase.name`, `Tool.body`, `Tool.description`, `Tool.name`, `ToolParam.name`, `Transform.examples`, `Transform.name`, `TransformArg.description`, `TransformArg.name`, `TypeNode.name`, `TypeSchemaRef.description`, `TypeSchemaRef.name`, `VersionOverflow.stringValue`, `Workflow.description`, `Workflow.name`, `WorkflowFolder.description`, `WorkflowFolder.name`, `WorkflowMetadata.copyrightInformation`, `WorkflowMetadata.name`

- ### `TYPE_FAIL_PROP_STR_LEN_LIMIT`
	Value must be no longer than the maximum permitted number of characters.
	- type: `StringMaxLengthError`
	- notes: Fails if the result of the function exceeds the maximum allowed string length.
	- raised by: `CallableParameter.defaultValueText`, `CallableParameter.paramName`, `CallableSignature.docstring`, `DataPoint.description`, `DataPoint.name`, `DataPoint.presetValue`, `DataPointInjection.description`, `DataPointInjection.name`, `Distribution.name`, `DistributionPackage.descriptionContentType`, `DistributionPackage.docsUrl`, `DistributionPackage.downloadUrl`, `DistributionPackage.homePage`, `DistributionPackage.license`, `DistributionPackage.projectUrl`, `DistributionPackage.summary`, `DistributionPackage.versionString`, `ErrorWatch.errorType`, `ExecutionNotification.message`, `Grouper.description`, `Grouper.name`, `MultiflowWorkflow.color`, `PackageExtra.name`, `PaymentMethod.stripePaymentMethodId`, `Step.color`, `Step.description`, `Step.name`, `Tag.description`, `Tag.name`, `TestCase.description`, `TestCase.name`, `TestExpectation.expectedValue`, `TestInput.value`, `Tool.body`, `Tool.description`, `Tool.name`, `ToolParam.name`, `Transform.description`, `Transform.examples`, `Transform.name`, `TransformArg.description`, `TransformArg.name`, `TransformOccurrenceArg.value`, `TypeNode.alias`, `TypeNode.name`, `TypeNode.namespace`, `TypeSchemaRef.description`, `TypeSchemaRef.name`, `User.apiKey`, `User.email`, `User.oauthId`, `User.password`, `User.resetToken`, `Version.local`, `VersionOverflow.stringValue`, `Workflow.description`, `Workflow.name`, `WorkflowFolder.description`, `WorkflowFolder.name`, `WorkflowMetadata.name`, `WorkflowRequirement.requiredExtraName`

- ### `TYPE_FAIL_PROP_STR_RCHR`
	Value must not contain restricted characters such as those that are invisible, prone to spoofing, or unsafe to display.
	- type: `StringContainsRestrictedChars`
	- raised by: `CallableParameter.defaultValueText`, `CallableParameter.paramName`, `CallableSignature.docstring`, `DataPoint.description`, `DataPoint.name`, `DataPoint.presetValue`, `DataPointInjection.description`, `DataPointInjection.name`, `Distribution.name`, `DistributionPackage.description`, `DistributionPackage.descriptionContentType`, `DistributionPackage.docsUrl`, `DistributionPackage.downloadUrl`, `DistributionPackage.homePage`, `DistributionPackage.keywords`, `DistributionPackage.license`, `DistributionPackage.projectUrl`, `DistributionPackage.summary`, `DistributionPackage.versionString`, `EnvMarkerComparison.value`, `ErrorWatch.errorType`, `ExecutionNotification.message`, `Grouper.description`, `Grouper.name`, `PackageExtra.name`, `PaymentMethod.stripePaymentMethodId`, `Step.description`, `Step.name`, `Tag.description`, `Tag.name`, `TestCase.description`, `TestCase.name`, `TestExpectation.expectedValue`, `TestInput.value`, `Tool.body`, `Tool.description`, `Tool.name`, `ToolParam.name`, `Transform.description`, `Transform.examples`, `Transform.name`, `TransformArg.description`, `TransformArg.name`, `TransformOccurrenceArg.value`, `TypeNode.alias`, `TypeNode.name`, `TypeNode.namespace`, `TypeSchemaRef.description`, `TypeSchemaRef.name`, `User.email`, `Version.local`, `VersionOverflow.stringValue`, `Workflow.description`, `Workflow.name`, `WorkflowFolder.description`, `WorkflowFolder.name`, `WorkflowMetadata.copyrightInformation`, `WorkflowMetadata.name`, `WorkflowRequirement.requiredExtraName`

- ### `TYPE_FAIL_PROP_STR_WSP`
	Value must not be blank, containing only whitespace.
	- type: `StringNotBlankError`
	- notes: Fails if the string is blank (contains only whitespace).
	- raised by: `CallableParameter.paramName`, `DataPoint.description`, `DataPoint.name`, `DataPoint.presetValue`, `DataPointInjection.description`, `DataPointInjection.name`, `Distribution.name`, `DistributionPackage.versionString`, `EnvMarkerComparison.value`, `ErrorWatch.errorType`, `ExecutionNotification.message`, `Grouper.description`, `Grouper.name`, `PackageExtra.name`, `PaymentMethod.lastFour`, `PaymentMethod.stripePaymentMethodId`, `Step.description`, `Step.name`, `Tag.description`, `Tag.name`, `TestCase.description`, `TestCase.name`, `Tool.body`, `Tool.description`, `Tool.name`, `ToolParam.name`, `Transform.description`, `Transform.examples`, `Transform.name`, `TransformArg.description`, `TransformArg.name`, `TransformOccurrenceArg.value`, `TypeNode.name`, `TypeSchemaRef.description`, `TypeSchemaRef.name`, `VersionOverflow.stringValue`, `Workflow.description`, `Workflow.name`, `WorkflowFolder.description`, `WorkflowFolder.name`, `WorkflowMetadata.copyrightInformation`, `WorkflowMetadata.name`

- ### `TYPE_FAIL_PROP_UINT32_MIN0`
	Value must be greater than or equal to zero.
	- type: `PositiveValueUIntError`
	- notes: Retained for documentation: [UInt] cannot hold a negative value, so the invariant this names is enforced by the type and this check never fires. It previously read `value >= 0U`, which is *always true* — every field carrying it was reported as violating on every validation.

- ### `TYPE_FAIL_PROP_UINT64_MIN0`
	Value must be greater than or equal to zero.
	- type: `PositiveValueULongError`
	- notes: Retained for documentation: [ULong] cannot hold a negative value, so the invariant this names is enforced by the type and this check never fires. It previously read `value >= 0UL`, which is *always true* — every field carrying it was reported as violating on every validation.
	- raised by: `BenchmarkReport.id`, `DataPoint.id`, `DataPointInjection.id`, `DataPointWrite.id`, `ErrorWatch.id`, `ErrorWatchFn.id`, `ErrorWatchFn.position`, `ExecutionNotification.id`, `Grouper.id`, `Inquisition.executionOrder`, `Inquisition.id`, `PaymentMethod.id`, `PhaseTag.subjectId`, `PhaseTag.tagId`, `Step.id`, `StepTestRun.id`, `Tag.id`, `TestCase.id`, `TestInput.id`, `TestOutcomeActual.id`, `TestOutcomeExpectation.id`, `Tool.id`, `ToolParam.id`, `Transform.id`, `TransformArg.id`, `TransformOccurrence.id`, `TransformOccurrenceArg.id`, `TypeParameter.id`, `TypeSchemaRef.id`, `User.id`, `Workflow.id`, `WorkflowExecution.id`, `WorkflowFolder.id`, `WorkflowJunction.id`, `WorkflowMetadata.id`

- ### `TYPE_INVALID_DECIMAL_SCALE`
	Value must fit the fixed precision and scale of its column.
	- type: `DecimalScaleExceeded`
	- notes: Fails if the value carries more than [maxScale] digits after the decimal point, or more than [maxPrecision] significant digits overall — the two facts a DB `DECIMAL(precision, scale)` column enforces natively and a Kotlin `Double` does not. Comparing against a `DECIMAL` type's own semantics (not float rounding) means going through [java.math.BigDecimal].

- ### `TYPE_INVALID_DOUBLE_OUTSIDE_RANGE`
	Value must fall within the permitted lower and upper bounds.
	- type: `DoubleValueOoB`
	- notes: Fails if value is outside the specified double range.
	- raised by: `Grouper.posX`, `Grouper.posY`, `MultiflowWorkflow.posX`, `MultiflowWorkflow.posY`, `Step.posX`, `Step.posY`

- ### `TYPE_INVALID_DT2B4DT1`
	The timestamps of events that must occur before and after one another are in the wrong order.
	- type: `PreventsTimeParadoxes`
	- notes: Fails if `completedAt` exists and is before `startedAt`.

- ### `TYPE_INVALID_HEX_COLOR`
	Value must be a hexadecimal colour in full six-digit form, such as #1E88E5.
	- type: `HexColorFormat`
	- notes: Fails unless the value is a full-form CSS hex colour: `#` followed by exactly six hex digits, either case. Null passes - the column is nullable and "no colour set" is a legitimate state. The short form (`#abc`) is deliberately rejected rather than expanded: the column is `varchar(7)`, and accepting two spellings of the same colour would mean every comparison and every round-trip has to normalise first.
	- raised by: `MultiflowWorkflow.color`, `Step.color`

- ### `TYPE_INVALID_INT_BELOW_MIN`
	Value must be greater than or equal to the minimum permitted value.
	- type: `IntValueMustMeetMinimum`
	- notes: Fails if the value of the constrained property is less than the provided minimum.

- ### `TYPE_INVALID_LIST_EMPTY`
	Value must not be an empty list.
	- type: `ListMustNotBeEmpty`
	- notes: Fails if list is empty.
	- raised by: `TestCase.expectedOutcomes`

- ### `TYPE_INVALID_LONG_ABOVE_MAX`
	Value must be less than or equal to the maximum permitted value.
	- type: `LongValueMustNotExceedMaximum`
	- notes: Fails if the value exceeds `maxValue`. Pairs with [LongValueMustMeetMinimum]; kept as a separate check rather than a combined range for a more specific per-violation message.

- ### `TYPE_INVALID_LONG_BELOW_MIN`
	Value must be greater than or equal to the minimum permitted value.
	- type: `LongValueMustMeetMinimum`
	- notes: Fails if the value is less than `minValue`.
	- raised by: `TestExpectation.id`

- ### `TYPE_INVALID_LONG_OUTSIDE_RANGE`
	Value must fall within the permitted lower and upper bounds.
	- type: `LongValueOoB`
	- notes: Fails if value is outside the specified range.

- ### `TYPE_INVALID_NUMERIC_ONLY`
	Value must contain only numeric characters.
	- type: `MustContainOnlyNumerals`
	- notes: Fails if the value contains anything other than digits.
	- raised by: `PaymentMethod.lastFour`

- ### `TYPE_INVALID_OAUTH_ID_COMBINATION`
	OAuth ID and OAuth provider must be set together, or both left unset.
	- type: `InconsistentOAuthId`

- ### `TYPE_INVALID_OAUTH_PROVIDER`
	Value must name one of the recognised OAuth providers.
	- type: `InvalidOAuthProvider`
	- raised by: `User.oauthProvider`

- ### `TYPE_INVALID_PY_VERSION_FORMAT`
	Value must comply with Python release versioning as specified in PEP 440.
	- type: `PythonVersionSegmentFormat`

- ### `TYPE_INVALID_RESET_TOKEN`
	Value must be a URL-safe string long enough to serve as a reset token.
	- type: `InvalidResetToken`
	- raised by: `User.apiKey`, `User.resetToken`

- ### `TYPE_INVALID_STR_BLANK`
	Value must not be blank, containing only whitespace.
	- type: `StringMustNotBeBlank`
	- notes: Fails if the string is blank (contains only whitespace).

- ### `TYPE_INVALID_STR_EMAIL`
	Value must be a valid email address.
	- type: `InvalidEmailFormat`
	- notes: Fails if value is not a valid email address.
	- raised by: `User.email`

- ### `TYPE_INVALID_STR_LEN_ABOVE_MAX`
	Value must be shorter than the maximum permitted number of characters.
	- type: `StringLengthMustBeLt`
	- notes: Fails if the result of the function exceeds the maximum allowed string length.

- ### `TYPE_INVALID_STR_LEN_BELOW_MIN`
	Value must be longer than the minimum required number of characters.
	- type: `StringLengthMustBeGt`
	- notes: Fails if the result of the function exceeds the maximum allowed string length.

- ### `TYPE_INVALID_STR_LEN_EXACT`
	Value must be exactly the required number of characters long.
	- type: `StringMustHaveExactRequiredLength`
	- notes: Fails if the string does not have exactly the required length.

- ### `TYPE_INVALID_STR_URL`
	Value must be a valid URL.
	- type: `InvalidUrlFormat`
	- notes: Fails if value is not a well-formed URL.

- ### `TYPE_INVALID_TAG_SOURCE_NONUSER`
	A tag whose source is not a user must not name a user.
	- type: `TagWithNonSourceUserIdError`

- ### `TYPE_INVALID_TAG_SOURCE_USER`
	A tag whose source is a user must name the user it belongs to.
	- type: `TagWithUserSourceUserIdError`

- ### `TYPE_INVALID_TEST_CASE_FOCUS`
	A test case must name either the workflow or the step it exercises.
	- type: `TestCaseSubject`
	- notes: Fails if both workflow and step are null.

- ### `TYPE_INVALID_TIMESTAMP_FORMAT`
	Value must comply with the ISO 8601 format, in UTC and terminated with Z.
	- type: `TimestampFormatMustMatchIso8601`
	- raised by: `ExecutionNotification.timestamp`, `PaymentMethod.createdAt`, `PaymentMethod.updatedAt`, `StepTestRun.completed`, `StepTestRun.started`, `User.createdAt`, `User.updatedAt`, `Workflow.createdAt`, `Workflow.updatedAt`, `WorkflowExecution.completedAt`, `WorkflowExecution.startedAt`, `WorkflowFolder.createdAt`, `WorkflowFolder.updatedAt`

- ### `TYPE_INVALID_TIMESTAMP_VALUE`
	Value must represent a real point in time.
	- type: `TimestampMustBeChronologicallyCoherent`
	- raised by: `ExecutionNotification.timestamp`, `PaymentMethod.createdAt`, `PaymentMethod.updatedAt`, `StepTestRun.completed`, `StepTestRun.started`, `User.createdAt`, `User.updatedAt`, `Workflow.createdAt`, `Workflow.updatedAt`, `WorkflowExecution.completedAt`, `WorkflowExecution.startedAt`, `WorkflowFolder.createdAt`, `WorkflowFolder.updatedAt`

- ### `TYPE_INVALID_VERSION_SPEC_UNDEFINED`
	At least one of the minimum, maximum, or exact version must be given.
	- type: `VersionSpecUndefined`
	- notes: Fails if all version constraint values are null.

## Database

> Raised when the store itself refuses or fails to complete the work. These describe the condition, not the statement that provoked it.

- ### `DB_CONNECTION_FAILURE`
	A connection to the database could not be established.
	- type: `ConnectionFailure`

- ### `DB_CONSTRAINT_VIOLATION`
	The operation would have violated a database constraint and was rejected.
	- type: `DbConstraintViolation`

- ### `DB_DEADLOCK`
	A deadlock was detected and this transaction was rolled back; the operation may be retried.
	- type: `DeadlockDetected`

- ### `DB_GENERIC_ERROR`
	The database reported an error while handling the operation.
	- type: `GenericError`

- ### `DB_TIMEOUT`
	The database did not respond within the time allowed.
	- type: `Timeout`

- ### `DB_TRANSACTION_ERROR`
	The transaction could not be completed and was rolled back.
	- type: `DbTransactionError`

