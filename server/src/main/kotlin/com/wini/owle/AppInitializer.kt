package com.wini.owle

import com.wini.owle.api.RoutesRegistry
import com.wini.owle.entity.TableRegistry
import org.koin.core.context.GlobalContext.startKoin
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*

object AppInitializer {
    init {
        val koin = startKoin {
            modules(appModule)
        }

        TableRegistry.registerTables()

        fun Application.module() {
            install(ContentNegotiation) {
                json()
            }

            val routesRegistry = koin.koin.get<RoutesRegistry>(RoutesRegistry::class)

            routing {
                routesRegistry.routes.forEach{
                    it.register(this)
                }
            }

        }
    }
}