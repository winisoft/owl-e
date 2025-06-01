package com.wini.owle.data

import com.wini.owle.data.comparePythonVersions
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue


class JepUsageTest {


    @Test
    fun testJepUsageWorks() {

        assertTrue(comparePythonVersions())

    }
}