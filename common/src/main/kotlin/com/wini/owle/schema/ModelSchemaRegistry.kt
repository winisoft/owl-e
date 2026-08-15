package com.wini.owle.schema

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * A centralized registry for model schemas across modules.
 * 
 * This singleton allows processors running in different modules to share model information
 * when generating documentation, particularly for OpenAPI specifications.
 */
object ModelSchemaRegistry {
    // Map of model name to serialized schema information
    private val modelSchemas = mutableMapOf<String, ModelInfo>()
    
    // Map of error code to error information
    private val errorDefs = mutableMapOf<String, ErrorInfo>()
    
    /**
     * Register a model with its schema information
     */
    fun registerModel(modelInfo: ModelInfo) {
        modelSchemas[modelInfo.name] = modelInfo
    }
    
    /**
     * Register an error definition
     */
    fun registerError(errorInfo: ErrorInfo) {
        errorDefs[errorInfo.errorCode] = errorInfo
    }
    
    /**
     * Get all registered models
     */
    fun getAllModels(): Map<String, ModelInfo> = modelSchemas
    
    /**
     * Get all registered errors
     */
    fun getAllErrors(): Map<String, ErrorInfo> = errorDefs
    
    /**
     * Save the registry to a JSON file that can be read by other modules
     */
    fun saveToFile(targetDir: String) {
        val registry = RegistryData(modelSchemas.values.toList(), errorDefs.values.toList())
        val json = Json { prettyPrint = true; encodeDefaults = true }
        
        try {
            val dir = File(targetDir)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            
            File("$targetDir/model-registry.json").writeText(
                json.encodeToString<RegistryData>(registry)
            )
        } catch (e: Exception) {
            println("Error saving model registry: ${e.message}")
        }
    }
    
    /**
     * Load the registry from a JSON file
     */
    fun loadFromFile(filePath: String) {
        try {
            val file = File(filePath)
            if (file.exists()) {
                val json = Json { ignoreUnknownKeys = true }
                val registry = json.decodeFromString<RegistryData>(file.readText())
                
                registry.models.forEach { model ->
                    modelSchemas[model.name] = model
                }
                
                registry.errors.forEach { error -> 
                    errorDefs[error.errorCode] = error
                }
            }
        } catch (e: Exception) {
            println("Error loading model registry: ${e.message}")
        }
    }
}

/**
 * Information about a model's schema
 */
@Serializable
data class ModelInfo(
    val name: String,
    val qualifiedName: String,
    val description: String,
    val properties: List<PropertyInfo> = emptyList()
)

/**
 * Information about a property in a model
 */
@Serializable
data class PropertyInfo(
    val name: String,
    val type: String,
    val description: String,
    val constraints: List<ConstraintInfo> = emptyList(),
    val required: Boolean = true
)

/**
 * Information about a constraint on a property
 */
@Serializable
data class ConstraintInfo(
    val type: String,
    val errorCode: String,
    val message: String,
    val parameters: Map<String, String> = emptyMap()
)

/**
 * Information about an error
 */
@Serializable
data class ErrorInfo(
    val errorCode: String,
    val message: String,
    val description: String
)

/**
 * Serializable container for registry data
 */
@Serializable
data class RegistryData(
    val models: List<ModelInfo> = emptyList(),
    val errors: List<ErrorInfo> = emptyList()
)
