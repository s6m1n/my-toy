package com.example.bingtoy.study.coroutines.job

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SuperVisorJobTest {
    private val result = mutableListOf<Int>()

    @BeforeEach
    fun setUp() {
        result.clear()
    }

    @Test
    fun `슈퍼바이저 잡은 자식 코루틴으로부터 예외를 전파받지 않는다`(): Unit =
        runTest {
            val supervisorJob = SupervisorJob(coroutineContext.job)
            val exceptionHandler =
                CoroutineExceptionHandler { _, throwable ->
                    println(throwable.message)
                }

            CoroutineScope(supervisorJob + exceptionHandler).launch {
                throw IllegalArgumentException("예외 발생!")
            }
            val childJob =
                CoroutineScope(supervisorJob + exceptionHandler).launch {
                    repeat(5) {
                        delay(100L)
                        println("$it 번째 작업 중..")
                        result.add(it)
                    }
                }
            childJob.join()
            supervisorJob.cancel()
            result shouldBe listOf(0, 1, 2, 3, 4)
        }
}
