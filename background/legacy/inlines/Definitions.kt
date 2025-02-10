package com.wini.owle.air.inlines

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@JvmInline value class LoId(val value: Long)

@JvmInline value class ApiId(val value: Long)

@JvmInline value class ApiOverrideId(val value: Long)

@JvmInline value class CallId(val value: Long)

@JvmInline value class CalOverrideId(val value: Long)

@JvmInline value class CarOverrideId(val value: Long)

@JvmInline value class EndpointOverrideId(val value: Long)

@JvmInline value class EndpointId(val value: Long)

@JvmInline value class EnvironmentId(val value: Long)

@JvmInline value class LoSharedPrefId(val value: Long)

@JvmInline value class LoSysPropId(val value: Long)

@JvmInline value class CarPropId(val value: Long)

@JvmInline value class LoCustomPropId(val value: Long)

@JvmInline value class LoItemId(val value: Long)

@JvmInline value class HeaderId(val value: Long)

@JvmInline value class CalibrationId(val value: String)

@JvmInline value class SystemPropId(val value: Long)

@JvmInline value class SystemPropOverrideId(val value: Long)

@JvmInline value class SharedPrefId(val value: Long)

@JvmInline value class SharedPrefOverrideId(val value: Long)

@JvmInline value class LoRootItemId(val value: Long)

@JvmInline value class Id(val value: Int)
@JvmInline value class Name(val value: String)
@JvmInline value class Notes(val value: String?)
@JvmInline value class IsEnabled(val value: Boolean)
@JvmInline value class IsExpanded(val value: Boolean)
@JvmInline value class Description(val value: String?)
@JvmInline value class DefaultValue(val value: String?)
@JvmInline value class SharedPrefKey(val value: String)
@JvmInline value class EnvironmentName(val value: String)
// API
@JvmInline value class OverrideUrl(val value: String)
@JvmInline value class PathPrepended(val value: String)

// CALL
@JvmInline value class DelayMillis(val value: Long)
@JvmInline value class PostBody(val value: JsonElement)
@JvmInline value class ResponseBody(val value: JsonElement)
// ENDPOINT
@JvmInline value class EndpointPath(val value: String)
@JvmInline value class LoRoute(val value: String)
// PROP
@JvmInline value class GroupName(val value: String)
@JvmInline value class GroupingId(val value: Long)
@JvmInline value class CalDefFileName(val value: String)
@JvmInline value class CalDefFileVersion(val value: Long)
@JvmInline value class CalVersion(val value: Long)
@JvmInline value class CalArrayLen(val value: Long?)
@JvmInline value class CalEncoding(val value: String)
@JvmInline value class CalEnumId(val value: String)
@JvmInline value class CalName(val value: String)
@JvmInline value class CalIsOverride(val value: Boolean)
@JvmInline value class CalType(val value: Long)
@JvmInline value class CalValue(val value: String?)
@JvmInline value class CalId(val value: String)
@JvmInline value class CalMin(val value: Float?)
@JvmInline value class CalMax(val value: Float?)
@JvmInline value class PropValue(val value: String?)
@JvmInline value class Key(val value: String)
@JvmInline value class GroupId(val value: Long)
@JvmInline value class EnvironmentGroupId(val value: Long)
@JvmInline value class SharedPrefRepoId(val value: String)

@JvmInline @Serializable value class QueryParams(val value: Map<String, List<String>>?)
@JvmInline @Serializable value class RequestHeaders(val value: Map<String, List<String>>)
@JvmInline @Serializable value class ResponseHeaders(val value: Map<String, List<String>>)
@JvmInline @Serializable value class OverrideName(val value: String)
@JvmInline @Serializable value class OverrideValue(val value: String)
@JvmInline @Serializable value class SysPropKey(val value: String)
@JvmInline @Serializable value class IsHorizontalDisabled(val value: Boolean)
@JvmInline @Serializable value class Utility(val value: String)
@JvmInline @Serializable value class AutoRun(val value: Boolean)
@JvmInline @Serializable  value class FireIntensity(val value: Long)
@JvmInline @Serializable value class TextScale(val value: Long)
@JvmInline @Serializable value class CloseBtnMinimizes(val value: Boolean)










