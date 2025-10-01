package com.example.bingtoy.presentation.socket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bingtoy.domain.repository.EchoDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocketViewModel
    @Inject
    constructor(
        private val echoDataSource: EchoDataSource,
    ) : ViewModel() {
        val messages: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

        init {
            viewModelScope.launch {
                echoDataSource.connect()
                echoDataSource.messages.collect { newMessage ->
                    messages.update { it + newMessage }
                }
            }
        }

        override fun onCleared() {
            echoDataSource.close()
        }

        fun send(text: String) {
            viewModelScope.launch {
                echoDataSource.send(text)
            }
        }
    }
