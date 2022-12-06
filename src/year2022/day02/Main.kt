package year2022.day02

import utils.runIt

fun main() = runIt(
    year = "2022",
    day = "02",
    testInput = """
        A Y
        B X
        C Z
    """.trimIndent(),
    ::part1,
    testAnswerPart1 = 15,
    ::part2,
    testAnswerPart2 = 12,
)

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
