package com.wini.owle.api

import com.wini.owle.validators.DtoValidator
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.entity.composteid.CompositeIdFactory
import io.ktor.server.application.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID


abstract class CompositeIdEndpointImpl<T: BaseDto>(
    override val dll: DLL<CompositeID, T>,
    serializer: KSerializer<T>,
    override val validator: DtoValidator<T>,
    open val compositeIdFactory: CompositeIdFactory<T>
): BaseEndpointImpl<CompositeID, T>(
    dll = dll,
    dtoSerializer = serializer,
    validator = validator,
    dtoListSerializer = ListSerializer(serializer)
) {

    abstract override fun ApplicationCall.readId(): AsyncResult<CompositeID>

    override suspend fun ApplicationCall.readIds(): AsyncResult<List<CompositeID>> {
        return when(val dtosResult = readDtos(dtoListSerializer, validator)) {
            is AsyncResult.Success ->
                dtosResult.data
                    .map { compositeIdFactory.fromDtoInstance(it) }
                    .let { compositeIds ->
                        AsyncResult.Success(compositeIds)
                    }
            is AsyncResult.Failure ->
                return dtosResult
        }
    }

}
