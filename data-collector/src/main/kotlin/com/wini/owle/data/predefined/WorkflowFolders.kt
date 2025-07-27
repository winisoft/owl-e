package com.wini.owle.data.predefined

import com.wini.owle.dto.workflows.WorkflowFolder



val wf1 = WorkflowFolder(
    id = 1L,
    parentFolder = null,
    name = "Root",
    description = "The app-level catch-all folder",
    createdAt = "[INVALID]", updatedAt = "[INVALID]")
val wf2 = WorkflowFolder(
    id = 2L,
    parentFolder = wf1,
    name = "File Management",
    description = "",
    createdAt = "[INVALID]", updatedAt = "[INVALID]")
val wf3 = WorkflowFolder(
    id = 3L,
    parentFolder = wf2,
    name = "File Conversion",
    description = "Workflows that convert files from one format to another.",
    createdAt = "[INVALID]", updatedAt = "[INVALID]")
val wf4 = WorkflowFolder(
    id = 4L,
    parentFolder = wf2,
    name = "File Downloads & Uploads",
    description = "Workflows that involve downloading or uploading files or data to a server or cloud service.",
    createdAt = "[INVALID]", updatedAt = "[INVALID]")
val wf5 = WorkflowFolder(
    id = 5L,
    parentFolder = wf2,
    name = "Data Processing",
    description = "Workflows that transform or process data in some way.",
    createdAt = "[INVALID]", updatedAt = "[INVALID]")
val wf6 = WorkflowFolder(
    id = 6L,
    parentFolder = wf1,
    name = "Media",
    description = "Workflows that involve media processing, such as image, video, or audio.",
    createdAt = "[INVALID]", updatedAt = "[INVALID]")
val wf7 = WorkflowFolder(
    id = 7L,
    parentFolder = wf6,
    name = "Image",
    description = "Workflows related to image processing, including resizing, cropping, and filtering",
    createdAt = "[INVALID]", updatedAt = "[INVALID]")
val wf8 = WorkflowFolder(
    id = 8L,
    parentFolder = wf6,
    name = "Video",
    description = "Workflows that involve video editing, processing, or generation.",
    createdAt = "[INVALID]", updatedAt = "[INVALID]")
val wf9 = WorkflowFolder(
    id = 9L,
    parentFolder = null,
    name = "Audio",
    description = "Workflows that process audio, such as cutting, mixing, or applying effects.",
    createdAt = "[INVALID]", updatedAt = "[INVALID]")
val wf10 = WorkflowFolder(
    id = 10L,
    parentFolder = wf1,
    name = "Speech Synthesis",
    description = "Voice Synthesis, Cloning, TTS, etc.",
    createdAt = "[INVALID]", updatedAt = "[INVALID]")
val wf11 = WorkflowFolder(
    id = 11L,
    parentFolder = wf10,
    name = "Text-to-Speech",
    description = "Workflows that convert text into spoken language (speech synthesis).",
    createdAt = "[INVALID]", updatedAt = "[INVALID]")
val wf12 = WorkflowFolder(
    id = 12L,
    parentFolder = wf10,
    name = "Speech-to-Text",
    description = "Workflows that convert audio speech into written text.",
    createdAt = "[INVALID]", updatedAt = "[INVALID]")
val wf13 = WorkflowFolder(
    id = 13L,
    parentFolder = wf12,
    name = "Alltalk",
    description = "Workflows involving the Alltalk Synthetic Speech solution",
    createdAt = "[INVALID]", updatedAt = "[INVALID]")
val wf14 =  WorkflowFolder(
    id = 14L,
    parentFolder = wf13,
    name = "Alltalk Use Cases",
    description = "Workflows involving the Alltalk Synthetic Speech solution",
    createdAt = "[INVALID]", updatedAt = "[INVALID]")

val predefinedWorkflowFolders: List<WorkflowFolder> = listOf(
    wf1,
    wf2,
    wf3,
    wf4,
    wf5,
    wf6,
    wf7,
    wf8,
    wf9,
    wf10,
    wf11,
    wf12,
    wf13
)