#!/usr/bin/env kotlin

import java.io.File
import java.io.InputStream
import java.net.URL

val testInput = """
    2-4,6-8
    2-3,4-5
    5-7,7-9
    2-8,3-7
    6-6,4-6
    2-6,4-8
""".trimIndent()
val testAnswerPart1: Int? = 2
val testAnswerPart2: Int? = 4

fun part1(input: String) = input.toAssignmentPairs().count { (a, b) -> a overlapsFully b }

fun part2(input: String) = input.toAssignmentPairs().count { (a, b) -> a overlaps b }

fun String.toAssignmentPairs() = split("\n").map { pair ->
    pair.split(",")
        .map {
            it.split("-").let { (start, end) ->
                start.toInt()..end.toInt()
            }
        }
        .let { (a, b) -> a to b }
}

operator fun IntRange.contains(other: IntRange) = other.first in this && other.last in this
infix fun IntRange.overlaps(other: IntRange) = first in other || other.first in this
infix fun IntRange.overlapsFully(other: IntRange) = other in this || this in other


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
