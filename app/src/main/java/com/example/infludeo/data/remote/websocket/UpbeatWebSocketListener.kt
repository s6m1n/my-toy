package com.example.infludeo.data.remote.websocket

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject

class UpbeatWebSocketListener
    @Inject
    constructor() : WebSocketListener() {
        private val _messages = MutableSharedFlow<String>(extraBufferCapacity = 64)
        val messages = _messages.asSharedFlow()

        // 서버 측에서 BroadCast 했을 때
        override fun onMessage(
            webSocket: WebSocket,
            text: String,
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                _messages.emit(text)
            }
        }

        // 서버 측에서 BroadCast 했을 때
        override fun onMessage(
            webSocket: WebSocket,
            bytes: ByteString,
        ) {
            Log.d("ㅌㅅㅌ okhttp Socket", "Receiving bytes : $bytes")
        }

        // 웹소켓이 열렸을 때
        override fun onOpen(
            webSocket: WebSocket,
            response: Response,
        ) {
            webSocket.send("{\"type\":\"ticker\", \"symbols\": [\"BTC_KRW\"], \"tickTypes\": [\"30M\"]}")
        }

        // 웹소켓이 닫혔을 때
        override fun onClosing(
            webSocket: WebSocket,
            code: Int,
            reason: String,
        ) {
            Log.d("ㅌㅅㅌ Socket", "Closing : $code / $reason")
            webSocket.close(NORMAL_CLOSURE_STATUS, null)
            webSocket.cancel()
        }

        // 웹소켓 통신이 실패했을 때
        override fun onFailure(
            webSocket: WebSocket,
            t: Throwable,
            response: Response?,
        ) {
            Log.d("ㅌㅅㅌ Socket", "Error : " + t.message)
        }

        companion object {
            private const val NORMAL_CLOSURE_STATUS = 1000
        }
    }
