package com.example.infludeo.domain.model

sealed interface DeleteResult {
    data object Success : DeleteResult

    data object Fail : DeleteResult
}
