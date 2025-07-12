package com.example.study.coroutines.sharedResource

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger

class AtomicTest {
    @Test
    fun `공유 자원 cnt는 원자성을 보장하지 않는다`() =
        runTest {
            var cnt = 0

            val jobs =
                List(10000) {
                    launch(Dispatchers.Default) {
                        delay((0L..100L).random())
                        cnt++
                    }
                }
            jobs.joinAll()

            cnt shouldNotBe 10000
        }

    @Test
    fun `Mutex로 공유 자원의 원자성을 보장한다`() =
        runTest {
            val mutex = Mutex()
            var cnt = 0

            val jobs =
                List(10000) {
                    launch(Dispatchers.Default) {
                        delay((0L..100L).random())
                        mutex.withLock { //
                            cnt++
                        }
                    }
                }
            jobs.joinAll()

            cnt shouldBe 10000
        }

    @Test
    fun `Semaphore로 공유 자원의 원자성을 보장한다`() =
        runTest {
            val semaphore =
                Semaphore(
                    permits = 1,
                    acquiredPermits = 0,
                )
            var cnt = 0

            val jobs =
                List(10000) {
                    launch(Dispatchers.Default) {
                        delay((0L..100L).random())
                        semaphore.acquire()
                        cnt++
                        semaphore.release()
                    }
                }
            jobs.joinAll()

            cnt shouldBe 10000
        }

    @Test
    fun `AtomicInteger로 공유 자원의 원자성을 보장한다`() =
        runTest {
            val cnt = AtomicInteger(0)

            val jobs =
                List(10000) {
                    launch(Dispatchers.Default) {
                        delay((0L..100L).random())
                        cnt.incrementAndGet()
                    }
                }
            jobs.joinAll()

            cnt.get() shouldBe 10000
        }
}
