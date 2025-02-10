package com.wini.owle.air.ktor.ssl

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import java.io.Closeable


class EventChannel
constructor(
    private val host: NioEventLoopGroup = NioEventLoopGroup(1),
    private val listener: NioEventLoopGroup = NioEventLoopGroup()
) :
    ServerBootstrap(), Closeable {

    init {
        group(host, listener).apply {
            channel(NioServerSocketChannel::class.java)
            handler(LoggingHandler(LogLevel.DEBUG))
            childHandler(SocketHandler())
            childOption(ChannelOption.AUTO_READ, true)
        }
    }

    override fun close() {
        host.shutdownGracefully()
        listener.shutdownGracefully()
    }

}