package com.wini.owle.air.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.domain.model.item.*

//
//class IsEnabledContext(
//    val isEnabledMessage: String,
//    val breadcrumb: List<Triple<ImageVector, String, ItemAction>>
//) {
//
//    constructor(
//        rootItem: LoRootItem,
//        environment: Environment,
//    ): this(
//        isEnabledMessage = environment.isEnabledMessage(rootItem.isEnabled.value),
//        breadcrumb = listOf(
//            Triple(
//                if (rootItem.isEnabled.value) rootItem.enabledIcon else rootItem.disabledExplicitIcon,
//                "LackOffice",
//                OnItemClicked(rootItem)
//            ),
//            Triple(
//                environment.isEnabledContextualIcon(rootItem.isEnabled.value),
//                environment.overrideName.value ?: "",
//                OnItemClicked(environment)
//            )
//        )
//    )
//
//    constructor(
//        rootItem: LoRootItem,
//        environment: Environment,
//        calHeader: CalOverrideGroup,
//        calibration: CalOverride
//    ): this(
//        isEnabledMessage = calibration.isEnabledMessage(
//            rootItem.isEnabled.value,
//            environment.isEnabled.value,
//            calHeader.isEnabled.value
//        ),
//        breadcrumb = listOf(
//            Triple(
//                if (rootItem.isEnabled.value) rootItem.enabledIcon else rootItem.disabledExplicitIcon,
//                "LackOffice",
//                OnItemClicked(rootItem)
//            ),
//            Triple(
//                environment.isEnabledContextualIcon(rootItem.isEnabled.value),
//                environment.overrideName.value ?: "",
//                OnItemClicked(environment)
//            ),
//            Triple(
//                calibration.isEnabledContextualIcon(
//                    rootItem.isEnabled.value,
//                    environment.isEnabled.value,
//                    calHeader.isEnabled.value
//                ),
//                calibration.overrideName?.value ?: "",
//                OnItemClicked(calibration)
//            )
//        )
//    )
//
//    constructor(
//        rootItem: LoRootItem,
//        environment: Environment,
//        carHeader: CarOverrideGroup,
//        carProp: CarPropOverride
//    ): this(
//        isEnabledMessage = carProp.isEnabledMessage(
//            rootItem.isEnabled.value,
//            environment.isEnabled.value,
//            carHeader.isEnabled.value
//        ),
//        breadcrumb = listOf(
//            Triple(
//                if (rootItem.isEnabled.value) rootItem.enabledIcon else rootItem.disabledExplicitIcon,
//                "LackOffice",
//                OnItemClicked(rootItem)
//            ),
//            Triple(
//                environment.isEnabledContextualIcon(rootItem.isEnabled.value),
//                environment.overrideName.value ?: "",
//                OnItemClicked(environment)
//            ),
//            Triple(
//                carProp.isEnabledContextualIcon(
//                    rootItem.isEnabled.value,
//                    environment.isEnabled.value,
//                    carHeader.isEnabled.value
//                ),
//                carProp.overrideName.value ?: "",
//                OnItemClicked(carProp)
//            )
//        )
//    )
//
//    constructor(
//        rootItem: LoRootItem,
//        environment: Environment,
//        sysPropOverrideHeader: SysPropOverrideGroup,
//        systemPropOverride: SystemPropOverride
//    ): this(
//        isEnabledMessage = systemPropOverride.isEnabledMessage(
//            rootItem.isEnabled.value,
//            environment.isEnabled.value,
//            sysPropOverrideHeader.isEnabled.value
//        ),
//        breadcrumb = listOf(
//            Triple(
//                if (rootItem.isEnabled.value) rootItem.enabledIcon else rootItem.disabledExplicitIcon,
//                "LackOffice",
//                OnItemClicked(rootItem)
//            ),
//            Triple(
//                environment.isEnabledContextualIcon(rootItem.isEnabled.value),
//                environment.overrideName.value ?: "",
//                OnItemClicked(environment)
//            ),
//            Triple(
//                systemPropOverride.isEnabledContextualIcon(
//                    rootItem.isEnabled.value,
//                    environment.isEnabled.value,
//                    sysPropOverrideHeader.isEnabled.value
//                ),
//                systemPropOverride.overrideName.value ?: "",
//                OnItemClicked(systemPropOverride)
//            )
//        )
//    )
//
//    constructor(
//        rootItem: LoRootItem,
//        environment: Environment,
//        sharedPrefOverrideGroup: SharedPrefOverrideGroup,
//        sharedPrefOverride: SharedPrefOverride
//    ): this(
//        isEnabledMessage = sharedPrefOverride.isEnabledMessage(
//            rootItem.isEnabled.value,
//            environment.isEnabled.value,
//            sharedPrefOverrideGroup.isEnabled.value
//        ),
//        breadcrumb = listOf(
//            Triple(
//                if (rootItem.isEnabled.value) rootItem.enabledIcon else rootItem.disabledExplicitIcon,
//                "LackOffice",
//                OnItemClicked(rootItem)
//            ),
//            Triple(
//                environment.isEnabledContextualIcon(rootItem.isEnabled.value),
//                environment.overrideName.value ?: "",
//                OnItemClicked(environment)
//            ),
//            Triple(
//                sharedPrefOverride.isEnabledContextualIcon(
//                    rootItem.isEnabled.value,
//                    environment.isEnabled.value,
//                    sharedPrefOverrideGroup.isEnabled.value
//                ),
//                sharedPrefOverride.overrideName.value ?: "",
//                OnItemClicked(sharedPrefOverride)
//            )
//        )
//    )
//
//    constructor(
//        rootItem: LoRootItem,
//        environment: Environment,
//        apiThingsHeader: ApisGroup,
//        apiOverride: ApiOverride
//    ): this(
//        isEnabledMessage = apiOverride.isEnabledMessage(
//            rootItem.isEnabled.value,
//            environment.isEnabled.value,
//            apiThingsHeader.isEnabled.value
//        ),
//        breadcrumb = listOf(
//            Triple(
//                if (rootItem.isEnabled.value) rootItem.enabledIcon else rootItem.disabledExplicitIcon,
//                "LackOffice",
//                OnItemClicked(rootItem)
//            ),
//            Triple(
//                environment.isEnabledContextualIcon(rootItem.isEnabled.value),
//                environment.overrideName.value ?: "",
//                OnItemClicked(environment)
//            ),
//            Triple(
//                apiOverride.isEnabledContextualIcon(
//                    rootItem.isEnabled.value,
//                    environment.isEnabled.value,
//                    apiThingsHeader.isEnabled.value
//                ),
//                apiOverride.overrideName.value ?: "",
//                OnItemClicked(apiOverride)
//            ),
//        )
//    )
//
//    constructor(
//        rootItem: LoRootItem,
//        environment: Environment,
//        apiThingsHeader: ApisGroup,
//        apiOverride: ApiOverride,
//        endpointOverride: EndpointOverride
//    ): this(
//        isEnabledMessage = endpointOverride.isEnabledMessage(
//            rootItem.isEnabled.value,
//            environment.isEnabled.value,
//            apiThingsHeader.isEnabled.value,
//            apiOverride.isEnabled.value
//        ),
//        breadcrumb = listOf(
//            Triple(
//                if (rootItem.isEnabled.value) rootItem.enabledIcon else rootItem.disabledExplicitIcon,
//                "LackOffice",
//                OnItemClicked(rootItem)
//            ),
//            Triple(
//                environment.isEnabledContextualIcon(rootItem.isEnabled.value),
//                environment.overrideName.value ?: "",
//                OnItemClicked(environment)
//            ),
//            Triple(
//                apiOverride.isEnabledContextualIcon(
//                    rootItem.isEnabled.value,
//                    environment.isEnabled.value,
//                    apiThingsHeader.isEnabled.value
//                ),
//                apiOverride.overrideName.value ?: "",
//                OnItemClicked(apiOverride)
//            ),
//            Triple(
//                endpointOverride.isEnabledContextualIcon(
//                    rootItem.isEnabled.value,
//                    environment.isEnabled.value,
//                    apiThingsHeader.isEnabled.value,
//                    apiOverride.isEnabled.value
//                ),
//                endpointOverride.overrideName?.value ?: "",
//                OnItemClicked(endpointOverride)
//            ),
//        )
//    )
//
//    constructor(
//        rootItem: LoRootItem,
//        environment: Environment,
//        apiThingsHeader: ApisGroup,
//        apiOverride: ApiOverride,
//        endpointOverride: EndpointOverride,
//        call: Call
//    ): this(
//        isEnabledMessage = call.isEnabledMessage(
//            rootItem.isEnabled.value,
//            environment.isEnabled.value,
//            apiThingsHeader.isEnabled.value,
//            apiOverride.isEnabled.value,
//            endpointOverride.isEnabled.value
//        ),
//        breadcrumb = listOf(
//            Triple(
//                if (rootItem.isEnabled.value) rootItem.enabledIcon else rootItem.disabledExplicitIcon,
//                "LackOffice",
//                OnItemClicked(rootItem)
//            ),
//            Triple(
//                environment.isEnabledContextualIcon(rootItem.isEnabled.value),
//                environment.overrideName.value ?: "",
//                OnItemClicked(environment)
//            ),
//            Triple(
//                apiOverride.isEnabledContextualIcon(
//                    rootItem.isEnabled.value,
//                    environment.isEnabled.value,
//                    apiThingsHeader.isEnabled.value
//                ),
//                apiOverride.overrideName.value ?: "",
//                OnItemClicked(apiOverride)
//            ),
//            Triple(
//                endpointOverride.isEnabledContextualIcon(
//                    rootItem.isEnabled.value,
//                    environment.isEnabled.value,
//                    apiThingsHeader.isEnabled.value,
//                    apiOverride.isEnabled.value
//                ),
//                endpointOverride.overrideName?.value ?: "",
//                OnItemClicked(endpointOverride)
//            ),
//            Triple(
//                call.isEnabledContextualIcon(
//                    rootItem.isEnabled.value,
//                    environment.isEnabled.value,
//                    apiThingsHeader.isEnabled.value,
//                    apiOverride.isEnabled.value,
//                    endpointOverride.isEnabled.value
//                ),
//                call.overrideName?.value ?: "",
//                OnItemClicked(call)
//            )
//        )
//    )
//}