package year2022.day12

import utils.findPathLengthOrNull
import utils.positionOfOrNull
import utils.positionsOf
import utils.runIt
import utils.toMutableGrid

fun main() = runIt(
	testInput = """
		Sabqponm
		abcryxxl
		accszExk
		acctuvwj
		abdefghi
	""".trimIndent(),
	::part1,
	testAnswerPart1 = 31,
	::part2,
	testAnswerPart2 = 29,
)

fun part1(input: String): Int = findPathThrough(input)

fun part2(input: String): Int = findPathThrough(input, tryAllStarts = true)

fun String.toMutableCharGrid() = lines().map(String::toMutableList).toMutableGrid()

fun findPathThrough(input: String, tryAllStarts: Boolean = false): Int {
	val grid = input.toMutableCharGrid()
	
	// avoid the need for special cases by just replacing the start/end with lowest/highest chars
	val endPosition = grid.positionOfOrNull('E') ?: error("no end found")
	grid[endPosition] = 'z'
	
	val startPosition = grid.positionOfOrNull('S') ?: error("no start found")
	grid[startPosition] = 'a'
	
	val bottomPositions = if (tryAllStarts) grid.positionsOf('a') else listOf(startPosition)
	
	return bottomPositions
		.mapNotNull { grid.findPathLengthOrNull(it, endPosition) { cur, prev -> cur - prev <= 1 } }
		.minOrNull()
		?: error("no path found")
}
