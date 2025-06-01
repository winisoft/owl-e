
*Priority 8*:  This package (in this gradle module anyway) is ready to serve the purpose it was designed for -- serving as a single source of truth regarding the 'setting' values of the application's data models' properties. 1.) reference the [[DDL]] 2.) where fields have length limits, ensure the limit itself is present in Schema and, in the object implementation from this module that is associated with the type, define a constant...

```kotlin
package com.wini.owle.schema.workflows
//...
object WorkflowSchema {  
    const val NAME_MAX_LENGTH = Schema.SizeLimits.LEN_255
//...
```

...that can be referenced in the exposed table definition:

```kotlin
package com.wini.owle.entity.workflows
//...
object Workflows : ULongIdTable("workflows") {  
    val name = varchar("name", Schema.Workflow.NAME_MAX_LENGTH)
//...
```

... as well as in the type validation constraints that have KSP decorators on them for the API-facing DTO types:

```kotlin
@ModelDefinition  
@PreventsTimeParadox("createdAt", "updatedAt")  
@Serializable  
data class Workflow(  
  
    @ConstraintSimple([PositiveValueULongError::class])  
    override val id: ULong,  
  
    /**  
     * Name of the workflow, used for identification in the UI.     */    @ConstraintSimple([StringSafeCharsError::class, StringNotBlankError::class, StringNotEmptyError::class])  
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.Workflow.NAME_MAX_LENGTH)  
    val name: String
```

Apply all the constraints defined in table definitions in this way.