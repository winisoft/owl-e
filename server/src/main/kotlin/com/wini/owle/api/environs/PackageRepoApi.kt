package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.PackageDto
import com.wini.owle.dto.environs.PackageRepoDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class PackageRepoApi(
    dll: DLL<Long, PackageRepoDto>,
    validator: (PackageRepoDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<PackageRepoDto>(
    dll = dll,
    serializer = PackageRepoDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/package-repo") {
                call.asyncResultList(getAll())
            }
            get("/api/package-repo/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/package-repo") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/package-repo/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/package-repo") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/package-repo/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/package-repo/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/package-repo/batch") {
                call.apply { asyncResultUnit(deleteBatch()) } 
            }
        }
    }
}