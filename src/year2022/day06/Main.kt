package year2022.day06

import utils.runIt

fun main() = runIt(
    year = "2022",
    day = "06",
    testInput = """
        mjqjpqmgbljsphdztnvjfqwrcgsmlb
    """.trimIndent(),
    ::part1,
    testAnswerPart1 = 7,
    ::part2,
    testAnswerPart2 = 19,
)

fun part1(input: String) = input.findMarkerEndPos(markerLength = 4)

fun part2(input: String) = input.findMarkerEndPos(markerLength = 14)

fun String.findMarkerEndPos(markerLength: Int) = this
    .windowed(markerLength) { it.toHashSet().size == it.length }
    .indexOfFirst { it }
    .plus(markerLength)
