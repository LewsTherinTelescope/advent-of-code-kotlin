package year2022.day10

import utils.runIt

fun main() = runIt(
	testInput = """
		addx 15
		addx -11
		addx 6
		addx -3
		addx 5
		addx -1
		addx -8
		addx 13
		addx 4
		noop
		addx -1
		addx 5
		addx -1
		addx 5
		addx -1
		addx 5
		addx -1
		addx 5
		addx -1
		addx -35
		addx 1
		addx 24
		addx -19
		addx 1
		addx 16
		addx -11
		noop
		noop
		addx 21
		addx -15
		noop
		noop
		addx -3
		addx 9
		addx 1
		addx -3
		addx 8
		addx 1
		addx 5
		noop
		noop
		noop
		noop
		noop
		addx -36
		noop
		addx 1
		addx 7
		noop
		noop
		noop
		addx 2
		addx 6
		noop
		noop
		noop
		noop
		noop
		addx 1
		noop
		noop
		addx 7
		addx 1
		noop
		addx -13
		addx 13
		addx 7
		noop
		addx 1
		addx -33
		noop
		noop
		noop
		addx 2
		noop
		noop
		noop
		addx 8
		noop
		addx -1
		addx 2
		addx 1
		noop
		addx 17
		addx -9
		addx 1
		addx 1
		addx -3
		addx 11
		noop
		noop
		addx 1
		noop
		addx 1
		noop
		noop
		addx -13
		addx -19
		addx 1
		addx 3
		addx 26
		addx -30
		addx 12
		addx -1
		addx 3
		addx 1
		noop
		noop
		noop
		addx -9
		addx 18
		addx 1
		addx 2
		noop
		noop
		addx 9
		noop
		noop
		noop
		addx -1
		addx 2
		addx -37
		addx 1
		addx 3
		noop
		addx 15
		addx -21
		addx 22
		addx -6
		addx 1
		noop
		addx 2
		addx 1
		noop
		addx -10
		noop
		noop
		addx 20
		addx 1
		addx 2
		addx 2
		addx -6
		addx -11
		noop
		noop
		noop
	""".trimIndent(),
	::part1B,
	testAnswerPart1 = 13140,
	::part2B,
	testAnswerPart2 = "\n" + """
		██░░██░░██░░██░░██░░██░░██░░██░░██░░██░░
		███░░░███░░░███░░░███░░░███░░░███░░░███░
		████░░░░████░░░░████░░░░████░░░░████░░░░
		█████░░░░░█████░░░░░█████░░░░░█████░░░░░
		██████░░░░░░██████░░░░░░██████░░░░░░████
		███████░░░░░░░███████░░░░░░░███████░░░░░
	""".trimIndent(),
)

// I've done two implementations for today's puzzle:
// - part#A functions take a loop-based imperative approach
// - part#B functions take a scan-based functional approach

sealed class Instruction(
	val ticks: Int,
) {
	object NoOp : Instruction(1)
	class Add(val value: Int) : Instruction(2)
	
	companion object {
		fun fromA(line: String): Instruction {
			val parts = line.split(" ")
			return when (val command = parts[0]) {
				"noop" -> NoOp
				"addx" -> Add(parts[1].toInt())
				else -> throw IllegalArgumentException("Unknown instruction: $command")
			}
		}
		
		fun fromB(line: String): List<Instruction> {
			val parts = line.split(" ")
			return when (val command = parts[0]) {
				"noop" -> listOf(NoOp)
				"addx" -> listOf(NoOp, Add(parts[1].toInt()))
				else -> throw IllegalArgumentException("Unknown instruction: $command")
			}
		}
	}
}

fun part1A(input: String): Int {
	val significantStrengths = mutableSetOf<Int>()
	parseAndExecute(input) { cycle, register ->
		// every 40, starting at 20
		if ((cycle + 20) % 40 == 0) significantStrengths += register * cycle
	}
	return significantStrengths.sum()
}

fun part2A(input: String): String = buildString {
	parseAndExecute(input) { cycle, register ->
		// cycles are 1-indexed, but columns are 0-indexed
		val column = (cycle - 1) % 40
		// sprite is 3 wide
		val shouldDrawSprite = column in ((register - 1)..(register + 1))
		append(if (shouldDrawSprite) "█" else "░")
		if (cycle % 40 == 0) appendLine()
	}
}.trimEnd() // remove trailing newline

inline fun parseAndExecute(
	input: String,
	onTick: (cycle: Int, register: Int) -> Unit = { _, _ -> },
) = input.lines()
	.map(Instruction::fromA)
	.let { instructions -> execute(instructions, onTick) }

inline fun execute(
	instructions: List<Instruction>,
	onTick: (cycle: Int, register: Int) -> Unit = { _, _ -> },
) {
	var register = 1
	var tick = 1
	instructions.forEach {
		repeat(it.ticks) {
			onTick(tick, register)
			tick++
		}
		when (it) {
			is Instruction.NoOp -> {}
			is Instruction.Add -> register += it.value
		}
	}
}

fun part1B(input: String) = input.lines()
	.flatMap(Instruction::fromB)
	.execute()
	.withIndex()
	.drop(19)
	.chunked(40)
	.map(List<IndexedValue<Int>>::first)
	.sumOf { (index, value) -> (index + 1) * value }

fun part2B(input: String) = "\n" + input.lines()
	.flatMap(Instruction::fromB)
	.execute()
	.mapIndexed { tick, register ->
		val column = tick % 40
		// sprite is 3 wide
		val shouldDrawSprite = column in ((register - 1)..(register + 1))
		if (shouldDrawSprite) "█" else "░"
	}
	.chunked(40)
	.joinToString("\n") { it.joinToString("") }

fun List<Instruction>.execute() = this
	.scan(1) { register, instruction ->
		when (instruction) {
			is Instruction.NoOp -> register
			is Instruction.Add -> register + instruction.value
		}
	}
	// result of the last computation will never be accessed as it's set at the END of the last tick
	.dropLast(1)
