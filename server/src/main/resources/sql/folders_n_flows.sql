
USE owle_ui;

START TRANSACTION;

INSERT INTO workflow_folders (id, name, description, parent_folder_id)
VALUES
(1, 'Root', 'The app-level catch-all folder', NULL),
(2, 'File Management', 'Workflows that handling, processing, or unique utilization of some file system resource', 1),
(3, 'File Conversion', 'Workflows that convert files from one format to another.', 2),
(4, 'File Downloads & Uploads', 'Workflows that involve downloading or uploading files or data to a server or cloud service.', 2),
(5, 'Data Processing', 'Workflows that transform or process data in some way.', 2),
(6, 'Media', 'Workflows that involve media processing, such as image, video, or audio.', 1),
(7, 'Image', 'Workflows related to image processing, including resizing, cropping, and filtering.', 6),
(8, 'Video', 'Workflows that involve video editing, processing, or generation.', 6),
(9, 'Audio', 'Workflows that process audio, such as cutting, mixing, or applying effects.', 6),
(10, 'Speech Synthesis', 'Voice Synthesis, Cloning, TTS, etc.', 1),
(11, 'Text-to-Speech', 'Workflows that convert text into spoken language (speech synthesis).', 10),
(12, 'Speech-to-Text', 'Workflows that convert audio speech into written text.', 10),
(13, 'Alltalk', 'Workflows involving the Alltalk Synthetic Speech solution', 12),
(14, 'Alltalk Use Cases', 'Workflows that harness an existing Alltalk server to generate speech', 13); 

INSERT INTO workflows (id, name, description, pythonVersion, parent_folder_id)
VALUES
(1, 'Clean Out Personal Storage', 'Reads the local file system and prompts an LLM to make inferences about the content for organization, migration, etc.', '3.11', 2),
(2, 'Convert PNG to SVG', 'Converts PNG files to SVG format.', '3.11', 3),
(3, 'Install Alltalk', 'Installs an Alltalk server in a conda environment', '3.11', 13),
(4, 'Streaming Translations', 'Utilizes Alltalk to continuously play audio of synthesized speech after translating into English', '3.10', 14),
(5, 'Resize Image for Web', 'Resizes images to a web-optimized format, reducing load times.', '3.11', 7),
(6, 'Convert Video to MP4', 'Converts various video formats to MP4.', '3.11', 8),
(7, 'Extract Audio from Video', 'Extracts the audio track from video files.', '3.11', 8),
(8, 'Audio Noise Reduction', 'Applies noise reduction filters to clean up audio files.', '3.11', 9),
(9, 'Audio Mixing Automation', 'Automatically adjusts volume levels for multiple audio tracks in a mix.', '3.11', 9),
(10, 'Generate Speech for Blog', 'Generates synthetic speech for blog articles using Text-to-Speech.', '3.11', 11),
(11, 'Transcribe Meeting Audio', 'Transcribes recorded meeting audio into text format.', '3.11', 12),
(12, 'Translate and Synthesize Audio', 'Translates a speech recording and synthesizes the result in a chosen language.', '3.11', 14),
(13, 'Image Compression Workflow', 'Applies lossless compression to images to optimize for storage.', '3.11', 7),
(14, 'Video Thumbnail Generation', 'Extracts a thumbnail image from a video at a specified timestamp.', '3.11', 8),
(15, 'Data Cleaning for CSVs', 'Automates cleaning of CSV files, removing duplicates, and standardizing format.', '3.11', 5),
(16, 'Automated Podcast Workflow', 'Combines recorded segments, applies filters, and exports as an MP3 file.', '3.11', 9),
(17, 'Download and Organize Images from URL', 'Downloads images from specified URLs and organizes them into folders.', '3.11', 4);

-- (10, 'API Interaction', 'Workflows that involve interacting with APIs (e.g., sending or receiving data from web services).'),
-- (11, 'Data Extraction', 'Workflows that scrape or extract data from sources such as websites, PDFs, or documents.'),
-- (12, 'Data Analysis', 'Workflows that perform statistical or analytical operations on datasets.'),
-- (13, 'Machine Learning', 'Workflows that involve training, testing, or deploying machine learning models.'),
-- (14, 'Natural Language Processing (NLP)', 'Workflows that handle text processing, such as sentiment analysis or language translation.'),
-- (15, 'Automation', 'General workflows that automate repetitive tasks, such as file organization or system monitoring.'),
-- (16, 'Notification', 'Workflows that send notifications via email, SMS, or other messaging services.'),
-- (17, 'Task Scheduling', 'Workflows that handle scheduling and time-based task automation.'),
-- (18, 'Monitoring', 'Workflows that monitor systems, websites, or services for specific conditions or events.'),
-- (19, 'File Management', 'Workflows for organizing, moving, copying, or deleting files.'),
-- (20, 'Compression', 'Workflows that compress or decompress files.'),
-- (21, 'Encryption', 'Workflows that encrypt or decrypt files or data.'),
-- (22, 'Backup', 'Workflows that create backups of files, databases, or system states.'),
-- (23, 'Report Generation', 'Workflows that create reports in various formats (24, PDF, Excel, etc.).'),
-- (24, 'Database Interaction', 'Workflows that interact with databases, executing queries or handling data storage.'),
-- (25, 'Workflow Orchestration', 'Workflows designed to manage and coordinate the execution of other workflows or tasks.'),
-- (26, 'Deployment', 'Workflows that handle deployment of software, applications, or models.'),
-- (27, 'User Interface (UI)', 'Workflows that create or modify UI elements for user interaction.'),
-- (28, 'Logging', 'Workflows that log data or events for tracking purposes.'),
-- (29, 'Testing', 'Workflows that perform automated testing of code or systems.'),
-- (30, 'Data Sanitization', 'Workflows that validate input data against certain criteria.'),
-- (31, 'Error Handling', 'Workflows that capture and handle errors in automated processes.'),
-- (32, 'Optimization', 'Workflows designed to optimize performance and/or system resources in IT processes'),
-- (33, 'Integration', 'Workflows featuring novel integrations of different services or tools to function together.'),
-- (34, 'Data Cleaning', 'Workflows that clean and sanitize datasets for further processing.'),
-- (35, 'Simulation', 'Workflows that simulate models, environments, or scenarios.'),
-- (36, 'Annotation', 'Workflows that add annotations to datasets, such as labeling data for machine learning purposes.'),
-- (37, 'Transformation', 'Workflows that transform data from one structure or format to another.'),
-- (38, 'Visualization', 'Workflows that generate visual representations of data, such as graphs or charts.'),
-- (39, 'Text Processing', 'Workflows that manipulate or transform text, such as formatting or tokenization.'),
-- (40, 'Object Detection', 'Workflows that analyze images or video for identifying specific objects.'),
-- (41, 'Authentication', 'Workflows that handle user authentication and authorization for secure access.'),
-- (42, 'Geolocation', 'Workflows that use geographic data or provide location-based services.');

COMMIT;