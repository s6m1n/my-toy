package com.example.study.coroutines.job

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class JobTest {
    /**
     * Job의 생명주기
     *
     * ```
     *                                      wait children
     * +-----+ start  +--------+ complete  +-------------+  finish  +-----------+
     * | New | -----> | Active | ---------> | Completing  | -------> | Completed |
     * +-----+        +--------+            +-------------+          +-----------+
     *                  |  cancel / fail       |
     *                  |                      |
     *                  V                      |
     *              +------------+             |
     *              | Cancelling | <-----------+
     *              +------------+
     *                   |
     *                   | finish
     *                   |
     *                   V
     *              +----------+
     *              | Cancelled|
     *              +----------+
     * ```
     */

    @Test
    fun `Job이 완료되면 생명주기가 Completed(완료)로 바뀐다`(): Unit =
        runBlocking {
            val job =
                launch(start = CoroutineStart.LAZY) {
                    delay(1000L)
                }
            // job 시작 전
            job.isActive.shouldBeFalse()
            job.isCompleted.shouldBeFalse()
            job.isCancelled.shouldBeFalse()

            job.start()
            delay(100L)
            job.isActive.shouldBeTrue()

            // 완료될 때까지 대기
            job.join()
            job.isCompleted.shouldBeTrue()
        }

    @Test
    fun `Job이 취소되면 생명주기가 Canceled(취소)로 바뀐다`(): Unit =
        runBlocking {
            val job =
                launch {
                    delay(2000L)
                }
            delay(100L)
            job.isActive.shouldBeTrue()

            // 완료될 때까지 대기
            job.cancel()
            delay(100L)
            job.isCancelled.shouldBeTrue()
        }

    @Test
    fun `Job 팩토리 함수로 생성하면 작업이 모두 끝나도 active 하다`(): Unit =
        runBlocking {
            // 부모 Job의 종료 여부 플래그
            var parentCompleted = false

            // 종료 시 플래그를 참으로 바꾸는 부모 Job을 만든다
            val parentJob = Job()
            parentJob.invokeOnCompletion {
                parentCompleted = true
            }

            // 부모 Job과 자식 Job을 이어준 뒤 자식 Job을 실행한다
            val childJob =
                CoroutineScope(parentJob).launch {
                    delay(1000L)
                }

            // 자식 Job이 종료됨을 확인한다
            childJob.join()
            childJob.isCompleted.shouldBeTrue()

            // 부모 Job이 실행 중임을 확인한다
            parentJob.isCompleted.shouldBeFalse()
            parentCompleted.shouldBeFalse()
        }
}
