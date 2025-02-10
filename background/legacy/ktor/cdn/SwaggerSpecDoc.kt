package com.wini.owle.air.ktor.cdn

import com.gm.lo.desktop.ui.style.Res
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.util.pipeline.*
import kotlinx.html.*

suspend fun ApplicationCall.buildSpecHtml() {}
//= Res.html {
//    head {
//        charset("UTF-8")
//        link(rel = "stylesheet", href = "https://cdn.jsdelivr.net/npm/swagger-ui-dist@4.6.2/swagger-ui.css")
//        link(rel = "stylesheet", href = "./dark-swagger-user.css")
//    }
//    body {
//        div {
//            id = "root"
//        }
//        script {
//            """
//                window.onload = {
//                    window.ui = SwaggerUIBundle({
//                        spec: ${request.queryString()}
//                        dom_id: '#root'
//                    });
//                }
//            function setSpec(openApiSpec) {
//                var options = {
//                        spec: openApiSpec,
//                        dom_id: '#root'
//                }
//                SwaggerUIBundle(options);
//            }
//            """.trimIndent()
//        }
//        script(type = "text/javascript", src = "https://unpkg.com/swagger-ui-dist@4.6.2/swagger-ui-bundle.js") {}
//    }
//}