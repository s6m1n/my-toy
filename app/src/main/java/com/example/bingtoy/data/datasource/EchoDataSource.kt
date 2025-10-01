package com.example.bingtoy.data.datasource

import kotlinx.coroutines.flow.SharedFlow

interface EchoDataSource {
    val messages: SharedFlow<String>

    fun connect()

    fun send(text: String): Boolean

    fun close(): Boolean
}
