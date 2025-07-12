package com.example.study.collections

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class CollectionIndexingTest {
    @Test
    fun `withIndex로 인덱스와 함께 표시할 수 있다`() {
        val arr = arrayOf("0", "1", "2", "3", "4")
        arr.withIndex().forEach {
            println("인덱스 = ${it.index}, 값 = ${it.value}")
            it.index shouldBe it.value.toInt()
        }
    }

    @Test
    fun `withIndex와 for문으로 인덱스와 값을 함께 순회할 수 있다`() {
        val arr = arrayOf("0", "1", "2", "3", "4")
        for ((idx, value) in arr.withIndex()) {
            println("인덱스 = $idx, 값 = $value")
            idx shouldBe value.toInt()
        }
    }

    @Test
    fun `indices와 for문으로 인덱스를 순회할 수 있다`() {
        val arr = arrayOf("0", "1", "2", "3", "4")
        for (idx in arr.indices) {
            println("인덱스 = $idx, 값 = ${arr[idx]}")
            idx shouldBe arr[idx].toInt()
        }
    }

    @Test
    fun `forEachIndexed로 인덱스와 함께 순회할 수 있다`() {
        val arr = arrayOf("0", "1", "2", "3", "4")
        arr.forEachIndexed { idx, value ->
            println("인덱스 = $idx, 값 = $value")
            idx shouldBe value.toInt()
        }
    }
}
