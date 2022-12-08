package year2022.day04

import utils.runIt

fun main() = runIt(
    testInput = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent(),
    ::part1,
    testAnswerPart1 = 2,
    ::part2,
    testAnswerPart2 = 4,
)

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
