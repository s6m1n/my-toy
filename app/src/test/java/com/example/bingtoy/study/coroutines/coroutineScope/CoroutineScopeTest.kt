package com.example.bingtoy.study.coroutines.coroutineScope

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

class CoroutineScopeTest {
    private val result = mutableListOf<String>()

    @BeforeEach
    fun setUp() {
        result.clear()
    }

    /**
     * coroutineScope 함수 :
     * 1. 현재 코루틴 내부에서 구조화된 하위 스코프를 생성하는 suspend 함수.
     * 2. 호출부의 Job과 부모-자식 관계인 새로운 Job을 만든다.
     * 3. 자식 코루틴이 모두 완료될 때까지 기다린다. (스코프 내부 코루틴들의 실행을 보장한다.)
     **/

    @Test
    fun `coroutineScope는 내부 작업의 완료를 보장한다`() {
        runBlocking {
            coroutineScope {
                result += "1"
                delay(100L)
                result += "2"
                delay(500L)
                result += "3"
                delay(500L)
                result += "4"
            }
            coroutineScope {
                result += "5"
                delay(500L)
                result += "6"
            }
            result += "7"
            result shouldBe listOf("1", "2", "3", "4", "5", "6", "7")
        }
    }

    @Test
    fun `coroutineScope는 내부 코루틴들의 완료를 보장한다`() {
        runBlocking {
            coroutineScope {
                result += "1"
                delay(100L)
                result += "2"
                launch {
                    delay(1000L)
                    result += "4"
                }
                launch {
                    delay(500L)
                    result += "3"
                }
            }
            result += "5"
            result shouldBe listOf("1", "2", "3", "4", "5")
        }
    }

    @Test
    fun `coroutineScope 함수는 구조화된 Job을 생성하고 완료를 보장한다`() {
        runTest {
            // given : 상위 CoroutineScope가 존재할 때
            val parentJob = coroutineContext[Job]!!
            val childJob: Job

            // when : coroutineScope로 스코프를 만들면
            coroutineScope {
                // then1 : 호출부의 Job을 부모로 갖는 새로운 Job을 생성한다
                childJob = coroutineContext[Job]!!
                childJob shouldNotBe parentJob
                parentJob.children.toList() shouldContain childJob
            }

            // then2 : 자식 Job이 완료되고 부모의 children에서 제거된다
            childJob.isCompleted shouldBe true
            parentJob.children.toList() shouldNotContain childJob
        }
    }

    @Test
    fun `coroutineScope는 부모 컨텍스트를 유지하면서 새로운 Job을 추가한다`() {
        runTest(CoroutineName("부모")) {
            // given : 상위 CoroutineScope가 존재할 때
            val parentContext = coroutineContext
            val childContext: CoroutineContext

            // when : coroutineScope로 스코프를 만들면
            coroutineScope {
                childContext = coroutineContext
            }

            // then2 : Job을 제외한 나머지 컨텍스트를 그대로 가져온다
            parentContext[Job] shouldNotBe childContext[Job]
            parentContext[CoroutineName] shouldBe childContext[CoroutineName]
            parentContext[CoroutineExceptionHandler] shouldBe childContext[CoroutineExceptionHandler]
            parentContext[ContinuationInterceptor] shouldBe childContext[ContinuationInterceptor]
        }
    }

    /**
     * CoroutineScope 팩토리 함수 :
     * 인자로 코루틴 컨텍스트를 받으며, Job이 포함되어있으면 해당 Job을 그대로 사용한다.
     * Job을 명시하지 않으면 새로운 독립 Job이 생성되며 이 Job은 직접 종료해야 한다.
     **/

    @Test
    fun `CoroutineScope 팩토리 함수에 Job을 전달하면 해당 Job을 그대로 사용한다`() {
        runBlocking {
            // given & when : 호출부의 코루틴 컨텍스트를 전달해 스코프를 생성
            val scope = CoroutineScope(coroutineContext)

            // then : scope의 Job은 호출부의 Job과 동일하다
            scope.coroutineContext[Job]!! shouldBe coroutineContext[Job]!!
        }
    }

    @Test
    fun `Job을 명시하지 않으면 새로운 독립 Job이 생성된다`() {
        runBlocking {
            // given : Job을 제외한 코루틴 컨텍스트를 정의한다
            val name = CoroutineName("커스텀 스코프")
            val dispatcher = Dispatchers.IO
            val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
            val customContext = name + dispatcher + exceptionHandler

            // when : Job을 제외한 코루틴 컨텍스트로 스코프를 만든다
            customContext[Job] shouldBe null
            val scope = CoroutineScope(customContext)

            // then 1 : 내부에서 새로운 Job을 만들며 부모 코루틴과 구조화된 동시성이 깨진다
            coroutineContext[Job]!! shouldNotBe scope.coroutineContext[Job]!!
            coroutineContext[Job]!!.children.toList() shouldNotContain scope.coroutineContext[Job]!!

            // then 2 : 다른 컨텍스트들은 넣어준 그대로 동일하다
            scope.coroutineContext[CoroutineName] shouldBe name
            scope.coroutineContext[CoroutineExceptionHandler] shouldBe exceptionHandler
            scope.coroutineContext[ContinuationInterceptor] shouldBe dispatcher
        }
    }

    @Test
    fun `CoroutineScope로 만들어진 Job은 직접 취소해야 한다`(): Unit =
        runBlocking {
            // 팩토리 함수로 CoroutineScope를 만든다
            val testDispatcher = Dispatchers.IO
            val scope = CoroutineScope(testDispatcher)
            val job = scope.coroutineContext[Job]!!

            // scope에서 job을 실행하고 종료를 기다린다
            val childJob =
                scope.launch {
                    delay(1000L)
                }
            childJob.join()
            childJob.isCompleted.shouldBeTrue()

            // 자식 job이 종료되어도 CoroutineScope의 job은 살아있다
            job.isCompleted.shouldBeFalse()
            job.isCancelled.shouldBeFalse()

            // scope를 취소하면 비로소 job도 종료된다
            scope.cancel()
            job.isCancelled.shouldBeTrue()
            job.isCompleted.shouldBeTrue()
        }
}
