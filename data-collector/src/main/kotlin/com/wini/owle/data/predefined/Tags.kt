package com.wini.owle.data.predefined

import com.wini.owle.dto.user.User
import com.wini.owle.dto.user.enums.SubscriptionStatus
import com.wini.owle.dto.user.tags.Tag

val predefinedTags = listOf(
    Tag(
        id = 1L,
        "Workflows",
        "The heart of Owl-E.",
        user = User(1L, "", "", null, null, null, SubscriptionStatus.REGISTERED, "", "", "", emptyList())
    ),
    Tag(
        id = 2L,
        name = "Tools",
        description = "A manageable Python function that performs a task.",
        user = User(1L, "", "", null, null, null, SubscriptionStatus.REGISTERED, "", "", "", emptyList())
    ),
    Tag(
        id = 3L,
        name = "Transformations",
        description = "A tweak to your tools' results or your data points to make their structure better suited for some purpose.",
        user = User(1L, "", "", null, null, null, SubscriptionStatus.REGISTERED, "", "", "", emptyList())
    ),
    Tag(
        id = 4L,
        name = "Download",
        description = "Workflows that involve downloading files or data from internet sources.",
        user = User(1L, "", "", null, null, null, SubscriptionStatus.REGISTERED, "", "", "", emptyList())
    ),
    Tag(
        id = 5L,
        name = "Upload",
        description = "Workflows that involve uploading files or data to a server or cloud service.",
        user = User(1L, "", "", null, null, null, SubscriptionStatus.REGISTERED, "", "", "", emptyList())
    ),
    Tag(
        id = 6L,
        name = "Data Processing",
        description = "General workflows that transform or process data in some way.",
        user = User(1L, "", "", null, null, null, SubscriptionStatus.REGISTERED, "", "", "", emptyList())
    ),
    Tag(
        id = 7L,
        name = "Image",
        description = "Workflows related to image processing, including resizing, cropping, and filtering.",
        user = User(1L, "", "", null, null, null, SubscriptionStatus.REGISTERED, "", "", "", emptyList())
    ),
    Tag(
        id = 8L,
        name = "Video",
        description = "Workflows that involve video editing, processing, or generation.",
        user = User(1L, "", "", null, null, null, SubscriptionStatus.REGISTERED, "", "", "", emptyList())
    ),
)

