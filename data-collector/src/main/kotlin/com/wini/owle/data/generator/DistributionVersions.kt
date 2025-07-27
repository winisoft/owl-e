package com.wini.owle.data.generator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger


class DistributionVersions(
    val codeGenerator: CodeGenerator,
    val logger: KSPLogger
) {

    fun generate(iteration: Int): Int {

        return iteration + 1
    }

    companion object {
        const val CHUNK_SIZE = 300_000
    }
}