package com.wini.owle.api.user

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.dto.user.tags.TagRelation
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import io.ktor.server.application.*
import org.jetbrains.exposed.dao.id.CompositeID
import com.wini.owle.api.OwleApiCall
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
//
//class TagRelationApi(
//    dll: DLL<CompositeID, TagRelation>,
//    validator: DtoValidator<TagRelation>,
//    compositeIdFactory: CompositeIdFactory<TagRelation>
//): CompositeIdEndpointImpl<TagRelation>(
//    dll = dll,
//    serializer = TagRelation.serializer(),
//    compositeIdFactory = compositeIdFactory,
//    validator = validator
//) {
//
//    override fun register(routing: Routing) {
//        routing.apply {
//            get("/api/tag-relation") {
//                getMany { call.execute(OwleApiCall.GetMany) }
//            }
//            get("/api/tag-relation/id") {
//                getOne { call.execute(OwleApiCall.GetOne) }
//            }
//            post("/api/tag-relation") {
//                postMany { call.execute(OwleApiCall.CreateOne) }
//            }
//            post("/api/tag-relation/batch") {
//                postMany { call.execute(OwleApiCall.CreateMany) }
//            }
//            put("/api/tag-relation") {
//                putOne { call.execute(OwleApiCall.UpdateOne) }
//            }
//            put("/api/tag-relation/batch") {
//                putMany { call.execute(OwleApiCall.UpdateMany) }
//            }
//            delete("/api/tag-relation/id") {
//                deleteOne { call.execute(OwleApiCall.DeleteOne) }
//            }
//            delete("/api/tag-relation/batch") {
//                deleteMany { call.execute(OwleApiCall.DeleteMany) }
//            }
//        }
//    }
//
//}