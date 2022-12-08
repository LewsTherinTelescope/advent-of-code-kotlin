package year2022.day01

import utils.runIt

fun main() = runIt(
    testInput = """
        1000
        2000
        3000
        
        4000
        
        5000
        6000
        
        7000
        8000
        9000
        
        10000
    """.trimIndent(),
    ::part1,
    testAnswerPart1 = 24000,
    ::part2,
    testAnswerPart2 = 45000
)

fun parseInput(input: String) = input.split("\n\n")
    .map { it.split("\n").map(String::toInt) }

fun sumInput(input: String) = parseInput(input)
    .map(Iterable<Int>::sum)
    .sortedDescending()

fun part1(input: String) = sumInput(input).first()

fun part2(input: String) = sumInput(input).take(3).sum()
