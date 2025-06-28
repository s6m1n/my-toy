package com.example.bingtoy.domain.repository

import kotlinx.coroutines.flow.SharedFlow

interface EchoRepository {
    val incoming: SharedFlow<String>

    fun send(text: String)

    fun close()
}
