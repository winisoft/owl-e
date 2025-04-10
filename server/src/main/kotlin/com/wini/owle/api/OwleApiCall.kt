package com.wini.owle.api

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.CompositeDto
import io.ktor.http.*
import kotlin.reflect.KClass


sealed class OwleApiCall<T: BaseDto>(open val targetType: KClass<T>) {

    data class GetMany<T: BaseDto>(override val targetType: KClass<T>) : OwleApiCall<T>(targetType)
    data class GetOne<T: BaseDto>(override val targetType: KClass<T>) : OwleApiCall<T>(targetType)
    data class CreateOne<T: BaseDto>(override val targetType: KClass<T>) : OwleApiCall<T>(targetType)
    data class CreateMany<T: BaseDto>(override val targetType: KClass<T>) : OwleApiCall<T>(targetType)
    data class UpdateOne<T: BaseDto>(override val targetType: KClass<T>) : OwleApiCall<T>(targetType)
    data class UpdateMany<T: BaseDto>(override val targetType: KClass<T>) : OwleApiCall<T>(targetType)
    data class DeleteOne<T: BaseDto>(override val targetType: KClass<T>) : OwleApiCall<T>(targetType)
    data class DeleteMany<T: BaseDto>(override val targetType: KClass<T>) : OwleApiCall<T>(targetType)

    companion object {

        val <T: BaseDto> OwleApiCall<T>.isCompositePK: Boolean
            get() = CompositeDto::class.isInstance(targetType.objectInstance)

        val <T: BaseDto> OwleApiCall<T>.isLongPK: Boolean
            get() = isCompositePK.not()

        val <T: BaseDto> OwleApiCall<T>.isBatchRequest: Boolean
            get() = when (this) {
                is CreateMany,
                is DeleteMany,
                is GetMany,
                is UpdateMany -> true
                else -> false
            }

        val <T: BaseDto> OwleApiCall<T>.isOneItemRequest: Boolean
            get() = isBatchRequest.not()

        val <T: BaseDto> OwleApiCall<T>.successStatus: HttpStatusCode
            get() = when (this) {
                is CreateMany,
                is CreateOne,
                    -> HttpStatusCode.Created
                is DeleteMany,
                is DeleteOne,
                    -> HttpStatusCode.NoContent
                else
                    -> HttpStatusCode.OK
            }
    }
}