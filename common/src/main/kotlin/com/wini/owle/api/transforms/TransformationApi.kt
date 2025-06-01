package com.wini.owle.api.transforms

import com.wini.owle.api.IOwleApi
import com.wini.owle.schema.api.ApiOperationHttpMethod
import com.wini.owle.schema.api.ApiOperationCardinality
import com.wini.owle.schema.api.ApiOperationDefinition

import com.wini.owle.schema.api.ApiEndpointDefinition



@ApiEndpointDefinition("api.transforms.transform", [ "transforms" ])
interface TransformationApi: IOwleApi {

    override val endpointPath: String get() = ENDPOINT_PATH
    
    @ApiOperationDefinition("api.transforms.transform", ApiOperationHttpMethod.GET, ApiOperationCardinality.MANY)
    override suspend fun getMany(block: suspend () -> Unit) { super.getMany(block) }

    @ApiOperationDefinition("api.transforms.transform", ApiOperationHttpMethod.GET, ApiOperationCardinality.ONE)
    override suspend fun getOne(block: suspend () -> Unit) { super.getOne(block) }

    @ApiOperationDefinition("api.transforms.transform", ApiOperationHttpMethod.POST, ApiOperationCardinality.MANY)
    override suspend fun postMany(block: suspend () -> Unit) { super.postMany(block) }

    @ApiOperationDefinition("api.transforms.transform", ApiOperationHttpMethod.POST, ApiOperationCardinality.ONE)
    override suspend fun postOne(block: suspend () -> Unit) { super.postOne(block) }

    @ApiOperationDefinition("api.transforms.transform", ApiOperationHttpMethod.PUT, ApiOperationCardinality.MANY)
    override suspend fun putMany(block: suspend () -> Unit) { super.putMany(block) }

    @ApiOperationDefinition("api.transforms.transform", ApiOperationHttpMethod.PUT, ApiOperationCardinality.ONE)
    override suspend fun putOne(block: suspend () -> Unit) { super.putOne(block) }

    @ApiOperationDefinition("api.transforms.transform", ApiOperationHttpMethod.DELETE, ApiOperationCardinality.MANY)
    override suspend fun deleteMany(block: suspend () -> Unit) { super.deleteMany(block) }

    @ApiOperationDefinition("api.transforms.transform", ApiOperationHttpMethod.DELETE, ApiOperationCardinality.ONE)
    override suspend fun deleteOne(block: suspend () -> Unit) { super.deleteOne(block) }



    companion object {
        const val ENDPOINT_PATH = "/transform"
    }
}