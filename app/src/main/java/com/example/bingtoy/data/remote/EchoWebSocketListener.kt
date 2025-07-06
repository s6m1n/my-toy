package com.example.bingtoy.data.remote

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class EchoWebSocketListener
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

        // 웹소켓이 닫혔을 때
        override fun onClosing(
            webSocket: WebSocket,
            code: Int,
            reason: String,
        ) {
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
