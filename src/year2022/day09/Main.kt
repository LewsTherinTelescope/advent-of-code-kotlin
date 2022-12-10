package year2022.day09

import utils.runIt
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() = runIt(
	testInput = """
		R 4
		U 4
		L 3
		D 1
		R 4
		D 1
		L 5
		R 2
	""".trimIndent(),
	part1 = ::part1,
	testAnswerPart1 = 13,
	testInputPart2 = """
		R 5
		U 8
		L 8
		D 3
		R 17
		D 10
		L 25
		U 20
	""".trimIndent(),
	part2 = ::part2,
	testAnswerPart2 = 36,
)

fun part1(input: String): Int = input.lines()
	.map(Move::from)
	.let(Rope(2)::moveTrackingTail)
	.size

fun part2(input: String): Int = input.lines()
	.map(Move::from)
	.let(Rope(10)::moveTrackingTail)
	.size

data class Move(
	val direction: Char,
	val amount: Int,
) {
	companion object {
		fun from(stringForm: String) = Move(
			direction = stringForm[0],
			amount = stringForm.substringAfter(" ").toInt(),
		)
	}
}

data class Rope(val length: Int) {
	fun moveTrackingTail(moves: List<Move>): Set<Point> = hashSetOf<Point>().apply {
		val knots = Array(length) { IntArray(2) }
		val lastKnot = length - 1
		
		moves.forEach { (direction, amount) ->
			repeat(amount) {
				when (direction) {
					'R' -> knots[0].x++
					'L' -> knots[0].x--
					'U' -> knots[0].y++
					'D' -> knots[0].y--
				}
				
				(1 until length).forEach { i ->
					val prev = i - 1
					if (chessDistance(knots[prev], knots[i]) > 1) {
						knots[i].x += (knots[prev].x compareTo knots[i].x).sign
						knots[i].y += (knots[prev].y compareTo knots[i].y).sign
					}
				}
				add(knots[lastKnot].toPoint())
			}
		}
	}
}

data class Point(val x: Int, val y: Int)

fun IntArray.toPoint() = Point(x, y)

inline var IntArray.x
	get() = get(0)
	set(value) { set(0, value) }

inline var IntArray.y
	get() = get(1)
	set(value) { set(1, value) }

fun chessDistance(first: IntArray, second: IntArray) =
	maxOf((second.x - first.x).absoluteValue, (second.y - first.y).absoluteValue)
