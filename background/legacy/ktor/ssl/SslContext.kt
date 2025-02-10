package com.wini.owle.air.ktor.ssl

import io.netty.handler.ssl.SslHandler
import java.io.FileInputStream
import java.security.KeyStore
import java.security.Security
import javax.net.ssl.*
import kotlin.system.exitProcess


object SslContext {

    private const val PROTOCOL = "TLS"
    private const val ALGORITHM_SUN_X509 = "SunX509"
    private const val ALGORITHM = "ssl.KeyManagerFactory.algorithm"
    private const val KEYSTORE = "/loki.jks"
    private const val KEYSTORE_TYPE = "JKS"
    private const val KEYSTORE_PASSWORD = "coreAppsIsCool"
    private const val CERT_PASSWORD = "coreAppsIsCool"
    private var serverSSLContext: SSLContext? = null

    val sslHandler: SslHandler by lazy {
        serverSSLContext?.run {
            createSSLEngine().apply {
                useClientMode = false
                needClientAuth = false
            }.let {
                SslHandler(it)
            }
        } ?: exitProcess(-1)
    }

    fun init() {
        FileInputStream(javaClass.classLoader.getResource(KEYSTORE)?.file ?: return).run {
            KeyStore.getInstance(KEYSTORE_TYPE)
            .also {
                it.load(this, KEYSTORE_PASSWORD.toCharArray()).also { close() }
            }.let {
                (Security.getProperty(ALGORITHM) ?: ALGORITHM_SUN_X509).apply {
                    KeyManagerFactory.getInstance(this).apply {
                        init(it, CERT_PASSWORD.toCharArray())
                    }.also {
                        serverSSLContext = SSLContext.getInstance(PROTOCOL)
                            .apply { init(it.keyManagers, null, null) }
                    }
                }
            }
        }
    }
}