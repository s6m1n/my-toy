package com.example.infludeo.presentation.socket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infludeo.domain.repository.EchoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocketViewModel
    @Inject
    constructor(
        private val echoRepository: EchoRepository,
    ) : ViewModel() {
        val messages: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

        init {
            viewModelScope.launch {
                echoRepository.incoming.collect { newMessage ->
                    messages.update { it + newMessage }
                }
            }
        }

        override fun onCleared() {
            echoRepository.close()
        }

        fun send(text: String) {
            viewModelScope.launch {
                echoRepository.send(text)
            }
        }
    }
