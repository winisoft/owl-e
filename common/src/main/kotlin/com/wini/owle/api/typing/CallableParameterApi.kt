package com.wini.owle.api.typing

import com.wini.owle.api.IOwleApi
import com.wini.owle.schema.api.ApiEndpointDefinition
import com.wini.owle.schema.api.ApiOperationCardinality
import com.wini.owle.schema.api.ApiOperationDefinition
import com.wini.owle.schema.api.ApiOperationHttpMethod


@ApiEndpointDefinition("api.typing.callableParameterApi", [ "type" ])
interface CallableParameterApi: IOwleApi {

    override val endpointPath: String get() = "/callable-parameter"

    @ApiOperationDefinition("api.typing.callableParameterApi", ApiOperationHttpMethod.GET, ApiOperationCardinality.MANY)
    override suspend fun getMany(block: suspend () -> Unit) { super.getMany(block) }

    @ApiOperationDefinition("api.typing.callableParameterApi", ApiOperationHttpMethod.GET, ApiOperationCardinality.ONE)
    override suspend fun getOne(block: suspend () -> Unit) { super.getOne(block) }

    @ApiOperationDefinition("api.typing.callableParameterApi", ApiOperationHttpMethod.POST, ApiOperationCardinality.MANY)
    override suspend fun postMany(block: suspend () -> Unit) { super.postMany(block) }

    @ApiOperationDefinition("api.typing.callableParameterApi", ApiOperationHttpMethod.POST, ApiOperationCardinality.ONE)
    override suspend fun postOne(block: suspend () -> Unit) { super.postOne(block) }

    @ApiOperationDefinition("api.typing.callableParameterApi", ApiOperationHttpMethod.PUT, ApiOperationCardinality.MANY)
    override suspend fun putMany(block: suspend () -> Unit) { super.putMany(block) }

    @ApiOperationDefinition("api.typing.callableParameterApi", ApiOperationHttpMethod.PUT, ApiOperationCardinality.ONE)
    override suspend fun putOne(block: suspend () -> Unit) { super.putOne(block) }

    @ApiOperationDefinition("api.typing.callableParameterApi", ApiOperationHttpMethod.DELETE, ApiOperationCardinality.MANY)
    override suspend fun deleteMany(block: suspend () -> Unit) { super.deleteMany(block) }

    @ApiOperationDefinition("api.typing.callableParameterApi", ApiOperationHttpMethod.DELETE, ApiOperationCardinality.ONE)
    override suspend fun deleteOne(block: suspend () -> Unit) { super.deleteOne(block) }

}
