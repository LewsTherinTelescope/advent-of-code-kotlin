package year2022.day05

import utils.runIt

fun main() = runIt(
    testInput = """
            [D]    
        [N] [C]    
        [Z] [M] [P]
        1   2   3 
        
        move 1 from 2 to 1
        move 3 from 1 to 3
        move 2 from 2 to 1
        move 1 from 1 to 2
    """.trimIndent(),
    ::part1,
    testAnswerPart1 = "CMZ",
    ::part2,
    testAnswerPart2 = "MCD"
)

fun part1(input: String) = input.parseMoveInstructions()
    .let { (crates, instructions) -> crates apply9000 instructions }
    .joinToString("", transform = List<Crate>::last)

fun part2(input: String) = input.parseMoveInstructions()
    .let { (crates, instructions) -> crates apply9001 instructions }
    .joinToString("", transform = List<Crate>::last)

@JvmInline
value class Crate(val label: String): CharSequence by label

data class MoveInstruction(
    val count: Int,
    val source: Int,
    val destination: Int
)

operator fun <T> List<T>.component6() = this[5]

fun String.parseMoveInstructions() = split("\n\n").let { (cratesStr, instructionsStr) ->
    // drop numbers row at end
    val crates = cratesStr.split("\n")
        .dropLast(1)
        .map { row -> row.chunked(4) }
        .rotated { Crate(it.trim().removeSurrounding("[", "]")) }
        .map { column ->
            column.toMutableList().apply {
                removeIf(CharSequence::isEmpty)
                reverse()
            }
        }
        .toMutableList()
    val instructions = instructionsStr.split("\n")
        .map { line ->
            line.split(" ").let { (_, count, _, source, _, destination) ->
                MoveInstruction(count.toInt(), source.toInt(), destination.toInt())
            }
        }
    crates to instructions
}

fun <T> List<List<T>>.rotated(): List<List<T>> = rotated { it }

inline fun <T, R> List<List<T>>.rotated(
    transform: (T) -> R
): List<List<R>> = fold(MutableList(first().size) { mutableListOf<R>() }) { acc, it ->
    it.forEachIndexed { index, value ->
        acc[index].add(transform(value))
    }
    acc
}

inline infix fun MutableList<MutableList<Crate>>.apply9000(instructions: List<MoveInstruction>) =
    applyPatches(instructions, preserveOrder = false)

inline infix fun MutableList<MutableList<Crate>>.apply9001(instructions: List<MoveInstruction>) =
    applyPatches(instructions, preserveOrder = true)

fun MutableList<MutableList<Crate>>.applyPatches(
    instructions: List<MoveInstruction>,
    preserveOrder: Boolean
) = apply {
    instructions.forEach { instruction ->
        val (count, source, destination) = instruction
        val src = this[source - 1]
        val dest = this[destination - 1]
        if (preserveOrder) {
            val toMove = src.takeLast(count)
            for (i in 0 until count) {
                src.removeLast()
            }
            dest.addAll(toMove)
        } else {
            for (i in 0 until count) {
                dest.add(src.removeLast())
            }
        }
    }
}
