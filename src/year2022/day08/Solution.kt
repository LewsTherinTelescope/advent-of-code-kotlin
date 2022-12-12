package year2022.day08

import utils.runIt

fun main() = runIt(
	testInput = """
		30373
		25512
		65332
		33549
		35390
	""".trimIndent(),
	::part1,
	testAnswerPart1 = 21,
	::part2,
	testAnswerPart2 = 8,
)

fun part1(input: String): Int {
	val treeHeights = input.toSingleDigitIntGrid()
	val rowCount = treeHeights.size
	val columnCount = treeHeights[0].size
	return treeHeights.indices.sumOf { r ->
		if (r == 0 || r == rowCount - 1) columnCount
		else treeHeights[r].indices.count { c ->
			c == 0 || c == columnCount - 1 || run {
				val height = treeHeights[r][c]				
				fun visibleLeft() = (c - 1 downTo 0).none { treeHeights[r][it] >= height } 
				fun visibleRight() = (c + 1 until columnCount).none { treeHeights[r][it] >= height }
				fun visibleTop() = (r - 1 downTo 0).none { treeHeights[it][c] >= height }
				fun visibleBottom() = (r + 1 until rowCount).none { treeHeights[it][c] >= height }
				visibleLeft() || visibleRight() || visibleTop() || visibleBottom()
			}
		}
	}
}

fun part2(input: String): Int {
	val treeHeights = input.toSingleDigitIntGrid()
	val rowCount = treeHeights.size
	val columnCount = treeHeights[0].size
	return treeHeights.indices.maxOf { r ->
		treeHeights[r].indices.maxOf { c ->
			val height = treeHeights[r][c]
			val sceneryLeft = (c - 1 downTo 0).countUntil { treeHeights[r][it] >= height } 
			val sceneryRight = (c + 1 until columnCount).countUntil { treeHeights[r][it] >= height }
			val sceneryTop = (r - 1 downTo 0).countUntil { treeHeights[it][c] >= height }
			val sceneryBottom = (r + 1 until rowCount).countUntil { treeHeights[it][c] >= height }
			sceneryLeft * sceneryRight * sceneryTop * sceneryBottom
		}
	}
}

fun String.toSingleDigitIntGrid() = lines()
	.map { line -> IntArray(line.length) { line[it].digitToInt() } }

inline fun <T> Iterable<T>.countUntil(predicate: (T) -> Boolean): Int {
	var count = 0
	for (element in this) {
		count++
		if (predicate(element)) break
	}
	return count
}
