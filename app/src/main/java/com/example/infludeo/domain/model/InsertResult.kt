package com.example.infludeo.domain.model

sealed interface InsertResult {
    data object Success : InsertResult

    data object Duplicated : InsertResult

    data object LimitReached : InsertResult
}
