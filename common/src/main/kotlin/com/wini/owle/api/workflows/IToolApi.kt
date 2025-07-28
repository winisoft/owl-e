package com.wini.owle.api.workflows

import com.wini.owle.api.IOwleApi
import com.wini.owle.schema.api.OpMethod
import com.wini.owle.schema.api.OpResult
import com.wini.owle.schema.api.OwleApiOp
import com.wini.owle.schema.api.OwleApiSpec

@OwleApiSpec("api.workflow.tool", [ "step" ])
interface IToolApi: IOwleApi {

    override val endpointPath: String get() = ENDPOINT_PATH

    @OwleApiOp("api.workflow.tool", OpMethod.GET, OpResult.MANY)
    override suspend fun getMany(block: suspend () -> Unit) { super.getMany(block) }

    @OwleApiOp("api.workflow.tool", OpMethod.GET, OpResult.ONE)
    override suspend fun getOne(block: suspend () -> Unit) { super.getOne(block) }

    @OwleApiOp("api.workflow.tool", OpMethod.POST, OpResult.MANY)
    override suspend fun postMany(block: suspend () -> Unit) { super.postMany(block) }

    @OwleApiOp("api.workflow.tool", OpMethod.POST, OpResult.ONE)
    override suspend fun postOne(block: suspend () -> Unit) { super.postOne(block) }

    @OwleApiOp("api.workflow.tool", OpMethod.PUT, OpResult.MANY)
    override suspend fun putMany(block: suspend () -> Unit) { super.putMany(block) }

    @OwleApiOp("api.workflow.tool", OpMethod.PUT, OpResult.ONE)
    override suspend fun putOne(block: suspend () -> Unit) { super.putOne(block) }

    @OwleApiOp("api.workflow.tool", OpMethod.DELETE, OpResult.MANY)
    override suspend fun deleteMany(block: suspend () -> Unit) { super.deleteMany(block) }

    @OwleApiOp("api.workflow.tool", OpMethod.DELETE, OpResult.ONE)
    override suspend fun deleteOne(block: suspend () -> Unit) { super.deleteOne(block) }



    companion object {
        const val ENDPOINT_PATH = "/tool-param"
    }
}