package com.wini.owle.util


class Logs {

    private const val MAX_LOG_SIZE_BYTES = 10_485_760 // 10MB
    private const val MAX_LOG_FILES = 5

    fun setupLogging(): Logger {
        val logDir = getLogDirectory()
        logDir.mkdirs()

        val logger = Logger.getLogger(APP_NAME)
        val timestamp = java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        )
        
        val logFile = File(logDir, "app_$timestamp.log")
        val fileHandler = FileHandler(logFile.absolutePath)
        fileHandler.formatter = SimpleFormatter()
        
        logger.addHandler(fileHandler)
        logger.info("Log file initialized at: ${logFile.absolutePath}")
        
        return logger
    }

    private fun getLogDirectory(): File {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")

        val logDir = when {
            os.contains("win") -> {
                // Windows: %AppData%\Local\YourAppName\logs
                File(System.getenv("LOCALAPPDATA"), "$APP_NAME/logs")
            }
            os.contains("mac") -> {
                // macOS: ~/Library/Application Support/YourAppName/logs
                File(userHome, "Library/Application Support/$APP_NAME/logs")
            }
            else -> {
                // Linux: ~/.local/share/YourAppName/logs
                File(userHome, ".local/share/$APP_NAME/logs")
            }
        }

        return logDir
    }
        
    private val logFile: File by lazy {
        val dir = File(LOG_DIR)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        File(dir, "owle-${System.currentTimeMillis()}.log")
    }

    private val logLock = ReentrantReadWriteLock()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")


    fun d(message: String) = log(Level.DEBUG, message)
    fun i(message: String) = log(Level.INFO, message)
    fun w(message: String) = log(Level.WARN, message)
    fun e(message: String) = log(Level.ERROR, message)
    fun e(message: String, throwable: Throwable) {
        e("$message\n${throwable.stackTraceToString()}")
    }

    private fun log(level: Level, message: String) {
        val timestamp = dateFormat.format(Date())
        val logMessage = "$timestamp ${level.prefix} $message\n"

        logLock.write {
            try {
                if (logFile.length() > MAX_LOG_SIZE_BYTES) {
                    rotateLogFiles()
                }
                logFile.appendText(logMessage)
            } catch (e: Exception) {
                System.err.println("Failed to write to log file: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun rotateLogFiles() {
        val dir = File(LOG_DIR)
        val logFiles = dir.listFiles { file -> file.name.startsWith("owle-") }
            ?.sortedByDescending { it.lastModified() }
            ?: return

        // Delete oldest files if we exceed max count
        if (logFiles.size >= MAX_LOG_FILES) {
            logFiles.drop(MAX_LOG_FILES - 1).forEach { it.delete() }
        }

        // Rename current log file with timestamp
        val timestamp = System.currentTimeMillis()
        logFile.renameTo(File(dir, "owle-$timestamp.log"))
    }

    
    enum class Level(val prefix: String) {
        DEBUG("[DEBUG]"),
        INFO("[INFO]"),
        WARN("[WARN]"),
        ERROR("[ERROR]")
    }
}