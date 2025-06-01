data_points = {
    "items": [
        {
            "name": "input_image_path",
            "description": "Path of the input image file",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1,
            "workflow_id": 1,
            "is_prerequisite": True
        },
        {
            "name": "depth_map_path",
            "description": "Path of the depth map file",
            "data_type": "builtins.str",
            "format": "file",
            "id": 2,
            "workflow_id": 1,
            "is_prerequisite": True
        },
        {
            "name": "ImagePaths",
            "description": "List of file paths to the images.",
            "data_type": "typing.List[builtins.str]",
            "format": "file",
            "id": 3,
            "workflow_id": 2,
            "is_prerequisite": True
        },
        {
            "name": "ImageLabels",
            "description": "Dictionary mapping image paths to their respective labels or categories.",
            "data_type": "typing.Dict[builtins.str, builtins.any]",
            "format": "field",
            "id": 4,
            "workflow_id": 2,
            "is_prerequisite": True
        },
        {
            "name": "PreprocessingParams",
            "description": "Parameters for preprocessing techniques like resize factor and normalization bounds.",
            "data_type": "typing.Dict[builtins.str, typing.Union[builtins.int, builtins.float]]",
            "format": "field",
            "id": 5,
            "workflow_id": 2,
            "is_prerequisite": True
        },
        {
            "name": "PCAComponents",
            "description": "Number of principal components to retain for dimensionality reduction.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 6,
            "workflow_id": 2,
            "is_prerequisite": True
        },
        {
            "name": "ProjectBrief",
            "description": "A detailed description of the video project including goals, objectives, and scope.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 7,
            "workflow_id": 4,
            "is_prerequisite": True
        },
        {
            "name": "Budget",
            "description": "A detailed breakdown of costs for the production.",
            "data_type": "typing.Dict[str, float]",
            "format": "field",
            "id": 8,
            "workflow_id": 4,
            "is_prerequisite": True
        },
        {
            "name": "Script",
            "description": "The script or storyboard for the video content.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 9,
            "workflow_id": 4,
            "is_prerequisite": True
        },
        {
            "name": "StoryboardImages",
            "description": "A list of images representing the visual plan for the video.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 10,
            "workflow_id": 4,
            "is_prerequisite": True
        },
        {
            "name": "LocationScoutReports",
            "description": "Reports from location scouting trips, including photos and notes.",
            "data_type": "typing.List[typing.Dict[str, str]]",
            "format": "field",
            "id": 11,
            "workflow_id": 4,
            "is_prerequisite": True
        },
        {
            "name": "CastAndCrewInfo",
            "description": "Information about the cast and crew involved in the production.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 12,
            "workflow_id": 4,
            "is_prerequisite": True
        },
        {
            "name": "CameraSpecs",
            "description": "Details of the camera equipment to be used for filming.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 13,
            "workflow_id": 4,
            "is_prerequisite": True
        },
        {
            "name": "EditingSoftwarePreferences",
            "description": "Preferences and settings for the video editing software.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 14,
            "workflow_id": 4,
            "is_prerequisite": True
        },
        {
            "name": "MusicAndSoundEffects",
            "description": "A list of music tracks and sound effects to be used in the video.",
            "data_type": "typing.List[str]",
            "format": "file",
            "id": 15,
            "workflow_id": 4,
            "is_prerequisite": True
        },
        {
            "name": "input_video_path",
            "description": "Path of the input video file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 16,
            "workflow_id": 7,
            "is_prerequisite": True
        },
        {
            "name": "language",
            "description": "Language used for transcription and text-to-speech.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 17,
            "workflow_id": 7,
            "is_prerequisite": True
        },
        {
            "name": "output_directory",
            "description": "Directory to store output files.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 18,
            "workflow_id": 7,
            "is_prerequisite": True
        },
        {
            "name": "whisper_model_size",
            "description": "Size of Whisper model used for transcription.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 19,
            "workflow_id": 7,
            "is_prerequisite": True
        },
        {
            "name": "platform_flexibility_requirements",
            "description": "The specific aspects of platform flexibility and power that need to be demonstrated.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 20,
            "workflow_id": 8,
            "is_prerequisite": True
        },
        {
            "name": "ai_model_name",
            "description": "The name or identifier of the AI model to interact with.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 21,
            "workflow_id": 8,
            "is_prerequisite": True
        },
        {
            "name": "input_data_path",
            "description": "The file path to the input data required for the AI model.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 22,
            "workflow_id": 8,
            "is_prerequisite": True
        },
        {
            "name": "output_data_format",
            "description": "The desired format of the generated configuration data (e.g., JSON, CSV).",
            "data_type": "builtins.str",
            "format": "field",
            "id": 23,
            "workflow_id": 8,
            "is_prerequisite": True
        },
        {
            "name": "Customer Queries",
            "description": "Historical customer inquiries and their resolved responses.",
            "data_type": "typing.List[typing.Tuple[str, str]]",
            "format": "field",
            "id": 24,
            "workflow_id": 33,
            "is_prerequisite": True
        },
        {
            "name": "Product/Service Database",
            "description": "Detailed information about products or services offered by the company.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 25,
            "workflow_id": 33,
            "is_prerequisite": True
        },
        {
            "name": "FAQs",
            "description": "Frequently asked questions and their respective answers.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 26,
            "workflow_id": 33,
            "is_prerequisite": True
        },
        {
            "name": "Chat/Email Access Credentials",
            "description": "Credentials to access chat or email platforms for communication.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 27,
            "workflow_id": 33,
            "is_prerequisite": True
        },
        {
            "name": "Customer Data",
            "description": "Customer information for personalized responses.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 28,
            "workflow_id": 33,
            "is_prerequisite": True
        },
        {
            "name": "SensorData",
            "description": "Raw sensor data from equipment",
            "data_type": "typing.List[builtins.Dict[str, builtins.Any]]",
            "format": "field",
            "id": 29,
            "workflow_id": 34,
            "is_prerequisite": True
        },
        {
            "name": "EquipmentMetadata",
            "description": "Metadata about the equipment (e.g., make, model, age)",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 30,
            "workflow_id": 34,
            "is_prerequisite": True
        },
        {
            "name": "FailureHistory",
            "description": "Historical data on equipment failures and maintenance",
            "data_type": "typing.List[builtins.Dict[str, builtins.Any]]",
            "format": "file",
            "id": 31,
            "workflow_id": 34,
            "is_prerequisite": True
        },
        {
            "name": "EnvironmentalFactors",
            "description": "Data on environmental factors that may affect equipment performance (e.g., temperature, humidity)",
            "data_type": "typing.List[builtins.Dict[str, builtins.Any]]",
            "format": "field",
            "id": 32,
            "workflow_id": 34,
            "is_prerequisite": True
        },
        {
            "name": "Social Media Platforms",
            "description": "List of platforms to fetch data from (e.g., Twitter, Facebook)",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 33,
            "workflow_id": 36,
            "is_prerequisite": True
        },
        {
            "name": "Keywords/Hashtags",
            "description": "Keywords or hashtags related to the brand/product for targeted data collection",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 34,
            "workflow_id": 36,
            "is_prerequisite": True
        },
        {
            "name": "Access Tokens/Credentials",
            "description": "API access tokens or credentials for social media platforms",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 35,
            "workflow_id": 36,
            "is_prerequisite": True
        },
        {
            "name": "Survey/Review URLs",
            "description": "URLs of surveys or review platforms to fetch data from",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 36,
            "workflow_id": 36,
            "is_prerequisite": True
        },
        {
            "name": "Data Collection Duration",
            "description": "Duration for which data needs to be fetched (e.g., last week, month)",
            "data_type": "str",
            "format": "field",
            "id": 37,
            "workflow_id": 36,
            "is_prerequisite": True
        },
        {
            "name": "Output CSV File Path",
            "description": "Path where the output CSV file will be saved",
            "data_type": "builtins.str",
            "format": "file",
            "id": 38,
            "workflow_id": 36,
            "is_prerequisite": True
        },
        {
            "name": "Output JSON File Path",
            "description": "Path where the output JSON file will be saved",
            "data_type": "builtins.str",
            "format": "file",
            "id": 39,
            "workflow_id": 36,
            "is_prerequisite": True
        },
        {
            "name": "HistoricalStockPrices",
            "description": "Historical stock prices data.",
            "data_type": "typing.Dict[builtins.str, typing.List[builtins.Tuple[builtins.float]]]",
            "format": "field",
            "id": 40,
            "workflow_id": 37,
            "is_prerequisite": True
        },
        {
            "name": "StockVolumes",
            "description": "Stock trading volumes over time.",
            "data_type": "typing.Dict[builtins.str, typing.List[builtins.int]]",
            "format": "field",
            "id": 41,
            "workflow_id": 37,
            "is_prerequisite": True
        },
        {
            "name": "NewsSentiment",
            "description": "Sentiments from news articles related to stocks.",
            "data_type": "typing.Dict[builtins.str, typing.List[builtins.Tuple[builtins.float]]]",
            "format": "field",
            "id": 42,
            "workflow_id": 37,
            "is_prerequisite": True
        },
        {
            "name": "TradingStrategyRules",
            "description": "Customizable trading strategy rules.",
            "data_type": "typing.List[typing.Dict[str, str]]",
            "format": "field",
            "id": 43,
            "workflow_id": 37,
            "is_prerequisite": True
        },
        {
            "name": "StockSymbolsList",
            "description": "List of stock symbols to track.",
            "data_type": "builtins.List[builtins.str]",
            "format": "field",
            "id": 44,
            "workflow_id": 37,
            "is_prerequisite": True
        },
        {
            "name": "HistoricalDataFilePaths",
            "description": "File paths for historical data.",
            "data_type": "builtins.List[builtins.str]",
            "format": "file",
            "id": 45,
            "workflow_id": 37,
            "is_prerequisite": True
        },
        {
            "name": "supported_languages",
            "description": "List of languages supported by the translation service.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 46,
            "workflow_id": 38,
            "is_prerequisite": True
        },
        {
            "name": "speech_recognition_model",
            "description": "Path to the speech recognition model file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 47,
            "workflow_id": 38,
            "is_prerequisite": True
        },
        {
            "name": "translation_api_key",
            "description": "API key for accessing translation services like Google Cloud Translation API or Microsoft Azure Text Translation service.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 48,
            "workflow_id": 38,
            "is_prerequisite": True
        },
        {
            "name": "translation_service_url",
            "description": "URL to the translation service endpoint.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 49,
            "workflow_id": 38,
            "is_prerequisite": True
        },
        {
            "name": "Source Code",
            "description": "The initial codebase that needs refactoring.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 50,
            "workflow_id": 39,
            "is_prerequisite": True
        },
        {
            "name": "User Preferences",
            "description": "User's preferred coding style and rules for refactoring.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 51,
            "workflow_id": 39,
            "is_prerequisite": True
        },
        {
            "name": "Refactoring Opportunities",
            "description": "Potential areas in the code identified for refactoring.",
            "data_type": "typing.List[typing.Tuple[builtins.int, builtins.str]]",
            "format": "field",
            "id": 52,
            "workflow_id": 39,
            "is_prerequisite": True
        },
        {
            "name": "Unit Tests",
            "description": "List of unit tests to ensure functionality is preserved.",
            "data_type": "typing.List[typing.Callable]",
            "format": "field",
            "id": 53,
            "workflow_id": 39,
            "is_prerequisite": True
        },
        {
            "name": "InputDocuments",
            "description": "The long documents, articles, or videos to be summarized.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 54,
            "workflow_id": 40,
            "is_prerequisite": True
        },
        {
            "name": "ModelCheckpoint",
            "description": "The path to the trained model checkpoint file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 55,
            "workflow_id": 40,
            "is_prerequisite": True
        },
        {
            "name": "HumanRefinements",
            "description": "A list of tuples containing the original summary and the refined summary by humans.",
            "data_type": "typing.List[typing.Tuple[str, str]]",
            "format": "field",
            "id": 56,
            "workflow_id": 40,
            "is_prerequisite": True
        },
        {
            "name": "Origin Coordinates",
            "description": "The starting point of the route.",
            "data_type": "typing.Tuple[float, float]",
            "format": "field",
            "id": 57,
            "workflow_id": 41,
            "is_prerequisite": True
        },
        {
            "name": "Destination Coordinates",
            "description": "The ending point of the route.",
            "data_type": "typing.Tuple[float, float]",
            "format": "field",
            "id": 58,
            "workflow_id": 41,
            "is_prerequisite": True
        },
        {
            "name": "Traffic Data Source",
            "description": "Source file or API endpoint for real-time traffic data.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 59,
            "workflow_id": 41,
            "is_prerequisite": True
        },
        {
            "name": "Weather Data Source",
            "description": "Source file or API endpoint for weather data.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 60,
            "workflow_id": 41,
            "is_prerequisite": True
        },
        {
            "name": "Road Closures Data Source",
            "description": "Source file or API endpoint for road closures data.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 61,
            "workflow_id": 41,
            "is_prerequisite": True
        },
        {
            "name": "Events Data Source",
            "description": "Source file or API endpoint for events data.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 62,
            "workflow_id": 41,
            "is_prerequisite": True
        },
        {
            "name": "recipients_data",
            "description": "List of recipients with their details (name, email, preferences)",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "id": 63,
            "workflow_id": 42,
            "is_prerequisite": True
        },
        {
            "name": "topics",
            "description": "List of topics for the emails",
            "data_type": "typing.List[str]",
            "id": 64,
            "workflow_id": 42,
            "is_prerequisite": True
        },
        {
            "name": "tone_preferences",
            "description": "Dictionary of tone preferences (recipient_email: tone)",
            "data_type": "typing.Dict[str, str]",
            "id": 65,
            "workflow_id": 42,
            "is_prerequisite": True
        },
        {
            "name": "email_template_path",
            "description": "Path to the email template file",
            "data_type": "builtins.str",
            "format": "file",
            "id": 66,
            "workflow_id": 42,
            "is_prerequisite": True
        },
        {
            "name": "grammar_check_tool",
            "description": "Path to the grammar check tool or API key",
            "data_type": "builtins.str",
            "format": "field",
            "id": 67,
            "workflow_id": 42,
            "is_prerequisite": True
        },
        {
            "name": "NPC Data",
            "description": "Information about NPCs including stats, abilities, dialogues.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 68,
            "workflow_id": 44,
            "is_prerequisite": True
        },
        {
            "name": "Player Data",
            "description": "Information about players including stats, inventory, quest progress.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 69,
            "workflow_id": 44,
            "is_prerequisite": True
        },
        {
            "name": "Game State",
            "description": "Current state of the game, including map location, NPCs present, etc.",
            "data_type": "typing.Any",
            "format": "field",
            "id": 70,
            "workflow_id": 44,
            "is_prerequisite": True
        },
        {
            "name": "Quests Data",
            "description": "Information about quests including objectives, rewards, status.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 71,
            "workflow_id": 44,
            "is_prerequisite": True
        },
        {
            "name": "Dialogue Scripts",
            "description": "Scripts for NPC dialogues and conversations.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 72,
            "workflow_id": 44,
            "is_prerequisite": True
        },
        {
            "name": "Combat Mechanics Data",
            "description": "Rules governing combat mechanics, such as damage calculation algorithms.",
            "data_type": "typing.Any",
            "format": "field",
            "id": 73,
            "workflow_id": 44,
            "is_prerequisite": True
        },
        {
            "name": "Game Events Scripts",
            "description": "Scripts for handling in-game events.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 74,
            "workflow_id": 44,
            "is_prerequisite": True
        },
        {
            "name": "Testing Scenarios",
            "description": "Scenarios and data needed for testing NPC behaviors, quest progression, etc.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 75,
            "workflow_id": 44,
            "is_prerequisite": True
        },
        {
            "name": "Video Source",
            "description": "The source video file or camera stream to process.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 76,
            "workflow_id": 45,
            "is_prerequisite": True
        },
        {
            "name": "Object Detection Model",
            "description": "A trained model for detecting objects in each frame.",
            "data_type": "typing.Any",
            "format": "field",
            "id": 77,
            "workflow_id": 45,
            "is_prerequisite": True
        },
        {
            "name": "Tracking Parameters",
            "description": "Parameters for the tracking algorithm, such as IoU threshold and maximum trace length.",
            "data_type": "typing.Dict[str, float]",
            "format": "field",
            "id": 78,
            "workflow_id": 45,
            "is_prerequisite": True
        },
        {
            "name": "Output Format",
            "description": "The desired format for the tracked object data (e.g., CSV, JSON).",
            "data_type": "builtins.str",
            "format": "field",
            "id": 79,
            "workflow_id": 45,
            "is_prerequisite": True
        },
        {
            "name": "customer_data",
            "description": "The raw customer data used for segmentation.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "file",
            "id": 80,
            "workflow_id": 46,
            "is_prerequisite": True
        },
        {
            "name": "segmentation_model",
            "description": "The chosen model for segmentation. Could be a pretrained model or a path to save/load the model.",
            "data_type": "typing.Any",
            "format": "field",
            "id": 81,
            "workflow_id": 46,
            "is_prerequisite": True
        },
        {
            "name": "customer_behaviors",
            "description": "Features representing customer behaviors like purchase history, browsing history, etc.",
            "data_type": "typing.List[builtins.int]",
            "format": "field",
            "id": 82,
            "workflow_id": 46,
            "is_prerequisite": True
        },
        {
            "name": "customer_preferences",
            "description": "Customer preferences derived from explicit inputs (like product ratings) or inferred preferences.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 83,
            "workflow_id": 46,
            "is_prerequisite": True
        },
        {
            "name": "customer_characteristics",
            "description": "Demographic characteristics of customers like age, gender, location, etc.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 84,
            "workflow_id": 46,
            "is_prerequisite": True
        },
        {
            "name": "Historical Listing Data",
            "description": "Past listings with their prices, quantities sold, and timestamps.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "file",
            "id": 85,
            "workflow_id": 48,
            "is_prerequisite": True
        },
        {
            "name": "Market Trends Data",
            "description": "Data showing demand and supply trends over time for similar products.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 86,
            "workflow_id": 48,
            "is_prerequisite": True
        },
        {
            "name": "Seasonality Indicators",
            "description": "Data showing seasonality patterns in sales or demand for related products.",
            "data_type": "typing.List[float]",
            "format": "file",
            "id": 87,
            "workflow_id": 48,
            "is_prerequisite": True
        },
        {
            "name": "Competitor Pricing Data",
            "description": "Current prices of similar products from competitors.",
            "data_type": "typing.Dict[str, float]",
            "format": "field",
            "id": 88,
            "workflow_id": 48,
            "is_prerequisite": True
        },
        {
            "name": "Listing Category Data",
            "description": "Data about the category or sub-category of listings for price adjustment.",
            "data_type": "typing.Dict[str, str]",
            "format": "file",
            "id": 89,
            "workflow_id": 48,
            "is_prerequisite": True
        },
        {
            "name": "Customer Behavior Data",
            "description": "Data on customer purchasing behavior and preferences.",
            "data_type": "typing.List[typing.Dict[str, int]]",
            "format": "field",
            "id": 90,
            "workflow_id": 48,
            "is_prerequisite": True
        },
        {
            "name": "input_matrix",
            "description": "The square matrix to be raised to a power.",
            "data_type": "typing.List[typing.List[int]]",
            "format": "field",
            "id": 91,
            "workflow_id": 73,
            "is_prerequisite": True
        },
        {
            "name": "power_n",
            "description": "The integer value to which the matrix will be raised.",
            "data_type": "int",
            "format": "field",
            "id": 92,
            "workflow_id": 73,
            "is_prerequisite": True
        },
        {
            "name": "user_input",
            "description": "The integer input provided by the user.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 93,
            "workflow_id": 74,
            "is_prerequisite": True
        },
        {
            "name": "Git Repository Directory",
            "description": "The directory containing the git repository to analyze.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 94,
            "workflow_id": 75,
            "is_prerequisite": True
        },
        {
            "name": "Commit History Data",
            "description": "List of commit objects, each containing hash, author name, author email, commit message, and timestamp.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 95,
            "workflow_id": 75,
            "is_prerequisite": True
        },
        {
            "name": "Lines Changed Data",
            "description": "List of tuples, each containing commit hash and lines added or removed in that commit.",
            "data_type": "typing.List[typing.Tuple[str, int]]",
            "format": "field",
            "id": 96,
            "workflow_id": 75,
            "is_prerequisite": True
        },
        {
            "name": "api_url",
            "description": "The URL of the API to collect data from.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 97,
            "workflow_id": 76,
            "is_prerequisite": True
        },
        {
            "name": "headers",
            "description": "Headers required for accessing the API.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 98,
            "workflow_id": 76,
            "is_prerequisite": True
        },
        {
            "name": "csv_file_path",
            "description": "The path where the CSV file will be stored.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 99,
            "workflow_id": 76,
            "is_prerequisite": True
        },
        {
            "name": "user_record_schema",
            "description": "The schema for user records, used for validation and normalization.",
            "data_type": "typing.Dict[builtins.str, typing.Union[builtins.int, builtins.float, builtins.bool]]",
            "format": "field",
            "id": 100,
            "workflow_id": 76,
            "is_prerequisite": True
        },
        {
            "name": "log_file_path",
            "description": "The path where the log file will be stored.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 101,
            "workflow_id": 76,
            "is_prerequisite": True
        },
        {
            "name": "News Articles",
            "description": "List of news articles, each represented as a dictionary with 'title' and 'text' keys.",
            "data_type": "typing.List[typing.Dict[str, str]]",
            "format": "field",
            "id": 102,
            "workflow_id": 77,
            "is_prerequisite": True
        },
        {
            "name": "Output File Path",
            "description": "Path to the file where the combined summary will be written.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 103,
            "workflow_id": 77,
            "is_prerequisite": True
        },
        {
            "name": "image_paths",
            "description": "Paths of the input images to be segmented.",
            "data_type": "builtins.List[builtins.str]",
            "format": "field",
            "id": 104,
            "workflow_id": 78,
            "is_prerequisite": True
        },
        {
            "name": "initial_centroids",
            "description": "Initial centroids for K-Means algorithm. If not provided, randomly initialized.",
            "data_type": "typing.Optional[typing.List[typing.Tuple[int, int]]]",
            "format": "field",
            "id": 105,
            "workflow_id": 78,
            "is_prerequisite": True
        },
        {
            "name": "num_clusters",
            "description": "Number of clusters to segment the image into.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 106,
            "workflow_id": 78,
            "is_prerequisite": True
        },
        {
            "name": "max_iterations",
            "description": "Maximum number of iterations for K-Means algorithm.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 107,
            "workflow_id": 78,
            "is_prerequisite": True
        },
        {
            "name": "tolerance",
            "description": "Tolerance for stopping criterion in K-Means algorithm.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 108,
            "workflow_id": 78,
            "is_prerequisite": True
        },
        {
            "name": "api_specification_files",
            "description": "List of OpenAPI/Swagger specification files to process.",
            "data_type": "typing.List[str]",
            "format": "file",
            "id": 109,
            "workflow_id": 79,
            "is_prerequisite": True
        },
        {
            "name": "output_format",
            "description": "Format of the generated API documentation (e.g., 'markdown', 'html').",
            "data_type": "str",
            "format": "field",
            "id": 110,
            "workflow_id": 79,
            "is_prerequisite": True
        },
        {
            "name": "output_directory",
            "description": "Directory where the generated API documentation will be saved.",
            "data_type": "str",
            "format": "file",
            "id": 111,
            "workflow_id": 79,
            "is_prerequisite": True
        },
        {
            "name": "dataset_path",
            "description": "Path to the CSV dataset file",
            "data_type": "builtins.str",
            "format": "file",
            "id": 112,
            "workflow_id": 81,
            "is_prerequisite": True
        },
        {
            "name": "column_mappings",
            "description": "Dictionary mapping column names to their intended data types (e.g., {'column1': 'int', 'column2': 'float'})",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 113,
            "workflow_id": 81,
            "is_prerequisite": True
        },
        {
            "name": "categorical_columns",
            "description": "List of column names that should be treated as categorical variables",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 114,
            "workflow_id": 81,
            "is_prerequisite": True
        },
        {
            "name": "Number of coin tosses",
            "description": "The total number of independent coin tosses performed.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 115,
            "workflow_id": 82,
            "is_prerequisite": True
        },
        {
            "name": "Number of heads",
            "description": "The total number of times the coin landed on heads.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 116,
            "workflow_id": 82,
            "is_prerequisite": True
        },
        {
            "name": "SupplierData",
            "description": "Contains details about suppliers such as name, contact information, and supplier ID.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 117,
            "workflow_id": 83,
            "is_prerequisite": True
        },
        {
            "name": "EthicalAuditReports",
            "description": "Detailed reports on suppliers' ethical practices in sustainability, labor standards, and supply chain transparency.",
            "data_type": "typing.List[typing.Dict[builtins.str, typing.Any]]",
            "format": "field",
            "id": 118,
            "workflow_id": 83,
            "is_prerequisite": True
        },
        {
            "name": "SupplierScores",
            "description": "An initial set of ethical scores for suppliers based on available data.",
            "data_type": "typing.Dict[builtins.int, builtins.float]",
            "format": "field",
            "id": 119,
            "workflow_id": 83,
            "is_prerequisite": True
        },
        {
            "name": "WeightagesForEthicalFactors",
            "description": "The weights assigned to sustainability practices, labor standards, and supply chain transparency.",
            "data_type": "typing.Dict[str, float]",
            "format": "field",
            "id": 120,
            "workflow_id": 83,
            "is_prerequisite": True
        },
        {
            "name": "SupplierDataFiles",
            "description": "Path to the files containing supplier data. These could be CSV, Excel, or other structured data files.",
            "data_type": "builtins.List[builtins.str]",
            "format": "file",
            "id": 121,
            "workflow_id": 83,
            "is_prerequisite": True
        },
        {
            "name": "AuditReportFiles",
            "description": "Path to the files containing ethical audit reports for suppliers. These could also be CSV, Excel, or other structured data files.",
            "data_type": "builtins.List[builtins.str]",
            "format": "file",
            "id": 122,
            "workflow_id": 83,
            "is_prerequisite": True
        },
        {
            "name": "Seed",
            "description": "A numeric value used for random number generation.",
            "data_type": "typing.Union[int, typing.Callable[[], int]]",
            "id": 123,
            "workflow_id": 84,
            "is_prerequisite": True
        },
        {
            "name": "Size",
            "description": "The dimensions of the landscape to generate.",
            "data_type": "tuple[int, int]",
            "id": 124,
            "workflow_id": 84,
            "is_prerequisite": True
        },
        {
            "name": "Elevation_Map",
            "description": "An optional existing elevation map file for modification or use as a starting point.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 125,
            "workflow_id": 84,
            "is_prerequisite": True
        },
        {
            "name": "repository_url",
            "description": "The URL of the Git repository to lint.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 126,
            "workflow_id": 85,
            "is_prerequisite": True
        },
        {
            "name": "branch_name",
            "description": "The name of the branch in the repository to lint.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 127,
            "workflow_id": 85,
            "is_prerequisite": True
        },
        {
            "name": "commit_hash",
            "description": "The hash of the commit to lint. Optional if using a branch name.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 128,
            "workflow_id": 85,
            "is_prerequisite": True
        },
        {
            "name": "ontology_data",
            "description": "The ontological data represented as a Python dictionary with classes as keys and a parents list containing their immediate parents.",
            "data_type": "typing.Dict[str, typing.List[str]]",
            "format": "field",
            "id": 129,
            "workflow_id": 86,
            "is_prerequisite": True
        },
        {
            "name": "file_path",
            "description": "The file path of the ontological data if it's stored in a file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 130,
            "workflow_id": 86,
            "is_prerequisite": True
        },
        {
            "name": "InputURLs",
            "description": "List of URLs to fetch news articles from.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 131,
            "workflow_id": 87,
            "is_prerequisite": True
        },
        {
            "name": "OutputFilePath",
            "description": "File path where the aggregated summary report will be saved.",
            "data_type": "str",
            "format": "file",
            "id": 132,
            "workflow_id": 87,
            "is_prerequisite": True
        },
        {
            "name": "number_of_samples",
            "description": "The number of samples to generate for estimating Pi.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 133,
            "workflow_id": 88,
            "is_prerequisite": True
        },
        {
            "name": "input_data",
            "description": "The data that needs to be transformed.",
            "data_type": "typing.Any",
            "format": "field",
            "id": 134,
            "workflow_id": 89,
            "is_prerequisite": True
        },
        {
            "name": "transformation_function",
            "description": "A function that performs the transformation on input_data.",
            "data_type": "types.FunctionType",
            "format": "field",
            "id": 135,
            "workflow_id": 89,
            "is_prerequisite": True
        },
        {
            "name": "transformation_description",
            "description": "A string describing the transformation applied by transformation_function.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 136,
            "workflow_id": 89,
            "is_prerequisite": True
        },
        {
            "name": "log_file_path",
            "description": "The file path where the audit trail log will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 137,
            "workflow_id": 89,
            "is_prerequisite": True
        },
        {
            "name": "API_keys",
            "description": "API keys for news sources like BBC, CNN, The Guardian.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 138,
            "workflow_id": 90,
            "is_prerequisite": True
        },
        {
            "name": "news_sources",
            "description": "List of news sources to aggregate from.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 139,
            "workflow_id": 90,
            "is_prerequisite": True
        },
        {
            "name": "output_file_path",
            "description": "Path where the final report will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 140,
            "workflow_id": 90,
            "is_prerequisite": True
        },
        {
            "name": "input_hashmap",
            "description": "The Hashmap that needs healing.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 141,
            "workflow_id": 91,
            "is_prerequisite": True
        },
        {
            "name": "resolution_strategy",
            "description": "Strategy to use for resolving duplicate keys (e.g., 'merge', 'keep_latest').",
            "data_type": "builtins.str",
            "format": "field",
            "id": 142,
            "workflow_id": 91,
            "is_prerequisite": True
        },
        {
            "name": "input_file_path",
            "description": "The path to the file containing the Hashmap data.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 143,
            "workflow_id": 91,
            "is_prerequisite": True
        },
        {
            "name": "diagnostic_log_file",
            "description": "The file where diagnostic messages will be written.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 144,
            "workflow_id": 91,
            "is_prerequisite": True
        },
        {
            "name": "input_image_path",
            "description": "Path of the input image file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 145,
            "workflow_id": 92,
            "is_prerequisite": True
        },
        {
            "name": "output_ascii_art",
            "description": "Generated ASCII art string.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 146,
            "workflow_id": 92,
            "is_prerequisite": True
        },
        {
            "name": "mapping_chars",
            "description": "List of characters used for mapping pixel intensities.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 147,
            "workflow_id": 92,
            "is_prerequisite": True
        },
        {
            "name": "input_code",
            "description": "The Python code string that needs to be reviewed.",
            "data_type": "typing.Str",
            "format": "field",
            "id": 148,
            "workflow_id": 93,
            "is_prerequisite": True
        },
        {
            "name": "pylint_config_file",
            "description": "Path to the pylint configuration file (if any).",
            "data_type": "builtins.str",
            "format": "file",
            "id": 149,
            "workflow_id": 93,
            "is_prerequisite": True
        },
        {
            "name": "repository_url",
            "description": "The URL of the open-source API repository on GitHub.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 150,
            "workflow_id": 94,
            "is_prerequisite": True
        },
        {
            "name": "user_preferences",
            "description": "A dictionary containing user's preferences for formatting and output.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 151,
            "workflow_id": 94,
            "is_prerequisite": True
        },
        {
            "name": "output_file_path",
            "description": "The file path where the generated documentation will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 152,
            "workflow_id": 94,
            "is_prerequisite": True
        },
        {
            "name": "RSS_Feeds_List",
            "description": "List of RSS feed URLs to fetch news articles from.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 153,
            "workflow_id": 95,
            "is_prerequisite": True
        },
        {
            "name": "Email_Credentials",
            "description": "Username and password for sending newsletter emails.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 154,
            "workflow_id": 95,
            "is_prerequisite": True
        },
        {
            "name": "Email_Template_Path",
            "description": "File path to the email template file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 155,
            "workflow_id": 95,
            "is_prerequisite": True
        },
        {
            "name": "Recipients_List",
            "description": "List of email addresses to send newsletter to.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 156,
            "workflow_id": 95,
            "is_prerequisite": True
        },
        {
            "name": "topics",
            "description": "A list of topics for the curriculum.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 157,
            "workflow_id": 96,
            "is_prerequisite": True
        },
        {
            "name": "learning_outcomes",
            "description": "A dictionary where keys are outcomes and values are their descriptions.",
            "data_type": "typing.Dict[builtins.str, builtins.str]",
            "format": "field",
            "id": 158,
            "workflow_id": 96,
            "is_prerequisite": True
        },
        {
            "name": "total_lessons",
            "description": "The total number of lessons available for allocation.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 159,
            "workflow_id": 96,
            "is_prerequisite": True
        },
        {
            "name": "dataset_path",
            "description": "Path to the dataset file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 160,
            "workflow_id": 97,
            "is_prerequisite": True
        },
        {
            "name": "language_model_choice",
            "description": "Selected language model (e.g., NLTK or spaCy).",
            "data_type": "typing.StrEnum(['NLTK', 'spaCy'])",
            "format": "field",
            "id": 161,
            "workflow_id": 97,
            "is_prerequisite": True
        },
        {
            "name": "analysis_tasks",
            "description": "List of analysis tasks to perform.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 162,
            "workflow_id": 97,
            "is_prerequisite": True
        },
        {
            "name": "model_parameters",
            "description": "Parameters for model training (e.g., epochs, learning rate).",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 163,
            "workflow_id": 97,
            "is_prerequisite": True
        },
        {
            "name": "evaluation_metrics",
            "description": "List of metrics to use for model evaluation.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 164,
            "workflow_id": 97,
            "is_prerequisite": True
        },
        {
            "name": "input_file_path",
            "description": "Path to the input CSV file",
            "data_type": "builtins.str",
            "format": "file",
            "id": 165,
            "workflow_id": 98,
            "is_prerequisite": True
        },
        {
            "name": "output_file_path",
            "description": "Path where the cleaned data and report will be saved",
            "data_type": "builtins.str",
            "format": "file",
            "id": 166,
            "workflow_id": 98,
            "is_prerequisite": True
        },
        {
            "name": "missing_value_strategy",
            "description": "Strategy to handle missing values ('drop' or 'impute')",
            "data_type": "builtins.str",
            "format": "field",
            "id": 167,
            "workflow_id": 98,
            "is_prerequisite": True
        },
        {
            "name": "outlier_detection_method",
            "description": "Method for outlier detection ('IQR' or 'Z-score')",
            "data_type": "builtins.str",
            "format": "field",
            "id": 168,
            "workflow_id": 98,
            "is_prerequisite": True
        },
        {
            "name": "topics",
            "description": "A list of educational topics.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 169,
            "workflow_id": 100,
            "is_prerequisite": True
        },
        {
            "name": "num_weeks",
            "description": "The desired number of weeks for the curriculum. Default is 4.",
            "data_type": "int",
            "format": "field",
            "default": 4,
            "id": 170,
            "workflow_id": 100,
            "is_prerequisite": True
        },
        {
            "name": "learning_outcomes_per_topic",
            "description": "The desired learning outcomes per topic. Default is 2.",
            "data_type": "int",
            "format": "field",
            "default": 2,
            "id": 171,
            "workflow_id": 100,
            "is_prerequisite": True
        },
        {
            "name": "Git Repository Path",
            "description": "Path to the Git repository",
            "data_type": "builtins.str",
            "format": "file",
            "id": 172,
            "workflow_id": 101,
            "is_prerequisite": True
        },
        {
            "name": "Commit History",
            "description": "List of commits with their timestamps and messages",
            "data_type": "typing.List[gitpython.objects Commit]",
            "format": "field",
            "id": 173,
            "workflow_id": 101,
            "is_prerequisite": True
        },
        {
            "name": "Fever Periods Data",
            "description": "Dictionary containing fever periods start times, end times, and commit counts",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 174,
            "workflow_id": 101,
            "is_prerequisite": True
        },
        {
            "name": "Number of Samples",
            "description": "Total number of random points to generate.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 175,
            "workflow_id": 102,
            "is_prerequisite": True
        },
        {
            "name": "Points Inside Quarter Circle",
            "description": "Number of randomly generated points that fall within a quarter circle.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 176,
            "workflow_id": 102,
            "is_prerequisite": True
        },
        {
            "name": "TargetFunction",
            "description": "The function to be wrapped and tracked.",
            "data_type": "typing.Callable[[], typing.Any]",
            "format": "field",
            "id": 177,
            "workflow_id": 103,
            "is_prerequisite": True
        },
        {
            "name": "LogFilePath",
            "description": "The file path where the wrapper will store its logs.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 178,
            "workflow_id": 103,
            "is_prerequisite": True
        },
        {
            "name": "ErrorHandlingStrategy",
            "description": "A dictionary specifying how to handle different types of errors.",
            "data_type": "typing.Dict[builtins.Type[Exception], typing.Callable[[Exception], None]]",
            "format": "field",
            "id": 179,
            "workflow_id": 103,
            "is_prerequisite": True
        },
        {
            "name": "QuizParameters",
            "description": "Contains parameters for quiz generation such as topic, number of questions and difficulty level.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 180,
            "workflow_id": 104,
            "is_prerequisite": True
        },
        {
            "name": "QuestionBanks",
            "description": "A dictionary where keys are topics and values are lists of questions related to those topics.",
            "data_type": "typing.Dict[str, typing.List[typing.Dict[str, str]]]",
            "format": "field",
            "id": 181,
            "workflow_id": 104,
            "is_prerequisite": True
        },
        {
            "name": "QuizQuestions",
            "description": "A list of selected quiz questions based on QuizParameters and QuestionBanks.",
            "data_type": "typing.List[typing.Dict[str, str]]",
            "format": "field",
            "id": 182,
            "workflow_id": 104,
            "is_prerequisite": True
        },
        {
            "name": "AnswerKey",
            "description": "A dictionary where keys are question numbers and values are correct answers.",
            "data_type": "typing.Dict[int, str]",
            "format": "field",
            "id": 183,
            "workflow_id": 104,
            "is_prerequisite": True
        },
        {
            "name": "Seed",
            "description": "Random number generation seed",
            "data_type": "builtins.int",
            "format": "field",
            "id": 184,
            "workflow_id": 105,
            "is_prerequisite": True
        },
        {
            "name": "ZoomLevel",
            "description": "Zoom level for fractal landscape",
            "data_type": "builtins.float",
            "format": "field",
            "id": 185,
            "workflow_id": 105,
            "is_prerequisite": True
        },
        {
            "name": "DetailScale",
            "description": "Detail scale for fractal landscape",
            "data_type": "builtins.float",
            "format": "field",
            "id": 186,
            "workflow_id": 105,
            "is_prerequisite": True
        },
        {
            "name": "OutputFilePath",
            "description": "Path to output file for generated fractal landscape image",
            "data_type": "builtins.str",
            "format": "file",
            "id": 187,
            "workflow_id": 105,
            "is_prerequisite": True
        },
        {
            "name": "topics",
            "description": "List of topics for the curriculum.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 188,
            "workflow_id": 106,
            "is_prerequisite": True
        },
        {
            "name": "desired_outcomes",
            "description": "List of desired outcomes for each topic.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 189,
            "workflow_id": 106,
            "is_prerequisite": True
        },
        {
            "name": "prerequisites",
            "description": "A dictionary mapping topics to their prerequisites.",
            "data_type": "typing.Dict[str, typing.List[str]]",
            "format": "field",
            "id": 190,
            "workflow_id": 106,
            "is_prerequisite": True
        },
        {
            "name": "input_file_path",
            "description": "File path of the input file containing topic and outcome data.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 191,
            "workflow_id": 106,
            "is_prerequisite": True
        },
        {
            "name": "Project Data",
            "description": "Information about the project, including tasks and their details.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 192,
            "workflow_id": 107,
            "is_prerequisite": True
        },
        {
            "name": "Task Steps",
            "description": "The individual steps or stages of each task in the project.",
            "data_type": "typing.List[typing.Tuple[str, str]]",
            "format": "field",
            "id": 193,
            "workflow_id": 107,
            "is_prerequisite": True
        },
        {
            "name": "Tool Libraries",
            "description": "A database or catalog of available software libraries and APIs with their functionalities.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "file",
            "id": 194,
            "workflow_id": 107,
            "is_prerequisite": True
        },
        {
            "name": "User Preferences",
            "description": "The user's preferences regarding tools, parameters, etc.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 195,
            "workflow_id": 107,
            "is_prerequisite": True
        },
        {
            "name": "Task Categorization Rules",
            "description": "Rules or algorithms used to categorize tasks based on their nature and context.",
            "data_type": "typing.List[typing.Tuple[str, typing.Any]]",
            "format": "file",
            "id": 196,
            "workflow_id": 107,
            "is_prerequisite": True
        },
        {
            "name": "numbers_to_check",
            "description": "A list of numbers to check for primality.",
            "data_type": "typing.List[builtins.int]",
            "format": "field",
            "id": 197,
            "workflow_id": 108,
            "is_prerequisite": True
        },
        {
            "name": "random_seed",
            "description": "A seed number for generating random numbers.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 198,
            "workflow_id": 108,
            "is_prerequisite": True
        },
        {
            "name": "input_file_path",
            "description": "Path of the input text file containing code snippets.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 199,
            "workflow_id": 109,
            "is_prerequisite": True
        },
        {
            "name": "delimiter",
            "description": "The character or string used to separate code snippets in the input file.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 200,
            "workflow_id": 109,
            "is_prerequisite": True
        },
        {
            "name": "challenge_templates",
            "description": "List of challenge templates for each category.",
            "data_type": "typing.Dict[str, typing.List[typing.Tuple[str, str]]]",
            "format": "field",
            "id": 201,
            "workflow_id": 110,
            "is_prerequisite": True
        },
        {
            "name": "generated_challenges",
            "description": "Set to store previously generated challenges.",
            "data_type": "set",
            "format": "field",
            "id": 202,
            "workflow_id": 110,
            "is_prerequisite": True
        },
        {
            "name": "category_file_path",
            "description": "Path to the file containing category data.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 203,
            "workflow_id": 110,
            "is_prerequisite": True
        },
        {
            "name": "dataset",
            "description": "Predefined dataset containing facts about books, authors, genres etc.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "file",
            "id": 204,
            "workflow_id": 111,
            "is_prerequisite": True
        },
        {
            "name": "author",
            "description": "The author to query books for",
            "data_type": "builtins.str",
            "format": "field",
            "id": 205,
            "workflow_id": 111,
            "is_prerequisite": True
        },
        {
            "name": "genre",
            "description": "The genre to query authors for",
            "data_type": "builtins.str",
            "format": "field",
            "id": 206,
            "workflow_id": 111,
            "is_prerequisite": True
        },
        {
            "name": "tasks_list",
            "description": "A list of tasks and their descriptions in natural language.",
            "data_type": "typing.List[typing.Tuple[str, str]]",
            "format": "field",
            "id": 207,
            "workflow_id": 112,
            "is_prerequisite": True
        },
        {
            "name": "tool_rules",
            "description": "Predefined rules or patterns for matching tasks to tools.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 208,
            "workflow_id": 112,
            "is_prerequisite": True
        },
        {
            "name": "default_parameters",
            "description": "Default parameters for tools that don't require optimization.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 209,
            "workflow_id": 112,
            "is_prerequisite": True
        },
        {
            "name": "dataset",
            "description": "The time-series dataset containing missing values",
            "data_type": "pandas.DataFrame",
            "format": "file",
            "id": 210,
            "workflow_id": 113,
            "is_prerequisite": True
        },
        {
            "name": "missing_values_threshold",
            "description": "The maximum number of consecutive missing values to consider as significant",
            "data_type": "builtins.int",
            "format": "field",
            "id": 211,
            "workflow_id": 113,
            "is_prerequisite": True
        },
        {
            "name": "topics",
            "description": "A list of topics for the quiz.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 212,
            "workflow_id": 114,
            "is_prerequisite": True
        },
        {
            "name": "correct_answers",
            "description": "The correct answers corresponding to each topic.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 213,
            "workflow_id": 114,
            "is_prerequisite": True
        },
        {
            "name": "incorrect_options",
            "description": "A list of incorrect options that can be mixed with the correct answer.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 214,
            "workflow_id": 114,
            "is_prerequisite": True
        },
        {
            "name": "initial_belief",
            "description": "The initial probability distribution for the unknown parameter (probability of getting heads).",
            "data_type": "typing.Dict[str, float]",
            "format": "field",
            "id": 215,
            "workflow_id": 115,
            "is_prerequisite": True
        },
        {
            "name": "observed_data",
            "description": "A list of outcomes from simulated coin flips ('H' or 'T').",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 216,
            "workflow_id": 115,
            "is_prerequisite": True
        },
        {
            "name": "API_specification_files",
            "description": "The OpenAPI specification files (YAML or JSON) to be parsed.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 217,
            "workflow_id": 116,
            "is_prerequisite": True
        },
        {
            "name": "output_destination",
            "description": "The destination directory or file where the generated markdown API documentation will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 218,
            "workflow_id": 116,
            "is_prerequisite": True
        },
        {
            "name": "user_input",
            "description": "User input for series generation.",
            "data_type": "builtins.str",
            "id": 219,
            "workflow_id": 117,
            "is_prerequisite": True
        },
        {
            "name": "start_value",
            "description": "The first value in the arithmetic or geometric series.",
            "data_type": "typing.Union[builtins.int, builtins.float]",
            "id": 220,
            "workflow_id": 117,
            "is_prerequisite": True
        },
        {
            "name": "common_difference_or_ratio",
            "description": "The common difference for an arithmetic series or ratio for a geometric series.",
            "data_type": "typing.Union[builtins.int, builtins.float]",
            "format": "field",
            "id": 221,
            "workflow_id": 117,
            "is_prerequisite": True
        },
        {
            "name": "number_of_terms",
            "description": "The number of terms to generate in the series.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 222,
            "workflow_id": 117,
            "is_prerequisite": True
        },
        {
            "name": "number_of_particles",
            "description": "The number of particles to simulate.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 223,
            "workflow_id": 118,
            "is_prerequisite": True
        },
        {
            "name": "steps_per_particle",
            "description": "The number of steps each particle will take.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 224,
            "workflow_id": 118,
            "is_prerequisite": True
        },
        {
            "name": "step_size",
            "description": "The size of each step taken by the particles in meters.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 225,
            "workflow_id": 118,
            "is_prerequisite": True
        },
        {
            "name": "output_file_path",
            "description": "The file path where simulation data will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 226,
            "workflow_id": 118,
            "is_prerequisite": True
        },
        {
            "name": "operation_range",
            "description": "The range within which operations will be generated.",
            "data_type": "typing.Tuple[int, int]",
            "format": "field",
            "id": 227,
            "workflow_id": 119,
            "is_prerequisite": True
        },
        {
            "name": "number_of_questions",
            "description": "The number of questions in each quiz.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 228,
            "workflow_id": 119,
            "is_prerequisite": True
        },
        {
            "name": "output_file_path",
            "description": "The path where the generated quiz will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 229,
            "workflow_id": 119,
            "is_prerequisite": True
        },
        {
            "name": "QuestionsMetadata",
            "description": "Metadata for each quiz question including text, difficulty level, topic.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 230,
            "workflow_id": 120,
            "is_prerequisite": True
        },
        {
            "name": "UserCredentials",
            "description": "Information required for user registration and login.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 231,
            "workflow_id": 120,
            "is_prerequisite": True
        },
        {
            "name": "UserPerformance",
            "description": "Data on how users perform in quizzes.",
            "data_type": "typing.List[typing.Dict[str, int]]",
            "format": "field",
            "id": 232,
            "workflow_id": 120,
            "is_prerequisite": True
        },
        {
            "name": "files_to_check",
            "description": "List of Python files to perform automated code review on",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 233,
            "workflow_id": 121,
            "is_prerequisite": True
        },
        {
            "name": "output_report_path",
            "description": "Path where the generated report will be saved as a file",
            "data_type": "builtins.str",
            "format": "file",
            "id": 234,
            "workflow_id": 121,
            "is_prerequisite": True
        },
        {
            "name": "topics",
            "description": "List of topics for the lesson plans.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 235,
            "workflow_id": 122,
            "is_prerequisite": True
        },
        {
            "name": "desired_outcomes",
            "description": "Dictionary where keys are topics and values are desired outcomes.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 236,
            "workflow_id": 122,
            "is_prerequisite": True
        },
        {
            "name": "input_data_path",
            "description": "File path to the input data if provided in a file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 237,
            "workflow_id": 122,
            "is_prerequisite": True
        },
        {
            "name": "clinical_data",
            "description": "The main clinical trial dataset containing patient information, interventions, and outcomes.",
            "data_type": "typing.Pandas.DataFrame",
            "format": "file",
            "id": 238,
            "workflow_id": 123,
            "is_prerequisite": True
        },
        {
            "name": "metadata",
            "description": "A dictionary containing metadata about the clinical trial, such as study ID, title, objectives, etc.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 239,
            "workflow_id": 123,
            "is_prerequisite": True
        },
        {
            "name": "experimental_design",
            "description": "A dictionary or data frame outlining the experimental design of the trial (e.g., groups, interventions, timelines).",
            "data_type": "typing.Union[typing.Dict, typing.Pandas.DataFrame]",
            "format": "field",
            "id": 240,
            "workflow_id": 123,
            "is_prerequisite": True
        },
        {
            "name": "statistical_tests",
            "description": "A list of statistical tests to perform on the data (e.g., t-tests, ANOVA).",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 241,
            "workflow_id": 123,
            "is_prerequisite": True
        },
        {
            "name": "plot_configurations",
            "description": "A list of dictionaries specifying plot configurations (e.g., plot types, axes limits, styles).",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 242,
            "workflow_id": 123,
            "is_prerequisite": True
        },
        {
            "name": "report_template",
            "description": "The markdown template for the report, including placeholders for data analysis and visualization results.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 243,
            "workflow_id": 123,
            "is_prerequisite": True
        },
        {
            "name": "input_data",
            "description": "The time-series dataset to be normalized.",
            "data_type": "pandas.core.frame.DataFrame",
            "format": "file",
            "id": 244,
            "workflow_id": 124,
            "is_prerequisite": True
        },
        {
            "name": "output_data",
            "description": "The normalized time-series dataset without trend and seasonality.",
            "data_type": "pandas.core.frame.DataFrame",
            "format": "field",
            "id": 245,
            "workflow_id": 124,
            "is_prerequisite": True
        },
        {
            "name": "Entities",
            "description": "List of entities (people, places, things)",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 246,
            "workflow_id": 125,
            "is_prerequisite": True
        },
        {
            "name": "Relations",
            "description": "List of relations between entities",
            "data_type": "typing.List[tuple[str, str]]",
            "format": "field",
            "id": 247,
            "workflow_id": 125,
            "is_prerequisite": True
        },
        {
            "name": "Entity Data File",
            "description": "File containing detailed information about entities",
            "data_type": "builtins.str",
            "format": "file",
            "id": 248,
            "workflow_id": 125,
            "is_prerequisite": True
        },
        {
            "name": "Relation Data File",
            "description": "File containing relations between entities",
            "data_type": "builtins.str",
            "format": "file",
            "id": 249,
            "workflow_id": 125,
            "is_prerequisite": True
        },
        {
            "name": "number_of_samples",
            "description": "The number of samples to use in the Monte Carlo simulation.",
            "data_type": "typing.Int",
            "format": "field",
            "id": 250,
            "workflow_id": 126,
            "is_prerequisite": True
        },
        {
            "name": "file_paths",
            "description": "List of file paths for US census information CSV files.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 251,
            "workflow_id": 127,
            "is_prerequisite": True
        },
        {
            "name": "expected_columns",
            "description": "List of expected column names (population, age, race) in the CSV files.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 252,
            "workflow_id": 127,
            "is_prerequisite": True
        },
        {
            "name": "column_ranges",
            "description": "Dictionary containing acceptable ranges for each numeric column.",
            "data_type": "typing.Dict[builtins.str, tuple[int, int]]",
            "format": "field",
            "id": 253,
            "workflow_id": 127,
            "is_prerequisite": True
        },
        {
            "name": "null_value_strategy",
            "description": "Strategy to handle null values (e.g., 'drop', 'fill_with_mean', etc.).",
            "data_type": "builtins.str",
            "format": "field",
            "id": 254,
            "workflow_id": 127,
            "is_prerequisite": True
        },
        {
            "name": "User Demographics",
            "description": "Basic user information for personalized learning.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 255,
            "workflow_id": 128,
            "is_prerequisite": True
        },
        {
            "name": "Quiz Answers",
            "description": "Users' responses to quizzes/questions.",
            "data_type": "typing.List[typing.Tuple[int, builtins.bool]]",
            "format": "field",
            "id": 256,
            "workflow_id": 128,
            "is_prerequisite": True
        },
        {
            "name": "Quiz Difficulty Levels",
            "description": "Difficulty levels of each quiz/question.",
            "data_type": "typing.List[int]",
            "format": "field",
            "id": 257,
            "workflow_id": 128,
            "is_prerequisite": True
        },
        {
            "name": "Performance History",
            "description": "Users' performance over time.",
            "data_type": "typing.List[typing.Tuple[builtins.float, builtins.bool]]",
            "format": "field",
            "id": 258,
            "workflow_id": 128,
            "is_prerequisite": True
        },
        {
            "name": "Learning Progress Records",
            "description": "Users' learning progress records.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 259,
            "workflow_id": 128,
            "is_prerequisite": True
        },
        {
            "name": "User Feedback",
            "description": "Users' feedback on the learning content and difficulty.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 260,
            "workflow_id": 128,
            "is_prerequisite": True
        },
        {
            "name": "API Keys",
            "description": "Keys for accessing news APIs like BBC, CNN, The Guardian.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 261,
            "workflow_id": 129,
            "is_prerequisite": True
        },
        {
            "name": "User Preferences",
            "description": "User's preferred topics, sources, and language.",
            "data_type": "typing.Dict[str, typing.Union[str, typing.List[str]]]",
            "format": "field",
            "id": 262,
            "workflow_id": 129,
            "is_prerequisite": True
        },
        {
            "name": "API Response Format",
            "description": "Format in which API responses will be returned.",
            "data_type": "str",
            "format": "field",
            "id": 263,
            "workflow_id": 129,
            "is_prerequisite": True
        },
        {
            "name": "News Articles Data",
            "description": "Fetched news articles data from various sources.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 264,
            "workflow_id": 129,
            "is_prerequisite": True
        },
        {
            "name": "Summary Report File Path",
            "description": "Path where the final summary report will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 265,
            "workflow_id": 129,
            "is_prerequisite": True
        },
        {
            "name": "urls_or_files",
            "description": "List of URLs or file paths of research papers to summarize.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 266,
            "workflow_id": 130,
            "is_prerequisite": True
        },
        {
            "name": "summary_length",
            "description": "Desired length of the final summary in sentences or characters.",
            "data_type": "int",
            "format": "field",
            "id": 267,
            "workflow_id": 130,
            "is_prerequisite": True
        },
        {
            "name": "topic_keywords",
            "description": "List of keywords to focus on during topic identification and summarization.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 268,
            "workflow_id": 130,
            "is_prerequisite": True
        },
        {
            "name": "input_files",
            "description": "Paths of the input CSV files to be harmonized.",
            "data_type": "builtins.List[builtins.str]",
            "format": "file",
            "id": 269,
            "workflow_id": 131,
            "is_prerequisite": True
        },
        {
            "name": "standard_column_names",
            "description": "A list of standard column names to use after renaming.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 270,
            "workflow_id": 131,
            "is_prerequisite": True
        },
        {
            "name": "missing_value_strategy",
            "description": "Strategy for handling missing values ('fill' or 'drop').",
            "data_type": "builtins.str",
            "format": "field",
            "id": 271,
            "workflow_id": 131,
            "is_prerequisite": True
        },
        {
            "name": "RSS Feeds",
            "description": "A list of RSS feed URLs to fetch headlines from.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 272,
            "workflow_id": 132,
            "is_prerequisite": True
        },
        {
            "name": "Search Query",
            "description": "The user's search query to filter relevant news headlines.",
            "data_type": "str",
            "format": "field",
            "id": 273,
            "workflow_id": 132,
            "is_prerequisite": True
        },
        {
            "name": "Environment Map",
            "description": "The 2D environment where the agent operates, including obstacles and goal positions.",
            "data_type": "typing.List[typing.Tuple[int, int]]",
            "format": "field",
            "id": 274,
            "workflow_id": 133,
            "is_prerequisite": True
        },
        {
            "name": "Agent Start Position",
            "description": "The initial position of the agent in the environment.",
            "data_type": "typing.Tuple[int, int]",
            "format": "field",
            "id": 275,
            "workflow_id": 133,
            "is_prerequisite": True
        },
        {
            "name": "Goal Position",
            "description": "The target position towards which the agent should move.",
            "data_type": "typing.Tuple[int, int]",
            "format": "field",
            "id": 276,
            "workflow_id": 133,
            "is_prerequisite": True
        },
        {
            "name": "Agent Movement Strategy",
            "description": "The rule-based strategy guiding the agent's movement decisions.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 277,
            "workflow_id": 133,
            "is_prerequisite": True
        },
        {
            "name": "Visualization Settings",
            "description": "Parameters for configuring how the agent's path is visualized.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 278,
            "workflow_id": 133,
            "is_prerequisite": True
        },
        {
            "name": "Scenario Files",
            "description": "Files containing different scenarios or environments where the agent will be tested.",
            "data_type": "builtins.list[str]",
            "format": "file",
            "id": 279,
            "workflow_id": 133,
            "is_prerequisite": True
        },
        {
            "name": "Topic",
            "description": "The news topic for which articles need to be summarized.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 280,
            "workflow_id": 134,
            "is_prerequisite": True
        },
        {
            "name": "RSS_Feed_URLs",
            "description": "List of RSS feed URLs on the specified topic.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 281,
            "workflow_id": 134,
            "is_prerequisite": True
        },
        {
            "name": "Summarization_Model",
            "description": "The summarization model to use (e.g., 'gensim', 'transformers').",
            "data_type": "builtins.str",
            "format": "field",
            "id": 282,
            "workflow_id": 134,
            "is_prerequisite": True
        },
        {
            "name": "Summarization_Config",
            "description": "Configuration for the chosen summarization model.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 283,
            "workflow_id": 134,
            "is_prerequisite": True
        },
        {
            "name": "Output_Summary_File",
            "description": "The file path where the consolidated summary report will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 284,
            "workflow_id": 134,
            "is_prerequisite": True
        },
        {
            "name": "repository_url",
            "description": "The URL of the Git repository to clone and build.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 285,
            "workflow_id": 135,
            "is_prerequisite": True
        },
        {
            "name": "branch_name",
            "description": "The branch name to checkout for building and testing.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 286,
            "workflow_id": 135,
            "is_prerequisite": True
        },
        {
            "name": "requirements_file_path",
            "description": "The path to the requirements file (pip freeze format) for installing dependencies.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 287,
            "workflow_id": 135,
            "is_prerequisite": True
        },
        {
            "name": "test_command",
            "description": "The command to run tests using pytest or similar.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 288,
            "workflow_id": 135,
            "is_prerequisite": True
        },
        {
            "name": "build_command",
            "description": "The command to build the application (e.g., PyInstaller).",
            "data_type": "builtins.str",
            "format": "field",
            "id": 289,
            "workflow_id": 135,
            "is_prerequisite": True
        },
        {
            "name": "artifact_upload_urls",
            "description": "A list of URLs for uploading built artifacts to artifact repositories.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 290,
            "workflow_id": 135,
            "is_prerequisite": True
        },
        {
            "name": "target_environments",
            "description": "A dictionary mapping environment names to deployment configuration (e.g., SSH keys, server details).",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 291,
            "workflow_id": 135,
            "is_prerequisite": True
        },
        {
            "name": "input_images",
            "description": "List of input images",
            "data_type": "typing.List[builtins.str]",
            "format": "file",
            "id": 292,
            "workflow_id": 136,
            "is_prerequisite": True
        },
        {
            "name": "output_directory",
            "description": "Directory to save output images",
            "data_type": "builtins.str",
            "format": "field",
            "id": 293,
            "workflow_id": 136,
            "is_prerequisite": True
        },
        {
            "name": "filters_to_apply",
            "description": "List of filters to apply on input images",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 294,
            "workflow_id": 136,
            "is_prerequisite": True
        },
        {
            "name": "input_image",
            "description": "The image file in JPGPNG format that needs to be captioned.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 295,
            "workflow_id": 137,
            "is_prerequisite": True
        },
        {
            "name": "model_name",
            "description": "The name of the pre-trained model like CLIP or DALL-E to use for generating captions.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 296,
            "workflow_id": 137,
            "is_prerequisite": True
        },
        {
            "name": "PasswordLength",
            "description": "The desired length of the generated passwords.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 297,
            "workflow_id": 139,
            "is_prerequisite": True
        },
        {
            "name": "IncludeUppercase",
            "description": "Flag indicating whether uppercase letters should be included in the password.",
            "data_type": "builtins.bool",
            "format": "field",
            "id": 298,
            "workflow_id": 139,
            "is_prerequisite": True
        },
        {
            "name": "IncludeLowercase",
            "description": "Flag indicating whether lowercase letters should be included in the password.",
            "data_type": "builtins.bool",
            "format": "field",
            "id": 299,
            "workflow_id": 139,
            "is_prerequisite": True
        },
        {
            "name": "IncludeNumbers",
            "description": "Flag indicating whether numbers should be included in the password.",
            "data_type": "builtins.bool",
            "format": "field",
            "id": 300,
            "workflow_id": 139,
            "is_prerequisite": True
        },
        {
            "name": "IncludeSpecialChars",
            "description": "Flag indicating whether special characters should be included in the password.",
            "data_type": "builtins.bool",
            "format": "field",
            "id": 301,
            "workflow_id": 139,
            "is_prerequisite": True
        },
        {
            "name": "ExcludedPatternsFile",
            "description": "Path to a file containing patterns to exclude from generated passwords.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 302,
            "workflow_id": 139,
            "is_prerequisite": True
        },
        {
            "name": "article_urls",
            "description": "A list of URLs pointing to news articles.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 303,
            "workflow_id": 140,
            "is_prerequisite": True
        },
        {
            "name": "summary_length",
            "description": "The desired length of the article summaries. Optional, default is 3 sentences.",
            "data_type": "int",
            "format": "field",
            "id": 304,
            "workflow_id": 140,
            "is_prerequisite": True
        },
        {
            "name": "limit",
            "description": "The maximum Fibonacci number to generate",
            "data_type": "builtins.int",
            "format": "field",
            "id": 305,
            "workflow_id": 141,
            "is_prerequisite": True
        },
        {
            "name": "fibonacci_list",
            "description": "A global list to store the generated Fibonacci numbers",
            "data_type": "typing.List[builtins.int]",
            "format": "field",
            "id": 306,
            "workflow_id": 141,
            "is_prerequisite": True
        },
        {
            "name": "base_url_api1",
            "description": "Base URL for API 1",
            "data_type": "builtins.str",
            "format": "field",
            "id": 307,
            "workflow_id": 142,
            "is_prerequisite": True
        },
        {
            "name": "api_key_api1",
            "description": "API Key for API 1 (if required)",
            "data_type": "builtins.Optional[builtins.str]",
            "format": "field",
            "id": 308,
            "workflow_id": 142,
            "is_prerequisite": True
        },
        {
            "name": "base_url_api2",
            "description": "Base URL for API 2",
            "data_type": "builtins.str",
            "format": "field",
            "id": 309,
            "workflow_id": 142,
            "is_prerequisite": True
        },
        {
            "name": "api_key_api2",
            "description": "API Key for API 2 (if required)",
            "data_type": "builtins.Optional[builtins.str]",
            "format": "field",
            "id": 310,
            "workflow_id": 142,
            "is_prerequisite": True
        },
        {
            "name": "StoryNodes",
            "description": "A dictionary where keys are story nodes (as strings) and values are lists containing scenario string and branching choices.",
            "data_type": "typing.Dict[builtins.str, typing.Tuple[str, typing.List[str]]]",
            "format": "field",
            "id": 311,
            "workflow_id": 143,
            "is_prerequisite": True
        },
        {
            "name": "CurrentNode",
            "description": "The current node in the story where user interaction occurs.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 312,
            "workflow_id": 143,
            "is_prerequisite": True
        },
        {
            "name": "PreviousNodes",
            "description": "A stack to store previously visited nodes for backtracking functionality.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 313,
            "workflow_id": 143,
            "is_prerequisite": True
        },
        {
            "name": "directory_path",
            "description": "The directory path containing the Python files to be linted and formatted.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 314,
            "workflow_id": 144,
            "is_prerequisite": True
        },
        {
            "name": "lint_threshold",
            "description": "The maximum number of linting errors allowed before considering a file for further review.",
            "data_type": "int",
            "format": "field",
            "id": 315,
            "workflow_id": 144,
            "is_prerequisite": True
        },
        {
            "name": "warning_threshold",
            "description": "The maximum number of linting warnings allowed before considering a file for further review.",
            "data_type": "int",
            "format": "field",
            "id": 316,
            "workflow_id": 144,
            "is_prerequisite": True
        },
        {
            "name": "image_paths",
            "description": "A list of file paths to the images that need to be resized.",
            "data_type": "typing.List[str]",
            "format": "file",
            "id": 317,
            "workflow_id": 145,
            "is_prerequisite": True
        },
        {
            "name": "output_directory",
            "description": "The directory where the resized images will be saved. Should exist and have write permissions.",
            "data_type": "str",
            "format": "field",
            "id": 318,
            "workflow_id": 145,
            "is_prerequisite": True
        },
        {
            "name": "max_width_height",
            "description": "A tuple (width, height) representing the maximum dimensions of the resized images.",
            "data_type": "typing.Tuple[int, int]",
            "format": "field",
            "id": 319,
            "workflow_id": 145,
            "is_prerequisite": True
        },
        {
            "name": "SourceCode",
            "description": "The JavaScript code to be translated.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 320,
            "workflow_id": 146,
            "is_prerequisite": True
        },
        {
            "name": "ExpectedOutput",
            "description": "The expected output of the source code for accuracy checking.",
            "data_type": "typing.Any",
            "format": "field",
            "id": 321,
            "workflow_id": 146,
            "is_prerequisite": True
        },
        {
            "name": "TranslationRules",
            "description": "Mapping rules for translating JavaScript syntax to Python.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 322,
            "workflow_id": 146,
            "is_prerequisite": True
        },
        {
            "name": "EquivalentControlStructures",
            "description": "Mapping of JavaScript control structures to their equivalent in Python.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 323,
            "workflow_id": 146,
            "is_prerequisite": True
        },
        {
            "name": "EquivalentFunctions",
            "description": "Mapping of JavaScript functions to their equivalent in Python.",
            "data_type": "typing.Dict[str, typing.Callable[[], None]]",
            "format": "field",
            "id": 324,
            "workflow_id": 146,
            "is_prerequisite": True
        },
        {
            "name": "data_file_path",
            "description": "Path of the data file",
            "data_type": "builtins.str",
            "format": "file",
            "id": 325,
            "workflow_id": 147,
            "is_prerequisite": True
        },
        {
            "name": "dataset_columns",
            "description": "Columns in the dataset",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 326,
            "workflow_id": 147,
            "is_prerequisite": True
        },
        {
            "name": "hypotheses",
            "description": "List of hypotheses to test",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 327,
            "workflow_id": 147,
            "is_prerequisite": True
        },
        {
            "name": "significance_level",
            "description": "Significance level for hypothesis testing (e.g., 0.05)",
            "data_type": "builtins.float",
            "format": "field",
            "id": 328,
            "workflow_id": 147,
            "is_prerequisite": True
        },
        {
            "name": "output_report_path",
            "description": "Path where the report will be saved",
            "data_type": "builtins.str",
            "format": "file",
            "id": 329,
            "workflow_id": 147,
            "is_prerequisite": True
        },
        {
            "name": "Input_PDF_File",
            "description": "The PDF file containing the academic research paper to be summarized.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 330,
            "workflow_id": 148,
            "is_prerequisite": True
        },
        {
            "name": "Summarization_Model",
            "description": "The transformer-based model to be used for summarization, e.g., 'bert-base-uncased' or 'distilbert-base-uncased'.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 331,
            "workflow_id": 148,
            "is_prerequisite": True
        },
        {
            "name": "Output_Format",
            "description": "The desired format for the output file containing the structured summaries, e.g., 'json' or 'markdown'.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 332,
            "workflow_id": 148,
            "is_prerequisite": True
        },
        {
            "name": "input_data",
            "description": "The raw data file (e.g., CSV) to be cleaned and processed.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 333,
            "workflow_id": 149,
            "is_prerequisite": True
        },
        {
            "name": "duplicate_columns",
            "description": "Columns in the dataset that should not contain duplicates. If None, all columns will be checked.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 334,
            "workflow_id": 149,
            "is_prerequisite": True
        },
        {
            "name": "missing_value_threshold",
            "description": "The maximum percentage of missing values allowed in a column for it to be retained. If None, all columns will be kept.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 335,
            "workflow_id": 149,
            "is_prerequisite": True
        },
        {
            "name": "outlier_method",
            "description": "The method used for outlier detection ('interquartile_range' or 'z_score').",
            "data_type": "builtins.str",
            "format": "field",
            "id": 336,
            "workflow_id": 149,
            "is_prerequisite": True
        },
        {
            "name": "outlier_threshold",
            "description": "The threshold value for outlier detection. If None, a default value will be used.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 337,
            "workflow_id": 149,
            "is_prerequisite": True
        },
        {
            "name": "population_size",
            "description": "The total number of individuals in the population.",
            "data_type": "typing.Union[builtins.int, typing.List[builtins.int]]",
            "format": "field",
            "id": 338,
            "workflow_id": 150,
            "is_prerequisite": True
        },
        {
            "name": "initial_infected",
            "description": "The initial number of infected individuals in the population.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 339,
            "workflow_id": 150,
            "is_prerequisite": True
        },
        {
            "name": "recovery_rate",
            "description": "The proportion of infected individuals who recover each day.",
            "data_type": "float",
            "format": "field",
            "id": 340,
            "workflow_id": 150,
            "is_prerequisite": True
        },
        {
            "name": "infection_rate",
            "description": "The probability that a susceptible individual will become infected by an infectious individual.",
            "data_type": "float",
            "format": "field",
            "id": 341,
            "workflow_id": 150,
            "is_prerequisite": True
        },
        {
            "name": "simulation_days",
            "description": "The number of days the simulation should run for.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 342,
            "workflow_id": 150,
            "is_prerequisite": True
        },
        {
            "name": "input_data",
            "description": "Dictionary containing instruments as keys and measurements as values",
            "data_type": "typing.Dict[builtins.str, typing.List[builtins.any]]",
            "format": "field",
            "id": 343,
            "workflow_id": 152,
            "is_prerequisite": True
        },
        {
            "name": "climate_change_data",
            "description": "The climate change dataset to be processed and analyzed.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 344,
            "workflow_id": 153,
            "is_prerequisite": True
        },
        {
            "name": "missing_values_threshold",
            "description": "The threshold for the proportion of missing values allowed in each column of the dataset.",
            "data_type": "typing.Float",
            "format": "field",
            "id": 345,
            "workflow_id": 153,
            "is_prerequisite": True
        },
        {
            "name": "outlier_detection_method",
            "description": "The method to be used for outlier detection (e.g., 'z-score', 'IQR').",
            "data_type": "builtins.str",
            "format": "field",
            "id": 346,
            "workflow_id": 153,
            "is_prerequisite": True
        },
        {
            "name": "statistical_measures_to_calculate",
            "description": "A list of statistical measures to calculate on the dataset.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 347,
            "workflow_id": 153,
            "is_prerequisite": True
        },
        {
            "name": "plots_to_generate",
            "description": "A list of plots to generate from the dataset (e.g., 'line_graph', 'bar_chart').",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 348,
            "workflow_id": 153,
            "is_prerequisite": True
        },
        {
            "name": "historical_data",
            "description": "The historical data used for time series forecasting.",
            "data_type": "pandas.DataFrame",
            "format": "file",
            "id": 349,
            "workflow_id": 154,
            "is_prerequisite": True
        },
        {
            "name": "target_column",
            "description": "The column in the DataFrame that represents the values to be predicted.",
            "data_type": "str",
            "format": "field",
            "id": 350,
            "workflow_id": 154,
            "is_prerequisite": True
        },
        {
            "name": "date_column",
            "description": "The column in the DataFrame that represents the date or timestamp for each data point.",
            "data_type": "str",
            "format": "field",
            "id": 351,
            "workflow_id": 154,
            "is_prerequisite": True
        },
        {
            "name": "forecast_horizon",
            "description": "The number of time steps into the future to predict.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 352,
            "workflow_id": 154,
            "is_prerequisite": True
        },
        {
            "name": "input_data",
            "description": "Data used for generating test cases dynamically.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 353,
            "workflow_id": 155,
            "is_prerequisite": True
        },
        {
            "name": "setup_operations",
            "description": "Operations to perform before each test case.",
            "data_type": "typing.List[typing.Callable[[], None]]",
            "format": "field",
            "id": 354,
            "workflow_id": 155,
            "is_prerequisite": True
        },
        {
            "name": "teardown_operations",
            "description": "Operations to perform after each test case.",
            "data_type": "typing.List[typing.Callable[[], None]]",
            "format": "field",
            "id": 355,
            "workflow_id": 155,
            "is_prerequisite": True
        },
        {
            "name": "report_config",
            "description": "Configuration for generating HTML report.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 356,
            "workflow_id": 155,
            "is_prerequisite": True
        },
        {
            "name": "test_report_path",
            "description": "File path to save the generated HTML report.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 357,
            "workflow_id": 155,
            "is_prerequisite": True
        },
        {
            "name": "API Keys",
            "description": "Keys for accessing news APIs",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 358,
            "workflow_id": 156,
            "is_prerequisite": True
        },
        {
            "name": "News Sources",
            "description": "List of news sources to fetch headlines from",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 359,
            "workflow_id": 156,
            "is_prerequisite": True
        },
        {
            "name": "Summarization Model",
            "description": "Text summarization model (e.g., Genius, BERT-extractive-summarizer)",
            "data_type": "builtins.str",
            "format": "field",
            "id": 360,
            "workflow_id": 156,
            "is_prerequisite": True
        },
        {
            "name": "limit",
            "description": "The upper limit for generating prime numbers.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 361,
            "workflow_id": 157,
            "is_prerequisite": True
        },
        {
            "name": "topics",
            "description": "List of topics for the curriculum plan.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 362,
            "workflow_id": 158,
            "is_prerequisite": True
        },
        {
            "name": "input_file_path",
            "description": "Path to the input file containing topic data.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 363,
            "workflow_id": 158,
            "is_prerequisite": True
        },
        {
            "name": "review_feedback",
            "description": "User feedback for refining the curriculum plan.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 364,
            "workflow_id": 158,
            "is_prerequisite": True
        },
        {
            "name": "research_topic",
            "description": "The topic of research for which literature review is required.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 365,
            "workflow_id": 159,
            "is_prerequisite": True
        },
        {
            "name": "database_choice",
            "description": "The academic database to query (e.g., 'Semantic Scholar', 'ArXiv').",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 366,
            "workflow_id": 159,
            "is_prerequisite": True
        },
        {
            "name": "query_params",
            "description": "Additional parameters for refining the literature search (e.g., publication date range, language).",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 367,
            "workflow_id": 159,
            "is_prerequisite": True
        },
        {
            "name": "file_path_access_rights",
            "description": "File path and access rights for saving/reading data during processing.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 368,
            "workflow_id": 159,
            "is_prerequisite": True
        },
        {
            "name": "input_image_path",
            "description": "The file path of the input image used as a reference for mosaic dimensions.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 369,
            "workflow_id": 160,
            "is_prerequisite": True
        },
        {
            "name": "image_directory_path",
            "description": "The file path of the directory containing multiple images to be selected from.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 370,
            "workflow_id": 160,
            "is_prerequisite": True
        },
        {
            "name": "patch_size",
            "description": "The size of each patch to be extracted and arranged in the mosaic.",
            "data_type": "typing.Tuple[int, int]",
            "format": "field",
            "id": 371,
            "workflow_id": 160,
            "is_prerequisite": True
        },
        {
            "name": "mosaic_dimensions",
            "description": "The dimensions of the final output mosaic image.",
            "data_type": "typing.Tuple[int, int]",
            "format": "field",
            "id": 372,
            "workflow_id": 160,
            "is_prerequisite": True
        },
        {
            "name": "number_of_patches",
            "description": "The total number of patches to be selected from the directory images for creating the mosaic.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 373,
            "workflow_id": 160,
            "is_prerequisite": True
        },
        {
            "name": "TimeSeriesData",
            "description": "The main time-series data to be analyzed and manipulated.",
            "data_type": "pandas.core.frame.DataFrame",
            "format": "field",
            "id": 374,
            "workflow_id": 161,
            "is_prerequisite": True
        },
        {
            "name": "ColumnsToAnalyze",
            "description": "A list of columns in the TimeSeriesData that users want to analyze.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 375,
            "workflow_id": 161,
            "is_prerequisite": True
        },
        {
            "name": "TimeIntervals",
            "description": "The time intervals selected by users for data analysis.",
            "data_type": "typing.List[tuple[int, int]]",
            "format": "field",
            "id": 376,
            "workflow_id": 161,
            "is_prerequisite": True
        },
        {
            "name": "FilePath",
            "description": "The file path of the time-series data if it's stored in a file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 377,
            "workflow_id": 161,
            "is_prerequisite": True
        },
        {
            "name": "population_size",
            "description": "Total number of individuals in the population.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 378,
            "workflow_id": 162,
            "is_prerequisite": True
        },
        {
            "name": "initial_infected_population",
            "description": "Number of initially infected individuals.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 379,
            "workflow_id": 162,
            "is_prerequisite": True
        },
        {
            "name": "infection_rate",
            "description": "Probability of infection per contact.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 380,
            "workflow_id": 162,
            "is_prerequisite": True
        },
        {
            "name": "recovery_rate",
            "description": "Rate at which infected individuals recover from the disease.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 381,
            "workflow_id": 162,
            "is_prerequisite": True
        },
        {
            "name": "contact_structure",
            "description": "Data on contact patterns among individuals in the population (e.g., who interacts with whom).",
            "data_type": "typing.List[typing.Dict[builtins.int, typing.List[builtins.int]]]",
            "format": "file",
            "id": 382,
            "workflow_id": 162,
            "is_prerequisite": True
        },
        {
            "name": "intervention_strategies",
            "description": "List of intervention strategies to model (quarantine, vaccination, social distancing).",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 383,
            "workflow_id": 162,
            "is_prerequisite": True
        },
        {
            "name": "intervention_parameters",
            "description": "Parameters related to the intervention strategies (e.g., quarantine period, vaccine effectiveness).",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "file",
            "id": 384,
            "workflow_id": 162,
            "is_prerequisite": True
        },
        {
            "name": "UserInputTopic",
            "description": "The topic provided by the user or fetched from predefined data.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 385,
            "workflow_id": 163,
            "is_prerequisite": True
        },
        {
            "name": "AIModelEndpoint",
            "description": "The endpoint of the AI model API, e.g., Hugging Face transformers.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 386,
            "workflow_id": 163,
            "is_prerequisite": True
        },
        {
            "name": "APIKey",
            "description": "The API key required for accessing the AI model API.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 387,
            "workflow_id": 163,
            "is_prerequisite": True
        },
        {
            "name": "OutputFilePath",
            "description": "The file path where the generated outline will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 388,
            "workflow_id": 163,
            "is_prerequisite": True
        },
        {
            "name": "topics",
            "description": "A list of topics the user is studying.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 389,
            "workflow_id": 164,
            "is_prerequisite": True
        },
        {
            "name": "performance_data",
            "description": "User performance data scores, with keys as topic names and values as lists of scores for each attempt on that topic.",
            "data_type": "typing.Dict[str, typing.List[int]]",
            "format": "field",
            "id": 390,
            "workflow_id": 164,
            "is_prerequisite": True
        },
        {
            "name": "score_threshold",
            "description": "The minimum score below which a topic is considered difficult for the user.",
            "data_type": "int",
            "format": "field",
            "id": 391,
            "workflow_id": 164,
            "is_prerequisite": True
        },
        {
            "name": "problem_difficulty_range",
            "description": "A tuple representing the range of problem difficulty levels (e.g., (1, 5)).",
            "data_type": "tuple[int, int]",
            "format": "field",
            "id": 392,
            "workflow_id": 164,
            "is_prerequisite": True
        },
        {
            "name": "study_guide_output_path",
            "description": "The file path where the generated adaptive study guide will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 393,
            "workflow_id": 164,
            "is_prerequisite": True
        },
        {
            "name": "python_library_path",
            "description": "Path to the Python library containing docstrings.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 394,
            "workflow_id": 165,
            "is_prerequisite": True
        },
        {
            "name": "test_cases_output_dir",
            "description": "Directory where generated test cases will be saved.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 395,
            "workflow_id": 165,
            "is_prerequisite": True
        },
        {
            "name": "test_report_output_path",
            "description": "Path to save the test report.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 396,
            "workflow_id": 165,
            "is_prerequisite": True
        },
        {
            "name": "pytest_config_file",
            "description": "Path to a pytest configuration file (if any).",
            "data_type": "builtins.str",
            "format": "file",
            "id": 397,
            "workflow_id": 165,
            "is_prerequisite": True
        },
        {
            "name": "openapi_yaml_file",
            "description": "The file path of the OpenAPI YAML file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 398,
            "workflow_id": 166,
            "is_prerequisite": True
        },
        {
            "name": "output_md_file",
            "description": "The file path where the generated markdown documentation will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 399,
            "workflow_id": 166,
            "is_prerequisite": True
        },
        {
            "name": "openapi_version",
            "description": "The version of the OpenAPI specification used in the input YAML file.",
            "data_type": "typing.Union[builtins.str, None]",
            "format": "field",
            "id": 400,
            "workflow_id": 166,
            "is_prerequisite": True
        },
        {
            "name": "error_messages",
            "description": "A list of error messages to be thrown during validation and processing.",
            "data_type": "typing.List[builtins str]",
            "format": "field",
            "id": 401,
            "workflow_id": 166,
            "is_prerequisite": True
        },
        {
            "name": "TopicsData",
            "description": "Raw data containing topics and other relevant information.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 402,
            "workflow_id": 167,
            "is_prerequisite": True
        },
        {
            "name": "DesiredOutcomes",
            "description": "List of desired outcomes for the generated lessons.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 403,
            "workflow_id": 167,
            "is_prerequisite": True
        },
        {
            "name": "UserFeedback",
            "description": "Gathered user feedback on generated lessons.",
            "data_type": "typing.List[typing.Dict[str, str]]",
            "format": "field",
            "id": 404,
            "workflow_id": 167,
            "is_prerequisite": True
        },
        {
            "name": "LessonStructureTemplate",
            "description": "Template defining the structure of each lesson.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "file",
            "id": 405,
            "workflow_id": 167,
            "is_prerequisite": True
        },
        {
            "name": "sensor_ids",
            "description": "List of sensor IDs to collect data from.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 406,
            "workflow_id": 168,
            "is_prerequisite": True
        },
        {
            "name": "api_credentials",
            "description": "Dictionary containing API credentials (e.g., username, password, token).",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 407,
            "workflow_id": 168,
            "is_prerequisite": True
        },
        {
            "name": "collection_duration",
            "description": "Duration for which data should be collected.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 408,
            "workflow_id": 168,
            "is_prerequisite": True
        },
        {
            "name": "sampling_interval",
            "description": "Interval at which sensor data should be read.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 409,
            "workflow_id": 168,
            "is_prerequisite": True
        },
        {
            "name": "output_file_path",
            "description": "File path where collected and cleaned data will be stored.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 410,
            "workflow_id": 168,
            "is_prerequisite": True
        },
        {
            "name": "json_api_endpoint",
            "description": "Endpoint URL for the API providing user information in JSON format.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 411,
            "workflow_id": 169,
            "is_prerequisite": True
        },
        {
            "name": "xml_api_endpoint",
            "description": "Endpoint URL for the API expecting user data in XML format.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 412,
            "workflow_id": 169,
            "is_prerequisite": True
        },
        {
            "name": "json_file_path",
            "description": "File path to a JSON file containing user information.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 413,
            "workflow_id": 169,
            "is_prerequisite": True
        },
        {
            "name": "xml_file_path",
            "description": "File path to an XML file containing user data.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 414,
            "workflow_id": 169,
            "is_prerequisite": True
        },
        {
            "name": "questions_data",
            "description": "List of questions with their respective answers",
            "data_type": "typing.List[typing.Tuple[str, typing.Any]]",
            "format": "field",
            "id": 415,
            "workflow_id": 170,
            "is_prerequisite": True
        },
        {
            "name": "quiz_output_file_path",
            "description": "File path to save the generated quiz string",
            "data_type": "builtins.str",
            "format": "file",
            "id": 416,
            "workflow_id": 170,
            "is_prerequisite": True
        },
        {
            "name": "questions",
            "description": "List of questions for the quiz.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 417,
            "workflow_id": 171,
            "is_prerequisite": True
        },
        {
            "name": "answers",
            "description": "Corresponding answers to the questions.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 418,
            "workflow_id": 171,
            "is_prerequisite": True
        },
        {
            "name": "csv_files",
            "description": "List of CSV files containing study data.",
            "data_type": "typing.List[builtins.str]",
            "format": "file",
            "id": 419,
            "workflow_id": 172,
            "is_prerequisite": True
        },
        {
            "name": "study_metadata",
            "description": "Metadata about each study, e.g., author, year.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "id": 420,
            "workflow_id": 172,
            "is_prerequisite": True
        },
        {
            "name": "effect_size_method",
            "description": "Method used to calculate effect size (e.g., Cohen's d, OR).",
            "data_type": "builtins.str",
            "id": 421,
            "workflow_id": 172,
            "is_prerequisite": True
        },
        {
            "name": "confidence_level",
            "description": "Confidence level for the pooled effect size (e.g., 95%).",
            "data_type": "typing.Union[builtins.float, builtins.int]",
            "id": 422,
            "workflow_id": 172,
            "is_prerequisite": True
        },
        {
            "name": "output_pdf_path",
            "description": "Path where the summary report PDF will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 423,
            "workflow_id": 172,
            "is_prerequisite": True
        },
        {
            "name": "Initial_Infected_Population",
            "description": "The initial number of infected individuals in the population.",
            "data_type": "typing.Int",
            "format": "field",
            "id": 424,
            "workflow_id": 173,
            "is_prerequisite": True
        },
        {
            "name": "Recovery_Rate",
            "description": "The rate at which infected individuals recover from the epidemic.",
            "data_type": "float",
            "format": "field",
            "id": 425,
            "workflow_id": 173,
            "is_prerequisite": True
        },
        {
            "name": "Infection_Rate",
            "description": "The rate at which susceptible individuals become infected.",
            "data_type": "float",
            "format": "field",
            "id": 426,
            "workflow_id": 173,
            "is_prerequisite": True
        },
        {
            "name": "Population_Size",
            "description": "The total size of the population being simulated.",
            "data_type": "typing.Int",
            "format": "field",
            "id": 427,
            "workflow_id": 173,
            "is_prerequisite": True
        },
        {
            "name": "Epidemic_Type",
            "description": "The type of epidemic to simulate (e.g., SIR, SEIR).",
            "data_type": "builtins.str",
            "format": "field",
            "id": 428,
            "workflow_id": 173,
            "is_prerequisite": True
        },
        {
            "name": "Input_Parameters_File",
            "description": "A file containing input parameters for the simulation.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 429,
            "workflow_id": 173,
            "is_prerequisite": True
        },
        {
            "name": "input_dataframe",
            "description": "The dataframe to be transformed and logged.",
            "data_type": "pandas.DataFrame",
            "id": 430,
            "workflow_id": 174,
            "is_prerequisite": True
        },
        {
            "name": "transformation_details",
            "description": "A dictionary that will store the details of each transformation.",
            "data_type": "typing.Dict[str, typing.Any]",
            "id": 431,
            "workflow_id": 174,
            "is_prerequisite": True
        },
        {
            "name": "News Sources",
            "description": "List of news sources to fetch articles from.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 432,
            "workflow_id": 175,
            "is_prerequisite": True
        },
        {
            "name": "API Credentials",
            "description": "Credentials for accessing news source APIs.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 433,
            "workflow_id": 175,
            "is_prerequisite": True
        },
        {
            "name": "Email Configuration",
            "description": "Configuration for sending daily digest via email.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 434,
            "workflow_id": 175,
            "is_prerequisite": True
        },
        {
            "name": "Summarization Library",
            "description": "Text summarization library to use (e.g. Gensim or sumy).",
            "data_type": "str",
            "format": "field",
            "id": 435,
            "workflow_id": 175,
            "is_prerequisite": True
        },
        {
            "name": "Daily Digest File Path",
            "description": "File path where daily digest will be stored before sending.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 436,
            "workflow_id": 175,
            "is_prerequisite": True
        },
        {
            "name": "Dataset",
            "description": "The time series dataset to be used for prediction.",
            "data_type": "pandas.DataFrame",
            "format": "file",
            "id": 437,
            "workflow_id": 176,
            "is_prerequisite": True
        },
        {
            "name": "Target_Column_Name",
            "description": "The name of the column in the dataset that represents the target variable (the value to predict).",
            "data_type": "builtins.str",
            "format": "field",
            "id": 438,
            "workflow_id": 176,
            "is_prerequisite": True
        },
        {
            "name": "Features_List",
            "description": "A list of feature columns to be used for prediction.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 439,
            "workflow_id": 176,
            "is_prerequisite": True
        },
        {
            "name": "Imputation_Method",
            "description": "The method to use for imputing missing values (e.g. 'mean', 'median', 'mode'). Default is None (no imputation).",
            "data_type": "builtins.str",
            "format": "field",
            "id": 440,
            "workflow_id": 176,
            "is_prerequisite": True
        },
        {
            "name": "Scaler_Type",
            "description": "The type of scaler to use for normalizing data. Options include 'StandardScaler', 'MinMaxScaler'. Default is None (no scaling).",
            "data_type": "builtins.str",
            "format": "field",
            "id": 441,
            "workflow_id": 176,
            "is_prerequisite": True
        },
        {
            "name": "ARIMA_p_d_q",
            "description": "A tuple representing the (p, d, q) parameters for ARIMA model. Default is None (not used).",
            "data_type": "typing.Tuple[int, int, int]",
            "format": "field",
            "id": 442,
            "workflow_id": 176,
            "is_prerequisite": True
        },
        {
            "name": "LSTM_Units",
            "description": "The number of units in the LSTM layer.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 443,
            "workflow_id": 176,
            "is_prerequisite": True
        },
        {
            "name": "LSTM_Layers",
            "description": "The number of LSTM layers to stack.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 444,
            "workflow_id": 176,
            "is_prerequisite": True
        },
        {
            "name": "initial_dialogue",
            "description": "The initial dialogue text to be displayed before the branching choices.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 445,
            "workflow_id": 177,
            "is_prerequisite": True
        },
        {
            "name": "choices_dict",
            "description": "A dictionary where keys are choices and values are corresponding responses. Keys should match user input exactly.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 446,
            "workflow_id": 177,
            "is_prerequisite": True
        },
        {
            "name": "initial_prior",
            "description": "Initial beliefs about the coin's bias.",
            "data_type": "typing.Dict[str, typing.Tuple[float, float]]",
            "format": "field",
            "id": 447,
            "workflow_id": 178,
            "is_prerequisite": True
        },
        {
            "name": "observed_flip_results",
            "description": "Sequence of observed flip results (heads or tails).",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 448,
            "workflow_id": 178,
            "is_prerequisite": True
        },
        {
            "name": "InitialString",
            "description": "The initial string for the L-system.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 449,
            "workflow_id": 179,
            "is_prerequisite": True
        },
        {
            "name": "Rules",
            "description": "A dictionary mapping symbols to their replacements in the L-system.",
            "data_type": "typing.Dict[builtins.str, typing.Union[builtins.str, typing.List[builtins.str]]]",
            "format": "field",
            "id": 450,
            "workflow_id": 179,
            "is_prerequisite": True
        },
        {
            "name": "Iterations",
            "description": "The number of iterations to perform on the L-system.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 451,
            "workflow_id": 179,
            "is_prerequisite": True
        },
        {
            "name": "Angle",
            "description": "The angle by which to turn after each instruction in degrees.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 452,
            "workflow_id": 179,
            "is_prerequisite": True
        },
        {
            "name": "FilenamePrefix",
            "description": "A prefix for the output SVG filenames.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 453,
            "workflow_id": 179,
            "is_prerequisite": True
        },
        {
            "name": "OutputDirectory",
            "description": "The directory where the SVG files will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 454,
            "workflow_id": 179,
            "is_prerequisite": True
        },
        {
            "name": "User Performance Data",
            "description": "Dictionary containing subjects as keys and lists of tuples representing user attempts at practice problems.",
            "data_type": "typing.Dict[builtins.str, typing.List[typing.Tuple[int, bool]]]",
            "format": "field",
            "id": 455,
            "workflow_id": 180,
            "is_prerequisite": True
        },
        {
            "name": "Difficulty Level Data (optional)",
            "description": "A dictionary mapping subjects to their corresponding difficulty levels.",
            "data_type": "typing.Dict[builtins.str, int]",
            "format": "field",
            "id": 456,
            "workflow_id": 180,
            "is_prerequisite": True
        },
        {
            "name": "User Preferences (optional)",
            "description": "A dictionary containing user preferences such as preferred learning pace and style.",
            "data_type": "typing.Dict[builtins.str, builtins.str]",
            "format": "field",
            "id": 457,
            "workflow_id": 180,
            "is_prerequisite": True
        },
        {
            "name": "api_dictionary",
            "description": "A dictionary containing API endpoints, methods, parameters and responses.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 458,
            "workflow_id": 181,
            "is_prerequisite": True
        },
        {
            "name": "input_function",
            "description": "The Python function to be refactored.",
            "data_type": "typing.Callable[[], typing.Any]",
            "format": "field",
            "id": 459,
            "workflow_id": 182,
            "is_prerequisite": True
        },
        {
            "name": "function_path",
            "description": "Path of the file containing the input function if it's in a separate file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 460,
            "workflow_id": 182,
            "is_prerequisite": True
        },
        {
            "name": "test_cases",
            "description": "A list of tuples, where each tuple contains an argument to pass to the function and the expected return value for validation testing.",
            "data_type": "typing.List[typing.Tuple[typing.Any, typing.Any]]",
            "format": "field",
            "id": 461,
            "workflow_id": 182,
            "is_prerequisite": True
        },
        {
            "name": "refactoring_tasks",
            "description": "A list of refactoring tasks to perform on the function (e.g., ['conditional_rewrite', 'duplicate_merge']).",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 462,
            "workflow_id": 182,
            "is_prerequisite": True
        },
        {
            "name": "packages_list",
            "description": "A list of packages with their dependencies in a predictable format.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 463,
            "workflow_id": 183,
            "is_prerequisite": True
        },
        {
            "name": "input_file_path",
            "description": "The file path to the input data containing the packages and their dependencies.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 464,
            "workflow_id": 183,
            "is_prerequisite": True
        },
        {
            "name": "task_sequence",
            "description": "The task sequence as a list of strings.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 465,
            "workflow_id": 184,
            "is_prerequisite": True
        },
        {
            "name": "tool_database",
            "description": "A dictionary where keys are tools and values are their parameters.",
            "data_type": "typing.Dict[str, typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 466,
            "workflow_id": 184,
            "is_prerequisite": True
        },
        {
            "name": "Package_List",
            "description": "List of software packages to analyze.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 467,
            "workflow_id": 185,
            "is_prerequisite": True
        },
        {
            "name": "Dependencies_File",
            "description": "Path to the file containing dependency information.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 468,
            "workflow_id": 185,
            "is_prerequisite": True
        },
        {
            "name": "Compatibility_Rules",
            "description": "Rules or constraints for building valid graphs.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 469,
            "workflow_id": 185,
            "is_prerequisite": True
        },
        {
            "name": "InputNumbers",
            "description": "A list of integers to test the prime checker function on.",
            "data_type": "typing.List[builtins.int]",
            "format": "field",
            "id": 470,
            "workflow_id": 186,
            "is_prerequisite": True
        },
        {
            "name": "ExpectedResults",
            "description": "The expected boolean results for the input numbers.",
            "data_type": "typing.Dict[builtins.int, bool]",
            "format": "field",
            "id": 471,
            "workflow_id": 186,
            "is_prerequisite": True
        },
        {
            "name": "input_file",
            "description": "The file path of the input code file to be refactored.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 472,
            "workflow_id": 187,
            "is_prerequisite": True
        },
        {
            "name": "variable_mapping",
            "description": "A mapping from old variable names to new variable names.",
            "data_type": "typing.Dict[builtins.str, builtins.str]",
            "format": "field",
            "id": 473,
            "workflow_id": 187,
            "is_prerequisite": True
        },
        {
            "name": "refactoring_strategies",
            "description": "A list of refactoring strategies to apply (e.g., ['rename_variables', 'extract_methods', 'convert_conditional_statements']).",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 474,
            "workflow_id": 187,
            "is_prerequisite": True
        },
        {
            "name": "output_file",
            "description": "The file path where the refactored code will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 475,
            "workflow_id": 187,
            "is_prerequisite": True
        },
        {
            "name": "input_files",
            "description": "List of input CSV files containing numeric data with potential errors",
            "data_type": "typing.List[builtins.str]",
            "format": "file",
            "id": 476,
            "workflow_id": 188,
            "is_prerequisite": True
        },
        {
            "name": "error_thresholds",
            "description": "Maximum allowable percentage of erroneous entries per file",
            "data_type": "typing.Dict[builtins.str, builtins.float]",
            "format": "field",
            "id": 477,
            "workflow_id": 188,
            "is_prerequisite": True
        },
        {
            "name": "outlier_strategy",
            "description": "Strategy for handling outliers (e.g., 'cap', 'remove', 'replace')",
            "data_type": "builtins.str",
            "format": "field",
            "id": 478,
            "workflow_id": 188,
            "is_prerequisite": True
        },
        {
            "name": "imputation_method",
            "description": "Method for data imputation (e.g., 'mean', 'median', 'mode')",
            "data_type": "builtins.str",
            "format": "field",
            "id": 479,
            "workflow_id": 188,
            "is_prerequisite": True
        },
        {
            "name": "validation_rules",
            "description": "Predefined rules for validating processed data (e.g., min/max limits)",
            "data_type": "typing.Dict[builtins.str, typing.Tuple[builtins.float, builtins.float]]",
            "format": "field",
            "id": 480,
            "workflow_id": 188,
            "is_prerequisite": True
        },
        {
            "name": "self_repair_mechanisms",
            "description": "List of self-repair mechanisms to implement upon validation failure",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 481,
            "workflow_id": 188,
            "is_prerequisite": True
        },
        {
            "name": "api_endpoint_url",
            "description": "The URL of the API endpoint to lint.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 482,
            "workflow_id": 189,
            "is_prerequisite": True
        },
        {
            "name": "linting_rules",
            "description": "A predefined set of rules for linting the API endpoints.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 483,
            "workflow_id": 189,
            "is_prerequisite": True
        },
        {
            "name": "access_rights",
            "description": "Access rights to read the API endpoint files.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 484,
            "workflow_id": 189,
            "is_prerequisite": True
        },
        {
            "name": "file_path",
            "description": "The file path of the API endpoint codebase.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 485,
            "workflow_id": 189,
            "is_prerequisite": True
        },
        {
            "name": "Dataset",
            "description": "The input data for the linear regression analysis.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 486,
            "workflow_id": 190,
            "is_prerequisite": True
        },
        {
            "name": "Target_Column",
            "description": "The column in the dataset that represents the target variable.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 487,
            "workflow_id": 190,
            "is_prerequisite": True
        },
        {
            "name": "number_of_clusters",
            "description": "Number of clusters for Gaussian Mixture Models.",
            "data_type": "typing.int",
            "format": "field",
            "id": 488,
            "workflow_id": 191,
            "is_prerequisite": True
        },
        {
            "name": "means",
            "description": "Means of the synthetic data from multiple Gaussian distributions.",
            "data_type": "typing.List[typing.Tuple[float, float]]",
            "format": "field",
            "id": 489,
            "workflow_id": 191,
            "is_prerequisite": True
        },
        {
            "name": "covariances",
            "description": "Covariances of the synthetic data from multiple Gaussian distributions.",
            "data_type": "typing.List[typing.Tuple[float, float]]",
            "format": "field",
            "id": 490,
            "workflow_id": 191,
            "is_prerequisite": True
        },
        {
            "name": "number_of_samples_per_cluster",
            "description": "Number of samples to generate per cluster.",
            "data_type": "typing.List[int]",
            "format": "field",
            "id": 491,
            "workflow_id": 191,
            "is_prerequisite": True
        },
        {
            "name": "Topics",
            "description": "List of topics for the lesson plan",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 492,
            "workflow_id": 192,
            "is_prerequisite": True
        },
        {
            "name": "Desired_Outcomes",
            "description": "List of desired outcomes for the lesson plan",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 493,
            "workflow_id": 192,
            "is_prerequisite": True
        },
        {
            "name": "tasks_list",
            "description": "A list of tasks with their respective contexts",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 494,
            "workflow_id": 193,
            "is_prerequisite": True
        },
        {
            "name": "tools_data",
            "description": "A dictionary containing tools as keys and their processing time and output quality as values",
            "data_type": "typing.Dict[str, typing.Tuple[float, float]]",
            "format": "field",
            "id": 495,
            "workflow_id": 193,
            "is_prerequisite": True
        },
        {
            "name": "user_priorities",
            "description": "A dictionary where keys are 'processing_time' or 'output_quality', and values are their respective priorities (0-1)",
            "data_type": "typing.Dict[str, float]",
            "format": "field",
            "id": 496,
            "workflow_id": 193,
            "is_prerequisite": True
        },
        {
            "name": "access_rights",
            "description": "A list of tuples containing file paths and required access rights",
            "data_type": "typing.List[tuple[str, str]]",
            "format": "field",
            "id": 497,
            "workflow_id": 193,
            "is_prerequisite": True
        },
        {
            "name": "Topics",
            "description": "A list of topics for the lesson plan.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 498,
            "workflow_id": 194,
            "is_prerequisite": True
        },
        {
            "name": "DesiredOutcomes",
            "description": "A dictionary where keys are topics and values are desired outcomes.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 499,
            "workflow_id": 194,
            "is_prerequisite": True
        },
        {
            "name": "package_manager_files",
            "description": "List of paths to package manager files (requirements.txt, package.json)",
            "data_type": "typing.List[str]",
            "format": "file",
            "id": 500,
            "workflow_id": 195,
            "is_prerequisite": True
        },
        {
            "name": "ExistingWorkflow",
            "description": "Current workflow with tasks and dependencies.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 501,
            "workflow_id": 196,
            "is_prerequisite": True
        },
        {
            "name": "AIModelPath",
            "description": "Path to the AI model used for generating tasks.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 502,
            "workflow_id": 196,
            "is_prerequisite": True
        },
        {
            "name": "NewTasksLimit",
            "description": "Maximum number of new tasks to generate.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 503,
            "workflow_id": 196,
            "is_prerequisite": True
        },
        {
            "name": "ReportOutputPath",
            "description": "Path where the report will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 504,
            "workflow_id": 196,
            "is_prerequisite": True
        },
        {
            "name": "rss_feeds",
            "description": "List of RSS feed URLs to aggregate news articles from.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 505,
            "workflow_id": 198,
            "is_prerequisite": True
        },
        {
            "name": "output_file_path",
            "description": "File path where the daily newsletter will be saved in markdown format.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 506,
            "workflow_id": 198,
            "is_prerequisite": True
        },
        {
            "name": "input_image_path",
            "description": "Path to the input color image file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 507,
            "workflow_id": 199,
            "is_prerequisite": True
        },
        {
            "name": "label_matrix",
            "description": "Matrix where each pixel's value represents its corresponding region ID.",
            "data_type": "typing.NumpyNDArray[typing.Union[builtins.int, builtins.float]]",
            "format": "field",
            "id": 508,
            "workflow_id": 199,
            "is_prerequisite": True
        },
        {
            "name": "segmentation_parameters",
            "description": "Parameters for image segmentation (e.g., color similarity threshold).",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 509,
            "workflow_id": 199,
            "is_prerequisite": True
        },
        {
            "name": "morphological_operations",
            "description": "Operations to refine segmented regions (e.g., dilation, erosion).",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 510,
            "workflow_id": 199,
            "is_prerequisite": True
        },
        {
            "name": "min_region_size",
            "description": "Minimum size of a region to consider it as valid (to remove noise-like regions).",
            "data_type": "builtins.int",
            "format": "field",
            "id": 511,
            "workflow_id": 199,
            "is_prerequisite": True
        },
        {
            "name": "Operations",
            "description": "The basic mathematical operations (addition, subtraction, multiplication) to be performed.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 512,
            "workflow_id": 200,
            "is_prerequisite": True
        },
        {
            "name": "ErrorHandlingConfig",
            "description": "Configuration for error handling mechanisms, e.g., retry limits, timeout values.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 513,
            "workflow_id": 200,
            "is_prerequisite": True
        },
        {
            "name": "InputData",
            "description": "Sample input data to test the functionality of generated functions.",
            "data_type": "builtins.list",
            "format": "field",
            "id": 514,
            "workflow_id": 200,
            "is_prerequisite": True
        },
        {
            "name": "LogFilePath",
            "description": "The path to the log file for storing error messages and recovery information.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 515,
            "workflow_id": 200,
            "is_prerequisite": True
        },
        {
            "name": "input_file_path",
            "description": "The file path of the input OpenAPI/Swagger document.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 516,
            "workflow_id": 201,
            "is_prerequisite": True
        },
        {
            "name": "output_file_path",
            "description": "The desired file path for the generated markdown documentation.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 517,
            "workflow_id": 201,
            "is_prerequisite": True
        },
        {
            "name": "input_format",
            "description": "The format of the input OpenAPI/Swagger document (json or yaml).",
            "data_type": "typing.Union[builtins.str, builtins.None]",
            "format": "field",
            "id": 518,
            "workflow_id": 201,
            "is_prerequisite": True
        },
        {
            "name": "error_handling_strategy",
            "description": "The strategy to follow for error handling during execution.",
            "data_type": "typing.Callable[[], None]",
            "format": "field",
            "id": 519,
            "workflow_id": 201,
            "is_prerequisite": True
        },
        {
            "name": "topics",
            "description": "A list of complex topics to be broken down into simpler subtopics.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 520,
            "workflow_id": 202,
            "is_prerequisite": True
        },
        {
            "name": "desired_outcomes",
            "description": "A list of desired learning outcomes for each topic.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 521,
            "workflow_id": 202,
            "is_prerequisite": True
        },
        {
            "name": "access_rights",
            "description": "File access rights if the data is provided in a file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 522,
            "workflow_id": 202,
            "is_prerequisite": True
        },
        {
            "name": "file_path",
            "description": "Path to the file containing topics or desired outcomes if applicable.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 523,
            "workflow_id": 202,
            "is_prerequisite": True
        },
        {
            "name": "input_json",
            "description": "The hierarchical data structure in JSON format.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 524,
            "workflow_id": 203,
            "is_prerequisite": True
        },
        {
            "name": "output_taxonomy",
            "description": "The equivalent taxonomy represented as a list of tuples.",
            "data_type": "typing.List[tuple[str, typing.Union[str, None]]]",
            "format": "field",
            "id": 525,
            "workflow_id": 203,
            "is_prerequisite": True
        },
        {
            "name": "studies_data",
            "description": "Data containing information about each study",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 526,
            "workflow_id": 204,
            "is_prerequisite": True
        },
        {
            "name": "study_files_path",
            "description": "Path to the directory containing all study files",
            "data_type": "builtins.str",
            "format": "file",
            "id": 527,
            "workflow_id": 204,
            "is_prerequisite": True
        },
        {
            "name": "number_of_samples",
            "description": "The number of random points to generate for sampling.",
            "data_type": "typing.Int",
            "format": "field",
            "id": 528,
            "workflow_id": 205,
            "is_prerequisite": True
        },
        {
            "name": "random_points_file",
            "description": "A file containing previously generated random points in the format (x, y).",
            "data_type": "builtins.str",
            "format": "file",
            "id": 529,
            "workflow_id": 205,
            "is_prerequisite": True
        },
        {
            "name": "input_packages",
            "description": "Dictionary representing software packages with their dependencies.",
            "data_type": "typing.Dict[builtins.str, typing.List[builtins.str]]",
            "format": "field",
            "id": 530,
            "workflow_id": 206,
            "is_prerequisite": True
        },
        {
            "name": "InputText",
            "description": "The text from which entities and relationships will be extracted.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 531,
            "workflow_id": 207,
            "is_prerequisite": True
        },
        {
            "name": "PredefinedOntology",
            "description": "A predefined ontology or NER model used for entity classification.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 532,
            "workflow_id": 207,
            "is_prerequisite": True
        },
        {
            "name": "RelationshipPatterns",
            "description": "Predefined relationship patterns to match in the text.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 533,
            "workflow_id": 207,
            "is_prerequisite": True
        },
        {
            "name": "InputFilePath",
            "description": "The file path of the input text if it's provided as a file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 534,
            "workflow_id": 207,
            "is_prerequisite": True
        },
        {
            "name": "input_code_file",
            "description": "The code file to be reviewed.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 535,
            "workflow_id": 209,
            "is_prerequisite": True
        },
        {
            "name": "Topics",
            "description": "List of topics or desired outcomes for the lesson plan.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 536,
            "workflow_id": 210,
            "is_prerequisite": True
        },
        {
            "name": "Subtopics",
            "description": "Dictionaries containing topic as key and list of subtopics as value.",
            "data_type": "typing.Dict[str, typing.List[str]]",
            "format": "field",
            "id": 537,
            "workflow_id": 210,
            "is_prerequisite": True
        },
        {
            "name": "Objectives",
            "description": "Dictionaries containing subtopic as key and learning objective as value.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 538,
            "workflow_id": 210,
            "is_prerequisite": True
        },
        {
            "name": "Resources",
            "description": "File paths or URLs of resources relevant to each topic or subtopic.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 539,
            "workflow_id": 210,
            "is_prerequisite": True
        },
        {
            "name": "trained_model",
            "description": "The trained machine learning model used for prediction and explanation.",
            "data_type": "sklearn.base.BaseEstimator",
            "format": "field",
            "id": 540,
            "workflow_id": 211,
            "is_prerequisite": True
        },
        {
            "name": "X_test",
            "description": "Test data to generate local interpretations.",
            "data_type": "numpy.ndarray",
            "format": "field",
            "id": 541,
            "workflow_id": 211,
            "is_prerequisite": True
        },
        {
            "name": "y_test",
            "description": "True labels for the test data used to generate local interpretations.",
            "data_type": "numpy.ndarray",
            "format": "field",
            "id": 542,
            "workflow_id": 211,
            "is_prerequisite": True
        },
        {
            "name": "model_file_path",
            "description": "The file path to save feature importance.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 543,
            "workflow_id": 211,
            "is_prerequisite": True
        },
        {
            "name": "XAI_explanations_dir",
            "description": "The directory to save XAI explanations.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 544,
            "workflow_id": 211,
            "is_prerequisite": True
        },
        {
            "name": "python_code_input",
            "description": "The string containing Python code for review.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 545,
            "workflow_id": 213,
            "is_prerequisite": True
        },
        {
            "name": "test_files",
            "description": "A list of file paths to the test cases.",
            "data_type": "typing.List[builtins.str]",
            "format": "file",
            "id": 546,
            "workflow_id": 213,
            "is_prerequisite": True
        },
        {
            "name": "coverage_threshold",
            "description": "The minimum percentage of code that should be covered by tests. Used to generate coverage report.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 547,
            "workflow_id": 213,
            "is_prerequisite": True
        },
        {
            "name": "dataset",
            "description": "The dataset to be processed and mitigated for bias.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 548,
            "workflow_id": 214,
            "is_prerequisite": True
        },
        {
            "name": "target_variable",
            "description": "The name of the target variable in the dataset.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 549,
            "workflow_id": 214,
            "is_prerequisite": True
        },
        {
            "name": "protected_variables",
            "description": "A list of protected variables (sensitive attributes) in the dataset.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 550,
            "workflow_id": 214,
            "is_prerequisite": True
        },
        {
            "name": "Chessboard Dimension",
            "description": "The size of the chessboard (NxN)",
            "data_type": "typing.Tuple[int, int]",
            "format": "field",
            "id": 551,
            "workflow_id": 215,
            "is_prerequisite": True
        },
        {
            "name": "Center Position",
            "description": "The coordinates of the center on an NxN board",
            "data_type": "typing.Tuple[int, int]",
            "format": "field",
            "id": 552,
            "workflow_id": 215,
            "is_prerequisite": True
        },
        {
            "name": "Number of Simulations",
            "description": "The total number of random walks to simulate",
            "data_type": "int",
            "format": "field",
            "id": 553,
            "workflow_id": 215,
            "is_prerequisite": True
        },
        {
            "name": "questions",
            "description": "List of questions with their difficulties.",
            "data_type": "typing.List[dict]",
            "format": "field",
            "id": 554,
            "workflow_id": 216,
            "is_prerequisite": True
        },
        {
            "name": "user_answers",
            "description": "List to store user's answers.",
            "data_type": "typing.List[builtins.bool]",
            "format": "field",
            "id": 555,
            "workflow_id": 216,
            "is_prerequisite": True
        },
        {
            "name": "correct_streak",
            "description": "Track the number of consecutive correct/incorrect answers.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 556,
            "workflow_id": 216,
            "is_prerequisite": True
        },
        {
            "name": "tool_dataset",
            "description": "A predefined dataset of tools and their functionalities.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 557,
            "workflow_id": 217,
            "is_prerequisite": True
        },
        {
            "name": "user_task",
            "description": "The user's current task step in plain language.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 558,
            "workflow_id": 217,
            "is_prerequisite": True
        },
        {
            "name": "input_image_path",
            "description": "Path to the input image file",
            "data_type": "builtins.str",
            "format": "file",
            "id": 559,
            "workflow_id": 1,
            "is_prerequisite": False
        },
        {
            "name": "depth_map_path",
            "description": "Path to the depth map file",
            "data_type": "builtins.str",
            "format": "file",
            "id": 560,
            "workflow_id": 1,
            "is_prerequisite": False
        },
        {
            "name": "model_name",
            "description": "Name for the generated 3D model",
            "data_type": "builtins.str",
            "format": "field",
            "id": 561,
            "workflow_id": 1,
            "is_prerequisite": False
        },
        {
            "name": "texture_resolution",
            "description": "Resolution for texture mapping (tuple of two integers)",
            "data_type": "typing.Tuple[builtins.int, builtins.int]",
            "format": "field",
            "id": 562,
            "workflow_id": 1,
            "is_prerequisite": False
        },
        {
            "name": "cleanup_threshold",
            "description": "Threshold value for mesh cleanup operations",
            "data_type": "builtins.float",
            "format": "field",
            "id": 563,
            "workflow_id": 1,
            "is_prerequisite": False
        },
        {
            "name": "ImageSources",
            "description": "Paths or URLs of the images to process.",
            "data_type": "builtins.List[str]",
            "format": "file",
            "id": 564,
            "workflow_id": 2,
            "is_prerequisite": False
        },
        {
            "name": "OutputDirectory",
            "description": "Path where processed images and analysis results will be saved.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 565,
            "workflow_id": 2,
            "is_prerequisite": False
        },
        {
            "name": "ColorAnalysisMethod",
            "description": "Method for color analysis (e.g., 'RGB', 'HSV').",
            "data_type": "builtins.str",
            "format": "field",
            "id": 566,
            "workflow_id": 2,
            "is_prerequisite": False
        },
        {
            "name": "EdgeDetectionAlgorithm",
            "description": "Algorithm for edge detection (e.g., 'Canny', 'Sobel').",
            "data_type": "builtins.str",
            "format": "field",
            "id": 567,
            "workflow_id": 2,
            "is_prerequisite": False
        },
        {
            "name": "PCAComponents",
            "description": "Number of components to retain after applying PCA.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 568,
            "workflow_id": 2,
            "is_prerequisite": False
        },
        {
            "name": "Project Brief",
            "description": "Details about the video project.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 569,
            "workflow_id": 4,
            "is_prerequisite": False
        },
        {
            "name": "Client Requirements",
            "description": "Specific requirements from the client.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 570,
            "workflow_id": 4,
            "is_prerequisite": False
        },
        {
            "name": "Concept Development",
            "description": "Idea generation and scriptwriting documents.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 571,
            "workflow_id": 4,
            "is_prerequisite": False
        },
        {
            "name": "Storyboard Images",
            "description": "Images representing the shots for the video.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 572,
            "workflow_id": 4,
            "is_prerequisite": False
        },
        {
            "name": "Video Footage",
            "description": "Raw video files.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 573,
            "workflow_id": 4,
            "is_prerequisite": False
        },
        {
            "name": "Audio Files",
            "description": "Raw audio recordings.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 574,
            "workflow_id": 4,
            "is_prerequisite": False
        },
        {
            "name": "Color Grading Profile",
            "description": "Specific color grading instructions or profile.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 575,
            "workflow_id": 4,
            "is_prerequisite": False
        },
        {
            "name": "Production Schedule",
            "description": "Detailed production schedule including filming dates and locations.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 576,
            "workflow_id": 4,
            "is_prerequisite": False
        },
        {
            "name": "Dataset",
            "description": "The main dataset for training, validation, and testing.",
            "data_type": "typing.Any",
            "format": "file",
            "id": 577,
            "workflow_id": 5,
            "is_prerequisite": False
        },
        {
            "name": "TargetVariableName",
            "description": "The name of the target variable in the dataset.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 578,
            "workflow_id": 5,
            "is_prerequisite": False
        },
        {
            "name": "FeatureNames",
            "description": "A list of feature names in the dataset.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 579,
            "workflow_id": 5,
            "is_prerequisite": False
        },
        {
            "name": "TrainTestSplitRatio",
            "description": "The ratio for splitting data into training and testing sets (e.g., 0.8).",
            "data_type": "float",
            "format": "field",
            "id": 580,
            "workflow_id": 5,
            "is_prerequisite": False
        },
        {
            "name": "ValidationSplitRatio",
            "description": "The ratio for splitting the training set into training and validation sets (e.g., 0.2).",
            "data_type": "float",
            "format": "field",
            "id": 581,
            "workflow_id": 5,
            "is_prerequisite": False
        },
        {
            "name": "ModelSelectionCriteria",
            "description": "The criteria used to select the best performing model.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 582,
            "workflow_id": 5,
            "is_prerequisite": False
        },
        {
            "name": "ModelDeploymentPlatform",
            "description": "The platform where the trained model will be deployed (e.g., web server, cloud service).",
            "data_type": "builtins.str",
            "format": "field",
            "id": 583,
            "workflow_id": 5,
            "is_prerequisite": False
        },
        {
            "name": "FeedbackCollectionMethod",
            "description": "How user feedback will be collected and used for updating models.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 584,
            "workflow_id": 5,
            "is_prerequisite": False
        },
        {
            "name": "input_video",
            "description": "The video file to be dubbed.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 585,
            "workflow_id": 7,
            "is_prerequisite": False
        },
        {
            "name": "output_directory",
            "description": "The directory where the output files will be stored.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 586,
            "workflow_id": 7,
            "is_prerequisite": False
        },
        {
            "name": "language",
            "description": "The language of the dubbing (used in TTS).",
            "data_type": "builtins.str",
            "format": "field",
            "id": 587,
            "workflow_id": 7,
            "is_prerequisite": False
        },
        {
            "name": "voice_id",
            "description": "The voice ID to be used for dubbing (if applicable, depends on TTS engine).",
            "data_type": "builtins.int",
            "format": "field",
            "id": 588,
            "workflow_id": 7,
            "is_prerequisite": False
        },
        {
            "name": "AI_Model_Name",
            "description": "The name of the AI model to interact with.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 589,
            "workflow_id": 8,
            "is_prerequisite": False
        },
        {
            "name": "API_Key",
            "description": "The API key or authentication token for interacting with the AI model.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 590,
            "workflow_id": 8,
            "is_prerequisite": False
        },
        {
            "name": "Input_Prompt",
            "description": "The initial prompt to generate sample configuration data.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 591,
            "workflow_id": 8,
            "is_prerequisite": False
        },
        {
            "name": "Output_Validation_Criteria",
            "description": "A set of rules or conditions for validating the generated output.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 592,
            "workflow_id": 8,
            "is_prerequisite": False
        },
        {
            "name": "Output_Cleaning_Rules",
            "description": "Rules for cleaning and formatting the generated output.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 593,
            "workflow_id": 8,
            "is_prerequisite": False
        },
        {
            "name": "Sample_Data_Files",
            "description": "Paths to any sample data files needed for validation or testing.",
            "data_type": "typing.List[builtins.str]",
            "format": "file",
            "id": 594,
            "workflow_id": 8,
            "is_prerequisite": False
        },
        {
            "name": "code_submission",
            "description": "The code submission file to be reviewed.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 595,
            "workflow_id": 29,
            "is_prerequisite": False
        },
        {
            "name": "review_report",
            "description": "A dictionary containing all the findings of the review, including errors and suggestions.",
            "data_type": "typing.Dict[builtins.str, typing.List[str]]",
            "format": "field",
            "id": 596,
            "workflow_id": 29,
            "is_prerequisite": False
        },
        {
            "name": "natural_language_explanations",
            "description": "A list of natural language explanations for identified errors and improvements.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 597,
            "workflow_id": 29,
            "is_prerequisite": False
        },
        {
            "name": "topics_and_keywords",
            "description": "List of topics and keywords for content generation.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 598,
            "workflow_id": 30,
            "is_prerequisite": False
        },
        {
            "name": "initial_content_data",
            "description": "Existing content data for initial model training or fine-tuning.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 599,
            "workflow_id": 30,
            "is_prerequisite": False
        },
        {
            "name": "human_feedback",
            "description": "Human feedback on generated content for model improvement.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 600,
            "workflow_id": 30,
            "is_prerequisite": False
        },
        {
            "name": "evaluation_metrics",
            "description": "Metrics used to evaluate generated content.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 601,
            "workflow_id": 30,
            "is_prerequisite": False
        },
        {
            "name": "generated_content_samples",
            "description": "Samples of generated content for human evaluation.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 602,
            "workflow_id": 30,
            "is_prerequisite": False
        },
        {
            "name": "Training Data",
            "description": "Conversations or logs containing user intents, questions, and responses.",
            "data_type": "typing.List[typing.Dict[str, str]]",
            "format": "field",
            "id": 603,
            "workflow_id": 31,
            "is_prerequisite": False
        },
        {
            "name": "Domain Knowledge Base",
            "description": "Information about the specific domain or topic the chatbot will specialize in.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 604,
            "workflow_id": 31,
            "is_prerequisite": False
        },
        {
            "name": "User Intents Data",
            "description": "Data containing user intents, entities, and examples for intent recognition.",
            "data_type": "typing.List[typing.Dict[str, typing.Union[str, builtins.List[str]]]]",
            "format": "field",
            "id": 605,
            "workflow_id": 31,
            "is_prerequisite": False
        },
        {
            "name": "Context Maintenance Rules",
            "description": "Rules or guidelines for maintaining context during conversation.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 606,
            "workflow_id": 31,
            "is_prerequisite": False
        },
        {
            "name": "UserProfile",
            "description": "Information about the user",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 607,
            "workflow_id": 32,
            "is_prerequisite": False
        },
        {
            "name": "LearningResources",
            "description": "List of learning resources",
            "data_type": "typing.List[typing.Tuple[str, str]]",
            "format": "field",
            "id": 608,
            "workflow_id": 32,
            "is_prerequisite": False
        },
        {
            "name": "QuizzesData",
            "description": "Quiz questions and answers data",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "file",
            "id": 609,
            "workflow_id": 32,
            "is_prerequisite": False
        },
        {
            "name": "UserPerformance",
            "description": "User's performance in quizzes/assignments",
            "data_type": "typing.List[typing.Tuple[int, int]]",
            "format": "field",
            "id": 610,
            "workflow_id": 32,
            "is_prerequisite": False
        },
        {
            "name": "ProgressHistory",
            "description": "History of user's learning progress",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "file",
            "id": 611,
            "workflow_id": 32,
            "is_prerequisite": False
        },
        {
            "name": "CustomerSupportDatabaseCredentials",
            "description": "Credentials to access customer support database.",
            "data_type": "builtins.tuple[str, str, str, int]",
            "format": "field",
            "id": 612,
            "workflow_id": 33,
            "is_prerequisite": False
        },
        {
            "name": "ExternalAPIsCredentials",
            "description": "Credentials to access external APIs.",
            "data_type": "typing.Dict[str, typing.Union[str, builtins.int]]",
            "format": "field",
            "id": 613,
            "workflow_id": 33,
            "is_prerequisite": False
        },
        {
            "name": "CustomerQueriesHistory",
            "description": "Previous customer queries and responses for reference.",
            "data_type": "typing.List[typing.Tuple[str, str]]",
            "format": "file",
            "id": 614,
            "workflow_id": 33,
            "is_prerequisite": False
        },
        {
            "name": "Historical Sensor Data",
            "description": "Raw sensor data over time, including timestamps and readings.",
            "data_type": "typing.List[typing.Tuple[builtins.datetime, typing.Union[builtins.float, builtins.int]]]",
            "format": "file",
            "id": 615,
            "workflow_id": 34,
            "is_prerequisite": False
        },
        {
            "name": "Failure Events Data",
            "description": "Timestamped data indicating equipment failures and associated sensor readings.",
            "data_type": "typing.List[typing.Tuple[builtins.datetime, typing.Union[builtins.float, builtins.int], builtins.bool]]",
            "format": "file",
            "id": 616,
            "workflow_id": 34,
            "is_prerequisite": False
        },
        {
            "name": "Equipment Metadata",
            "description": "Additional information about the equipment, such as make/model, age, maintenance history.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 617,
            "workflow_id": 34,
            "is_prerequisite": False
        },
        {
            "name": "Maintenance Schedule Data",
            "description": "Planned maintenance events and associated sensor readings or equipment status.",
            "data_type": "typing.List[typing.Tuple[builtins.datetime, typing.Union[builtins.float, builtins.int], builtins.bool]]",
            "format": "file",
            "id": 618,
            "workflow_id": 34,
            "is_prerequisite": False
        },
        {
            "name": "Social Media Data",
            "description": "Text data from social media platforms.",
            "data_type": "typing.List[str]",
            "format": "file",
            "id": 619,
            "workflow_id": 36,
            "is_prerequisite": False
        },
        {
            "name": "Reviews Data",
            "description": "Text reviews from products or services.",
            "data_type": "typing.List[str]",
            "format": "file",
            "id": 620,
            "workflow_id": 36,
            "is_prerequisite": False
        },
        {
            "name": "Surveys Data",
            "description": "Text data from survey responses.",
            "data_type": "typing.List[str]",
            "format": "file",
            "id": 621,
            "workflow_id": 36,
            "is_prerequisite": False
        },
        {
            "name": "Brand Names",
            "description": "List of brands to analyze sentiment for.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 622,
            "workflow_id": 36,
            "is_prerequisite": False
        },
        {
            "name": "Output Format",
            "description": "Desired format for exporting analysis results.",
            "data_type": "str",
            "format": "field",
            "id": 623,
            "workflow_id": 36,
            "is_prerequisite": False
        },
        {
            "name": "StockSymbols",
            "description": "List of stock symbols to track.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 624,
            "workflow_id": 37,
            "is_prerequisite": False
        },
        {
            "name": "HistoricalPricesFilePath",
            "description": "File path for historical stock prices data.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 625,
            "workflow_id": 37,
            "is_prerequisite": False
        },
        {
            "name": "NewsSentimentFilePath",
            "description": "File path for news sentiment data.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 626,
            "workflow_id": 37,
            "is_prerequisite": False
        },
        {
            "name": "TradingStrategyParams",
            "description": "Customizable parameters for trading strategies.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 627,
            "workflow_id": 37,
            "is_prerequisite": False
        },
        {
            "name": "BotPerformanceMetrics",
            "description": "List to store bot performance metrics.",
            "data_type": "typing.List[float]",
            "format": "field",
            "id": 628,
            "workflow_id": 37,
            "is_prerequisite": False
        },
        {
            "name": "supported_languages",
            "description": "List of languages supported by the translator.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 629,
            "workflow_id": 38,
            "is_prerequisite": False
        },
        {
            "name": "translation_api_key",
            "description": "API key for accessing translation services (e.g., Google Cloud Translation API).",
            "data_type": "builtins.str",
            "format": "field",
            "id": 630,
            "workflow_id": 38,
            "is_prerequisite": False
        },
        {
            "name": "speech_recognition_engine",
            "description": "Speech recognition engine to convert speech input into text.",
            "data_type": "typing.Union[str, typing.Callable[[], str]]",
            "format": "field",
            "id": 631,
            "workflow_id": 38,
            "is_prerequisite": False
        },
        {
            "name": "audio_input_file_path",
            "description": "Path to the file containing audio input for speech translation.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 632,
            "workflow_id": 38,
            "is_prerequisite": False
        },
        {
            "name": "translator_model",
            "description": "Pre-trained NLP model used for advanced language processing tasks.",
            "data_type": "typing.Any",
            "format": "field",
            "id": 633,
            "workflow_id": 38,
            "is_prerequisite": False
        },
        {
            "name": "SourceCode",
            "description": "The original source code file to be refactored.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 634,
            "workflow_id": 39,
            "is_prerequisite": False
        },
        {
            "name": "RefactoringRules",
            "description": "A list of rules or guidelines for refactoring, e.g., PEP 8 style guide.",
            "data_type": "typing.List[typing.Dict[str, str]]",
            "format": "field",
            "id": 635,
            "workflow_id": 39,
            "is_prerequisite": False
        },
        {
            "name": "UserPreferences",
            "description": "User's preferences on refactoring, like prioritized suggestions or blacklisted rules.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 636,
            "workflow_id": 39,
            "is_prerequisite": False
        },
        {
            "name": "FileAccessRights",
            "description": "Permissions to read and write the source code file.",
            "data_type": "builtins.tuple[builtins.str, builtins.int, builtins.int]",
            "format": "field",
            "id": 637,
            "workflow_id": 39,
            "is_prerequisite": False
        },
        {
            "name": "Input Documents",
            "description": "Documents/articles/videos from which text will be extracted.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 638,
            "workflow_id": 40,
            "is_prerequisite": False
        },
        {
            "name": "Ground Truth Summaries",
            "description": "Human-written summaries of input documents for model training and evaluation.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 639,
            "workflow_id": 40,
            "is_prerequisite": False
        },
        {
            "name": "Model Parameters",
            "description": "Hyperparameters for the AI models used in summarization.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 640,
            "workflow_id": 40,
            "is_prerequisite": False
        },
        {
            "name": "Starting Location",
            "description": "The initial location of the vehicle, drone or pedestrian.",
            "data_type": "typing.Tuple[builtins.float, builtins.float]",
            "format": "field",
            "id": 641,
            "workflow_id": 41,
            "is_prerequisite": False
        },
        {
            "name": "Destination Location",
            "description": "The desired end point for the route.",
            "data_type": "typing.Tuple[builtins.float, builtins.float]",
            "format": "field",
            "id": 642,
            "workflow_id": 41,
            "is_prerequisite": False
        },
        {
            "name": "Mode of Transportation",
            "description": "Whether it's a vehicle, drone or pedestrian.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 643,
            "workflow_id": 41,
            "is_prerequisite": False
        },
        {
            "name": "Real-Time Traffic Data API Endpoint",
            "description": "The URL or path to fetch real-time traffic data.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 644,
            "workflow_id": 41,
            "is_prerequisite": False
        },
        {
            "name": "Other Factors Data Sources",
            "description": "API endpoints or file paths for weather, road closures, events data.",
            "data_type": "typing.List[typing.Tuple[builtins.str, builtins.str]]",
            "format": "field",
            "id": 645,
            "workflow_id": 41,
            "is_prerequisite": False
        },
        {
            "name": "Access Tokens/Keys",
            "description": "Any required authentication tokens or keys for APIs or files.",
            "data_type": "typing.Dict[builtins.str, builtins.str]",
            "format": "field",
            "id": 646,
            "workflow_id": 41,
            "is_prerequisite": False
        },
        {
            "name": "recipients_data",
            "description": "Data containing recipient's details like name, email address etc.",
            "data_type": "typing.List[typing.Dict[str, str]]",
            "format": "field",
            "id": 647,
            "workflow_id": 42,
            "is_prerequisite": False
        },
        {
            "name": "topics_list",
            "description": "List of topics to be incorporated into the emails.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 648,
            "workflow_id": 42,
            "is_prerequisite": False
        },
        {
            "name": "tone_preferences",
            "description": "Data containing tone preferences for each recipient or a default tone preference.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 649,
            "workflow_id": 42,
            "is_prerequisite": False
        },
        {
            "name": "email_template_file_path",
            "description": "Path to the file containing the email template.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 650,
            "workflow_id": 42,
            "is_prerequisite": False
        },
        {
            "name": "Historical Sales Data",
            "description": "Previous sales data including dates, quantities sold, prices, and product details.",
            "data_type": "typing.List[builtins.Dict[str, typing.Union[int, float, str]]]",
            "format": "file",
            "id": 651,
            "workflow_id": 43,
            "is_prerequisite": False
        },
        {
            "name": "Market Trends Data",
            "description": "Information about market conditions such as demand fluctuations, competitor pricing, and economic indicators.",
            "data_type": "typing.List[builtins.Dict[str, typing.Union[int, float]]]",
            "format": "file",
            "id": 652,
            "workflow_id": 43,
            "is_prerequisite": False
        },
        {
            "name": "Product Inventory Data",
            "description": "Data about product availability, stock levels, and inventory turnover rates.",
            "data_type": "typing.Dict[str, typing.List[builtins.int]]",
            "format": "file",
            "id": 653,
            "workflow_id": 43,
            "is_prerequisite": False
        },
        {
            "name": "Customer Demographic Data",
            "description": "Information about customer characteristics such as location, age, income level, etc.",
            "data_type": "typing.Dict[str, typing.List[builtins.str]]",
            "format": "file",
            "id": 654,
            "workflow_id": 43,
            "is_prerequisite": False
        },
        {
            "name": "Seasonality Factors",
            "description": "Historical data showing how sales vary by season or time of year.",
            "data_type": "typing.Dict[str, builtins.float]",
            "format": "field",
            "id": 655,
            "workflow_id": 43,
            "is_prerequisite": False
        },
        {
            "name": "Price Elasticity Index",
            "description": "Data measuring the responsiveness of demand to changes in price.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 656,
            "workflow_id": 43,
            "is_prerequisite": False
        },
        {
            "name": "Sales Forecasting Model Configuration",
            "description": "Settings for sales forecasting models such as ARIMA parameters, LSTM layers, or Prophet's changing points.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 657,
            "workflow_id": 43,
            "is_prerequisite": False
        },
        {
            "name": "InputVideoSource",
            "description": "The source video stream or file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 658,
            "workflow_id": 45,
            "is_prerequisite": False
        },
        {
            "name": "ObjectTrackingAlgorithm",
            "description": "The algorithm used for tracking objects (e.g., YOLO, DeepSORT).",
            "data_type": "typing.Any",
            "format": "field",
            "id": 659,
            "workflow_id": 45,
            "is_prerequisite": False
        },
        {
            "name": "FrameBufferSize",
            "description": "The number of frames to buffer for real-time processing.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 660,
            "workflow_id": 45,
            "is_prerequisite": False
        },
        {
            "name": "ObjectTrackingParameters",
            "description": "Parameters specific to the tracking algorithm (e.g., confidence threshold, IoU threshold).",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 661,
            "workflow_id": 45,
            "is_prerequisite": False
        },
        {
            "name": "OutputVideoSource",
            "description": "The output video stream or file for displaying tracked objects.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 662,
            "workflow_id": 45,
            "is_prerequisite": False
        },
        {
            "name": "Customer Data",
            "description": "Information about customers, including demographics and transaction history.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "file",
            "id": 663,
            "workflow_id": 46,
            "is_prerequisite": False
        },
        {
            "name": "Product Data",
            "description": "Information about products or services offered by the business.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "file",
            "id": 664,
            "workflow_id": 46,
            "is_prerequisite": False
        },
        {
            "name": "Interaction History",
            "description": "Data on customer interactions with the business, such as emails opened, links clicked, etc.",
            "data_type": "typing.List[typing.Dict[builtins.str, typing.Any]]",
            "format": "file",
            "id": 665,
            "workflow_id": 46,
            "is_prerequisite": False
        },
        {
            "name": "Segmentation Metrics",
            "description": "Metrics to evaluate the quality of segmentation, such as silhouette score or Calinski-Harabasz index.",
            "data_type": "typing.List[builtins.float]",
            "format": "field",
            "id": 666,
            "workflow_id": 46,
            "is_prerequisite": False
        },
        {
            "name": "DeviceInventory",
            "description": "List of IoT devices with their capabilities.",
            "data_type": "typing.Dict[str, typing.Dict[str, str]]",
            "format": "field",
            "id": 667,
            "workflow_id": 47,
            "is_prerequisite": False
        },
        {
            "name": "UserCommands",
            "description": "History of user voice commands.",
            "data_type": "typing.List[typing.Tuple[str, datetime.datetime]]",
            "format": "field",
            "id": 668,
            "workflow_id": 47,
            "is_prerequisite": False
        },
        {
            "name": "RoutinesSchedule",
            "description": "Scheduled routines and triggers for automation.",
            "data_type": "typing.Dict[str, typing.List[str]]",
            "format": "field",
            "id": 669,
            "workflow_id": 47,
            "is_prerequisite": False
        },
        {
            "name": "ErrorLog",
            "description": "Log of errors encountered during execution.",
            "data_type": "typing.List[tuple[int, str, datetime.datetime]]",
            "format": "field",
            "id": 670,
            "workflow_id": 47,
            "is_prerequisite": False
        },
        {
            "name": "UserQueries",
            "description": "History of user queries for contextual information.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 671,
            "workflow_id": 47,
            "is_prerequisite": False
        },
        {
            "name": "DeviceConfigurations",
            "description": "Configuration files for IoT devices.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 672,
            "workflow_id": 47,
            "is_prerequisite": False
        },
        {
            "name": "Demand Data",
            "description": "Historical demand data for each listing in the marketplace.",
            "data_type": "typing.List[typing.Tuple[str, typing.Dict[str, int]]]",
            "format": "field",
            "id": 673,
            "workflow_id": 48,
            "is_prerequisite": False
        },
        {
            "name": "Supply Data",
            "description": "Current supply data for each listing in the marketplace.",
            "data_type": "typing.List[typing.Tuple[str, int]]",
            "format": "field",
            "id": 674,
            "workflow_id": 48,
            "is_prerequisite": False
        },
        {
            "name": "Market Factors Data",
            "description": "Historical market factors data that affect pricing.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "file",
            "id": 675,
            "workflow_id": 48,
            "is_prerequisite": False
        },
        {
            "name": "Listing Information",
            "description": "Details about each listing in the marketplace.",
            "data_type": "typing.List[typing.Tuple[str, typing.Dict[str, str]]]",
            "format": "field",
            "id": 676,
            "workflow_id": 48,
            "is_prerequisite": False
        },
        {
            "name": "Pricing History Data",
            "description": "Historical pricing data for each listing in the marketplace.",
            "data_type": "typing.List[typing.Tuple[str, typing.List[int]]]",
            "format": "file",
            "id": 677,
            "workflow_id": 48,
            "is_prerequisite": False
        },
        {
            "name": "thing_to_query",
            "description": "The specific thing or ID about which data will be retrieved.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 678,
            "workflow_id": 49,
            "is_prerequisite": False
        },
        {
            "name": "data_source",
            "description": "The API endpoint or file path from where the data will be retrieved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 679,
            "workflow_id": 49,
            "is_prerequisite": False
        },
        {
            "name": "access_rights",
            "description": "Access credentials (if required) to retrieve data from the source.",
            "data_type": "typing.Dict[builtins.str, builtins.str]",
            "format": "field",
            "id": 680,
            "workflow_id": 49,
            "is_prerequisite": False
        },
        {
            "name": "aggregation_criteria",
            "description": "The criteria or rules for combining the retrieved data.",
            "data_type": "typing.List[typing.Tuple[builtins.str, typing.Any]]",
            "format": "field",
            "id": 681,
            "workflow_id": 49,
            "is_prerequisite": False
        },
        {
            "name": "cleaning_rules",
            "description": "Rules for cleaning and preparing the raw data.",
            "data_type": "typing.Dict[builtins.str, typing.Callable]",
            "format": "field",
            "id": 682,
            "workflow_id": 49,
            "is_prerequisite": False
        },
        {
            "name": "input_matrix",
            "description": "The square matrix as a list of lists.",
            "data_type": "typing.List[typing.List[float]]",
            "id": 683,
            "workflow_id": 73,
            "is_prerequisite": False
        },
        {
            "name": "power",
            "description": "The integer power to raise the matrix.",
            "data_type": "int",
            "id": 684,
            "workflow_id": 73,
            "is_prerequisite": False
        },
        {
            "name": "input_number",
            "description": "The integer to check for primality",
            "data_type": "builtins.int",
            "format": "field",
            "id": 685,
            "workflow_id": 74,
            "is_prerequisite": False
        },
        {
            "name": "Repository_Path",
            "description": "The file path of the Git repository.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 686,
            "workflow_id": 75,
            "is_prerequisite": False
        },
        {
            "name": "Commit_History",
            "description": "List of commits with their metadata.",
            "data_type": "typing.List[git_commit.Commit]",
            "format": "field",
            "id": 687,
            "workflow_id": 75,
            "is_prerequisite": False
        },
        {
            "name": "Contributors",
            "description": "Dictionary mapping contributors to their commit counts.",
            "data_type": "typing.Dict[builtins.str, builtins.int]",
            "format": "field",
            "id": 688,
            "workflow_id": 75,
            "is_prerequisite": False
        },
        {
            "name": "Lines_Changed_History",
            "description": "List of tuples containing date and lines changed.",
            "data_type": "typing.List[tuple[builtins.datetime, builtins.int]]",
            "format": "field",
            "id": 689,
            "workflow_id": 75,
            "is_prerequisite": False
        },
        {
            "name": "api_url",
            "description": "URL of the API from which to collect data.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 690,
            "workflow_id": 76,
            "is_prerequisite": False
        },
        {
            "name": "headers",
            "description": "Headers required for making API requests. If none, use an empty dictionary.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 691,
            "workflow_id": 76,
            "is_prerequisite": False
        },
        {
            "name": "csv_file_path",
            "description": "Path where the CSV file will be stored.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 692,
            "workflow_id": 76,
            "is_prerequisite": False
        },
        {
            "name": "columns_to_keep",
            "description": "List of columns to keep from the API response. If all are needed, use '*' for wildcard.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 693,
            "workflow_id": 76,
            "is_prerequisite": False
        },
        {
            "name": "logging_level",
            "description": "Level of logging detail (e.g., DEBUG, INFO, WARNING).",
            "data_type": "builtins.str",
            "format": "field",
            "id": 694,
            "workflow_id": 76,
            "is_prerequisite": False
        },
        {
            "name": "news_articles",
            "description": "List of news articles, each represented as a dictionary with 'title' and 'text' keys.",
            "data_type": "typing.List[typing.Dict[str, str]]",
            "format": "field",
            "id": 695,
            "workflow_id": 77,
            "is_prerequisite": False
        },
        {
            "name": "ImageSources",
            "description": "List of paths or URLs where the images are located.",
            "data_type": "typing.List[builtins.str]",
            "format": "file",
            "id": 696,
            "workflow_id": 78,
            "is_prerequisite": False
        },
        {
            "name": "InitialClusterCenters",
            "description": "Number of initial cluster centers to randomly select from the image pixels.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 697,
            "workflow_id": 78,
            "is_prerequisite": False
        },
        {
            "name": "MaxIterations",
            "description": "Maximum number of iterations for K-Means clustering algorithm before convergence is checked.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 698,
            "workflow_id": 78,
            "is_prerequisite": False
        },
        {
            "name": "ConvergenceThreshold",
            "description": "Minimum change in cluster centers to consider the algorithm has converged.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 699,
            "workflow_id": 78,
            "is_prerequisite": False
        },
        {
            "name": "input_files",
            "description": "List of OpenAPI/Swagger specification files to process.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 700,
            "workflow_id": 79,
            "is_prerequisite": False
        },
        {
            "name": "output_file",
            "description": "Path where the generated documentation will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 701,
            "workflow_id": 79,
            "is_prerequisite": False
        },
        {
            "name": "output_format",
            "description": "Format of the output documentation (e.g., 'markdown', 'html').",
            "data_type": "builtins.str",
            "format": "field",
            "id": 702,
            "workflow_id": 79,
            "is_prerequisite": False
        },
        {
            "name": "project_info",
            "description": "Dictionary containing project information like title, description, version.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 703,
            "workflow_id": 79,
            "is_prerequisite": False
        },
        {
            "name": "CustomerReviewDataset",
            "description": "The dataset containing customer reviews on the product, brand, public figure, demographic, or subculture.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 704,
            "workflow_id": 80,
            "is_prerequisite": False
        },
        {
            "name": "TargetVariable",
            "description": "The sentiment labels in the reviews (e.g., positive, negative).",
            "data_type": "typing.Series",
            "format": "field",
            "id": 705,
            "workflow_id": 80,
            "is_prerequisite": False
        },
        {
            "name": "BiasIndicator",
            "description": "A field or feature that indicates the potential bias in the dataset.",
            "data_type": "typing.Series",
            "format": "field",
            "id": 706,
            "workflow_id": 80,
            "is_prerequisite": False
        },
        {
            "name": "InputDataFilePath",
            "description": "The file path to the input data if it's stored in a file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 707,
            "workflow_id": 80,
            "is_prerequisite": False
        },
        {
            "name": "input_file_path",
            "description": "Path to the input CSV file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 708,
            "workflow_id": 81,
            "is_prerequisite": False
        },
        {
            "name": "output_file_path",
            "description": "Path where the cleaned dataset will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 709,
            "workflow_id": 81,
            "is_prerequisite": False
        },
        {
            "name": "column_info",
            "description": "Dictionary containing column names and their data types (for type conversion).",
            "data_type": "typing.Dict[builtins.str, typing.Union[str, int, float]]",
            "format": "field",
            "id": 710,
            "workflow_id": 81,
            "is_prerequisite": False
        },
        {
            "name": "categorical_cols",
            "description": "List of column names that need encoding.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 711,
            "workflow_id": 81,
            "is_prerequisite": False
        },
        {
            "name": "missing_data_strategy",
            "description": "Strategy to handle missing values (e.g., 'fill_with_mean', 'drop').",
            "data_type": "builtins.str",
            "format": "field",
            "id": 712,
            "workflow_id": 81,
            "is_prerequisite": False
        },
        {
            "name": "number_of_trials",
            "description": "The total number of coin tosses.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 713,
            "workflow_id": 82,
            "is_prerequisite": False
        },
        {
            "name": "number_of_heads",
            "description": "The number of heads observed in the coin tosses.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 714,
            "workflow_id": 82,
            "is_prerequisite": False
        },
        {
            "name": "SupplierData",
            "description": "Data containing supplier details like name, contact info, etc.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 715,
            "workflow_id": 83,
            "is_prerequisite": False
        },
        {
            "name": "EthicalMetrics",
            "description": "Data containing ethical considerations metrics for each supplier (e.g., sustainability practices scores, labor standards ratings).",
            "data_type": "typing.List[typing.Dict[str, typing.Float]]",
            "format": "field",
            "id": 716,
            "workflow_id": 83,
            "is_prerequisite": False
        },
        {
            "name": "SupplierFilesPath",
            "description": "File path(s) where supplier data and ethical metrics are stored.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 717,
            "workflow_id": 83,
            "is_prerequisite": False
        },
        {
            "name": "Input Data",
            "description": "Data that the landscape generation tool will process through pipes.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 718,
            "workflow_id": 84,
            "is_prerequisite": False
        },
        {
            "name": "Configuration File",
            "description": "A file containing configuration settings for the landscape generator (e.g., algorithm parameters).",
            "data_type": "builtins.str",
            "format": "file",
            "id": 719,
            "workflow_id": 84,
            "is_prerequisite": False
        },
        {
            "name": "repository_url",
            "description": "The URL of the Git repository to lint.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 720,
            "workflow_id": 85,
            "is_prerequisite": False
        },
        {
            "name": "branch_name",
            "description": "The name of the branch in the repository to lint.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 721,
            "workflow_id": 85,
            "is_prerequisite": False
        },
        {
            "name": "commit_hash",
            "description": "The hash of the commit to lint (optional, if not provided, the latest commit will be used).",
            "data_type": "builtins.str",
            "format": "field",
            "id": 722,
            "workflow_id": 85,
            "is_prerequisite": False
        },
        {
            "name": "config_file_path",
            "description": "The path to the configuration file for linting tools (optional).",
            "data_type": "builtins.str",
            "format": "file",
            "id": 723,
            "workflow_id": 85,
            "is_prerequisite": False
        },
        {
            "name": "ontological_data",
            "description": "Dictionary representing the ontological data with classes as keys and a parents list containing their immediate parents.",
            "data_type": "typing.Dict[builtins.str, typing.List[builtins.str]]",
            "format": "field",
            "id": 724,
            "workflow_id": 86,
            "is_prerequisite": False
        },
        {
            "name": "file_path",
            "description": "Path to the file containing the ontological data if it's stored in a file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 725,
            "workflow_id": 86,
            "is_prerequisite": False
        },
        {
            "name": "input_urls",
            "description": "List of URLs to fetch news articles from.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 726,
            "workflow_id": 87,
            "is_prerequisite": False
        },
        {
            "name": "summarization_method",
            "description": "Method used for text summarization (e.g., 'gensim', 'sumbasic').",
            "data_type": "str",
            "format": "field",
            "id": 727,
            "workflow_id": 87,
            "is_prerequisite": False
        },
        {
            "name": "access_rights",
            "description": "File access rights if data is stored in files.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 728,
            "workflow_id": 87,
            "is_prerequisite": False
        },
        {
            "name": "number_of_samples",
            "description": "The number of random samples to generate",
            "data_type": "typing.Int32",
            "format": "field",
            "id": 729,
            "workflow_id": 88,
            "is_prerequisite": False
        },
        {
            "name": "input_data",
            "description": "The data to be transformed.",
            "data_type": "builtins.Any",
            "format": "field",
            "id": 730,
            "workflow_id": 89,
            "is_prerequisite": False
        },
        {
            "name": "transformation_function",
            "description": "The function to perform the transformation on the input data.",
            "data_type": "typing.Callable[[builtins.Any], builtins.Any]",
            "format": "field",
            "id": 731,
            "workflow_id": 89,
            "is_prerequisite": False
        },
        {
            "name": "transformation_description",
            "description": "A description of the transformation to be performed.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 732,
            "workflow_id": 89,
            "is_prerequisite": False
        },
        {
            "name": "log_file_path",
            "description": "The file path where the audit trail log will be stored.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 733,
            "workflow_id": 89,
            "is_prerequisite": False
        },
        {
            "name": "API_keys",
            "description": "The API keys for accessing the news sources.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 734,
            "workflow_id": 90,
            "is_prerequisite": False
        },
        {
            "name": "news_sources",
            "description": "A list of news sources to aggregate from.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 735,
            "workflow_id": 90,
            "is_prerequisite": False
        },
        {
            "name": "InputHashmap",
            "description": "The Hashmap to be analyzed and repaired.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 736,
            "workflow_id": 91,
            "is_prerequisite": False
        },
        {
            "name": "DiagnosticMessages",
            "description": "A list to store diagnostic messages during analysis.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 737,
            "workflow_id": 91,
            "is_prerequisite": False
        },
        {
            "name": "RepairedHashmap",
            "description": "The repaired Hashmap after performing repairs.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 738,
            "workflow_id": 91,
            "is_prerequisite": False
        },
        {
            "name": "InputFilePath",
            "description": "The file path of the input Hashmap if it's stored in a file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 739,
            "workflow_id": 91,
            "is_prerequisite": False
        },
        {
            "name": "input_image_path",
            "description": "Path to the input image file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 740,
            "workflow_id": 92,
            "is_prerequisite": False
        },
        {
            "name": "output_ascii_art_path",
            "description": "Path where the ASCII art output will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 741,
            "workflow_id": 92,
            "is_prerequisite": False
        },
        {
            "name": "input_code",
            "description": "The Python code string to be reviewed.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 742,
            "workflow_id": 93,
            "is_prerequisite": False
        },
        {
            "name": "pylint_config_file",
            "description": "Path to the pylint configuration file (if any).",
            "data_type": "builtins.str",
            "format": "file",
            "id": 743,
            "workflow_id": 93,
            "is_prerequisite": False
        },
        {
            "name": "repository_url",
            "description": "The URL of the open-source API repository.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 744,
            "workflow_id": 94,
            "is_prerequisite": False
        },
        {
            "name": "output_format",
            "description": "The desired format for the generated documentation (e.g., 'html', 'pdf').",
            "data_type": "builtins.str",
            "format": "field",
            "id": 745,
            "workflow_id": 94,
            "is_prerequisite": False
        },
        {
            "name": "documentation_files",
            "description": "A list of file paths or URLs containing API documentation.",
            "data_type": "typing.List[builtins.Union[builtins.str, typing.Dict[str, any]]]",
            "format": "field",
            "id": 746,
            "workflow_id": 94,
            "is_prerequisite": False
        },
        {
            "name": "RSS_Feeds_List",
            "description": "List of URLs for RSS feeds to fetch news articles.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 747,
            "workflow_id": 95,
            "is_prerequisite": False
        },
        {
            "name": "Email_Credentials",
            "description": "Credentials for sending emails (username, password, SMTP server).",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 748,
            "workflow_id": 95,
            "is_prerequisite": False
        },
        {
            "name": "Subscriber_Email_List",
            "description": "List of subscribers' email addresses to send the newsletter.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 749,
            "workflow_id": 95,
            "is_prerequisite": False
        },
        {
            "name": "Newsletter_Template",
            "description": "Template for generating the aggregated newsletter.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 750,
            "workflow_id": 95,
            "is_prerequisite": False
        },
        {
            "name": "topics_list",
            "description": "A list of topics for the curriculum.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 751,
            "workflow_id": 96,
            "is_prerequisite": False
        },
        {
            "name": "desired_outcomes",
            "description": "A dictionary mapping outcomes to their respective importance/weight in lessons.",
            "data_type": "typing.Dict[str, int]",
            "format": "field",
            "id": 752,
            "workflow_id": 96,
            "is_prerequisite": False
        },
        {
            "name": "total_lessons",
            "description": "The total number of lessons to be allocated based on topics.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 753,
            "workflow_id": 96,
            "is_prerequisite": False
        },
        {
            "name": "dataset_path",
            "description": "Path to the dataset file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 754,
            "workflow_id": 97,
            "is_prerequisite": False
        },
        {
            "name": "language_models",
            "description": "List of language models (e.g., NLTK, spaCy) to use for preprocessing.",
            "data_type": "typing.List[str]",
            "id": 755,
            "workflow_id": 97,
            "is_prerequisite": False
        },
        {
            "name": "analysis_tasks",
            "description": "List of analysis tasks (e.g., sentiment analysis, topic modeling).",
            "data_type": "typing.List[str]",
            "id": 756,
            "workflow_id": 97,
            "is_prerequisite": False
        },
        {
            "name": "input_file_path",
            "description": "The file path of the input CSV file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 757,
            "workflow_id": 98,
            "is_prerequisite": False
        },
        {
            "name": "output_file_path",
            "description": "The file path where the cleaned DataFrame will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 758,
            "workflow_id": 98,
            "is_prerequisite": False
        },
        {
            "name": "missing_values_strategy",
            "description": "Strategy to handle missing values ('drop' or 'impute').",
            "data_type": "typing.Literal['drop', 'impute']",
            "format": "field",
            "id": 759,
            "workflow_id": 98,
            "is_prerequisite": False
        },
        {
            "name": "outlier_detection_method",
            "description": "Method to detect outliers (e.g., 'IQR', 'Z-score').",
            "data_type": "typing.Literal['IQR', 'Z-score']",
            "format": "field",
            "id": 760,
            "workflow_id": 98,
            "is_prerequisite": False
        },
        {
            "name": "visualization_options",
            "description": "List of visualization options to generate.",
            "data_type": "typing.List[typing.Union[str, None]]",
            "format": "field",
            "id": 761,
            "workflow_id": 98,
            "is_prerequisite": False
        },
        {
            "name": "report_file_path",
            "description": "The file path where the report markdown file will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 762,
            "workflow_id": 98,
            "is_prerequisite": False
        },
        {
            "name": "Ontology Input File",
            "description": "The RDF file containing the ontology to be visualized.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 763,
            "workflow_id": 99,
            "is_prerequisite": False
        },
        {
            "name": "Output Directory Path",
            "description": "The directory where the generated visual output will be saved.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 764,
            "workflow_id": 99,
            "is_prerequisite": False
        },
        {
            "name": "Output File Format",
            "description": "The format of the generated graph file (e.g., 'png', 'pdf').",
            "data_type": "builtins.str",
            "format": "field",
            "id": 765,
            "workflow_id": 99,
            "is_prerequisite": False
        },
        {
            "name": "topics",
            "description": "List of educational topics.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 766,
            "workflow_id": 100,
            "is_prerequisite": False
        },
        {
            "name": "num_weeks",
            "description": "Number of weeks in the lesson plan (optional).",
            "data_type": "builtins.int",
            "format": "field",
            "id": 767,
            "workflow_id": 100,
            "is_prerequisite": False
        },
        {
            "name": "learning_outcomes_per_topic",
            "description": "Desired learning outcomes per topic (optional).",
            "data_type": "builtins.int",
            "format": "field",
            "id": 768,
            "workflow_id": 100,
            "is_prerequisite": False
        },
        {
            "name": "topic_data",
            "description": "Dictionary containing topic details.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 769,
            "workflow_id": 100,
            "is_prerequisite": False
        },
        {
            "name": "git_repo_url",
            "description": "The URL of the Git repository.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 770,
            "workflow_id": 101,
            "is_prerequisite": False
        },
        {
            "name": "commit_history_file",
            "description": "The file containing the commit history (e.g., from 'git log --pretty=format:'%H|%an|%ad|%s' --graph).",
            "data_type": "builtins.str",
            "format": "file",
            "id": 771,
            "workflow_id": 101,
            "is_prerequisite": False
        },
        {
            "name": "interval_length",
            "description": "The length of time (in hours) to use for calculating commit intervals.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 772,
            "workflow_id": 101,
            "is_prerequisite": False
        },
        {
            "name": "Actual Pi Value",
            "description": "The known mathematical value of Pi.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 773,
            "workflow_id": 102,
            "is_prerequisite": False
        },
        {
            "name": "Number of Samples",
            "description": "The total number of random points to generate.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 774,
            "workflow_id": 102,
            "is_prerequisite": False
        },
        {
            "name": "Points",
            "description": "List of tuples representing the coordinates of randomly generated points in the square.",
            "data_type": "typing.List[builtins.tuple[builtins.float, builtins.float]]",
            "format": "field",
            "id": 775,
            "workflow_id": 102,
            "is_prerequisite": False
        },
        {
            "name": "target_function",
            "description": "The function to be wrapped and monitored.",
            "data_type": "typing.Callable[..., typing.Any]",
            "format": "field",
            "id": 776,
            "workflow_id": 103,
            "is_prerequisite": False
        },
        {
            "name": "log_file_path",
            "description": "The file path where the logs will be written.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 777,
            "workflow_id": 103,
            "is_prerequisite": False
        },
        {
            "name": "reset_signal",
            "description": "A signal to reset the wrapper's internal state, e.g., a manual intervention flag.",
            "data_type": "typing.Optional[builtins.bool]",
            "format": "field",
            "id": 778,
            "workflow_id": 103,
            "is_prerequisite": False
        },
        {
            "name": "question_banks",
            "description": "Topic-specific lists of questions with multiple-choice options.",
            "data_type": "typing.Dict[str, typing.List[typing.Tuple[str, typing.List[str]]]]",
            "format": "field",
            "id": 779,
            "workflow_id": 104,
            "is_prerequisite": False
        },
        {
            "name": "topics",
            "description": "List of topics from which to select questions.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 780,
            "workflow_id": 104,
            "is_prerequisite": False
        },
        {
            "name": "num_questions",
            "description": "Number of questions to include in the quiz.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 781,
            "workflow_id": 104,
            "is_prerequisite": False
        },
        {
            "name": "difficulty_level",
            "description": "Difficulty level for selected questions (e.g., 'easy', 'medium', 'hard').",
            "data_type": "str",
            "format": "field",
            "id": 782,
            "workflow_id": 104,
            "is_prerequisite": False
        },
        {
            "name": "quiz_output_path",
            "description": "File path where the generated quiz will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 783,
            "workflow_id": 104,
            "is_prerequisite": False
        },
        {
            "name": "Seed",
            "description": "Random number generation seed.",
            "data_type": "typing.Union[int, NoneType]",
            "format": "field",
            "id": 784,
            "workflow_id": 105,
            "is_prerequisite": False
        },
        {
            "name": "ZoomLevel",
            "description": " Zoom level for fractal landscape.",
            "data_type": "float",
            "format": "field",
            "id": 785,
            "workflow_id": 105,
            "is_prerequisite": False
        },
        {
            "name": "DetailScale",
            "description": "Detail scale for fractal generation.",
            "data_type": "float",
            "format": "field",
            "id": 786,
            "workflow_id": 105,
            "is_prerequisite": False
        },
        {
            "name": "topics_list",
            "description": "A list of topics for the curriculum.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 787,
            "workflow_id": 106,
            "is_prerequisite": False
        },
        {
            "name": "desired_outcomes",
            "description": "A dictionary where keys are topics and values are desired outcomes.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 788,
            "workflow_id": 106,
            "is_prerequisite": False
        },
        {
            "name": "prerequisites_data",
            "description": "Data representing the prerequisite relationships between topics. It could be a list of tuples where each tuple contains two elements: the prerequisite topic and the dependent topic.",
            "data_type": "typing.List[tuple[str, str]]",
            "format": "field",
            "id": 789,
            "workflow_id": 106,
            "is_prerequisite": False
        },
        {
            "name": "input_file_path",
            "description": "The path to the input file containing topics and prerequisites data if provided as a file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 790,
            "workflow_id": 106,
            "is_prerequisite": False
        },
        {
            "name": "ProjectData",
            "description": "Details of the project including tasks and steps.",
            "data_type": "typing.Dict[str, typing.List[typing.Dict[str, str]]]",
            "format": "field",
            "id": 791,
            "workflow_id": 107,
            "is_prerequisite": False
        },
        {
            "name": "ToolLibrary",
            "description": "A library of tools with their functionalities, parameters, and usage.",
            "data_type": "typing.Dict[str, typing.Dict[str, typing.Any]]",
            "format": "file",
            "id": 792,
            "workflow_id": 107,
            "is_prerequisite": False
        },
        {
            "name": "ProjectRequirements",
            "description": "Specific requirements and constraints of the project.",
            "data_type": "typing.List[typing.Tuple[str, str]]",
            "format": "field",
            "id": 793,
            "workflow_id": 107,
            "is_prerequisite": False
        },
        {
            "name": "UserPreferences",
            "description": "User's preferred tools and parameters if any.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "file",
            "id": 794,
            "workflow_id": 107,
            "is_prerequisite": False
        },
        {
            "name": "number",
            "description": "The number to check for primality.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 795,
            "workflow_id": 108,
            "is_prerequisite": False
        },
        {
            "name": "input_file_path",
            "description": "Path of the input text file containing Python code snippets.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 796,
            "workflow_id": 109,
            "is_prerequisite": False
        },
        {
            "name": "dataset",
            "description": "A predefined dataset containing facts about books, authors, genres etc.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 797,
            "workflow_id": 111,
            "is_prerequisite": False
        },
        {
            "name": "graph_file_path",
            "description": "The file path to save/load the graph data.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 798,
            "workflow_id": 111,
            "is_prerequisite": False
        },
        {
            "name": "tasks",
            "description": "List of tasks with descriptions in natural language.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 799,
            "workflow_id": 112,
            "is_prerequisite": False
        },
        {
            "name": "tools",
            "description": "Predefined rules or patterns for matching tools to tasks.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 800,
            "workflow_id": 112,
            "is_prerequisite": False
        },
        {
            "name": "tool_parameters",
            "description": "Default parameters and optimization rules for each tool.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 801,
            "workflow_id": 112,
            "is_prerequisite": False
        },
        {
            "name": "task_categories",
            "description": "Predefined categories or types of tasks.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 802,
            "workflow_id": 112,
            "is_prerequisite": False
        },
        {
            "name": "Dataset",
            "description": "The time-series dataset used for interpolation.",
            "data_type": "pandas.core.frame.DataFrame",
            "format": "file",
            "id": 803,
            "workflow_id": 113,
            "is_prerequisite": False
        },
        {
            "name": "Input_File_Path",
            "description": "The file path of the dataset.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 804,
            "workflow_id": 113,
            "is_prerequisite": False
        },
        {
            "name": "topics",
            "description": "List of topics for the quiz questions.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 805,
            "workflow_id": 114,
            "is_prerequisite": False
        },
        {
            "name": "options",
            "description": "List of possible options (correct and incorrect).",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 806,
            "workflow_id": 114,
            "is_prerequisite": False
        },
        {
            "name": "answer",
            "description": "The correct answer to the question.",
            "data_type": "str",
            "format": "field",
            "id": 807,
            "workflow_id": 114,
            "is_prerequisite": False
        },
        {
            "name": "Initial_Belief",
            "description": "Prior probability distribution for the unknown parameter (probability of getting heads)",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 808,
            "workflow_id": 115,
            "is_prerequisite": False
        },
        {
            "name": "Number_of_Flips",
            "description": "Number of coin flips to simulate",
            "data_type": "builtins.int",
            "format": "field",
            "id": 809,
            "workflow_id": 115,
            "is_prerequisite": False
        },
        {
            "name": "Output_File_Path",
            "description": "File path for writing the results",
            "data_type": "builtins.str",
            "format": "file",
            "id": 810,
            "workflow_id": 115,
            "is_prerequisite": False
        },
        {
            "name": "openapi_files",
            "description": "List of OpenAPI files to process.",
            "data_type": "typing.List[builtins.str]",
            "format": "file",
            "id": 811,
            "workflow_id": 116,
            "is_prerequisite": False
        },
        {
            "name": "output_file_path",
            "description": "Path where the generated markdown documentation will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 812,
            "workflow_id": 116,
            "is_prerequisite": False
        },
        {
            "name": "start_value",
            "description": "The initial value of the series.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 813,
            "workflow_id": 117,
            "is_prerequisite": False
        },
        {
            "name": "common_difference_or_ratio",
            "description": "For arithmetic series, it's the common difference. For geometric series, it's the ratio.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 814,
            "workflow_id": 117,
            "is_prerequisite": False
        },
        {
            "name": "num_terms",
            "description": "The number of terms to generate in the series.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 815,
            "workflow_id": 117,
            "is_prerequisite": False
        },
        {
            "name": "series_type",
            "description": "Whether the series is arithmetic ('arithmetic') or geometric ('geometric').",
            "data_type": "builtins.str",
            "format": "field",
            "id": 816,
            "workflow_id": 117,
            "is_prerequisite": False
        },
        {
            "name": "number_of_particles",
            "description": "The number of particles to simulate.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 817,
            "workflow_id": 118,
            "is_prerequisite": False
        },
        {
            "name": "time_steps",
            "description": "The total steps for the particles' movement.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 818,
            "workflow_id": 118,
            "is_prerequisite": False
        },
        {
            "name": "step_size_meters",
            "description": "The distance each particle moves in one step.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 819,
            "workflow_id": 118,
            "is_prerequisite": False
        },
        {
            "name": "initial_positions",
            "description": "The initial positions of the particles.",
            "data_type": "typing.List[typing.Tuple[builtins.float, builtins.float]]",
            "format": "field",
            "id": 820,
            "workflow_id": 118,
            "is_prerequisite": False
        },
        {
            "name": "operation_ranges",
            "description": "The ranges for each operation (addition, subtraction, multiplication)",
            "data_type": "[{'builtins.tuple', 'typing.List'}]",
            "format": "field",
            "id": 821,
            "workflow_id": 119,
            "is_prerequisite": False
        },
        {
            "name": "total_questions",
            "description": "The total number of questions in the quiz",
            "data_type": "'builtins.int'",
            "format": "field",
            "id": 822,
            "workflow_id": 119,
            "is_prerequisite": False
        },
        {
            "name": "Question_Bank",
            "description": "A collection of questions with their respective answers and difficulty levels.",
            "data_type": "typing.List[dict]",
            "format": "field",
            "id": 823,
            "workflow_id": 120,
            "is_prerequisite": False
        },
        {
            "name": "User_Responses",
            "description": "A record of user's responses to previous questions.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 824,
            "workflow_id": 120,
            "is_prerequisite": False
        },
        {
            "name": "User_Performance_Data",
            "description": "Data about the user's performance on past quizzes.",
            "data_type": "typing.List[dict]",
            "format": "field",
            "id": 825,
            "workflow_id": 120,
            "is_prerequisite": False
        },
        {
            "name": "Quiz_Metadata",
            "description": "Metadata associated with each quiz (e.g., topic, duration).",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 826,
            "workflow_id": 120,
            "is_prerequisite": False
        },
        {
            "name": "User_Profile",
            "description": "Information about the user, such as their current skill level.",
            "data_type": "dict",
            "format": "field",
            "id": 827,
            "workflow_id": 120,
            "is_prerequisite": False
        },
        {
            "name": "files_to_check",
            "description": "A list of Python files to perform code review on.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 828,
            "workflow_id": 121,
            "is_prerequisite": False
        },
        {
            "name": "violations_report",
            "description": "A dictionary to store violations found during code review, with file names as keys and lists of violations as values.",
            "data_type": "typing.Dict[str, typing.List[str]]",
            "format": "field",
            "id": 829,
            "workflow_id": 121,
            "is_prerequisite": False
        },
        {
            "name": "topics",
            "description": "List of topics for the lesson plan.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 830,
            "workflow_id": 122,
            "is_prerequisite": False
        },
        {
            "name": "desired_outcomes",
            "description": "List of desired outcomes for the lesson plan.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 831,
            "workflow_id": 122,
            "is_prerequisite": False
        },
        {
            "name": "lesson_duration",
            "description": "Duration of each lesson in minutes.",
            "data_type": "int",
            "format": "field",
            "id": 832,
            "workflow_id": 122,
            "is_prerequisite": False
        },
        {
            "name": "input_data_file",
            "description": "File containing additional data like resources, activities, etc. for the lesson plan.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 833,
            "workflow_id": 122,
            "is_prerequisite": False
        },
        {
            "name": "Input Data",
            "description": "The time-series dataset to be normalized.",
            "data_type": "pandas.DataFrame",
            "format": "file",
            "id": 834,
            "workflow_id": 124,
            "is_prerequisite": False
        },
        {
            "name": "DatetimeIndex Column Name",
            "description": "The name of the column containing datetime values for indexing.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 835,
            "workflow_id": 124,
            "is_prerequisite": False
        },
        {
            "name": "Target Column Name",
            "description": "The name of the column containing the time-series data to be normalized.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 836,
            "workflow_id": 124,
            "is_prerequisite": False
        },
        {
            "name": "number_of_samples",
            "description": "The number of samples to use in the Monte Carlo simulation.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 837,
            "workflow_id": 126,
            "is_prerequisite": False
        },
        {
            "name": "csv_files",
            "description": "List of paths to CSV files containing US census information.",
            "data_type": "builtins.List[builtins.str]",
            "format": "file",
            "id": 838,
            "workflow_id": 127,
            "is_prerequisite": False
        },
        {
            "name": "expected_columns",
            "description": "List of expected column names in the CSV files for population, age, race data.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 839,
            "workflow_id": 127,
            "is_prerequisite": False
        },
        {
            "name": "valid_age_range",
            "description": "Tuple containing min and max valid age values.",
            "data_type": "builtins.tuple[builtins.int, builtins.int]",
            "format": "field",
            "id": 840,
            "workflow_id": 127,
            "is_prerequisite": False
        },
        {
            "name": "valid_population_range",
            "description": "Tuple containing min and max valid population values.",
            "data_type": "builtins.tuple[int, int]",
            "format": "field",
            "id": 841,
            "workflow_id": 127,
            "is_prerequisite": False
        },
        {
            "name": "UserProfiles",
            "description": "Data structure containing user profiles with attributes like username, email, proficiency level etc.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 842,
            "workflow_id": 128,
            "is_prerequisite": False
        },
        {
            "name": "PerformanceData",
            "description": "Contains user performance data including scores, timestamps, and quiz IDs.",
            "data_type": "typing.List[typing.Dict[str, typing.Union[int, float, str]]]",
            "format": "field",
            "id": 843,
            "workflow_id": 128,
            "is_prerequisite": False
        },
        {
            "name": "QuizBank",
            "description": "A collection of quizzes or assessments with questions and answer keys.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "file",
            "id": 844,
            "workflow_id": 128,
            "is_prerequisite": False
        },
        {
            "name": "UserAnswers",
            "description": "Contains user responses to quiz/assessment questions.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 845,
            "workflow_id": 128,
            "is_prerequisite": False
        },
        {
            "name": "DifficultyLevels",
            "description": "Defines different difficulty levels for quizzes/assessments and their corresponding proficiency ranges.",
            "data_type": "typing.List[typing.Tuple[int, float]]",
            "format": "field",
            "id": 846,
            "workflow_id": 128,
            "is_prerequisite": False
        },
        {
            "name": "user_preferences",
            "description": "User's preferred news sources, topics, and language.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 847,
            "workflow_id": 129,
            "is_prerequisite": False
        },
        {
            "name": "api_keys",
            "description": "API keys for accessing news sources APIs.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 848,
            "workflow_id": 129,
            "is_prerequisite": False
        },
        {
            "name": "news_sources_data_file",
            "description": "File containing data about available news sources and their APIs.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 849,
            "workflow_id": 129,
            "is_prerequisite": False
        },
        {
            "name": "PaperSources",
            "description": "List of URLs or file paths to the research papers.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 850,
            "workflow_id": 130,
            "is_prerequisite": False
        },
        {
            "name": "SummarizationThreshold",
            "description": "Percentage or fraction of text length for summary extraction.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 851,
            "workflow_id": 130,
            "is_prerequisite": False
        },
        {
            "name": "TopicsOfInterest",
            "description": "List of topics or keywords to focus on during summarization and identification.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 852,
            "workflow_id": 130,
            "is_prerequisite": False
        },
        {
            "name": "file_paths",
            "description": "Paths of the CSV files to process.",
            "data_type": "builtins.List[builtins.str]",
            "format": "field",
            "id": 853,
            "workflow_id": 131,
            "is_prerequisite": False
        },
        {
            "name": "standard_column_names",
            "description": "Standard naming convention for columns.",
            "data_type": "builtins.Dict[builtins.str, builtins.str]",
            "format": "field",
            "id": 854,
            "workflow_id": 131,
            "is_prerequisite": False
        },
        {
            "name": "missing_value_strategy",
            "description": "Strategy to handle missing values ('drop' or 'fill').",
            "data_type": "builtins.str",
            "format": "field",
            "id": 855,
            "workflow_id": 131,
            "is_prerequisite": False
        },
        {
            "name": "logging_config",
            "description": "Configuration for logging.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 856,
            "workflow_id": 131,
            "is_prerequisite": False
        },
        {
            "name": "RSS Feeds",
            "description": "List of RSS feed URLs to fetch news headlines from",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 857,
            "workflow_id": 132,
            "is_prerequisite": False
        },
        {
            "name": "Search Query",
            "description": "The topic or keyword to search for in the headlines",
            "data_type": "str",
            "format": "field",
            "id": 858,
            "workflow_id": 132,
            "is_prerequisite": False
        },
        {
            "name": "Environment Map",
            "description": "A representation of the 2D environment where agent operates.",
            "data_type": "typing.Dict[builtins.str, typing.List[builtins.float]]",
            "format": "field",
            "id": 859,
            "workflow_id": 133,
            "is_prerequisite": False
        },
        {
            "name": "Agent Start Position",
            "description": "The initial position of the agent in the environment.",
            "data_type": "typing.List[builtins.float]",
            "format": "field",
            "id": 860,
            "workflow_id": 133,
            "is_prerequisite": False
        },
        {
            "name": "Goal Position",
            "description": "The target position that the agent should reach.",
            "data_type": "typing.List[builtins.float]",
            "format": "field",
            "id": 861,
            "workflow_id": 133,
            "is_prerequisite": False
        },
        {
            "name": "Obstacle Positions",
            "description": "A list of positions occupied by obstacles in the environment.",
            "data_type": "typing.List[typing.List[builtins.float]]",
            "format": "field",
            "id": 862,
            "workflow_id": 133,
            "is_prerequisite": False
        },
        {
            "name": "Agent Movement Strategy",
            "description": "The rule-based strategy that defines the agent's behavior.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 863,
            "workflow_id": 133,
            "is_prerequisite": False
        },
        {
            "name": "RSS_Feed_URLs",
            "description": "List of RSS feed URLs related to the specified topic.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 864,
            "workflow_id": 134,
            "is_prerequisite": False
        },
        {
            "name": "Topic",
            "description": "The specific news topic to aggregate articles for.",
            "data_type": "str",
            "format": "field",
            "id": 865,
            "workflow_id": 134,
            "is_prerequisite": False
        },
        {
            "name": "Summary_Length",
            "description": "Desired length of the consolidated summary report.",
            "data_type": "int",
            "format": "field",
            "id": 866,
            "workflow_id": 134,
            "is_prerequisite": False
        },
        {
            "name": "Output_Summary_File",
            "description": "Path to output file for the consolidated summary report.",
            "data_type": "str",
            "format": "file",
            "id": 867,
            "workflow_id": 134,
            "is_prerequisite": False
        },
        {
            "name": "repository_url",
            "description": "The URL of the Git repository.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 868,
            "workflow_id": 135,
            "is_prerequisite": False
        },
        {
            "name": "local_path",
            "description": "The local path where the repository will be cloned.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 869,
            "workflow_id": 135,
            "is_prerequisite": False
        },
        {
            "name": "branch_name",
            "description": "The name of the branch to clone from.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 870,
            "workflow_id": 135,
            "is_prerequisite": False
        },
        {
            "name": "requirements_file",
            "description": "The path to the requirements.txt file for installing dependencies.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 871,
            "workflow_id": 135,
            "is_prerequisite": False
        },
        {
            "name": "executable_name",
            "description": "The name of the standalone executable to be created.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 872,
            "workflow_id": 135,
            "is_prerequisite": False
        },
        {
            "name": "input_images",
            "description": "Set of input images for processing.",
            "data_type": "builtins.List[builtins.str]",
            "format": "file",
            "id": 873,
            "workflow_id": 136,
            "is_prerequisite": False
        },
        {
            "name": "output_directory",
            "description": "Directory where filtered images will be saved.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 874,
            "workflow_id": 136,
            "is_prerequisite": False
        },
        {
            "name": "filters_to_apply",
            "description": "List of filters to apply on input images.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 875,
            "workflow_id": 136,
            "is_prerequisite": False
        },
        {
            "name": "cv2_version",
            "description": "Version of OpenCV library installed in the environment.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 876,
            "workflow_id": 136,
            "is_prerequisite": False
        },
        {
            "name": "image_path",
            "description": "Path to the input image file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 877,
            "workflow_id": 137,
            "is_prerequisite": False
        },
        {
            "name": "model_path",
            "description": "Path to the pre-trained model file or directory.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 878,
            "workflow_id": 137,
            "is_prerequisite": False
        },
        {
            "name": "output_path",
            "description": "Path where the output caption file will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 879,
            "workflow_id": 137,
            "is_prerequisite": False
        },
        {
            "name": "max_words",
            "description": "Maximum number of words in the generated caption.",
            "data_type": "typing.Int",
            "format": "field",
            "id": 880,
            "workflow_id": 137,
            "is_prerequisite": False
        },
        {
            "name": "iris_dataset",
            "description": "The Iris dataset for classification.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "file",
            "id": 881,
            "workflow_id": 138,
            "is_prerequisite": False
        },
        {
            "name": "target_variable",
            "description": "The target variable (species) encoded as numerical data.",
            "data_type": "typing.List[int]",
            "format": "field",
            "id": 882,
            "workflow_id": 138,
            "is_prerequisite": False
        },
        {
            "name": "features_data",
            "description": "Preprocessed features data with missing values handled.",
            "data_type": "typing.Array[typing.Any, typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 883,
            "workflow_id": 138,
            "is_prerequisite": False
        },
        {
            "name": "target_labels",
            "description": "Encoded target labels for training the model.",
            "data_type": "typing.Array[int]",
            "format": "field",
            "id": 884,
            "workflow_id": 138,
            "is_prerequisite": False
        },
        {
            "name": "train_features",
            "description": "Features data for the training set after splitting.",
            "data_type": "typing.Array[typing.Any, typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 885,
            "workflow_id": 138,
            "is_prerequisite": False
        },
        {
            "name": "train_labels",
            "description": "Target labels for the training set after splitting.",
            "data_type": "typing.Array[int]",
            "format": "field",
            "id": 886,
            "workflow_id": 138,
            "is_prerequisite": False
        },
        {
            "name": "test_features",
            "description": "Features data for the test set after splitting.",
            "data_type": "typing.Array[typing.Any, typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 887,
            "workflow_id": 138,
            "is_prerequisite": False
        },
        {
            "name": "test_labels",
            "description": "Target labels for the test set after splitting.",
            "data_type": "typing.Array[int]",
            "format": "field",
            "id": 888,
            "workflow_id": 138,
            "is_prerequisite": False
        },
        {
            "name": "PasswordLength",
            "description": "The desired length of the generated passwords.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 889,
            "workflow_id": 139,
            "is_prerequisite": False
        },
        {
            "name": "IncludeUppercase",
            "description": "Boolean indicating whether to include uppercase letters in the password.",
            "data_type": "builtins.bool",
            "format": "field",
            "id": 890,
            "workflow_id": 139,
            "is_prerequisite": False
        },
        {
            "name": "IncludeLowercase",
            "description": "Boolean indicating whether to include lowercase letters in the password.",
            "data_type": "builtins.bool",
            "format": "field",
            "id": 891,
            "workflow_id": 139,
            "is_prerequisite": False
        },
        {
            "name": "IncludeNumbers",
            "description": "Boolean indicating whether to include numbers in the password.",
            "data_type": "builtins.bool",
            "format": "field",
            "id": 892,
            "workflow_id": 139,
            "is_prerequisite": False
        },
        {
            "name": "IncludeSpecialChars",
            "description": "Boolean indicating whether to include special characters in the password.",
            "data_type": "builtins.bool",
            "format": "field",
            "id": 893,
            "workflow_id": 139,
            "is_prerequisite": False
        },
        {
            "name": "ExcludeCommonPatterns",
            "description": "A list of common patterns to exclude from the generated passwords.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 894,
            "workflow_id": 139,
            "is_prerequisite": False
        },
        {
            "name": "limit",
            "description": "The maximum Fibonacci number to generate.",
            "data_type": "typing.Union[int, None]",
            "format": "field",
            "id": 895,
            "workflow_id": 141,
            "is_prerequisite": False
        },
        {
            "name": "fibonacci_list",
            "description": "A global list to store the generated Fibonacci numbers.",
            "data_type": "typing.List[builtins.int]",
            "format": "field",
            "id": 896,
            "workflow_id": 141,
            "is_prerequisite": False
        },
        {
            "name": "schema_api1",
            "description": "Parsed schema from API 1",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 897,
            "workflow_id": 142,
            "is_prerequisite": False
        },
        {
            "name": "schema_api2",
            "description": "Parsed schema from API 2",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 898,
            "workflow_id": 142,
            "is_prerequisite": False
        },
        {
            "name": "mapping_document",
            "description": "Mapping document highlighting differences and recommended transformations",
            "data_type": "builtins.str",
            "format": "file",
            "id": 899,
            "workflow_id": 142,
            "is_prerequisite": False
        },
        {
            "name": "StoryNodes",
            "description": "A dictionary where keys are story nodes (as strings) and values are lists containing scenario string and branching choices.",
            "data_type": "typing.Dict[builtins.str, typing.Tuple[str, typing.List[str]]]",
            "format": "field",
            "id": 900,
            "workflow_id": 143,
            "is_prerequisite": False
        },
        {
            "name": "CurrentNode",
            "description": "The current node in the story where user interaction occurs.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 901,
            "workflow_id": 143,
            "is_prerequisite": False
        },
        {
            "name": "PreviousNodes",
            "description": "A stack to store previously visited nodes for backtracking functionality.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 902,
            "workflow_id": 143,
            "is_prerequisite": False
        },
        {
            "name": "files_to_lint",
            "description": "List of Python files to lint and format.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 903,
            "workflow_id": 144,
            "is_prerequisite": False
        },
        {
            "name": "lint_results",
            "description": "Dictionary containing file paths as keys and their respective linting results as values.",
            "data_type": "typing.Dict[builtins.str, flake8.Flake8Report]",
            "format": "field",
            "id": 904,
            "workflow_id": 144,
            "is_prerequisite": False
        },
        {
            "name": "formatted_files",
            "description": "List of Python files that have been formatted using Black.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 905,
            "workflow_id": 144,
            "is_prerequisite": False
        },
        {
            "name": "backup_files",
            "description": "Dictionary containing file paths as keys and their respective backup paths as values.",
            "data_type": "typing.Dict[builtins.str, builtins.str]",
            "format": "field",
            "id": 906,
            "workflow_id": 144,
            "is_prerequisite": False
        },
        {
            "name": "resized_images",
            "description": "List of tuples containing the resized image paths and their respective dimensions.",
            "data_type": "typing.List[tuple[str, int, int]]",
            "id": 907,
            "workflow_id": 145,
            "is_prerequisite": False
        },
        {
            "name": "image_paths",
            "description": "List of file paths to the images that need to be resized.",
            "data_type": "builtins.str",
            "id": 908,
            "workflow_id": 145,
            "is_prerequisite": False
        },
        {
            "name": "output_directory",
            "description": "The directory where the resized images will be saved. Should exist and have write permissions.",
            "data_type": "builtins.str",
            "id": 909,
            "workflow_id": 145,
            "is_prerequisite": False
        },
        {
            "name": "max_width_height",
            "description": "A tuple (width, height) representing the maximum dimensions of the resized images.",
            "data_type": "typing.Tuple[int, int]",
            "id": 910,
            "workflow_id": 145,
            "is_prerequisite": False
        },
        {
            "name": "JavaScriptDataTypes",
            "description": "List of JavaScript data types found in the source code.",
            "data_type": "typing.Set[str]",
            "format": "field",
            "id": 911,
            "workflow_id": 146,
            "is_prerequisite": False
        },
        {
            "name": "MappedDataTypes",
            "description": "Mapping of JavaScript to Python data types.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 912,
            "workflow_id": 146,
            "is_prerequisite": False
        },
        {
            "name": "JavaScriptFunctions",
            "description": "List of JavaScript functions found in the source code.",
            "data_type": "typing.Set[str]",
            "format": "field",
            "id": 913,
            "workflow_id": 146,
            "is_prerequisite": False
        },
        {
            "name": "PythonEquivalentsFunctions",
            "description": "Mapping of JavaScript functions to their Python equivalents.",
            "data_type": "typing.Dict[str, typing.Callable[[], None]]",
            "format": "field",
            "id": 914,
            "workflow_id": 146,
            "is_prerequisite": False
        },
        {
            "name": "TranslatedCode",
            "description": "The translated Python code from the original JavaScript source code.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 915,
            "workflow_id": 146,
            "is_prerequisite": False
        },
        {
            "name": "TranslationHistory",
            "description": "A log of all translation steps and decisions made for future reference or debugging.",
            "data_type": "typing.List[dict]",
            "format": "file",
            "id": 916,
            "workflow_id": 146,
            "is_prerequisite": False
        },
        {
            "name": "cleaned_data",
            "description": "Data after cleaning and type conversion",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 917,
            "workflow_id": 147,
            "is_prerequisite": False
        },
        {
            "name": "processed_data",
            "description": "Data after handling missing values",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 918,
            "workflow_id": 147,
            "is_prerequisite": False
        },
        {
            "name": "exploratory_analysis_results",
            "description": "Results from exploratory data analysis",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 919,
            "workflow_id": 147,
            "is_prerequisite": False
        },
        {
            "name": "statistical_analysis_results",
            "description": "Results from statistical analysis",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 920,
            "workflow_id": 147,
            "is_prerequisite": False
        },
        {
            "name": "regression_models",
            "description": "Fitted regression models",
            "data_type": "typing.Dict[str, sklearn.base.BaseEstimator]",
            "format": "field",
            "id": 921,
            "workflow_id": 147,
            "is_prerequisite": False
        },
        {
            "name": "model_evaluation_results",
            "description": "Results from evaluating regression models",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 922,
            "workflow_id": 147,
            "is_prerequisite": False
        },
        {
            "name": "hypothesis_testing_results",
            "description": "Results from hypothesis testing",
            "data_type": "typing.List[typing.Tuple[str, bool]]",
            "format": "field",
            "id": 923,
            "workflow_id": 147,
            "is_prerequisite": False
        },
        {
            "name": "report_content",
            "description": "Content for the final report",
            "data_type": "builtins.str",
            "format": "file",
            "id": 924,
            "workflow_id": 147,
            "is_prerequisite": False
        },
        {
            "name": "Cleaned_Text",
            "description": "The text after cleaning (removing unnecessary spaces, punctuation, converting to lowercase)",
            "data_type": "builtins.str",
            "format": "field",
            "id": 925,
            "workflow_id": 148,
            "is_prerequisite": False
        },
        {
            "name": "Section_Headers",
            "description": "List of section or chapter headers found in the PDF file",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 926,
            "workflow_id": 148,
            "is_prerequisite": False
        },
        {
            "name": "Segmented_Text",
            "description": "List of texts segmented into sections or chapters based on headers",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 927,
            "workflow_id": 148,
            "is_prerequisite": False
        },
        {
            "name": "Summarized_Sections",
            "description": "List of summaries for each section or chapter",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 928,
            "workflow_id": 148,
            "is_prerequisite": False
        },
        {
            "name": "raw_data",
            "description": "The raw data after import but before cleaning.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 929,
            "workflow_id": 149,
            "is_prerequisite": False
        },
        {
            "name": "duplicate_columns_to_check",
            "description": "Columns in the dataset that should be checked for duplicates (if 'duplicate_columns' was None).",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 930,
            "workflow_id": 149,
            "is_prerequisite": False
        },
        {
            "name": "columns_with_missing_values",
            "description": "Columns with missing values that exceed the threshold.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 931,
            "workflow_id": 149,
            "is_prerequisite": False
        },
        {
            "name": "outliers",
            "description": "Rows containing outliers, based on the 'outlier_method' chosen.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 932,
            "workflow_id": 149,
            "is_prerequisite": False
        },
        {
            "name": "cleaned_data",
            "description": "The final cleaned dataset after all preprocessing steps.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 933,
            "workflow_id": 149,
            "is_prerequisite": False
        },
        {
            "name": "preprocessing_report",
            "description": "A report summarizing the preprocessing steps taken.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 934,
            "workflow_id": 149,
            "is_prerequisite": False
        },
        {
            "name": "cleaned_data_path",
            "description": "The file path where the cleaned data is exported to.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 935,
            "workflow_id": 149,
            "is_prerequisite": False
        },
        {
            "name": "simulation_results",
            "description": "The final populations for each category after simulation.",
            "data_type": "typing.Dict[str, int]",
            "format": "field",
            "id": 936,
            "workflow_id": 150,
            "is_prerequisite": False
        },
        {
            "name": "simulation_log",
            "description": "Daily change in susceptible, infected and recovered populations during the simulation.",
            "data_type": "builtins.list[typing.Tuple[int, int, int]]",
            "format": "field",
            "id": 937,
            "workflow_id": 150,
            "is_prerequisite": False
        },
        {
            "name": "quiz_data",
            "description": "Parsed and structured data from the CSV file containing questions, choices, and answers.",
            "data_type": "typing.List[dict]",
            "format": "field",
            "id": 938,
            "workflow_id": 151,
            "is_prerequisite": False
        },
        {
            "name": "user_answers",
            "description": "Answers provided by the user during the quiz.",
            "data_type": "typing.Dict[int, str]",
            "format": "field",
            "id": 939,
            "workflow_id": 151,
            "is_prerequisite": False
        },
        {
            "name": "quiz_score",
            "description": "The calculated score of the user's performance in the quiz.",
            "data_type": "int",
            "format": "field",
            "id": 940,
            "workflow_id": 151,
            "is_prerequisite": False
        },
        {
            "name": "input_data",
            "description": "Dictionary containing instruments as keys and measurements as values.",
            "data_type": "typing.Dict[builtins.str, typing.List[builtins.any]]",
            "id": 941,
            "workflow_id": 152,
            "is_prerequisite": False
        },
        {
            "name": "means",
            "description": "Dictionary containing means of each instrument's measurements.",
            "data_type": "typing.Dict[builtins.str, float]",
            "format": "field",
            "id": 942,
            "workflow_id": 152,
            "is_prerequisite": False
        },
        {
            "name": "std_devs",
            "description": "Dictionary containing standard deviations of each instrument's measurements.",
            "data_type": "typing.Dict[builtins.str, float]",
            "format": "field",
            "id": 943,
            "workflow_id": 152,
            "is_prerequisite": False
        },
        {
            "name": "normalized_data",
            "description": "New dictionary with normalized measurement for each instrument.",
            "data_type": "typing.Dict[builtins.str, tuple]",
            "format": "field",
            "id": 944,
            "workflow_id": 152,
            "is_prerequisite": False
        },
        {
            "name": "cleaned_data",
            "description": "The climate change dataset after data cleaning",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 945,
            "workflow_id": 153,
            "is_prerequisite": False
        },
        {
            "name": "missing_values_summary",
            "description": "Summary of missing values in the original dataset",
            "data_type": "typing.Dict[builtins.str, builtins.float]",
            "format": "field",
            "id": 946,
            "workflow_id": 153,
            "is_prerequisite": False
        },
        {
            "name": "outliers_detected",
            "description": "Outliers detected in the cleaned data",
            "data_type": "typing.List[builtins.int]",
            "format": "field",
            "id": 947,
            "workflow_id": 153,
            "is_prerequisite": False
        },
        {
            "name": "statistical_measures",
            "description": "Calculated statistical measures on the cleaned data",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 948,
            "workflow_id": 153,
            "is_prerequisite": False
        },
        {
            "name": "plots_generated",
            "description": "Generated plots from the cleaned data",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 949,
            "workflow_id": 153,
            "is_prerequisite": False
        },
        {
            "name": "report_path",
            "description": "Path to save the summary report file",
            "data_type": "builtins.str",
            "format": "file",
            "id": 950,
            "workflow_id": 153,
            "is_prerequisite": False
        },
        {
            "name": "historical_data",
            "description": "The historical data used for time series forecasting.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 951,
            "workflow_id": 154,
            "is_prerequisite": False
        },
        {
            "name": "target_column",
            "description": "The column in the DataFrame that represents the values to be predicted.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 952,
            "workflow_id": 154,
            "is_prerequisite": False
        },
        {
            "name": "date_column",
            "description": "The column in the DataFrame that represents the date or timestamp for each data point.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 953,
            "workflow_id": 154,
            "is_prerequisite": False
        },
        {
            "name": "forecast_horizon",
            "description": "The number of time steps into the future to predict.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 954,
            "workflow_id": 154,
            "is_prerequisite": False
        },
        {
            "name": "model",
            "description": "The fitted time-series forecasting model used for predictions.",
            "data_type": "object",
            "format": "field",
            "id": 955,
            "workflow_id": 154,
            "is_prerequisite": False
        },
        {
            "name": "tuned_parameters",
            "description": "The optimal parameters obtained from hyperparameter tuning.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 956,
            "workflow_id": 154,
            "is_prerequisite": False
        },
        {
            "name": "predictions",
            "description": "A DataFrame containing the predicted values for the forecast horizon.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 957,
            "workflow_id": 154,
            "is_prerequisite": False
        },
        {
            "name": "error_metrics",
            "description": "A dictionary containing various error metrics calculated from predictions and actual values.",
            "data_type": "typing.Dict[builtins.str, builtins.float]",
            "format": "field",
            "id": 958,
            "workflow_id": 154,
            "is_prerequisite": False
        },
        {
            "name": "fig_file_path",
            "description": "The file path where the forecast visualization plot will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 959,
            "workflow_id": 154,
            "is_prerequisite": False
        },
        {
            "name": "test_cases",
            "description": "Generated test cases based on input data.",
            "data_type": "typing.List[unittest.TestCase]",
            "format": "field",
            "id": 960,
            "workflow_id": 155,
            "is_prerequisite": False
        },
        {
            "name": "generated_html_report_path",
            "description": "Path to the generated HTML report file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 961,
            "workflow_id": 155,
            "is_prerequisite": False
        },
        {
            "name": "news_headlines",
            "description": "The latest headlines fetched from multiple news APIs.",
            "data_type": "typing.List[builtins.Dict[str, str]]",
            "format": "field",
            "id": 962,
            "workflow_id": 156,
            "is_prerequisite": False
        },
        {
            "name": "article_summaries",
            "description": "Summarized versions of each fetched article.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 963,
            "workflow_id": 156,
            "is_prerequisite": False
        },
        {
            "name": "aggregate_summary",
            "description": "The aggregate summary report from the summaries of individual articles.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 964,
            "workflow_id": 156,
            "is_prerequisite": False
        },
        {
            "name": "limit",
            "description": "The upper limit for generating prime numbers.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 965,
            "workflow_id": 157,
            "is_prerequisite": False
        },
        {
            "name": "prime_numbers",
            "description": "List of prime numbers generated using the Sieve of Eratosthenes algorithm up to the given limit.",
            "data_type": "typing.List[builtins.int]",
            "format": "field",
            "id": 966,
            "workflow_id": 157,
            "is_prerequisite": False
        },
        {
            "name": "topics",
            "description": "List of topics for the curriculum plan.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 967,
            "workflow_id": 158,
            "is_prerequisite": False
        },
        {
            "name": "input_file_path",
            "description": "Path to the input file containing topic data.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 968,
            "workflow_id": 158,
            "is_prerequisite": False
        },
        {
            "name": "review_feedback",
            "description": "User feedback for refining the curriculum plan.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 969,
            "workflow_id": 158,
            "is_prerequisite": False
        },
        {
            "name": "subtopics",
            "description": "Subtopics broken down from each main topic.",
            "data_type": "typing.List[typing.Tuple[str, typing.List[str]]]",
            "format": "field",
            "id": 970,
            "workflow_id": 158,
            "is_prerequisite": False
        },
        {
            "name": "progression_validity",
            "description": "Validation of topics arranged in planned progression.",
            "data_type": "bool",
            "format": "field",
            "id": 971,
            "workflow_id": 158,
            "is_prerequisite": False
        },
        {
            "name": "curriculum_plan",
            "description": "The final curriculum plan with structured lessons.",
            "data_type": "typing.List[dict]",
            "format": "field",
            "id": 972,
            "workflow_id": 158,
            "is_prerequisite": False
        },
        {
            "name": "retrieved_papers",
            "description": "List of retrieved papers from the academic database search.",
            "data_type": "typing.List[builtins.Dict[str, any]]",
            "format": "field",
            "id": 973,
            "workflow_id": 159,
            "is_prerequisite": False
        },
        {
            "name": "paper_summaries",
            "description": "Summarized key information extracted from each paper using NLP techniques.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 974,
            "workflow_id": 159,
            "is_prerequisite": False
        },
        {
            "name": "organized_data",
            "description": "Structured data (e.g., JSON or CSV) containing the organized and presented information.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 975,
            "workflow_id": 159,
            "is_prerequisite": False
        },
        {
            "name": "topic_analysis_results",
            "description": "Results of topic analysis, including dominant themes and trends.",
            "data_type": "typing.Dict[str, any]",
            "format": "field",
            "id": 976,
            "workflow_id": 159,
            "is_prerequisite": False
        },
        {
            "name": "related_topics_suggestions",
            "description": "Suggestions for related topics or further research questions generated based on the analysis.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 977,
            "workflow_id": 159,
            "is_prerequisite": False
        },
        {
            "name": "input_image",
            "description": "The input image loaded for reference dimensions.",
            "data_type": "PIL.Image.Image",
            "format": "field",
            "id": 978,
            "workflow_id": 160,
            "is_prerequisite": False
        },
        {
            "name": "image_list",
            "description": "A list of images in the provided directory to select patches from.",
            "data_type": "typing.List[PIL.Image.Image]",
            "format": "field",
            "id": 979,
            "workflow_id": 160,
            "is_prerequisite": False
        },
        {
            "name": "selected_patches",
            "description": "The selected image patches used for creating the mosaic.",
            "data_type": "typing.List[typing.Tuple[PIL.Image.Image, typing.Tuple[int, int]]]",
            "format": "field",
            "id": 980,
            "workflow_id": 160,
            "is_prerequisite": False
        },
        {
            "name": "mosaic_image",
            "description": "The final generated mosaic image to be saved.",
            "data_type": "PIL.Image.Image",
            "format": "field",
            "id": 981,
            "workflow_id": 160,
            "is_prerequisite": False
        },
        {
            "name": "output_image_path",
            "description": "The file path where the generated mosaic image will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 982,
            "workflow_id": 160,
            "is_prerequisite": False
        },
        {
            "name": "FilteredTimeSeriesData",
            "description": "The time-series data filtered based on selected columns and time intervals.",
            "data_type": "pandas.core.frame.DataFrame",
            "format": "field",
            "id": 983,
            "workflow_id": 161,
            "is_prerequisite": False
        },
        {
            "name": "NewFeatures",
            "description": "Additional features created to enhance data analysis.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 984,
            "workflow_id": 161,
            "is_prerequisite": False
        },
        {
            "name": "AnalysisResults",
            "description": "The results of the time-series data analysis.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 985,
            "workflow_id": 161,
            "is_prerequisite": False
        },
        {
            "name": "VisualizationData",
            "description": "Preprocessed data ready for visualization based on selected columns and time intervals.",
            "data_type": "typing.Any",
            "format": "field",
            "id": 986,
            "workflow_id": 161,
            "is_prerequisite": False
        },
        {
            "name": "UserPreferences",
            "description": "User's preferences such as selected columns, time intervals, and other configuration options.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "file",
            "id": 987,
            "workflow_id": 161,
            "is_prerequisite": False
        },
        {
            "name": "infected_population",
            "description": "Number of currently infected individuals.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 988,
            "workflow_id": 162,
            "is_prerequisite": False
        },
        {
            "name": "recovered_population",
            "description": "Number of individuals who have recovered from the disease.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 989,
            "workflow_id": 162,
            "is_prerequisite": False
        },
        {
            "name": "deaths_population",
            "description": "Number of deaths due to the disease.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 990,
            "workflow_id": 162,
            "is_prerequisite": False
        },
        {
            "name": "simulation_results",
            "description": "Results from each simulation run, including infection, recovery, and death counts over time.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 991,
            "workflow_id": 162,
            "is_prerequisite": False
        },
        {
            "name": "intervention_data",
            "description": "Data on the interventions applied during each simulation run.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 992,
            "workflow_id": 162,
            "is_prerequisite": False
        },
        {
            "name": "visualization_settings",
            "description": "Settings for visualizing the infection spread over time.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 993,
            "workflow_id": 162,
            "is_prerequisite": False
        },
        {
            "name": "simulation_logs",
            "description": "Logs from each simulation run, including important events and decisions made during the simulation.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 994,
            "workflow_id": 162,
            "is_prerequisite": False
        },
        {
            "name": "user_input_topic",
            "description": "The topic provided by the user or fetched from predefined data.",
            "data_type": "builtins.str",
            "id": 995,
            "workflow_id": 163,
            "is_prerequisite": False
        },
        {
            "name": "ai_model_endpoint",
            "description": "The endpoint of the AI model API, e.g., Hugging Face transformers.",
            "data_type": "builtins.str",
            "id": 996,
            "workflow_id": 163,
            "is_prerequisite": False
        },
        {
            "name": "api_key",
            "description": "The API key required for accessing the AI model API.",
            "data_type": "builtins.str",
            "id": 997,
            "workflow_id": 163,
            "is_prerequisite": False
        },
        {
            "name": "output_formatting_phase",
            "description": "Prepare and output the generated outline.",
            "data_type": "typing.Any",
            "id": 998,
            "workflow_id": 163,
            "is_prerequisite": False
        },
        {
            "name": "output_file_path",
            "description": "The file path where the generated outline will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 999,
            "workflow_id": 163,
            "is_prerequisite": False
        },
        {
            "name": "model_instance",
            "description": "The loaded and prepared AI model instance.",
            "data_type": "typing.Any",
            "id": 1000,
            "workflow_id": 163,
            "is_prerequisite": False
        },
        {
            "name": "generated_outline",
            "description": "The list of subtopics generated by the AI model.",
            "data_type": "typing.List[builtins.str]",
            "id": 1001,
            "workflow_id": 163,
            "is_prerequisite": False
        },
        {
            "name": "normalized_scores",
            "description": "Performance scores normalized for analysis.",
            "data_type": "typing.Dict[str, typing.List[float]]",
            "format": "field",
            "id": 1002,
            "workflow_id": 164,
            "is_prerequisite": False
        },
        {
            "name": "average_scores",
            "description": "Average scores computed for each topic.",
            "data_type": "typing.Dict[str, float]",
            "format": "field",
            "id": 1003,
            "workflow_id": 164,
            "is_prerequisite": False
        },
        {
            "name": "difficult_topics",
            "description": "Topics where the user scored below threshold.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1004,
            "workflow_id": 164,
            "is_prerequisite": False
        },
        {
            "name": "adaptive_study_guide",
            "description": "Generated adaptive study guide prioritizing difficult topics.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1005,
            "workflow_id": 164,
            "is_prerequisite": False
        },
        {
            "name": "current_difficulty_level",
            "description": "The current difficulty level of recommended practice problems.",
            "data_type": "int",
            "format": "field",
            "id": 1006,
            "workflow_id": 164,
            "is_prerequisite": False
        },
        {
            "name": "parsed_docstrings",
            "description": "The extracted and parsed docstrings from the Python library functions.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 1007,
            "workflow_id": 165,
            "is_prerequisite": False
        },
        {
            "name": "generated_test_cases",
            "description": "The generated test cases based on the parsed docstrings.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 1008,
            "workflow_id": 165,
            "is_prerequisite": False
        },
        {
            "name": "test_results",
            "description": "The results of running the generated test cases using pytest.",
            "data_type": "pytest.TestReport",
            "format": "field",
            "id": 1009,
            "workflow_id": 165,
            "is_prerequisite": False
        },
        {
            "name": "test_report_content",
            "description": "The content of the test report to be saved as a file.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1010,
            "workflow_id": 165,
            "is_prerequisite": False
        },
        {
            "name": "openapi_data",
            "description": "Parsed OpenAPI data from the YAML file.",
            "data_type": "typing.Any",
            "format": "field",
            "id": 1011,
            "workflow_id": 166,
            "is_prerequisite": False
        },
        {
            "name": "md_template",
            "description": "Predefined markdown template for generating documentation.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1012,
            "workflow_id": 166,
            "is_prerequisite": False
        },
        {
            "name": "generated_md",
            "description": "Generated markdown documentation as a string.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1013,
            "workflow_id": 166,
            "is_prerequisite": False
        },
        {
            "name": "error_log",
            "description": "List of errors encountered during processing.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 1014,
            "workflow_id": 166,
            "is_prerequisite": False
        },
        {
            "name": "InitialTopics",
            "description": "Topics selected for the first iteration of lesson generation.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 1015,
            "workflow_id": 167,
            "is_prerequisite": False
        },
        {
            "name": "GeneratedLessons",
            "description": "Structured lessons generated based on the chosen topics and desired outcomes.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 1016,
            "workflow_id": 167,
            "is_prerequisite": False
        },
        {
            "name": "RefinedTopics",
            "description": "Topics refined after considering user feedback.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 1017,
            "workflow_id": 167,
            "is_prerequisite": False
        },
        {
            "name": "UpdatedLessonStructureTemplate",
            "description": "Lesson structure template updated based on feedback and improvements.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 1018,
            "workflow_id": 167,
            "is_prerequisite": False
        },
        {
            "name": "FeedbackReports",
            "description": "Detailed reports summarizing user feedback for each lesson.",
            "data_type": "typing.List[typing.Dict[str, builtins.str]]",
            "format": "file",
            "id": 1019,
            "workflow_id": 167,
            "is_prerequisite": False
        },
        {
            "name": "IterationHistory",
            "description": "Record of all past iterations and changes made to topics and lesson structure.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1020,
            "workflow_id": 167,
            "is_prerequisite": False
        },
        {
            "name": "raw_data",
            "description": "Collected sensor data before cleaning and normalization.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 1021,
            "workflow_id": 168,
            "is_prerequisite": False
        },
        {
            "name": "cleaned_data",
            "description": "Sensor data after handling missing values.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 1022,
            "workflow_id": 168,
            "is_prerequisite": False
        },
        {
            "name": "normalized_data",
            "description": "Normalized sensor data ready for analysis.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 1023,
            "workflow_id": 168,
            "is_prerequisite": False
        },
        {
            "name": "missing_values_handled",
            "description": "Flag indicating if missing values have been handled or not.",
            "data_type": "builtins.bool",
            "format": "field",
            "id": 1024,
            "workflow_id": 168,
            "is_prerequisite": False
        },
        {
            "name": "output_data_path",
            "description": "File path where the final cleaned and normalized data will be stored as a CSV file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1025,
            "workflow_id": 168,
            "is_prerequisite": False
        },
        {
            "name": "json_data",
            "description": "JSON data retrieved from the JSON API endpoint or file.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 1026,
            "workflow_id": 169,
            "is_prerequisite": False
        },
        {
            "name": "xml_data",
            "description": "XML data retrieved from the XML API endpoint or file.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1027,
            "workflow_id": 169,
            "is_prerequisite": False
        },
        {
            "name": "mapped_json_data",
            "description": "JSON data after mapping and reconciliation with the XML schema.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 1028,
            "workflow_id": 169,
            "is_prerequisite": False
        },
        {
            "name": "converted_xml_data",
            "description": "XML data after conversion from JSON while reconciling field names.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1029,
            "workflow_id": 169,
            "is_prerequisite": False
        },
        {
            "name": "questions_data",
            "description": "List of questions with their respective answers.",
            "data_type": "typing.List[typing.Tuple[str, typing.Any]]",
            "id": 1030,
            "workflow_id": 170,
            "is_prerequisite": False
        },
        {
            "name": "quiz_output_string",
            "description": "Generated quiz string including question numbers, text, and answer choices if any.",
            "data_type": "str",
            "format": "field",
            "id": 1031,
            "workflow_id": 170,
            "is_prerequisite": False
        },
        {
            "name": "quiz_output_file_path",
            "description": "File path to save the generated quiz string.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1032,
            "workflow_id": 170,
            "is_prerequisite": False
        },
        {
            "name": "questions",
            "description": "List of questions for the quiz.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1033,
            "workflow_id": 171,
            "is_prerequisite": False
        },
        {
            "name": "answers",
            "description": "Corresponding answers to the questions.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1034,
            "workflow_id": 171,
            "is_prerequisite": False
        },
        {
            "name": "shuffled_questions",
            "description": "Questions randomly shuffled for the quiz.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1035,
            "workflow_id": 171,
            "is_prerequisite": False
        },
        {
            "name": "shuffled_answers",
            "description": "Answers randomly shuffled corresponding to questions.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1036,
            "workflow_id": 171,
            "is_prerequisite": False
        },
        {
            "name": "quiz_string",
            "description": "Formatted quiz string ready for assessment.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1037,
            "workflow_id": 171,
            "is_prerequisite": False
        },
        {
            "name": "cleaned_data",
            "description": "Data after cleaning (missing values, outliers)",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 1038,
            "workflow_id": 172,
            "is_prerequisite": False
        },
        {
            "name": "effect_sizes",
            "description": "Calculated effect sizes from each study",
            "data_type": "typing.List[float]",
            "format": "field",
            "id": 1039,
            "workflow_id": 172,
            "is_prerequisite": False
        },
        {
            "name": "pooled_effect_size",
            "description": "Pooled effect size",
            "data_type": "float",
            "format": "field",
            "id": 1040,
            "workflow_id": 172,
            "is_prerequisite": False
        },
        {
            "name": "forest_plot_data",
            "description": "Data for generating forest plot",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 1041,
            "workflow_id": 172,
            "is_prerequisite": False
        },
        {
            "name": "summary_report",
            "description": "Summary report content in string format",
            "data_type": "str",
            "format": "field",
            "id": 1042,
            "workflow_id": 172,
            "is_prerequisite": False
        },
        {
            "name": "input_dataframe",
            "description": "The dataframe to be transformed and logged.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 1043,
            "workflow_id": 174,
            "is_prerequisite": False
        },
        {
            "name": "transformation_details",
            "description": "A dictionary that will store the details of each transformation.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 1044,
            "workflow_id": 174,
            "is_prerequisite": False
        },
        {
            "name": "News Articles",
            "description": "Articles fetched from news sources.",
            "data_type": "typing.List[typing.Dict[str, str]]",
            "format": "field",
            "id": 1045,
            "workflow_id": 175,
            "is_prerequisite": False
        },
        {
            "name": "Article Summaries",
            "description": "Summarized articles ready to be sent in the daily digest.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1046,
            "workflow_id": 175,
            "is_prerequisite": False
        },
        {
            "name": "Daily Digest",
            "description": "The final email content containing all article summaries.",
            "data_type": "str",
            "format": "file",
            "id": 1047,
            "workflow_id": 175,
            "is_prerequisite": False
        },
        {
            "name": "Preprocessed_Dataset",
            "description": "The dataset after missing value imputation and normalization.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 1048,
            "workflow_id": 176,
            "is_prerequisite": False
        },
        {
            "name": "Trend_and_Seasonality_Features",
            "description": "The dataset with added trend and seasonality features.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 1049,
            "workflow_id": 176,
            "is_prerequisite": False
        },
        {
            "name": "Trained_ARIMA_Model",
            "description": "The ARIMA model trained on the dataset.",
            "data_type": "statsmodels.tsa.arima_model.ARIMA",
            "format": "field",
            "id": 1050,
            "workflow_id": 176,
            "is_prerequisite": False
        },
        {
            "name": "Trained_LSTM_Model",
            "description": "The LSTM model trained on the dataset.",
            "data_type": "keras.models.Sequential",
            "format": "field",
            "id": 1051,
            "workflow_id": 176,
            "is_prerequisite": False
        },
        {
            "name": "Predictions",
            "description": "The predicted future values of the target variable.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 1052,
            "workflow_id": 176,
            "is_prerequisite": False
        },
        {
            "name": "observed_flip_results",
            "description": "Sequence of observed flip results (heads or tails)",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1053,
            "workflow_id": 178,
            "is_prerequisite": False
        },
        {
            "name": "initial_prior",
            "description": "Initial beliefs about the coin's bias.",
            "data_type": "typing.Dict[builtins.str, typing.Tuple[float, float]]",
            "format": "field",
            "id": 1054,
            "workflow_id": 178,
            "is_prerequisite": False
        },
        {
            "name": "posterior_samples",
            "description": "Samples from the posterior distribution after each update.",
            "data_type": "typing.List[typing.Tuple[float, float]]",
            "format": "field",
            "id": 1055,
            "workflow_id": 178,
            "is_prerequisite": False
        },
        {
            "name": "number_of_samples",
            "description": "Number of samples to draw from the posterior distribution.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 1056,
            "workflow_id": 178,
            "is_prerequisite": False
        },
        {
            "name": "plot_data",
            "description": "Data used for plotting the inference process.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 1057,
            "workflow_id": 178,
            "is_prerequisite": False
        },
        {
            "name": "fairness_probability",
            "description": "Probability that the coin is fair (θ <= 0.5).",
            "data_type": "builtins.float",
            "format": "field",
            "id": 1058,
            "workflow_id": 178,
            "is_prerequisite": False
        },
        {
            "name": "results_log",
            "description": "Log of inference results for each update.",
            "data_type": "typing.List[str]",
            "format": "file",
            "id": 1059,
            "workflow_id": 178,
            "is_prerequisite": False
        },
        {
            "name": "InitialString",
            "description": "The initial string for the L-system.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1060,
            "workflow_id": 179,
            "is_prerequisite": False
        },
        {
            "name": "Rules",
            "description": "A dictionary mapping symbols to their replacements in the L-system.",
            "data_type": "typing.Dict[builtins.str, typing.Union[builtins.str, typing.List[builtins.str]]]",
            "format": "field",
            "id": 1061,
            "workflow_id": 179,
            "is_prerequisite": False
        },
        {
            "name": "Iterations",
            "description": "The number of iterations to perform on the L-system.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 1062,
            "workflow_id": 179,
            "is_prerequisite": False
        },
        {
            "name": "Angle",
            "description": "The angle by which to turn after each instruction in degrees.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 1063,
            "workflow_id": 179,
            "is_prerequisite": False
        },
        {
            "name": "FilenamePrefix",
            "description": "A prefix for the output SVG filenames.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1064,
            "workflow_id": 179,
            "is_prerequisite": False
        },
        {
            "name": "OutputDirectory",
            "description": "The directory where the SVG files will be saved.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1065,
            "workflow_id": 179,
            "is_prerequisite": False
        },
        {
            "name": "GeneratedCoordinates",
            "description": "The coordinates generated from the L-system interpretations.",
            "data_type": "typing.List[typing.Tuple[builtins.float, builtins.float]]",
            "format": "field",
            "id": 1066,
            "workflow_id": 179,
            "is_prerequisite": False
        },
        {
            "name": "SVGData",
            "description": "The SVG data string for the generated shape.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1067,
            "workflow_id": 179,
            "is_prerequisite": False
        },
        {
            "name": "SVGFilename",
            "description": "The full path to the output SVG file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1068,
            "workflow_id": 179,
            "is_prerequisite": False
        },
        {
            "name": "Struggling Subjects",
            "description": "Subjects where the user has struggled based on performance metrics.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 1069,
            "workflow_id": 180,
            "is_prerequisite": False
        },
        {
            "name": "Adaptive Learning Plan",
            "description": "Generated adaptive learning plan prioritizing struggling topics and appropriate difficulty levels.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 1070,
            "workflow_id": 180,
            "is_prerequisite": False
        },
        {
            "name": "api_dictionary",
            "description": "Dictionary containing API endpoints, methods, parameters and responses.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 1071,
            "workflow_id": 181,
            "is_prerequisite": False
        },
        {
            "name": "generated_documentation",
            "description": "Markdown-formatted API documentation generated from api_dictionary.",
            "data_type": "str",
            "format": "field",
            "id": 1072,
            "workflow_id": 181,
            "is_prerequisite": False
        },
        {
            "name": "output_file_path",
            "description": "Path to the file where generated markdown will be saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1073,
            "workflow_id": 181,
            "is_prerequisite": False
        },
        {
            "name": "function_code",
            "description": "The extracted code of the input function for analysis and refactoring.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1074,
            "workflow_id": 182,
            "is_prerequisite": False
        },
        {
            "name": "merged_duplicate_blocks",
            "description": "The merged duplicate blocks after 'Duplicate Code Merge' step.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 1075,
            "workflow_id": 182,
            "is_prerequisite": False
        },
        {
            "name": "function_analysis_results",
            "description": "The results from the 'Function Analysis' step, including function structure and behavior insights.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 1076,
            "workflow_id": 182,
            "is_prerequisite": False
        },
        {
            "name": "rewritten_conditionals",
            "description": "The rewritten conditional statements after the 'Conditional Statement Rewriting' step.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 1077,
            "workflow_id": 182,
            "is_prerequisite": False
        },
        {
            "name": "refactored_function_code",
            "description": "The final refactored code of the input function.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1078,
            "workflow_id": 182,
            "is_prerequisite": False
        },
        {
            "name": "refactoring_report",
            "description": "A report summarizing the refactoring tasks performed and their results.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "file",
            "id": 1079,
            "workflow_id": 182,
            "is_prerequisite": False
        },
        {
            "name": "parsed_packages",
            "description": "The parsed packages and their dependencies after data preparation.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "id": 1080,
            "workflow_id": 183,
            "is_prerequisite": False
        },
        {
            "name": "dependency_graph",
            "description": "The dependency graph constructed from the parsed packages.",
            "data_type": "typing.Dict[str, typing.List[str]]",
            "id": 1081,
            "workflow_id": 183,
            "is_prerequisite": False
        },
        {
            "name": "compatibility_metadata",
            "description": "The compatibility metadata integrated into the dependency graph.",
            "data_type": "typing.Optional[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 1082,
            "workflow_id": 183,
            "is_prerequisite": False
        },
        {
            "name": "output_file_path",
            "description": "The file path to save the output dictionary representing the dependency graph.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1083,
            "workflow_id": 183,
            "is_prerequisite": False
        },
        {
            "name": "input_data",
            "description": "The input data containing the packages and their dependencies.",
            "data_type": "typing.Any",
            "format": "field",
            "id": 1084,
            "workflow_id": 183,
            "is_prerequisite": False
        },
        {
            "name": "task_sequence",
            "description": "The task sequence as a list of strings.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1085,
            "workflow_id": 184,
            "is_prerequisite": False
        },
        {
            "name": "tool_database",
            "description": "A dictionary where keys are tools and values are their parameters.",
            "data_type": "typing.Dict[str, typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 1086,
            "workflow_id": 184,
            "is_prerequisite": False
        },
        {
            "name": "analyzed_steps",
            "description": "Individual steps in the task sequence after analysis.",
            "data_type": "typing.List[typing.Tuple[str, typing.Dict[str, any]]]",
            "format": "field",
            "id": 1087,
            "workflow_id": 184,
            "is_prerequisite": False
        },
        {
            "name": "matched_tools",
            "description": "Tools matched with each step based on predefined rules or machine learning models.",
            "data_type": "typing.List[typing.Tuple[str, str]]",
            "format": "field",
            "id": 1088,
            "workflow_id": 184,
            "is_prerequisite": False
        },
        {
            "name": "suggested_tools",
            "description": "Optimal tools along with their recommended parameters for each step.",
            "data_type": "typing.List[typing.Tuple[str, typing.Dict[str, any]]]",
            "format": "field",
            "id": 1089,
            "workflow_id": 184,
            "is_prerequisite": False
        },
        {
            "name": "Parsed_Data",
            "description": "Data parsed from the dependencies file.",
            "data_type": "typing.Any",
            "format": "field",
            "id": 1090,
            "workflow_id": 185,
            "is_prerequisite": False
        },
        {
            "name": "Dependency_Graph",
            "description": "The dependency graph model constructed from the parsed data.",
            "data_type": "networkx.DiGraph",
            "format": "field",
            "id": 1091,
            "workflow_id": 185,
            "is_prerequisite": False
        },
        {
            "name": "Package_Dependencies",
            "description": "A dictionary where keys are packages and values are their dependencies.",
            "data_type": "typing.Dict[str, typing.List[str]]",
            "format": "field",
            "id": 1092,
            "workflow_id": 185,
            "is_prerequisite": False
        },
        {
            "name": "UserInput",
            "description": "The integer input from the user for primality checking.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 1093,
            "workflow_id": 186,
            "is_prerequisite": False
        },
        {
            "name": "IsPrimeResult",
            "description": "The boolean result of whether the user input is prime or not.",
            "data_type": "bool",
            "format": "field",
            "id": 1094,
            "workflow_id": 186,
            "is_prerequisite": False
        },
        {
            "name": "TestNumbers",
            "description": "A list of integers to test the prime checker function on (not predetermined).",
            "data_type": "typing.List[builtins.int]",
            "format": "field",
            "id": 1095,
            "workflow_id": 186,
            "is_prerequisite": False
        },
        {
            "name": "PerformanceData",
            "description": "The time taken by the function for different inputs during performance testing.",
            "data_type": "typing.Dict[builtins.int, float]",
            "format": "field",
            "id": 1096,
            "workflow_id": 186,
            "is_prerequisite": False
        },
        {
            "name": "LogFilePath",
            "description": "The path to the log file where the app persists its runtime logs.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1097,
            "workflow_id": 186,
            "is_prerequisite": False
        },
        {
            "name": "refactored_code",
            "description": "The refactored code as a string.",
            "data_type": "builtins.str",
            "id": 1098,
            "workflow_id": 187,
            "is_prerequisite": False
        },
        {
            "name": "parse_tree",
            "description": "The parse tree of the input code for analysis.",
            "data_type": "ast.Module",
            "format": "field",
            "id": 1099,
            "workflow_id": 187,
            "is_prerequisite": False
        },
        {
            "name": "refactoring_opportunities",
            "description": "A list of refactoring opportunities identified in the code.",
            "data_type": "typing.List[builtins.tuple[str, typing.Any]]",
            "format": "field",
            "id": 1100,
            "workflow_id": 187,
            "is_prerequisite": False
        },
        {
            "name": "temp_output_file",
            "description": "A temporary file path for saving intermediate refactored code.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1101,
            "workflow_id": 187,
            "is_prerequisite": False
        },
        {
            "name": "error_logs",
            "description": "List of tuples containing file name, row index, and error description for every erroneous entry.",
            "data_type": "typing.List[tuple[str, int, str]]",
            "format": "field",
            "id": 1102,
            "workflow_id": 188,
            "is_prerequisite": False
        },
        {
            "name": "imputed_data",
            "description": "Imputed data after handling missing values using the specified method.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 1103,
            "workflow_id": 188,
            "is_prerequisite": False
        },
        {
            "name": "outlier_indices",
            "description": "List of indices containing outliers in the data.",
            "data_type": "typing.List[int]",
            "format": "field",
            "id": 1104,
            "workflow_id": 188,
            "is_prerequisite": False
        },
        {
            "name": "processed_data",
            "description": "Data after imputation and outlier handling.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 1105,
            "workflow_id": 188,
            "is_prerequisite": False
        },
        {
            "name": "validation_results",
            "description": "Dictionary containing validation results for each file, indicating whether data adheres to min/max limits or not.",
            "data_type": "typing.Dict[str, bool]",
            "format": "field",
            "id": 1106,
            "workflow_id": 188,
            "is_prerequisite": False
        },
        {
            "name": "error_files",
            "description": "List of files that contain errors exceeding the specified threshold and need self-repair mechanisms.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1107,
            "workflow_id": 188,
            "is_prerequisite": False
        },
        {
            "name": "api_endpoint_url",
            "description": "The URL of the API endpoint to lint.",
            "data_type": "builtins.str",
            "id": 1108,
            "workflow_id": 189,
            "is_prerequisite": False
        },
        {
            "name": "linting_rules",
            "description": "A predefined set of rules for linting the API endpoints.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "id": 1109,
            "workflow_id": 189,
            "is_prerequisite": False
        },
        {
            "name": "access_rights",
            "description": "Access rights to read the API endpoint files.",
            "data_type": "builtins.str",
            "id": 1110,
            "workflow_id": 189,
            "is_prerequisite": False
        },
        {
            "name": "file_path",
            "description": "The file path of the API endpoint codebase.",
            "data_type": "builtins.str",
            "id": 1111,
            "workflow_id": 189,
            "is_prerequisite": False
        },
        {
            "name": "cleaned_dataset",
            "description": "The dataset after preprocessing (handling missing values and outliers)",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 1112,
            "workflow_id": 190,
            "is_prerequisite": False
        },
        {
            "name": "scaled_features",
            "description": "Features scaled for the linear regression model",
            "data_type": "numpy.ndarray",
            "format": "field",
            "id": 1113,
            "workflow_id": 190,
            "is_prerequisite": False
        },
        {
            "name": "model",
            "description": "The trained linear regression model",
            "data_type": "sklearn.linear_model.LinearRegression",
            "format": "field",
            "id": 1114,
            "workflow_id": 190,
            "is_prerequisite": False
        },
        {
            "name": "model_significance_tests",
            "description": "Results of t-test and F-test for model significance",
            "data_type": "dict[str, float]",
            "format": "field",
            "id": 1115,
            "workflow_id": 190,
            "is_prerequisite": False
        },
        {
            "name": "model_evaluation_metrics",
            "description": "Evaluation metrics of the trained model (e.g., R2 score)",
            "data_type": "dict[str, float]",
            "format": "field",
            "id": 1116,
            "workflow_id": 190,
            "is_prerequisite": False
        },
        {
            "name": "visualization_data",
            "description": "Data prepared for visualization (e.g., predicted vs actual values)",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 1117,
            "workflow_id": 190,
            "is_prerequisite": False
        },
        {
            "name": "feature_importance",
            "description": "Importance of each feature in the model (if applicable)",
            "data_type": "list[tuple[str, float]]",
            "format": "field",
            "id": 1118,
            "workflow_id": 190,
            "is_prerequisite": False
        },
        {
            "name": "generated_data",
            "description": "Synthetic data generated from multiple Gaussian distributions.",
            "data_type": "typing.List[builtins.Tuple[float, float]]",
            "format": "field",
            "id": 1119,
            "workflow_id": 191,
            "is_prerequisite": False
        },
        {
            "name": "model",
            "description": "Fitted Gaussian Mixture Model using scikit-learn library.",
            "data_type": "sklearn.mixture.GaussianMixture",
            "format": "field",
            "id": 1120,
            "workflow_id": 191,
            "is_prerequisite": False
        },
        {
            "name": "cluster_assignments",
            "description": "Cluster assignments for the generated data points.",
            "data_type": "typing.List[int]",
            "format": "field",
            "id": 1121,
            "workflow_id": 191,
            "is_prerequisite": False
        },
        {
            "name": "Topics",
            "description": "List of topics for the lesson plan",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1122,
            "workflow_id": 192,
            "is_prerequisite": False
        },
        {
            "name": "Desired_Outcomes",
            "description": "List of desired outcomes for the lesson plan",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1123,
            "workflow_id": 192,
            "is_prerequisite": False
        },
        {
            "name": "Lesson_Plan",
            "description": "Generated structured lesson plans with introductory, intermediate, and advanced sections for each topic.",
            "data_type": "typing.Dict[str, typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 1124,
            "workflow_id": 192,
            "is_prerequisite": False
        },
        {
            "name": "Outcome_Coverage",
            "description": "Mapping of desired outcomes to lesson plan sections ensuring they are covered appropriately.",
            "data_type": "typing.Dict[str, typing.List[str]]",
            "format": "field",
            "id": 1125,
            "workflow_id": 192,
            "is_prerequisite": False
        },
        {
            "name": "Formatted_Lesson_Plan",
            "description": "User-friendly formatted version of the generated lesson plans.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1126,
            "workflow_id": 192,
            "is_prerequisite": False
        },
        {
            "name": "filtered_tools",
            "description": "Tools filtered based on processing time, output quality, and user priorities.",
            "data_type": "typing.Dict[str, typing.Tuple[float, float]]",
            "format": "field",
            "id": 1127,
            "workflow_id": 193,
            "is_prerequisite": False
        },
        {
            "name": "task_requirements",
            "description": "Requirements extracted from the input tasks for tool selection.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 1128,
            "workflow_id": 193,
            "is_prerequisite": False
        },
        {
            "name": "optimal_tools",
            "description": "Optimal tools along with recommended parameters for completing each task based on the given context.",
            "data_type": "typing.Dict[str, typing.Tuple[str, typing.Any]]",
            "format": "field",
            "id": 1129,
            "workflow_id": 193,
            "is_prerequisite": False
        },
        {
            "name": "persistence_data",
            "description": "Data that needs to be persisted for future use by the app.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1130,
            "workflow_id": 193,
            "is_prerequisite": False
        },
        {
            "name": "Topics",
            "description": "A list of topics for the lesson plan.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1131,
            "workflow_id": 194,
            "is_prerequisite": False
        },
        {
            "name": "DesiredOutcomes",
            "description": "A dictionary where keys are topics and values are desired outcomes.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 1132,
            "workflow_id": 194,
            "is_prerequisite": False
        },
        {
            "name": "PreparedTopics",
            "description": "Topics prepared for the lesson plan generation.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1133,
            "workflow_id": 194,
            "is_prerequisite": False
        },
        {
            "name": "PreparedOutcomes",
            "description": "Desired outcomes prepared for validation.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 1134,
            "workflow_id": 194,
            "is_prerequisite": False
        },
        {
            "name": "LessonPlans",
            "description": "Generated lesson plans for each topic.",
            "data_type": "typing.List[dict]",
            "format": "field",
            "id": 1135,
            "workflow_id": 194,
            "is_prerequisite": False
        },
        {
            "name": "ValidationReport",
            "description": "Report on curriculum validation.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1136,
            "workflow_id": 194,
            "is_prerequisite": False
        },
        {
            "name": "parsed_packages",
            "description": "List of packages parsed from package manager files",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "id": 1137,
            "workflow_id": 195,
            "is_prerequisite": False
        },
        {
            "name": "dependency_graph",
            "description": "Directed graph representing software ecosystems",
            "data_type": "networkx.DiGraph",
            "id": 1138,
            "workflow_id": 195,
            "is_prerequisite": False
        },
        {
            "name": "edge_styling_data",
            "description": "Data for styling edges based on dependency strength or direction",
            "data_type": "typing.Any",
            "id": 1139,
            "workflow_id": 195,
            "is_prerequisite": False
        },
        {
            "name": "node_styling_data",
            "description": "Data for styling nodes based on package properties like popularity or version",
            "data_type": "typing.Any",
            "id": 1140,
            "workflow_id": 195,
            "is_prerequisite": False
        },
        {
            "name": "tasks",
            "description": "List of tasks with their required steps.",
            "data_type": "typing.List[dict]",
            "format": "field",
            "id": 1141,
            "workflow_id": 197,
            "is_prerequisite": False
        },
        {
            "name": "filtered_tools",
            "description": "Tools filtered based on task requirements.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1142,
            "workflow_id": 197,
            "is_prerequisite": False
        },
        {
            "name": "suggested_tools",
            "description": "Suggested tools and parameters for each step.",
            "data_type": "typing.Dict[str, typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 1143,
            "workflow_id": 197,
            "is_prerequisite": False
        },
        {
            "name": "selected_tools",
            "description": "Selected tools for each step with their parameters.",
            "data_type": "typing.Dict[str, typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 1144,
            "workflow_id": 197,
            "is_prerequisite": False
        },
        {
            "name": "report_output_format",
            "description": "Format(s) specified for report output.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1145,
            "workflow_id": 197,
            "is_prerequisite": False
        },
        {
            "name": "generated_report_path",
            "description": "Path to the generated report file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1146,
            "workflow_id": 197,
            "is_prerequisite": False
        },
        {
            "name": "rss_feeds",
            "description": "List of RSS feed URLs to aggregate news articles from.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1147,
            "workflow_id": 198,
            "is_prerequisite": False
        },
        {
            "name": "articles",
            "description": "Aggregated news articles with title, source, publication date, and content.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 1148,
            "workflow_id": 198,
            "is_prerequisite": False
        },
        {
            "name": "summarized_articles",
            "description": "Articles with summarized content.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 1149,
            "workflow_id": 198,
            "is_prerequisite": False
        },
        {
            "name": "newsletter_content",
            "description": "Combined and formatted newsletter content in markdown format.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1150,
            "workflow_id": 198,
            "is_prerequisite": False
        },
        {
            "name": "output_file_path",
            "description": "File path where the daily newsletter will be saved in markdown format.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1151,
            "workflow_id": 198,
            "is_prerequisite": False
        },
        {
            "name": "input_image_path",
            "description": "Path to the input color image file.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1152,
            "workflow_id": 199,
            "is_prerequisite": False
        },
        {
            "name": "segmentation_parameters",
            "description": "Parameters for image segmentation (e.g., color similarity threshold).",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 1153,
            "workflow_id": 199,
            "is_prerequisite": False
        },
        {
            "name": "morphological_operations",
            "description": "Operations to refine segmented regions (e.g., dilation, erosion).",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1154,
            "workflow_id": 199,
            "is_prerequisite": False
        },
        {
            "name": "min_region_size",
            "description": "Minimum size of a region to consider it as valid (to remove noise-like regions).",
            "data_type": "builtins.int",
            "format": "field",
            "id": 1155,
            "workflow_id": 199,
            "is_prerequisite": False
        },
        {
            "name": "label_matrix",
            "description": "Matrix where each pixel's value represents its corresponding region ID.",
            "data_type": "typing.NumpyNDArray[typing.Union[builtins.int, builtins.float]]",
            "format": "field",
            "id": 1156,
            "workflow_id": 199,
            "is_prerequisite": False
        },
        {
            "name": "GeneratedFunctions",
            "description": "List of mathematical functions generated on-the-fly.",
            "data_type": "typing.List[builtins.type]",
            "format": "field",
            "id": 1157,
            "workflow_id": 200,
            "is_prerequisite": False
        },
        {
            "name": "ProblematicStateInfo",
            "description": "Information about the problematic part of state that needs reinitialization.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1158,
            "workflow_id": 200,
            "is_prerequisite": False
        },
        {
            "name": "NewFunctionGenerator",
            "description": "A function generator for creating new functions in case of recovery.",
            "data_type": "builtins.type",
            "format": "field",
            "id": 1159,
            "workflow_id": 200,
            "is_prerequisite": False
        },
        {
            "name": "ErrorRecoveryLog",
            "description": "Log file path for storing error messages and recovery information.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1160,
            "workflow_id": 200,
            "is_prerequisite": False
        },
        {
            "name": "parsed_data",
            "description": "The parsed OpenAPI/Swagger data.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 1161,
            "workflow_id": 201,
            "is_prerequisite": False
        },
        {
            "name": "api_info_list",
            "description": "A list of API information extracted from the parsed data.",
            "data_type": "typing.List[typing.Dict[builtins.str, typing.Any]]",
            "format": "field",
            "id": 1162,
            "workflow_id": 201,
            "is_prerequisite": False
        },
        {
            "name": "markdown_documentation",
            "description": "The generated markdown documentation as a string.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1163,
            "workflow_id": 201,
            "is_prerequisite": False
        },
        {
            "name": "error_logs",
            "description": "A list of error logs encountered during execution.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 1164,
            "workflow_id": 201,
            "is_prerequisite": False
        },
        {
            "name": "cleaned_json",
            "description": "The input JSON data after cleaning.",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 1165,
            "workflow_id": 203,
            "is_prerequisite": False
        },
        {
            "name": "taxonomy",
            "description": "The built taxonomy represented as a list of tuples.",
            "data_type": "typing.List[tuple[str, typing.Union[str, None]]]",
            "format": "field",
            "id": 1166,
            "workflow_id": 203,
            "is_prerequisite": False
        },
        {
            "name": "extracted_data",
            "description": "Relevant data extracted from the collection of studies.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 1167,
            "workflow_id": 204,
            "is_prerequisite": False
        },
        {
            "name": "selected_studies",
            "description": "Studies selected based on predefined criteria.",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 1168,
            "workflow_id": 204,
            "is_prerequisite": False
        },
        {
            "name": "heterogeneity_results",
            "description": "Results from the heterogeneity testing.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 1169,
            "workflow_id": 204,
            "is_prerequisite": False
        },
        {
            "name": "selected_model",
            "description": "Selected effect model (fixed or random) based on heterogeneity results.",
            "data_type": "str",
            "format": "field",
            "id": 1170,
            "workflow_id": 204,
            "is_prerequisite": False
        },
        {
            "name": "pooled_effect_size",
            "description": "Calculated pooled effect size using the selected model.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 1171,
            "workflow_id": 204,
            "is_prerequisite": False
        },
        {
            "name": "analysis_report_path",
            "description": "Path to the markdown file containing the comprehensive report of analysis steps and results.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1172,
            "workflow_id": 204,
            "is_prerequisite": False
        },
        {
            "name": "random_points",
            "description": "Random points generated within the unit square.",
            "data_type": "typing.List[tuple[float, float]]",
            "format": "field",
            "id": 1173,
            "workflow_id": 205,
            "is_prerequisite": False
        },
        {
            "name": "number_of_hits",
            "description": "Number of random points that fall within the unit circle.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 1174,
            "workflow_id": 205,
            "is_prerequisite": False
        },
        {
            "name": "estimated_pi",
            "description": "Estimated value of Pi based on random sampling.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 1175,
            "workflow_id": 205,
            "is_prerequisite": False
        },
        {
            "name": "accuracy",
            "description": "Accuracy of the estimated Pi value.",
            "data_type": "builtins.float",
            "format": "field",
            "id": 1176,
            "workflow_id": 205,
            "is_prerequisite": False
        },
        {
            "name": "adjacency_list",
            "description": "Adjacency list representation of the dependency graph.",
            "data_type": "typing.Dict[builtins.str, typing.List[builtins.str]]",
            "format": "field",
            "id": 1177,
            "workflow_id": 206,
            "is_prerequisite": False
        },
        {
            "name": "incompatible_dependencies",
            "description": "List of detected incompatible dependencies.",
            "data_type": "typing.List[typing.Tuple[builtins.str, builtins.str]]",
            "format": "field",
            "id": 1178,
            "workflow_id": 206,
            "is_prerequisite": False
        },
        {
            "name": "circular_dependencies",
            "description": "List of detected circular dependencies.",
            "data_type": "typing.List[typing.List[builtins.str]]",
            "format": "field",
            "id": 1179,
            "workflow_id": 206,
            "is_prerequisite": False
        },
        {
            "name": "output_file_path",
            "description": "Path to the file where output data will be stored.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1180,
            "workflow_id": 206,
            "is_prerequisite": False
        },
        {
            "name": "Entities",
            "description": "Extracted entities from the text.",
            "data_type": "typing.Set[str]",
            "format": "field",
            "id": 1181,
            "workflow_id": 207,
            "is_prerequisite": False
        },
        {
            "name": "Relationships",
            "description": "Extracted relationships between entities.",
            "data_type": "typing.List[tuple[str, str]]",
            "format": "field",
            "id": 1182,
            "workflow_id": 207,
            "is_prerequisite": False
        },
        {
            "name": "Graph",
            "description": "RDF-like graph representation of entities and their relationships.",
            "data_type": "networkx.Graph",
            "format": "field",
            "id": 1183,
            "workflow_id": 207,
            "is_prerequisite": False
        },
        {
            "name": "PreprocessedText",
            "description": "Cleaned and tokenized input text.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1184,
            "workflow_id": 207,
            "is_prerequisite": False
        },
        {
            "name": "task_description",
            "description": "A detailed description of the task to be accomplished.",
            "data_type": "builtins.str",
            "id": 1185,
            "workflow_id": 208,
            "is_prerequisite": False
        },
        {
            "name": "unique_steps",
            "description": "The unique steps required to complete the task, identified during Step Identification.",
            "data_type": "typing.List[builtins.str]",
            "id": 1186,
            "workflow_id": 208,
            "is_prerequisite": False
        },
        {
            "name": "tools_researched",
            "description": "A dictionary containing tools researched for each step, with the step as the key and a list of tools as the value.",
            "data_type": "typing.Dict[builtins.str, typing.List[str]]",
            "id": 1187,
            "workflow_id": 208,
            "is_prerequisite": False
        },
        {
            "name": "optimal_parameters",
            "description": "A dictionary containing optimal parameters for selected tools. The tool name is the key and the parameters are the value.",
            "data_type": "typing.Dict[str, typing.Any]",
            "id": 1188,
            "workflow_id": 208,
            "is_prerequisite": False
        },
        {
            "name": "selected_tools",
            "description": "A list of tools selected to be integrated into a single workflow.",
            "data_type": "typing.List[str]",
            "id": 1189,
            "workflow_id": 208,
            "is_prerequisite": False
        },
        {
            "name": "workflow_script",
            "description": "The final script integrating all selected tools and parameters.",
            "format": "file",
            "id": 1190,
            "workflow_id": 208,
            "is_prerequisite": False
        },
        {
            "name": "input_code_file",
            "description": "The code file to be reviewed.",
            "data_type": "builtins.str",
            "format": "field",
            "id": 1191,
            "workflow_id": 209,
            "is_prerequisite": False
        },
        {
            "name": "pylint_output",
            "description": "The output generated by pylint after running on the input code file.",
            "data_type": "typing.Any",
            "format": "field",
            "id": 1192,
            "workflow_id": 209,
            "is_prerequisite": False
        },
        {
            "name": "violated_rules",
            "description": "A list of violated rules extracted from pylint's output.",
            "data_type": "typing.List[builtins.str]",
            "format": "field",
            "id": 1193,
            "workflow_id": 209,
            "is_prerequisite": False
        },
        {
            "name": "report",
            "description": "The generated report based on the extracted violations.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1194,
            "workflow_id": 209,
            "is_prerequisite": False
        },
        {
            "name": "fix_suggestions",
            "description": "A dictionary containing suggestions for fixing common violations.",
            "data_type": "typing.Dict[builtins.str, typing.Any]",
            "format": "field",
            "id": 1195,
            "workflow_id": 209,
            "is_prerequisite": False
        },
        {
            "name": "Topics",
            "description": "List of topics or desired outcomes for the lesson plan.",
            "data_type": "typing.List[str]",
            "format": "field",
            "id": 1196,
            "workflow_id": 210,
            "is_prerequisite": False
        },
        {
            "name": "Subtopics",
            "description": "Dictionaries containing topic as key and list of subtopics as value.",
            "data_type": "typing.Dict[str, typing.List[str]]",
            "format": "field",
            "id": 1197,
            "workflow_id": 210,
            "is_prerequisite": False
        },
        {
            "name": "Objectives",
            "description": "Dictionaries containing subtopic as key and learning objective as value.",
            "data_type": "typing.Dict[str, str]",
            "format": "field",
            "id": 1198,
            "workflow_id": 210,
            "is_prerequisite": False
        },
        {
            "name": "Resources",
            "description": "File paths or URLs of resources relevant to each topic or subtopic.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1199,
            "workflow_id": 210,
            "is_prerequisite": False
        },
        {
            "name": "explainer",
            "description": "The explainer object generated by the XAI library.",
            "data_type": "<XAI_library.Explainer>",
            "format": "field",
            "id": 1200,
            "workflow_id": 211,
            "is_prerequisite": False
        },
        {
            "name": "feature_importance",
            "description": "The feature importance values generated by the model explainability step.",
            "data_type": "typing.List[builtins.float]",
            "format": "field",
            "id": 1201,
            "workflow_id": 211,
            "is_prerequisite": False
        },
        {
            "name": "surrogate_model",
            "description": "The global surrogate model created to explain overall model behavior.",
            "data_type": "<sklearn.base.BaseEstimator>",
            "format": "field",
            "id": 1202,
            "workflow_id": 211,
            "is_prerequisite": False
        },
        {
            "name": "explanations_dir_path",
            "description": "The directory path where XAI explanations are saved.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1203,
            "workflow_id": 211,
            "is_prerequisite": False
        },
        {
            "name": "pycodestyle_result",
            "description": "Result of pycodestyle check.",
            "data_type": "typing.Dict[str, typing.List[str]]",
            "id": 1204,
            "workflow_id": 213,
            "is_prerequisite": False
        },
        {
            "name": "pyflakes3_result",
            "description": "Result of pyflakes3 linting.",
            "data_type": "typing.Any",
            "id": 1205,
            "workflow_id": 213,
            "is_prerequisite": False
        },
        {
            "name": "coverage_result",
            "description": "Result of test coverage generation.",
            "data_type": "builtins.str",
            "format": "file",
            "id": 1206,
            "workflow_id": 213,
            "is_prerequisite": False
        },
        {
            "name": "imputed_dataset",
            "description": "The dataset after missing value imputation.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 1207,
            "workflow_id": 214,
            "is_prerequisite": False
        },
        {
            "name": "demographic_parity_ratio",
            "description": "The demographic parity ratio calculated for the dataset.",
            "data_type": "float",
            "format": "field",
            "id": 1208,
            "workflow_id": 214,
            "is_prerequisite": False
        },
        {
            "name": "equal_opportunity_ratio",
            "description": "The equal opportunity ratio calculated for the dataset.",
            "data_type": "float",
            "format": "field",
            "id": 1209,
            "workflow_id": 214,
            "is_prerequisite": False
        },
        {
            "name": "mitigated_dataset",
            "description": "The dataset after bias mitigation techniques have been applied.",
            "data_type": "pandas.DataFrame",
            "format": "field",
            "id": 1210,
            "workflow_id": 214,
            "is_prerequisite": False
        },
        {
            "name": "Chessboard Dimension",
            "description": "The size of the chessboard (NxN)",
            "data_type": "typing.Tuple[int, int]",
            "format": "field",
            "id": 1211,
            "workflow_id": 215,
            "is_prerequisite": False
        },
        {
            "name": "Center Position",
            "description": "The coordinates of the center on an NxN board",
            "data_type": "typing.Tuple[int, int]",
            "format": "field",
            "id": 1212,
            "workflow_id": 215,
            "is_prerequisite": False
        },
        {
            "name": "Number of Simulations",
            "description": "The total number of random walks to simulate.",
            "data_type": "int",
            "format": "field",
            "id": 1213,
            "workflow_id": 215,
            "is_prerequisite": False
        },
        {
            "name": "Expected Moves",
            "description": "The expected number of moves to reach the center calculated from simulations.",
            "data_type": "float",
            "format": "field",
            "id": 1214,
            "workflow_id": 215,
            "is_prerequisite": False
        },
        {
            "name": "Standard Error",
            "description": "The standard error or confidence interval for the estimated expected moves.",
            "data_type": "float",
            "format": "field",
            "id": 1215,
            "workflow_id": 215,
            "is_prerequisite": False
        },
        {
            "name": "user_answers",
            "description": "List to store user's answers.",
            "data_type": "typing.List[builtins.bool]",
            "format": "field",
            "id": 1216,
            "workflow_id": 216,
            "is_prerequisite": False
        },
        {
            "name": "correct_streak",
            "description": "Track the number of consecutive correct/incorrect answers.",
            "data_type": "builtins.int",
            "format": "field",
            "id": 1217,
            "workflow_id": 216,
            "is_prerequisite": False
        },
        {
            "name": "processed_user_input",
            "description": "User input after preprocessing",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 1218,
            "workflow_id": 217,
            "is_prerequisite": False
        },
        {
            "name": "matched_tools",
            "description": "Tools from the dataset that match the user's task",
            "data_type": "typing.List[typing.Dict[str, typing.Any]]",
            "format": "field",
            "id": 1219,
            "workflow_id": 217,
            "is_prerequisite": False
        },
        {
            "name": "recommended_parameters",
            "description": "Recommended parameters or settings for the matched tools",
            "data_type": "typing.Dict[str, typing.Any]",
            "format": "field",
            "id": 1220,
            "workflow_id": 217,
            "is_prerequisite": False
        }
    ]
}