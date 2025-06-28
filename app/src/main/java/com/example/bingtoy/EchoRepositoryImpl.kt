package com.example.bingtoy.data.repository

import com.example.bingtoy.data.remote.websocket.EchoWebSocketListener
import com.example.bingtoy.domain.repository.EchoRepository
import kotlinx.coroutines.flow.SharedFlow
import okhttp3.WebSocket
import javax.inject.Inject
import javax.inject.Named

class EchoRepositoryImpl
    @Inject
    constructor(
        @Named("echo") private val webSocket: WebSocket,
        private val listener: EchoWebSocketListener,
    ) : EchoRepository {
        override val incoming: SharedFlow<String> = listener.messages

        override fun send(text: String) {
            webSocket.send(text)
        }

        override fun close() {
            webSocket.close(1000, "Goodbye, WebSocket!")
        }
    }
