#!/usr/bin/env kotlin

import java.net.URL
import java.io.File
import java.io.InputStream

val testInput = """
""".trimIndent()
val testAnswerPart1: Int? = null
val testAnswerPart2: Int? = null

fun part1(input: String): Int {
    return input.length
}

fun part2(input: String): Int {
    return input.length
}

// Shouldn't need to modify anything below this line
// -----------------------------------------------------------------------------
run {
    val scriptFile: File = @Suppress("UNRESOLVED_REFERENCE") __FILE__
    val scriptDir = scriptFile.canonicalFile.parentFile
    val realInput = scriptDir.resolve("input.txt").let {
        if (it.exists()) it.readText().trim()
        else {
            val folders = scriptDir.toPath().map { it.toString() }.takeLast(2)
            val year = folders[0].removePrefix("year")
            val day = folders[1].removePrefix("day").trimStart('0')
            val session = scriptDir.parentFile.parentFile.resolve(".session").let {
                check(it.exists()) { ".session file with cookie value must exist!" }
                it.readText().trim()
            }
            URL("https://adventofcode.com/$year/day/$day/input").openConnection()
                .apply { setRequestProperty("Cookie", "session=$session") }
                .getInputStream()
                .use(InputStream::readAllBytes)
                .also(it::writeBytes)
                .decodeToString()
                .trimIndent()
        }
    }

    if (testAnswerPart1 != null) {
        println("Part 1:")
        val testResultPart1 = part1(testInput)
        check(testResultPart1 == testAnswerPart1) { "Expected $testAnswerPart1, got $testResultPart1" }
        println(part1(realInput))
    }

    if (testAnswerPart2 != null) {
        println("-".repeat(80))
        println("Part 2:")
        val testResultPart2 = part2(testInput)
        check(testResultPart2 == testAnswerPart2) { "Expected $testAnswerPart2, got $testResultPart2" }
        println(part2(realInput))
    }
}
