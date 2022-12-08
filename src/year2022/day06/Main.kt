package year2022.day06

import utils.runIt

fun main() = runIt(
	testInput = """
		mjqjpqmgbljsphdztnvjfqwrcgsmlb
	""".trimIndent(),
	::part1,
	testAnswerPart1 = 7,
	::part2,
	testAnswerPart2 = 19,
)

fun part1(input: String) = input.findMarkerEndPos(markerSize = 4)

fun part2(input: String) = input.findMarkerEndPos(markerSize = 14)

fun CharSequence.findMarkerEndPos(markerSize: Int) = 
	windowedSequence(markerSize).indexOfFirst { it.toHashSet().size == it.length } + markerSize
