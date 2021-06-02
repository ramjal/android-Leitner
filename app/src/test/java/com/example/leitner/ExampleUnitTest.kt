package com.example.leitner

import android.util.Log
import org.junit.Test

import org.junit.Assert.*
import java.time.Instant
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
//    @Test
//    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//    }

    @Test
    fun dateTesting() {
        val instant: Instant = Instant.now()
        val timeStampSeconds: Long = instant.epochSecond
        val convertedDate = Date(timeStampSeconds * 1000)

        println("ExampleUnitTest.instant = $instant")
        println("ExampleUnitTest.timeStampSeconds = $timeStampSeconds")
        println("ExampleUnitTest.convertedDate = $convertedDate")

    }
}