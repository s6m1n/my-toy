package com.example.bingtoy.data.repository

import com.example.bingtoy.data.remote.EchoWebSocketListener
import com.example.bingtoy.domain.repository.EchoDataSource
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject
import javax.inject.Named

class EchoDataSourceImpl
    @Inject
    constructor(
        @Named("WebSocketClient") private val okHttpClient: OkHttpClient,
        @Named("echo") private val request: Request,
        private val listener: EchoWebSocketListener,
    ) : EchoDataSource {
        override val messages = listener.messages

        private var webSocket: WebSocket? = null

        override fun connect() {
            webSocket = okHttpClient.newWebSocket(request, listener)
        }

        override fun send(text: String): Boolean = webSocket?.send(text) ?: false

        override fun close(): Boolean = webSocket?.close(1000, "Goodbye, WebSocket!") ?: false
    }
