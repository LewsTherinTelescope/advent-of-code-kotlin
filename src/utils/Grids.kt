@file:Suppress("NOTHING_TO_INLINE")

package utils

import java.util.LinkedList
import kotlin.math.absoluteValue
import kotlin.math.hypot

/**
 * Uses good ol' a² + b² = c² to find the distance between points.
 */
public inline fun distanceHypotenuse(a: Vec2, b: Vec2) =
	hypot((a.x - b.x).toDouble(), (a.y - b.y).toDouble())

/**
 * Adds together the horizontal and vertical distances, like city blocks.
 */
public inline fun distanceManhattan(a: Vec2, b: Vec2) =
	(a.x - b.x).absoluteValue + (a.y - b.y).absoluteValue

/**
 * Find the largest of the two distances (vertical and horizontal).
 */
public inline fun distanceChessboard(a: Vec2, b: Vec2) =
	maxOf((a.x - b.x).absoluteValue, (a.y - b.y).absoluteValue)

public data class Vec2(val x: Int, val y: Int) {
	operator fun plus(other: Vec2) = Vec2(x + other.x, y + other.y)
}

public val Vec2.neighbors get() = Direction.values().map { this + it.changeVector }
public infix fun Vec2.inIndices(grid: Grid<*>) = y in grid.y_indices && x in grid.x_indices

/**
 * Note that these assume y=0 as the TOPMOST row and x=0 as the LEFTMOST column.
 */
public enum class Direction(val changeVector: Vec2) {
	UP(Vec2(0, -1)),
	DOWN(Vec2(0, 1)),
	LEFT(Vec2(-1, 0)),
	RIGHT(Vec2(1, 0));
	
	public operator fun component1() = changeVector
}

public interface Grid<T> {
	public val x_indices: IntRange
	public val y_indices: IntRange
	
	public operator fun get(row: Int, column: Int): T
	public operator fun get(vec: Vec2): T = get(vec.y, vec.x)
}

interface MutableGrid<T> : Grid<T> {
	public operator fun set(row: Int, column: Int, value: T): T
	public operator fun set(vec: Vec2, value: T): T = set(vec.y, vec.x, value)
}

@JvmInline value class WrappingGrid<T>(
	public val lists: List<List<T>>,
) : Grid<T> {
	override val x_indices: IntRange get() = lists[0].indices
	override val y_indices: IntRange get() = lists.indices
	
	override operator fun get(row: Int, column: Int): T = lists[row][column]
}

@JvmInline value class WrappingMutableGrid<T>(
	public val lists: List<MutableList<T>>,
) : MutableGrid<T> {
	override val x_indices: IntRange get() = lists[0].indices
	override val y_indices: IntRange get() = lists.indices
	
	override operator fun get(row: Int, column: Int): T = lists[row][column]
	override operator fun set(row: Int, column: Int, value: T): T = lists[row].set(column, value)
}

public fun <T> Grid<T>.positionOfOrNull(value: T): Vec2? {
	for (row in y_indices) {
		for (col in x_indices) {
			if (this[row, col] == value) return Vec2(col, row)
		}
	}
	return null
}

public fun <T> Grid<T>.positionsOf(value: T): List<Vec2> {
	val positions = mutableListOf<Vec2>()
	for (row in y_indices) {
		for (col in x_indices) {
			if (this[row, col] == value) positions += Vec2(col, row)
		}
	}
	return positions
}

public fun <T> List<List<T>>.toGrid(): Grid<T> = WrappingGrid(this)
public fun <T> List<MutableList<T>>.toMutableGrid(): MutableGrid<T> = WrappingMutableGrid(this)

private data class PathSegment(
	val position: Vec2,
	val depth: Int,
)

public fun <T> Grid<T>.findPathLengthOrNull(
	start: Vec2,
	end: Vec2,
	validateMoveValues: (current: T, previous: T) -> Boolean = { _, _ -> true },
): Int? {
	val visited = hashSetOf(start)
	val queue = LinkedList<PathSegment>()
	queue += PathSegment(start, 0)
	
	while (queue.isNotEmpty()) {
		val (current, depth) = queue.removeFirst()
		if (current == end) return depth
		
		queue += current.neighbors
			.filter { neighbor ->
				neighbor !in visited &&
					neighbor inIndices this &&
					validateMoveValues(this[neighbor], this[current])
			}
			.map { neighbor ->
				visited += neighbor
				PathSegment(
					position = neighbor,
					depth = depth + 1,
				)
			}
	}
	
	return null
}
