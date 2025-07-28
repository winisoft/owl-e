package com.wini.owle.api.typing

import com.wini.owle.api.IOwleApi
import com.wini.owle.schema.api.OpMethod
import com.wini.owle.schema.api.OpResult
import com.wini.owle.schema.api.OwleApiOp

import com.wini.owle.schema.api.OwleApiSpec



@OwleApiSpec("api.typing.typeParameter", [ "type" ])
interface ITypeParameterApi: IOwleApi {

    override val endpointPath: String get() = ENDPOINT_PATH
    
    @OwleApiOp("api.typing.typeParameter", OpMethod.GET, OpResult.MANY)
    override suspend fun getMany(block: suspend () -> Unit) { super.getMany(block) }

    @OwleApiOp("api.typing.typeParameter", OpMethod.GET, OpResult.ONE)
    override suspend fun getOne(block: suspend () -> Unit) { super.getOne(block) }

    @OwleApiOp("api.typing.typeParameter", OpMethod.POST, OpResult.MANY)
    override suspend fun postMany(block: suspend () -> Unit) { super.postMany(block) }

    @OwleApiOp("api.typing.typeParameter", OpMethod.POST, OpResult.ONE)
    override suspend fun postOne(block: suspend () -> Unit) { super.postOne(block) }

    @OwleApiOp("api.typing.typeParameter", OpMethod.PUT, OpResult.MANY)
    override suspend fun putMany(block: suspend () -> Unit) { super.putMany(block) }

    @OwleApiOp("api.typing.typeParameter", OpMethod.PUT, OpResult.ONE)
    override suspend fun putOne(block: suspend () -> Unit) { super.putOne(block) }

    @OwleApiOp("api.typing.typeParameter", OpMethod.DELETE, OpResult.MANY)
    override suspend fun deleteMany(block: suspend () -> Unit) { super.deleteMany(block) }

    @OwleApiOp("api.typing.typeParameter", OpMethod.DELETE, OpResult.ONE)
    override suspend fun deleteOne(block: suspend () -> Unit) { super.deleteOne(block) }



    companion object {
        const val ENDPOINT_PATH = "/type-parameter"
    }
}