package com.group.libraryapp

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KotlinJunitTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            println("모든 테스트 시작 전에 한 번만 실행됩니다.")
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            println("모든 테스트 종료 후 한 번만 실행됩니다.")
        }
    }


    @BeforeEach
    fun beforeEach() {
        println("각 테스트 시작 전에 실행되요!")
    }

    @AfterEach
    fun afterEach() {
        println("각 테스트 종료 후 실행되요!")
    }

    
    @Test
    fun test1() {
        println("테스트1")
    }

    @Test
    fun test2() {
        println("테스트2")
    }
}