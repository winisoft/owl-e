package com.wini.owle.air.ktor.ssl

import com.wini.owle.air.ktor.ssl.SslContext.sslHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInitializer
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.*
import io.netty.handler.codec.http.HttpResponseStatus.OK
import io.netty.handler.codec.http.HttpVersion.HTTP_1_1


class SocketHandler : ChannelInitializer<SocketChannel>() {

    override fun initChannel(socketChannel: SocketChannel) {
        socketChannel.pipeline()
            .addLast(
                sslHandler,
                HttpServerCodec(),
                HttpObjectAggregator(1048576),
                object : SimpleChannelInboundHandler<FullHttpRequest?>() {
                    override fun channelRead0(
                        channelHandlerContext: ChannelHandlerContext,
                        fullHttpRequest: FullHttpRequest?
                    ) {
                        channelHandlerContext.apply {
                            writeAndFlush(DefaultHttpResponse(HTTP_1_1, OK))
                            channel().close()
                        }
                    }
                }
            )
    }
}