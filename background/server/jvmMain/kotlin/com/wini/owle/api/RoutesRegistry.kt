package com.wini.owle.api

class RoutesRegistry(
    private val environs: EnvironsRoutes,
    private val runs: RunsRoutes,
    private val steps: StepsRoutes,
    private val transforms: TransformsRoutes,
    private val typing: TypingRoutes,
    private val user: UserRoutes,
    private val workflows: WorkflowsRoutes
) {

    val routes: List<RoutedApi> = listOf(
        environs,
        runs,
        steps,
        transforms,
        typing,
        user,
        workflows,
    ).flatten()

}

