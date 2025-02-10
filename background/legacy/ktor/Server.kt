package com.wini.owle.air.ktor

import com.wini.owle.air.ktor.feature.mocker.IResponseMocker
import io.ktor.http.*
import io.ktor.network.tls.certificates.*
import io.ktor.network.tls.extensions.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.dsl.module
import org.slf4j.LoggerFactory
import java.io.File
import java.security.KeyStore
import kotlin.text.get


class Server(
    private val responseMocker: IResponseMocker,
    val stateCallback: (ServerState) -> Unit
) {
    val pass = "abcd1234"

    val server: NettyApplicationEngine

    init {
        server = createServer()
    }

    private fun createServer(): NettyApplicationEngine {
        val alias = "certificateAlias"

        val keystore = buildKeyStore {
            certificate(alias) {
                hash = HashAlgorithm.SHA256
                sign = SignatureAlgorithm.ECDSA
                keySizeInBits = 256
                password = pass
            }
        }

        val server = embeddedServer(Netty, applicationEngineEnvironment {
            sslConnector(keystore,
                alias,
                { "".toCharArray() },
                { pass.toCharArray() }) {
                port = 55570
                keyStorePath = keyStore.asFile.absoluteFile

                module {
                    environment.monitor.apply {
                        subscribe(ApplicationStarted) {
                            stateCallback(ServerState.Running)
                        }
                        subscribe(ApplicationStopped) {
                            stateCallback(ServerState.Stopped)
                        }
                    }
                    intercept(ApplicationCallPipeline.Setup) {
                        val call = call
                        val path = call.request.path()
                        application.routing {
                            when (call.request.httpMethod) {
                                HttpMethod.Get -> get { responseMocker.mockResponse(this) }
                                HttpMethod.Post -> post { responseMocker.mockResponse(this) }
                                HttpMethod.Put -> put { responseMocker.mockResponse(this) }
                                HttpMethod.Delete -> put { }
                                else -> route(path) {}
                            }
                        }
                    }
                }
            }
        })

        return server
    }

    fun start() {
        server.start()
    }

    fun stop() {
        server.stop(gracePeriodMillis = 2000, timeoutMillis = 2000)
    }

    private val KeyStore.asFile: File
        get() {
            val keyStoreFile = File("build/temp.jks")
            this.saveToFile(keyStoreFile, pass)
            return keyStoreFile
        }
}
//
//class Server(
//    val ioScope: CoroutineScope,
//    val responseMocker: IResponseMocker,
//    val stateCallback: (ServerState) -> Unit
//) {
//
//    val alias = "lokiAlias"
//    val pass = "lokiPass"
//
//    val initializer: ApplicationEngineEnvironmentBuilder.() -> Unit = {
//        log = LoggerFactory.getLogger("ktor.application")
//        configureModule()
//    }
//
//    val certFile = File("resources/loki.jks")
//    val appEnvironment = applicationEngineEnvironment(initializer)
//    val cert = generateCertificate(certFile, keyAlias = alias, jksPassword = pass)
//
//
//    fun ApplicationEngineEnvironmentBuilder.configureModule() = module {
//        connector { port = 8080 }
//        sslConnector(keyStore = cert, alias, { "".toCharArray() }, { pass.toCharArray() }) {
//            port = 8443
//            //keyStorePath = certFile.also { keyStore.saveToFile(it, pass) }.absoluteFile
//        }
//        apply {
//            //install(io.ktor.features.DefaultHeaders)
//            //install(io.ktor.features.CORS) { anyHost() }
//            environment.monitor.apply {
//                subscribe(ApplicationStarted) {
//                    stateCallback(ServerState.Running)
//                }
//                subscribe(ApplicationStopped) {
//                    stateCallback(ServerState.Stopped)
//                }
//            }
//            install(io.ktor.routing.Routing.Feature) {
//                routing {
//                    intercept(ApplicationCallPipeline.Call) { interceptor ->
//                        val call = call
//                        val path = call.request.path()
//                        responseMocker.mockResponse(this)
//                    }
//                }
//            }
//        }
//    }
//
//    fun start() {
//        appEnvironment.start()
//    }
//
//    fun stop() {
//        appEnvironment.stop()
//    }
//}

//.apply {
//            embeddedServer(Netty, this) {
//                application.apply {
//                    install(DefaultHeaders)
//                    install(CORS) {
//                        anyHost()
//                    }
//
//                    intercept(ApplicationCallPipeline.Setup) {
//                        val call = call
//                        val path = call.request.path()
//                        responseMocker.mockResponse(this)
//                        routing {
//                            when (call.request.httpMethod) {
//                                HttpMethod.Get -> get { responseMocker.mockResponse(this) }
//                                HttpMethod.Post -> post { responseMocker.mockResponse(this) }
//                                HttpMethod.Put -> put { responseMocker.mockResponse(this) }
//                                HttpMethod.Delete -> put { }
//                                else -> route(path) {}
//                            }
//                        }
//                    }
//                }
////                    install(Routing.Feature) {
////                        intercept(ApplicationCallPipeline.Setup) { interceptor ->
////
////                            val call = call
////                            val path = call.request.path()
////
////                            routing {
////
////                                when (call.request.httpMethod) {
////                                    HttpMethod.Get -> get(path) {
////                                        responseMocker.mockResponse(this)
////                                    }
////                                    HttpMethod.Post -> post(path) {
////                                        responseMocker.mockResponse(this)
////                                    }
////                                    HttpMethod.Put -> put(path) {
////                                        responseMocker.mockResponse(this)
////                                    }
////                                    HttpMethod.Delete -> put(path) {
////
////                                    }
////                                    else -> route(path) {}
////                                }
////                            }
////                            return@intercept finish()
////                        }
////                    }
////                }
//            }
//        }.apply { instance = this; start(true) }
//            module {
//                NettyApplicationEngine.Configuration().apply {
//                    environment.monitor.apply {
//                        subscribe(ApplicationStarted) {
//                            stateCallback(ServerState.Running)
//                        }
//                        subscribe(ApplicationStopped) {
//                            stateCallback(ServerState.Stopped)
//                        }
//                    }
//                    install(Routing.Feature) {
//                        intercept(ApplicationCallPipeline.Setup) { interceptor ->
//
//                            val call = call
//                            val path = call.request.path()
//
//                            routing {
//
//                                when (call.request.httpMethod) {
//                                    HttpMethod.Get -> get(path) {
//                                        responseMocker.mockResponse(this)
//                                    }
//                                    HttpMethod.Post -> post(path) {
//                                        responseMocker.mockResponse(this)
//                                    }
//                                    HttpMethod.Put -> put(path) {
//                                        responseMocker.mockResponse(this)
//                                    }
//                                    HttpMethod.Delete -> put(path) {
//
//                                    }
//                                    else -> route(path) {}
//                                }
//                            }
//                            return@intercept finish()
//                        }
//                    }
//                }
//            }
//        }.start(true)
//        {
//            NettyApplicationEngine.Configuration().apply {
//                application.environment.monitor.apply {
//                    subscribe(ApplicationStarted) {
//                        stateCallback(ServerState.Running)
//                    }
//                    subscribe(ApplicationStopped) {
//                        stateCallback(ServerState.Stopped)
//                    }
//                }
//                routingFeature.install(application)
//            }
        //}
//    }.run {
//        stateCallback
//    }


//}

fun Application.loModule(stateCallback: (ServerState) -> Unit) {
    environment.monitor.subscribe(ApplicationStarted) {
        stateCallback(ServerState.Running)
    }
    environment.monitor.subscribe(ApplicationStopped) {
        stateCallback(ServerState.Stopped)
    }
}