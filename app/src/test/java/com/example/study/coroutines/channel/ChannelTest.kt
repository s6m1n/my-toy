package com.example.study.coroutines.channel

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Collections

class ChannelTest {
    private val result = mutableListOf<Int>()

    @BeforeEach
    fun setUp() {
        result.clear()
    }

    @Test
    fun `채널은 코루틴에서 값을 송·수신하며, 송신자와 수신자가 만날 때 작동한다`() {
        val channel = Channel<Int>()
        // channel.send(1) -> 경고 "Suspend function 'send' should be called only from a coroutine or another suspend function"
        // channel.receive() -> 경고 Suspend function 'receive' should be called only from a coroutine or another suspend function
        runTest {
            // channel.send(1) -> 수신자를 만나지 않아 무한으로 중단됨
            // val a = channel.receive() -> 송신자를 만나지 않아 무한으로 중단됨
            launch { channel.send(1) }
            val a = channel.receive()
            a shouldBe 1
        }
    }

    @Test
    fun `송신자가 보낸 값을 수신자가 순차적으로 처리한다`() =
        runTest {
            val channel = Channel<Int>() // 기본 capacity = 0

            val sender =
                launch {
                    for (num in 1..5) {
                        channel.send(num) // 채널의 버퍼가 가득 차면 중단
                        result.add(num)
                        println("$num 전송 완료\n")
                    }
                }

            repeat(5) {
                val num = channel.receive() // 채널에 값이 없으면 중단
                println("$num 수신 완료\n")
                result.add(num)
            }

            sender.join()
            result shouldBe listOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)
        }

    @Test
    fun `버퍼가 2개면, 채널에 예약 작업을 2개 등록할 수 있다`() =
        runTest {
            val channel = Channel<Int>(2) // 기본 capacity = 0

            val sender =
                launch {
                    for (num in 1..5) {
                        channel.send(num) // 채널의 버퍼가 가득 차면 send를 예약하고 중단
                        result.add(num * -1)
                        println("${num * -1}")
                    }
                }

            repeat(5) {
                val num = channel.receive() // 채널에 값이 없으면 receive를 예약하고 중단
                println("$num")
                result.add(num)
            }

            sender.join()
            result shouldBe listOf(-1, -2, -3, 1, 2, 3, 4, -4, -5, 5)
        }

    @Test
    fun `채널로 코루틴 간 값을 전달할 수 있다`() =
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
    fun `채널에 여러 개의 수신자가 존재해도, 하나의 값은 하나의 수신자만 받을 수 있다`() =
        runTest {
            val channel = Channel<Int>()
            val result = Collections.synchronizedList(mutableListOf<Int>())

            // 3개의 값을 보냄
            launch {
                channel.send(1)
                channel.send(2)
                channel.send(3)
            }

            // 동시에 3개의 수신 코루틴 실행
            val receivers = List(3) {
                launch {
                    val value = channel.receive()
                    result.add(value)
                }
            }
            receivers.joinAll()
            result.sorted() shouldBe listOf(1, 2, 3)
        }

    @Test
    fun `produce는 값을 다 보내면 채널을 닫는다`() = runTest {
        val channel: ReceiveChannel<Int> = produce {
            for (i in 0..5) {
                send(i)
                println("$i 보냄")
            }
        }
        launch {
            try {
                while (true) {
                    println("${channel.receive()} 받음")
                }
            } catch (e: Exception) {
                println("채널 종료 예외 : ${e.message}")
            }
        }
    }

    @Test
    fun `for, consumeEach는 채널이 닫히기 전까지 값을 받는다`() = runTest {
        val channel: ReceiveChannel<Int> =
            produce { // 모든 값을 보내면 채널을 close
                for (i in 0..10) {
                    send(i);println("$i 보냄")
                }
            }
        launch {
            channel.consumeEach { // 채널이 닫히기 전까지 값 수신
                println("consumeEach로 $it 받음")
            }
        }
        launch {
            for (i in channel) { // 채널이 닫히기 전까지 값 수신
                println("for로 $i 받음")
            }
        }
    }
}
