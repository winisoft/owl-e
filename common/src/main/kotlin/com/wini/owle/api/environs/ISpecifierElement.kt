package com.wini.owle.api.environs

import com.wini.owle.api.IOwleApi
import com.wini.owle.schema.api.ApiOperationHttpMethod
import com.wini.owle.schema.api.ApiOperationCardinality
import com.wini.owle.schema.api.ApiOperationDefinition

import com.wini.owle.schema.api.ApiEndpointDefinition


@ApiEndpointDefinition("api.environs.specifierElement", [ "environs" ])
interface ISpecifierElement: IOwleApi {

    override val endpointPath: String get() = ENDPOINT_PATH
    
    @ApiOperationDefinition(ENDPOINT_SPEC_ID, ApiOperationHttpMethod.GET, ApiOperationCardinality.MANY)
    override suspend fun getMany(block: suspend () -> Unit) { super.getMany(block) }

    @ApiOperationDefinition(ENDPOINT_SPEC_ID, ApiOperationHttpMethod.GET, ApiOperationCardinality.ONE)
    override suspend fun getOne(block: suspend () -> Unit) { super.getOne(block) }

    @ApiOperationDefinition(ENDPOINT_SPEC_ID, ApiOperationHttpMethod.POST, ApiOperationCardinality.MANY)
    override suspend fun postMany(block: suspend () -> Unit) { super.postMany(block) }

    @ApiOperationDefinition(ENDPOINT_SPEC_ID, ApiOperationHttpMethod.POST, ApiOperationCardinality.ONE)
    override suspend fun postOne(block: suspend () -> Unit) { super.postOne(block) }

    @ApiOperationDefinition(ENDPOINT_SPEC_ID, ApiOperationHttpMethod.PUT, ApiOperationCardinality.MANY)
    override suspend fun putMany(block: suspend () -> Unit) { super.putMany(block) }

    @ApiOperationDefinition(ENDPOINT_SPEC_ID, ApiOperationHttpMethod.PUT, ApiOperationCardinality.ONE)
    override suspend fun putOne(block: suspend () -> Unit) { super.putOne(block) }

    @ApiOperationDefinition(ENDPOINT_SPEC_ID, ApiOperationHttpMethod.DELETE, ApiOperationCardinality.MANY)
    override suspend fun deleteMany(block: suspend () -> Unit) { super.deleteMany(block) }

    @ApiOperationDefinition(ENDPOINT_SPEC_ID, ApiOperationHttpMethod.DELETE, ApiOperationCardinality.ONE)
    override suspend fun deleteOne(block: suspend () -> Unit) { super.deleteOne(block) }

    companion object {
        const val ENDPOINT_SPEC_ID = "api.environs.specifierElement"
        const val ENDPOINT_PATH = ""
    }

}