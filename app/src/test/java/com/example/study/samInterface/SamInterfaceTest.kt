package com.example.study.samInterface

import org.junit.Test

/**
일반적인 인터페이스는 1개 이상의 추상 메서드를 가질 수 있다.
 ```kotlin
 interface ClickListener {
 fun onClick()

 fun onClick2()
 }
 ```

만약 인터페이스에 추상 메서드가 단 하나만 존재한다면, fun 키워드를 붙여 Sam(Single abstract method) 인터페이스로 선언할 수 있다.

```kotlin
fun interface SamClickListener {
fun onClick()
}
```

fun interface는 람다식으로 더 간단하게 구현할 수 있다.
반면, 일반 인터페이스는 람다로 구현할 수 없다.
```kotlin
val clickListener2 = ClickListener { println("안녕하세요!") } // 컴파일 에러
val clickListener1 = SamClickListener { println("안녕하세요!") } // 가능
```
 **/

interface ClickListener {
    fun onClick1()

    fun onClick2()
}

fun interface SamClickListener {
    fun onClick()
}

class SamInterfaceTest {
    @Test
    fun `익명 객체로 일반 인터페이스를 구현할 수 있다`() {
        val clickListener =
            object : ClickListener {
                override fun onClick1() {
                    println("안녕하세요!")
                }

                override fun onClick2() {
                    println("만나서 반가워요.")
                }
            }

        clickListener.onClick1()
        clickListener.onClick2()
    }

    @Test
    fun `익명 객체로 Sam 인터페이스를 구현할 수 있다`() {
        val clickListener =
            object : SamClickListener {
                override fun onClick() {
                    println("안녕하세요!")
                }
            }

        clickListener.onClick()
    }

    @Test
    fun `람다로 Sam 인터페이스를 구현할 수 있다`() {
        val samClickListener = SamClickListener { println("안녕하세요!") }
        samClickListener.onClick()
    }

    @Test
    fun `익명 객체로 구현된 일반 인터페이스를 함수 인자로 전달할 수 있다`() {
        val clickListener =
            object : ClickListener {
                override fun onClick1() {
                    println("안녕하세요!")
                }

                override fun onClick2() {
                    println("Sam 인터페이스를 공부해요.")
                }
            }

        val introduce: (ClickListener) -> Unit = { clickListener ->
            print("인사말 : ")
            clickListener.onClick1()
            print("테스트 명 : ")
            clickListener.onClick2()
        }

        introduce(clickListener)
    }

    @Test
    fun `익명 객체로 구현된 Sam 인터페이스를 함수 인자로 전달할 수 있다`() {
        val samClickListener =
            object : SamClickListener {
                override fun onClick() {
                    println("나는 Sam 인터페이스!")
                }
            }

        val introduce: (SamClickListener) -> Unit = { clickListener ->
            print("인사말 : ")
            clickListener.onClick()
        }

        introduce(samClickListener)
    }

    @Test
    fun `람다로 구현된 Sam 인터페이스를 함수 인자로 전달할 수 있다`() {
        val samClickListener =
            SamClickListener {
                println("나는 Sam 인터페이스!")
            }

        val introduce: (SamClickListener) -> Unit = { clickListener ->
            print("인사말 : ")
            clickListener.onClick()
        }

        introduce(samClickListener)
    }

    @Test
    fun `Sam 인터페이스에 람다를 직접 전달할 수 있다`() {
        val introduce: (SamClickListener) -> Unit = { clickListener ->
            print("인사말 : ")
            clickListener.onClick()
        }

        introduce {
            println("나는 Sam 인터페이스!")
        }
    }
}
