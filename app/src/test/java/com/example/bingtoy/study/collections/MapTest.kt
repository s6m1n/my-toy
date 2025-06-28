package com.example.bingtoy.study.collections

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.maps.shouldContain
import kotlin.test.Test

class MapTest : BehaviorSpec() {
    @Test
    fun `Pair로 Map을 만들 수 있다`() {
        // Given : Pair 객체가 존재한다
        val pair1 = Pair("빙티", 1)
        val pair2 = Pair("빙빙", 2)
        val pair3 = "티빙" to 3
        val pair4 = "티티" to 4

        // When : 맵은 Pair로 구성된다
        val map1 = mapOf(pair1, pair2, pair3, pair4)

        // Then : Map은 Pair를 원소로 포함한다
        map1 shouldContain pair1
        map1 shouldContain pair2
        map1 shouldContain pair3
        map1 shouldContain pair4
    }

    @Test
    fun `groupBy의 keySelector로 그룹화 할 수 있다`() {
        val list = arrayOf(1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4)
        val name = listOf("빙티", "빙수", "빙빙", "티빙", "티라노")

        // When : key를 기준으로 그룹화 할 수 있다
        val mapByValue = list.groupBy { it }
        val nameMap = name.groupBy { it.first() }

        // Then : 결과 - Map<K, List<V>>
        println(mapByValue.entries.joinToString(", "))
        println(nameMap.entries.joinToString(", "))
    }

    @Test
    fun `groupBy의 keySelector와 valueTransform로 그룹화 할 수 있다`() {
        val list = arrayOf(1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4)
        val name = listOf("빙티", "빙수", "빙빙", "티빙", "티라노")

        // When : key를 기준으로 value를 그룹화 할 수 있다
        val mapByValue = list.withIndex().groupBy({ it.value }, { it.index })
        val nameMap = name.withIndex().groupBy({ it.index }, { it.value })

        // Then : 결과 - Map<K, List<V>>
        println(mapByValue.entries.joinToString(", "))
        println(nameMap.entries.joinToString(", "))
    }

    @Test
    fun `associateBy를 통해 K와 V가 1대1인 Map으로 묶을 수 있다`() {
        val name =
            listOf(
                1 to "빙티",
                2 to "빙수",
                3 to "빙빙",
                4 to "티빙",
            )

        // When : 값을 선택해 그룹화 할 수 있다
        val idWithPair = name.associateBy { it.second[0] }
        val idWithName = name.associateBy({ it.first }, { it.second })

        // Then : 결과 - Map<K, V>
        println(idWithPair.entries.joinToString(", "))
        println(idWithName.entries.joinToString(", "))
    }
}
