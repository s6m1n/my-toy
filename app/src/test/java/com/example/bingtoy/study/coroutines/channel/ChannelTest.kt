package com.example.bingtoy.study.coroutines.channel

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ChannelTest {
    private val result = mutableListOf<Int>()

    @BeforeEach
    fun setUp() {
        result.clear()
    }

    @Test
    fun `송신자가 보낸 값을 수신자가 순차적으로 처리한다`() =
        runTest {
            val channel = Channel<Int>() // 기본 capacity = 0

            val sender =
                launch {
                    for (num in 1..5) {
                        println("송신자 코루틴 : ($channel)에 $num 추가")
                        channel.send(num) // 채널의 버퍼가 가득 차면 중단
                        result.add(num)
                        println("추가 완료\n")
                    }
                }

            repeat(5) {
                delay(100L)
                println("수신자 : ($channel)의 값을 가져온다")
                val num = channel.receive() // 채널에 값이 없으면 중단
                println("수신자 : $num 값 가져옴 ($channel)")
                result.add(num)
            }

            sender.join()
            result shouldBe listOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)
        }

    @Test
    fun `버퍼가 3개일 때 송신자가 보낸 값을 수신자가 순차적으로 처리한다`() =
        runTest {
            val channel = Channel<Int>(3) // 기본 capacity = 0

            val sender =
                launch {
                    for (num in 1..5) {
                        println("송신자 코루틴 : ($channel)에 $num 추가")
                        channel.send(num) // 채널의 버퍼가 가득 차면 중단
                        result.add(num)
                        println("추가 완료\n")
                    }
                }

            repeat(5) {
                delay(100L)
                println("수신자 : ($channel)의 값을 가져온다")
                val num = channel.receive() // 채널에 값이 없으면 중단
                println("수신자 : $num 값 가져옴 ($channel)")
                result.add(num)
            }

            sender.join()
            result shouldBe listOf(1, 2, 3, 1, 4, 2, 5, 3, 4, 5)
        }

    @Test
    fun `채널로 코루틴 간 값을 전달할 수 있다2`() =
        runTest {
            val channel = Channel<Int>() // 기본 capacity = 0

            val sender =
                launch {
                    for (num in 1..5) {
                        channel.send(num) // 채널의 버퍼가 가득 차면 중단
                        result.add(num)
                    }
                }

            val receiver =
                launch {
                    repeat(5) {
                        delay(100L)
                        val num = channel.receive() // 채널에 값이 없으면 중단
                        result.add(-num)
                    }
                }

            sender.join()
            receiver.join()
            result shouldBe listOf(-1, 1, -2, 2, -3, 3, -4, 4, -5, 5)
        }

    @Test
    fun `send 작업을 3개 예약한다`() =
        runTest {
            val channel = Channel<Int>()
            repeat(3) {
                launch {
                    println(channel)
                    channel.send(it)
                }
            }
            delay(2000L)
            println(channel)
            channel.cancel()
        }

    @Test
    fun `receive 작업을 3개 예약한다`() =
        runTest {
            val channel = Channel<Int>()
            repeat(3) {
                launch {
                    println(channel)
                    channel.receive()
                }
            }
            delay(2000L)
            println(channel)
            channel.cancel()
        }

    @Test
    fun `여러 개의 수신 코루틴이 존재할 수 있다`() =
        runTest {
            val answer = (0..10).toList()
            val channel =
                produce {
                    for (i in answer) {
                        send(i)
                    }
                }

            launch {
                val num = channel.receive() // receive로 받기
                println("num = $num")
            }
            launch {
                val nums1 = mutableListOf<Int>()
                for (x in channel) { // for loop로 받기
                    nums1.add(x)
                }
                println("nums1 = $nums1")
            }
            launch {
                val nums2 = mutableListOf<Int>()
                channel.consumeEach { // consumeEach로 받기
                    nums2.add(it)
                }
                println("nums2 = $nums2")
            }
        }

    @Test
    fun `여러 개의 송신 코루틴이 존재할 수 있다`() =
        runTest {
            val channel = Channel<Int>()

            val jobs =
                List(5) { i ->
                    launch { channel.send(i) }
                }

            launch {
                channel.consumeEach { // 채널이 닫히기 전까지 값을 무한히 대기, 채널이 닫히면 종료되는 구조
                    result.add(it)
                }
            }

            jobs.joinAll() // 모든 송신자 코루틴이 완료될 때까지 기다림
            channel.close() // 송신 완료 후 명시적으로 채널 닫기

            result shouldBe listOf(0, 1, 2, 3, 4)
        }
}
