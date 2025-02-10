package com.wini.owle.air.api.operations

import kotlinx.serialization.Serializable

@Serializable
data class Param(
    override val baseName: String,
    override val baseType: String,
    override val dataType: String,
    override val description: String?,
    override val example: String,
    override val exclusiveMaximum: Boolean,
    override val exclusiveMinimum: Boolean,
    override val hasHeaders: Boolean,
    override val hasInnerObject: Boolean,
    override val hasMore: Boolean,
    override val isAlias: Boolean,
    override val isArrayModel: Boolean,
    override val isBinary: Boolean,
    override val isBodyParam: Boolean,
    override val isBoolean: Boolean,
    override val isByteArray: Boolean,
    override val isCollectionFormatMulti: Boolean,
    override val isContainer: Boolean,
    override val isCookieParam: Boolean,
    override val isDate: Boolean,
    override val isDateTime: Boolean,
    override val isDefault: Boolean,
    override val isDouble: Boolean,
    override val isEnum: Boolean,
    override val isFile: Boolean,
    override val isFloat: Boolean,
    override val isFormParam: Boolean,
    override val isHeaderParam: Boolean,
    override val isInteger: Boolean,
    override val isListContainer: Boolean,
    override val isLong: Boolean,
    override val isMapContainer: Boolean,
    override val isMultipart: Boolean,
    override val isNotContainer: Boolean,
    override val isNullable: Boolean,
    override val isNumber: Boolean,
    override val isNumeric: Boolean,
    override val isObject: Boolean,
    override val isPathParam: Boolean,
    override val isPrimitiveType: Boolean,
    override val isQueryParam: Boolean,
    override val isReadOnly: Boolean,
    override val isResponseBinary: Boolean,
    override val isResponseFile: Boolean,
    override val isString: Boolean,
    override val isUuid: Boolean,
    override val jsonSchema: String,
    override val notFile: Boolean,
    override val nullable: Boolean,
    override val paramName: String,
    override val required: Boolean,
    override val secondaryParam: Boolean,
    override val unescapedDescription: String,
    override val uniqueItems: Boolean,
    override val vendorExtensions: Map<String, Boolean>
): Parameter

interface Parameter {
    val baseName: String
    val baseType: String
    val dataType: String
    val description: String?
    val example: String
    val exclusiveMaximum: Boolean
    val exclusiveMinimum: Boolean
    val hasHeaders: Boolean
    val hasInnerObject: Boolean
    val hasMore: Boolean
    val isAlias: Boolean
    val isArrayModel: Boolean
    val isBinary: Boolean
    val isBodyParam: Boolean
    val isBoolean: Boolean
    val isByteArray: Boolean
    val isCollectionFormatMulti: Boolean
    val isContainer: Boolean
    val isCookieParam: Boolean
    val isDate: Boolean
    val isDateTime: Boolean
    val isDefault: Boolean
    val isDouble: Boolean
    val isEnum: Boolean
    val isFile: Boolean
    val isFloat: Boolean
    val isFormParam: Boolean
    val isHeaderParam: Boolean
    val isInteger: Boolean
    val isListContainer: Boolean
    val isLong: Boolean
    val isMapContainer: Boolean
    val isMultipart: Boolean
    val isNotContainer: Boolean
    val isNullable: Boolean
    val isNumber: Boolean
    val isNumeric: Boolean
    val isObject: Boolean
    val isPathParam: Boolean
    val isPrimitiveType: Boolean
    val isQueryParam: Boolean
    val isReadOnly: Boolean
    val isResponseBinary: Boolean
    val isResponseFile: Boolean
    val isString: Boolean
    val isUuid: Boolean
    val jsonSchema: String
    val notFile: Boolean
    val nullable: Boolean
    val paramName: String
    val required: Boolean
    val secondaryParam: Boolean
    val unescapedDescription: String
    val uniqueItems: Boolean
    val vendorExtensions: Map<String, Boolean>
}