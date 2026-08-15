package com.wini.owle.schema

/**
 * Marks a DTO as a model of the Owl-E domain, making it a target for KSP processing.
 *
 * Classes carrying this annotation are collected by `OwleSymbolCollector` and drive two
 * generation passes:
 *  - a `DtoValidator` implementation per model, derived from the `@Constraint*` annotations
 *    on its properties (see `ValidatorFileWriter`)
 *  - the Koin module binding those validators (see `ValidatorModuleFileWriter`)
 *
 * @property skipValidation when true, the model is still collected but no validator is
 *   generated for it. Read by `ModelAnnotation.isSkipValidation` via the argument name
 *   "skipValidation"; defaults to false so a bare `@ModelDefinition` is a validation target.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ModelDefinition(
    val skipValidation: Boolean = false
)
