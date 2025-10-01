package com.example.bingtoy.data.remote.datasource.echo

import android.util.Log
import com.example.bingtoy.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject

class EchoWebSocketListener
    @Inject
    constructor(
        @ApplicationScope private val scope: CoroutineScope,
    ) : WebSocketListener() {
        private val _messages = MutableSharedFlow<String>(extraBufferCapacity = 64)
        val messages = _messages.asSharedFlow()

        private val _errors = MutableSharedFlow<Throwable>()
        val errors = _errors.asSharedFlow()

        override fun onOpen(
            webSocket: WebSocket,
            response: Response,
        ) {
            webSocket.send("연결 성공")
            Log.d("ㅌㅅㅌ onOpen", "연결 성공")
        }

        override fun onMessage(
            webSocket: WebSocket,
            text: String,
        ) {
            scope.launch {
                Log.d("ㅌㅅㅌ onMessage", "text : $text")
                _messages.emit(text)
            }
        }

        override fun onMessage(
            webSocket: WebSocket,
            bytes: ByteString,
        ) {
            Log.d("ㅌㅅㅌ onMessage", "bytes : $bytes")
        }

        override fun onClosing(
            webSocket: WebSocket,
            code: Int,
            reason: String,
        ) {
            Log.d("ㅌㅅㅌ onClosing", "연결 종료")
            webSocket.close(NORMAL_CLOSURE_STATUS, null)
            webSocket.cancel()
        }

        override fun onFailure(
            webSocket: WebSocket,
            t: Throwable,
            response: Response?,
        ) {
            Log.d("ㅌㅅㅌ onFailure", "Error : " + t.message)
            scope.launch {
                _errors.emit(t)
            }
        }

        companion object {
            private const val NORMAL_CLOSURE_STATUS = 1000
        }
    }
