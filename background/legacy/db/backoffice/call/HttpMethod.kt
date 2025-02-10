package com.wini.owle.air.db.backoffice.call

sealed class HttpMethod(val key: String) {

    object Get: com.wini.owle.air.db.backoffice.call.HttpMethod("GET")

    object Post: com.wini.owle.air.db.backoffice.call.HttpMethod("POST")

    object Put: com.wini.owle.air.db.backoffice.call.HttpMethod("PUT")

    object Delete: com.wini.owle.air.db.backoffice.call.HttpMethod("DELETE")

    object Head: com.wini.owle.air.db.backoffice.call.HttpMethod("HEAD")

    object Connect: com.wini.owle.air.db.backoffice.call.HttpMethod("CONNECT")

    object Options: com.wini.owle.air.db.backoffice.call.HttpMethod("OPTIONS")

    object Trace: com.wini.owle.air.db.backoffice.call.HttpMethod("TRACE")

    companion object {
        fun fromString(value: String): com.wini.owle.air.db.backoffice.call.HttpMethod = when(value) {
            com.wini.owle.air.db.backoffice.call.HttpMethod.Get.key -> com.wini.owle.air.db.backoffice.call.HttpMethod.Get
            com.wini.owle.air.db.backoffice.call.HttpMethod.Post.key -> com.wini.owle.air.db.backoffice.call.HttpMethod.Post
            com.wini.owle.air.db.backoffice.call.HttpMethod.Put.key -> com.wini.owle.air.db.backoffice.call.HttpMethod.Put
            com.wini.owle.air.db.backoffice.call.HttpMethod.Delete.key -> com.wini.owle.air.db.backoffice.call.HttpMethod.Delete
            else -> error("Unanticipated HttpMethod key")
        }
    }
}