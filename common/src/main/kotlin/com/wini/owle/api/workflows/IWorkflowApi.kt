package com.wini.owle.api.workflows

import com.wini.owle.api.IOwleApi
import com.wini.owle.schema.api.OpMethod
import com.wini.owle.schema.api.OpResult
import com.wini.owle.schema.api.OwleApiOp
import com.wini.owle.schema.api.OwleApiSpec



@OwleApiSpec("api.workflows.workflow", [ "workflows" ])
interface IWorkflowApi: IOwleApi {

    override val endpointPath: String get() = ENDPOINT_PATH
    
    @OwleApiOp("api.workflows.workflow", OpMethod.GET, OpResult.MANY)
    override suspend fun getMany(block: suspend () -> Unit) { super.getMany(block) }

    @OwleApiOp("api.workflows.workflow", OpMethod.GET, OpResult.ONE)
    override suspend fun getOne(block: suspend () -> Unit) { super.getOne(block) }

    @OwleApiOp("api.workflows.workflow", OpMethod.POST, OpResult.MANY)
    override suspend fun postMany(block: suspend () -> Unit) { super.postMany(block) }

    @OwleApiOp("api.workflows.workflow", OpMethod.POST, OpResult.ONE)
    override suspend fun postOne(block: suspend () -> Unit) { super.postOne(block) }

    @OwleApiOp("api.workflows.workflow", OpMethod.PUT, OpResult.MANY)
    override suspend fun putMany(block: suspend () -> Unit) { super.putMany(block) }

    @OwleApiOp("api.workflows.workflow", OpMethod.PUT, OpResult.ONE)
    override suspend fun putOne(block: suspend () -> Unit) { super.putOne(block) }

    @OwleApiOp("api.workflows.workflow", OpMethod.DELETE, OpResult.MANY)
    override suspend fun deleteMany(block: suspend () -> Unit) { super.deleteMany(block) }

    @OwleApiOp("api.workflows.workflow", OpMethod.DELETE, OpResult.ONE)
    override suspend fun deleteOne(block: suspend () -> Unit) { super.deleteOne(block) }



    companion object {
        const val ENDPOINT_PATH = "/workflow"
    }
}