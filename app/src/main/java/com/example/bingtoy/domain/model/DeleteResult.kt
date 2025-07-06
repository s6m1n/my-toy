package com.example.bingtoy.domain.model

sealed interface DeleteResult {
    data object Success : DeleteResult

    data object Fail : DeleteResult
}
