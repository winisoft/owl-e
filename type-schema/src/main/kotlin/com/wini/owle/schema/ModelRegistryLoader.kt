package com.wini.owle.schema

import com.google.devtools.ksp.processing.KSPLogger
import com.wini.owle.content.doc.DtoModelDoc
import com.wini.owle.content.doc.ModelPropertyDoc
import com.wini.owle.content.doc.OwleErrorDoc
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Utility class for loading model information from the registry generated
 * by the common module's OwleModelProcessor.
 */
class ModelRegistryLoader(
    private val logger: KSPLogger
) {
    /**
     * Load model information from the registry file
     */
    fun loadModelRegistry(registryPath: String): ModelRegistryData {
        logger.info("Loading model registry from $registryPath")
        
        return try {
            val file = File(registryPath)
            if (!file.exists()) {
                logger.warn("Model registry file not found at $registryPath. No models will be imported from common module.")
                return ModelRegistryData(emptyMap(), emptyMap())
            }
            
            val json = Json { ignoreUnknownKeys = true }
            val registryData = json.decodeFromString<RegistryData>(file.readText())
            
            // Convert to DtoModelDoc format
            val modelMap = registryData.models.associate { model ->
                model.qualifiedName to DtoModelDoc(
                    name = model.name,
                    description = model.description,
                    properties = model.properties.map { prop ->
                        ModelPropertyDoc(
                            name = prop.name,
                            type = prop.type,
                            description = prop.description,
                            constraints = prop.constraints.map { constraint ->
                                OwleErrorDoc(
                                    errorCode = constraint.errorCode,
                                    message = constraint.message,
                                    attributes = constraint.parameters
                                )
                            }
                        )
                    }
                )
            }
            
            // Convert to OwleErrorDoc format
            val errorMap = registryData.errors.associate { error ->
                error.errorCode to OwleErrorDoc(
                    errorCode = error.errorCode,
                    message = error.message,
                    attributes = mapOf("description" to error.description)
                )
            }
            
            logger.info("Loaded ${modelMap.size} models and ${errorMap.size} errors from registry")
            
            ModelRegistryData(modelMap, errorMap)
        } catch (e: Exception) {
            logger.error("Error loading model registry: ${e.message}")
            e.printStackTrace()
            ModelRegistryData(emptyMap(), emptyMap())
        }
    }
    
    /**
     * Model registry data for internal use
     */
    data class ModelRegistryData(
        val models: Map<String, DtoModelDoc>,
        val errors: Map<String, OwleErrorDoc>
    )
    
    @Serializable
    internal data class RegistryData(
        val models: List<ModelInfo> = emptyList(),
        val errors: List<ErrorInfo> = emptyList()
    )
    
    @Serializable
    internal data class ModelInfo(
        val name: String,
        val qualifiedName: String,
        val description: String,
        val properties: List<PropertyInfo> = emptyList()
    )
    
    @Serializable
    internal data class PropertyInfo(
        val name: String,
        val type: String,
        val description: String,
        val constraints: List<ConstraintInfo> = emptyList(),
        val required: Boolean = true
    )
    
    @Serializable
    internal data class ConstraintInfo(
        val type: String,
        val errorCode: String,
        val message: String,
        val parameters: Map<String, String> = emptyMap()
    )
    
    @Serializable
    internal data class ErrorInfo(
        val errorCode: String,
        val message: String,
        val description: String
    )
}
