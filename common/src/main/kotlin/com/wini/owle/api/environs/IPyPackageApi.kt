package com.wini.owle.api.environs

import com.wini.owle.api.IOwleApi


interface IPyPackageApi: IOwleApi {

    override val endpointPath: String get() = ENDPOINT_PATH

    override suspend fun getMany(block: suspend () -> Unit) { super.getMany(block) }

    
    override suspend fun getOne(block: suspend () -> Unit) { super.getOne(block) }

    
    override suspend fun postMany(block: suspend () -> Unit) { super.postMany(block) }

    
    override suspend fun postOne(block: suspend () -> Unit) { super.postOne(block) }

    
    override suspend fun putMany(block: suspend () -> Unit) { super.putMany(block) }

    
    override suspend fun putOne(block: suspend () -> Unit) { super.putOne(block) }

    
    override suspend fun deleteMany(block: suspend () -> Unit) { super.deleteMany(block) }

    
    override suspend fun deleteOne(block: suspend () -> Unit) { super.deleteOne(block) }

    companion object {
        const val ENDPOINT_PATH = "/package"
    }
}

