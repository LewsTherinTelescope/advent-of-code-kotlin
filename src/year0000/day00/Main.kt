package year0000.day00

import utils.runIt

fun main() = runIt(
    year = "0000",
    day = "00",
    testInput = """
    """.trimIndent(),
    ::part1,
    testAnswerPart1 = null,
    ::part2,
    testAnswerPart2 = null,
)

fun part1(input: String): Int {
    return input.length
}

fun part2(input: String): Int {
    return input.length
}
