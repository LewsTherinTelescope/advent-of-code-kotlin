#!/usr/bin/env kotlin

import java.net.URL
import java.io.File
import java.io.InputStream

val testInput = """
    A Y
    B X
    C Z
    """.trimIndent()
val testAnswerPart1: Int? = 15
val testAnswerPart2: Int? = 12

fun part1(input: String) = splitInput(input).fold(0) { acc, (them, you) ->
    acc + playRound(them, xyzToAbc[you]!!)
}

fun part2(input: String) = splitInput(input).fold(0) { acc, (them, action) ->
    acc + cheatRound(them, action)
}

fun splitInput(input: String) = input.split("\n").map { it.split(" ") }

val xyzToAbc = mapOf(
    "X" to "A",
    "Y" to "B",
    "Z" to "C",
)

val shapeScores = mapOf(
    "A" to 1,
    "B" to 2,
    "C" to 3,
)

val beatingShapes = mapOf(
    "A" to "B",
    "B" to "C",
    "C" to "A",
)

val losingShapes = beatingShapes.map { (k, v) -> v to k }.toMap()

fun playRound(them: String, you: String): Int = shapeScores[you]!! + when (you) {
    them -> 3
    beatingShapes[them] -> 6
    else -> 0
}

fun cheatRound(them: String, action: String): Int = playRound(them, you = when (action) {
    "X" -> losingShapes[them]!!
    "Y" -> them
    "Z" -> beatingShapes[them]!!
    else -> throw IllegalArgumentException("Invalid action: $action")
})

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
