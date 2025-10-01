package com.example.bingtoy.domain.repository

import kotlinx.coroutines.flow.SharedFlow

interface EchoDataSource {
    val messages: SharedFlow<String>

    fun connect()

    fun send(text: String): Boolean

    fun close(): Boolean
}
