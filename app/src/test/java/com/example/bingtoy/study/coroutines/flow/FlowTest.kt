package com.example.bingtoy.study.coroutines.flow

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class FlowTest {
    private val result = mutableListOf<Int>()

    @BeforeEach
    fun setUp() {
        result.clear()
    }

    @Test
    fun `flow로 값을 보내고 받을 수 있다`() =
        runTest {
            val numbers =
                flow {
                    for (i in 1..5) {
                        emit(i)
                    }
                }

            numbers.collect { value ->
                result.add(value)
            }

            result shouldBe listOf(1, 2, 3, 4, 5) // [1, 2, 3, 4, 5]
        }

    @Test
    fun `여러 곳에서 flow를 구독할 수 있다`() {
        val numbers =
            flow {
                for (i in 1..5) {
                    delay(500L)
                    emit(i)
                }
            }
        runBlocking {
            launch {
                numbers.collect { value ->
                    println("collect1 $value")
                    result.add(value)
                }
            }
            launch {
                numbers.collect { value ->
                    println("collect2 $value")
                    result.add(value)
                }
            }
        }

        result shouldBe listOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5) // [1, 2, 3, 4, 5]
    }

    @Test
    fun `flow를 변환할 수 있다`() =
        runTest {
            val numbers =
                flowOf(1, 2, 3)
                    .map { it * 2 }

            numbers.collect { value ->
                result.add(value)
            }

            result shouldBe listOf(2, 4, 6) // [2, 4, 6]
        }

    @Test
    fun `flow에서 필터링할 수 있다`() =
        runTest {
            val numbers =
                flowOf(1, 2, 3, 4, 5)
                    .filter { it % 2 == 0 }

            numbers.collect { value ->
                result.add(value)
            }

            result shouldBe listOf(2, 4) // [2, 4]
        }

    @Test
    fun `flow의 중간 연산자를 체이닝할 수 있다`() =
        runTest {
            val numbers =
                flowOf(1, 2, 3, 4, 5)
                    .map { it * 2 }
                    .filter { it > 5 }

            numbers.collect { value ->
                result.add(value)
            }

            result shouldBe listOf(6, 8, 10)
        }

    @Test
    fun `flow를 중단 없이 collect할 수 있다`() =
        runTest {
            val numbers =
                flow {
                    for (i in 1..3) {
                        delay(100)
                        emit(i)
                    }
                }

            numbers.collect { value ->
                result.add(value)
            }

            result shouldBe listOf(1, 2, 3)
        }

    @Test
    fun `flow를 조합할 수 있다`() =
        runTest {
            val flow1 = flowOf(1, 2, 3)
            val flow2 = flowOf(10, 20, 30)

            flow1.zip(flow2) { a, b -> a + b }
                .collect { value ->
                    result.add(value)
                }

            result shouldBe listOf(11, 22, 33)
        }

    @Test
    fun `flow를 처음부터 일부만 받을 수 있다`() =
        runTest {
            val numbers =
                flowOf(1, 2, 3, 4, 5)
                    .take(3)

            numbers.collect { value ->
                result.add(value)
            }

            result shouldBe listOf(1, 2, 3)
        }
}
