package com.wini.owle.data.predefined

import com.wini.owle.dto.workflows.Workflow



val predefinedWorkflows: List<Workflow> = listOf(
    Workflow(
        id = 1L,
        name = "Clean Out Personal Storage",
        description = "Reads the local file system and prompts an LLM to make inferences about the content for organization, migration, etc.",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[1],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 2L,
        name = "Convert PNG to SVG",
        description = "Converts PNG files to SVG format",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[2],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 3L,
        name = "Install Alltalk",
        description = "Installs an Alltalk server in a conda environment",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[12],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 4L,
        name = "Streaming Translations",
        description = "Utilizes Alltalk to continuously play audio of synthesized speech after translating into English",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[13],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 5L,
        name = "Resize Image for Web",
        description = "Resizes images to a web-optimized format, reducing load times.",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[6],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 6L,
        name = "Convert Video to MP4",
        description = "Converts various video formats to MP4",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[7],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 7L,
        name = "Extract Audio from Video",
        description = "Extracts the audio track from video files.",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[7],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 8L,
        name = "Audio Noise Reduction",
        description = "Applies noise reduction filters to clean up audio files",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[8],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 9L,
        name = "Audio Mixing Automation",
        description = "Automatically adjusts volume levels for multiple audio tracks in a mix.",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[9],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 10L,
        name = "Generate Speech for Blog",
        description = "Generates synthetic speech for blog articles using Text-to-Speech.",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[11],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 11L,
        name = "Transcribe Meeting Audio",
        description = "Transcribes recorded meeting audio into text format.",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[12],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 12L,
        name = "Translate and Synthesize Audio",
        description = "Translates a speech recording and synthesizes the result in a chosen language.",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[14],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 13L,
        name = "Image Compression Workflow",
        description = "Applies lossless compression to images to optimize for storage.",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[7],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 14L,
        name = "Video Thumbnail Generation",
        description = "Extracts a thumbnail image from a video at a specified timestamp.",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[8],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 15L,
        name = "Data Cleaning for CSVs",
        description = "Automates cleaning of CSV files, removing duplicates, and standardizing format.",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[5],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 16L,
        name = "Automated Podcast Workflow",
        description = "Combines recorded segments, applies filters, and exports as an MP3 file.",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[9],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    ),
    Workflow(
        id = 17L,
        name = "Download and Organize Images from URL",
        description = "Downloads images from specified URLs and organizes them into folders.",
        pythonVersion = "",
        parentFolder = predefinedWorkflowFolders[4],
        createdAt = "[INVALID]",
        updatedAt = "[INVALID]"
    )
)

//-- (10, 'API Interaction', 'Workflows that involve interacting with APIs (e.g., sending or receiving data from web services).'),
//-- (11, 'Data Extraction', 'Workflows that scrape or extract data from sources such as websites, PDFs, or documents.'),
//-- (12, 'Data Analysis', 'Workflows that perform statistical or analytical operations on datasets.'),
//-- (13, 'Machine Learning', 'Workflows that involve training, testing, or deploying machine learning models.'),
//-- (14, 'Natural Language Processing (NLP)', 'Workflows that handle text processing, such as sentiment analysis or language translation.'),
//-- (15, 'Automation', 'General workflows that automate repetitive tasks, such as file organization or system monitoring.'),
//-- (16, 'Notification', 'Workflows that send notifications via email, SMS, or other messaging services.'),
//-- (17, 'Task Scheduling', 'Workflows that handle scheduling and time-based task automation.'),
//-- (18, 'Monitoring', 'Workflows that monitor systems, websites, or services for specific conditions or events.'),
//-- (19, 'File Management', 'Workflows for organizing, moving, copying, or deleting files.'),
//-- (20, 'Compression', 'Workflows that compress or decompress files.'),
//-- (21, 'Encryption', 'Workflows that encrypt or decrypt files or data.'),
//-- (22, 'Backup', 'Workflows that create backups of files, databases, or system states.'),
//-- (23, 'Report Generation', 'Workflows that create reports in various formats (24, PDF, Excel, etc.).'),
//-- (24, 'Database Interaction', 'Workflows that interact with databases, executing queries or handling data storage.'),
//-- (25, 'Workflow Orchestration', 'Workflows designed to manage and coordinate the execution of other workflows or tasks.'),
//-- (26, 'Deployment', 'Workflows that handle deployment of software, applications, or models.'),
//-- (27, 'User Interface (UI)', 'Workflows that create or modify UI elements for user interaction.'),
//-- (28, 'Logging', 'Workflows that log data or events for tracking purposes.'),
//-- (29, 'Testing', 'Workflows that perform automated testing of code or systems.'),
//-- (30, 'Data Sanitization', 'Workflows that validate input data against certain criteria.'),
//-- (31, 'Error Handling', 'Workflows that capture and handle errors in automated processes.'),
//-- (32, 'Optimization', 'Workflows designed to optimize performance and/or system resources in IT processes'),
//-- (33, 'Integration', 'Workflows featuring novel integrations of different services or tools to function together.'),
//-- (34, 'Data Cleaning', 'Workflows that clean and sanitize datasets for further processing.'),
//-- (35, 'Simulation', 'Workflows that simulate models, environments, or scenarios.'),
//-- (36, 'Annotation', 'Workflows that add annotations to datasets, such as labeling data for machine learning purposes.'),
//-- (37, 'Transformation', 'Workflows that transform data from one structure or format to another.'),
//-- (38, 'Visualization', 'Workflows that generate visual representations of data, such as graphs or charts.'),
//-- (39, 'Text Processing', 'Workflows that manipulate or transform text, such as formatting or tokenization.'),
//-- (40, 'Object Detection', 'Workflows that analyze images or video for identifying specific objects.'),
//-- (41, 'Authentication', 'Workflows that handle user authentication and authorization for secure access.'),
//-- (42, 'Geolocation', 'Workflows that use geographic data or provide location-based services.');